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
    private static final int EXP_MULTIPLER = 1000; //100
    private static final int CAPTURE_RANGE = 10;
    private static final int CAPTURE_DIFFICULT = 3;
    private static final int MONEY_WON = 70;
    private static final int MONEY_LOST = 50;
    private static final int EXTRA_MOVE_ATTACK = 30;
    private static final int EXTRA_MOVE_PP = 999;

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

    private MonsterBattleImpl(final Player trainer, final List<Monster> enemyTeam) {
        this.trainer = trainer;
        this.battleStatus = true;
        this.playerLose = false;
        this.enemyTrainer = Optional.empty();
        this.playerTeam = trainer.getAllMonsters();
        this.playerCurrentMonster = chooseStartingMonster();
        this.enemyTeam = new ArrayList<>(enemyTeam);
        this.enemy = enemyTeam.get(0);
        this.extraMoves = new MovesImpl("Testata", EXTRA_MOVE_ATTACK, MonsterType.NONE, EXTRA_MOVE_PP);
        this.areEndPP = false;
    }

    public MonsterBattleImpl(final Player trainer, final NpcTrainer enemyTrainer) {

        this(trainer, enemyTrainer.getMonstersOwned());
        this.enemyTrainer = Optional.of(enemyTrainer);

    }

    public MonsterBattleImpl(final Player trainer, final Monster wildMonster) {
        this(trainer, List.of(wildMonster));
    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public Moves enemyAttack() {
        int x = (int) (Math.random() * this.enemy.getNumberOfMoves());
        while (enemy.isOutOfPP(enemy.getMoves(x))) {
            x = (x + 1) % this.enemy.getNumberOfMoves();
        }
        return enemy.getMoves(x);
    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public boolean capture() {
        throwExceptionIfItIsOver();
        if (!enemy.isWild()) {
            return false;
        }

        final int attempt = (int) (Math.random() * CAPTURE_RANGE);
        if (attempt <= CAPTURE_DIFFICULT) {
            this.trainer.addMonster(enemy);
            this.battleStatus = false;
            return true;
        }
        return false;

    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public boolean escape() {
        throwExceptionIfItIsOver();
        if (!enemy.isWild()) {

            return false;
        } else {
            this.battleStatus = false;
            return true;
        }

    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public boolean playerChangeMonster(final int index) {
        throwExceptionIfItIsOver();
        Monster changingMonster = null;
        if (index == playerCurrentMonster.getId()) {
            return false;
        } else {
            for (final var monster : playerTeam) {
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

    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public boolean movesSelection(final int moveIndex) {

        if (!this.playerCurrentMonster.isOutOfPP(playerCurrentMonster.getMoves(moveIndex)) && this.battleStatus
                && this.playerCurrentMonster.isAlive()) {

            turn(this.playerCurrentMonster.getMoves(moveIndex));

            return true;
        }
        throwExceptionIfItIsOver();
        return false;
    }

    private void turn(final Moves monsterMove) {
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

        }

        if (!areThereEnemies()) {
            // ending battle

            if (this.enemyTrainer.isPresent()) {
                enemyTrainer.get().setDefeated(true);
            }
            this.battleStatus = false;
        } else {
            this.enemy = enemyTeam.stream().filter(m -> m.isAlive()).findAny().get(); // change enemy's
                                                                                      // monster
        }

    }

    private void monsterAttack(final Monster m1, final Monster m2, final Moves move) {

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

    private void restoreAllMonsters(final List<Monster> monster) {
        monster.stream().forEach(m -> {
            m.restoreAllMovesPP();
            m.restoreStats();
        });
    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public boolean isCurrentMonsterAlive() {
        return this.playerCurrentMonster.isAlive();
    }

    private boolean allPlayerMonsterDeafeted() {
        return this.playerTeam.stream().filter(m -> m.isAlive()).count() == 0;

    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public boolean isOver() {

        return !battleStatus;
    }

    private void throwExceptionIfItIsOver() {
        if (!this.battleStatus) {
            throw new IllegalStateException();
        }
    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public boolean useItem(final GameItem item) {
        return item.use(playerCurrentMonster);
    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public Monster getCurrentPlayerMonster() {

        return this.playerCurrentMonster;
    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public Monster getCurrentEnemyMonster() {

        return this.enemy;
    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public Player getPlayer() {
        return this.trainer;
    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public Optional<NpcTrainer> getNpcEnemy() {
        return this.enemyTrainer;
    }

    /***
     * {@inheritDoc}.
     */
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

    /***
     * {@inheritDoc}.
     */
    @Override
    public boolean attackWithExtraMove() {

        if (this.battleStatus && this.playerCurrentMonster.isAlive()) {

            this.turn(extraMoves);

            return true;
        }
        throwExceptionIfItIsOver();
        return false;

    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public boolean hasPlayerLost() {
        return this.playerLose;
    }

    /***
     * {@inheritDoc}.
     */
    @Override
    public void endingBattle() {
        if (hasPlayerLost()) {
            this.trainer.setMoney(trainer.getMoney() - MONEY_LOST);
            restoreAllMonsters(this.playerTeam);
            restoreAllMonsters(this.enemyTeam);
        } else {
            this.trainer.setMoney(trainer.getMoney() + MONEY_WON);
        }

        this.trainer.evolveMonsters();
    }

    private Monster chooseStartingMonster() {
        for (final var monster : this.playerTeam) {
            if (monster.isAlive()) {
                return monster;
            }
        }
        return null;
    }
}
