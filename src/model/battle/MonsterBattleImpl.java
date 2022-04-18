package model.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.gameitem.GameItem;
import model.monster.Monster;
import model.monster.MonsterType;
import model.npc.NpcTrainer;
import model.player.Player;

public class MonsterBattleImpl implements MonsterBattle {
    private final static int EXP_MULTIPLER = 100;
    private final static int ESCAPE_RANGE = 10;
    private final static int ESCAPE_DIFFICULT = 5;
    private final static int CAPTURE_RANGE = 10;
    private final static int CAPTURE_DIFFICULT = 3;
    private final static int MONEY_WON = 70;
    private final static int MONEY_LOST = 50;

    private boolean battleStatus; // true if the battle enemy/player team is defeat, false otherwise
    private boolean areEndPP;
    private boolean playerLose;
    private Monster playerCurrentMonster;
    private Monster enemy;
    private List<Monster> playerTeam;
    private List<Monster> enemyTeam;
    private Player trainer;

    private Optional<NpcTrainer> enemyTrainer;

    private Moves extraMoves;

    private MonsterBattleImpl(Player trainer, List<Monster> enemyTeam) {
	this.trainer = trainer;
	this.battleStatus = true;
	this.playerLose = false;
	this.enemyTrainer = Optional.empty();
	this.playerTeam = trainer.getAllMonsters();
	this.playerCurrentMonster = playerTeam.get(0);
	this.enemyTeam = new ArrayList<>(enemyTeam);
	this.enemy = enemyTeam.get(0);
	this.extraMoves = new MovesImpl("Testata", 30, MonsterType.NONE, 999);
	this.areEndPP = false;
    }

    public MonsterBattleImpl(Player trainer, NpcTrainer enemyTrainer) {

	this(trainer, enemyTrainer.getMonstersOwned());
	this.enemyTrainer = Optional.of(enemyTrainer);

    }

    public MonsterBattleImpl(Player trainer, Monster wildMonster) {
	this(trainer, List.of(wildMonster));
    }

    @Override
    public Moves enemyAttack() {
	int x = (int) (Math.random() * this.enemy.getNumberOfMoves());
	while (enemy.isOutOfPP(enemy.getMoves(x))) {
	    x = (x + 1) % this.enemy.getNumberOfMoves();
	}
	return enemy.getMoves(x);
    }

    @Override
    public boolean capture() {
	throwExceptionIfItIsOver();
	if (!enemy.getWild()) {
	    return false;
	}

	int attempt = (int) (Math.random() * CAPTURE_RANGE);
	if (attempt <= CAPTURE_DIFFICULT) {
	    this.trainer.addMonster(enemy);
	    int expReached = enemy.getLevel() * EXP_MULTIPLER;
	    this.playerCurrentMonster.incExp(expReached);
	    this.battleStatus = false;
	    return true;
	}
	return false;

    }

    @Override
    public boolean escape() {
	throwExceptionIfItIsOver();
	if (!enemy.getWild()) {

	    return false;
	}
	int attempt = (int) (Math.random() * ESCAPE_RANGE);
	if (attempt <= ESCAPE_DIFFICULT) {

	    this.battleStatus = false;
	    return true;
	}

	return false;

    }

    @Override
    public boolean playerChangeMonster(int index) {
	throwExceptionIfItIsOver();
	Monster changingMonster = null;
	if (index == playerCurrentMonster.getId()) {
	    return false;
	}
	for (var monster : playerTeam) {
	    if (monster.getId() == index) {
		changingMonster = monster;
	    }
	}
	if (changingMonster.isAlive()) {
	    this.playerCurrentMonster = changingMonster;

	    return true;
	}

	return false;

    }

    @Override
    public boolean movesSelection(int moveIndex) {

	if (!this.playerCurrentMonster.isOutOfPP(playerCurrentMonster.getMoves(moveIndex)) && this.battleStatus
		&& this.playerCurrentMonster.isAlive()) {

	    turn(this.playerCurrentMonster.getMoves(moveIndex));

	    return true;
	}
	throwExceptionIfItIsOver();
	return false;
    }

    private void turn(Moves monsterMove) {
	if (this.playerCurrentMonster.getStats().getSpeed() > this.enemy.getStats().getSpeed()) {
	    monsterAttack(playerCurrentMonster, enemy, monsterMove);
	    if (enemy.isAlive()) {
		monsterAttack(enemy, playerCurrentMonster, enemyAttack());
	    }
	} else {
	    monsterAttack(enemy, playerCurrentMonster, enemyAttack());
	    if (playerCurrentMonster.isAlive()) {
		monsterAttack(playerCurrentMonster, enemy, monsterMove);
	    }
	}
	if (!enemy.isAlive()) {
	    playerCurrentMonster.incExp(enemy.getLevel() * EXP_MULTIPLER);
	}
	if (allPlayerMonsterDeafeted()) { // player's team defeated
	    this.battleStatus = false;
	    this.playerLose = true;
	    this.trainer.setMoney(trainer.getMoney() - MONEY_LOST);
	    restoreAllMonsters();
	    this.trainer.evolveMonsters();
	}

	if (!areThereEnemies()) {
	    // ending battle

	    this.trainer.setMoney(trainer.getMoney() + MONEY_WON);
	    if (this.enemyTrainer.isPresent()) {
		enemyTrainer.get().setDefeated(true);
	    }
	    this.battleStatus = false;
	    this.trainer.evolveMonsters();
	} else {
	    this.enemy = enemyTeam.stream().filter(m -> m.isAlive()).findAny().get(); // change enemy's
										      // monster
	}

    }

    private void monsterAttack(Monster m1, Monster m2, Moves move) {

	int damage = move.getDamage(m2.getType()) * move.getBase() + m1.getStats().getAttack()
		- m2.getStats().getDefense();
	if (damage < 1) {
	    damage = 1;
	}
	if (move != extraMoves) {
	    m1.decMovePP(move);
	}

	m2.setHealth(m2.getStats().getHealth() - damage);
    }

    private boolean areThereEnemies() {

	return enemyTeam.stream().filter(m -> m.isAlive()).count() > 0;
    }

    private void restoreAllMonsters() {
	this.playerTeam.stream().forEach(m -> {
	    m.restoreAllMovesPP();
	    m.restoreStats();
	});
    }

    @Override
    public boolean isCurrentMonsterAlive() {
	return this.playerCurrentMonster.isAlive();
    }

    private boolean allPlayerMonsterDeafeted() {
	return this.playerTeam.stream().filter(m -> m.isAlive()).count() == 0;

    }

    @Override
    public boolean isOver() {

	return !battleStatus;
    }

    private void throwExceptionIfItIsOver() {
	if (!this.battleStatus) {
	    throw new IllegalStateException();
	}
    }

    @Override
    public boolean useItem(GameItem item) {
	return item.use(playerCurrentMonster);
    }

    @Override
    public Monster getCurrentPlayerMonster() {

	return this.playerCurrentMonster;
    }

    @Override
    public Monster getCurrentEnemyMonster() {

	return this.enemy;
    }

    @Override
    public Player getPlayer() {
	return this.trainer;
    }

    @Override
    public Optional<NpcTrainer> getNpcEnemy() {
	return this.enemyTrainer;
    }

    @Override
    public boolean isOverOfPP() {
	this.areEndPP = true;
	for (int c = 0; c < this.playerCurrentMonster.getNumberOfMoves(); c++) {
	    if (!this.playerCurrentMonster.isOutOfPP(playerCurrentMonster.getMoves(c))) {
		this.areEndPP = false;
	    }
	}
	return this.areEndPP;
    }

    @Override
    public boolean attackWithExtraMove() {

	if (this.battleStatus && this.playerCurrentMonster.isAlive()) {

	    this.turn(extraMoves);

	    return true;
	}
	throwExceptionIfItIsOver();
	return false;

    }
}
