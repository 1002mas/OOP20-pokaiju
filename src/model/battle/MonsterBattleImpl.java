package model.battle;

import java.util.ArrayList;
import java.util.List;

import model.monster.Monster;
import model.npc.NpcImpl;
import model.player.PlayerImpl;

public class MonsterBattleImpl implements MonsterBattle {
	private final static int EXP_MULTIPLER = 5;
	private final static int ESCAPE_RANGE = 10;
	private final static int ESCAPE_DIFFICULT = 5;
	private final static int CAPTURE_RANGE = 10;
	private final static int CAPTURE_DIFFICULT = 3;
	private final static int NUMBER_OF_ATTACKS = 4;
	private final static int MONEY_ADD = 70;
	private final static int MONEY_LOST = 50;

	private boolean battleStatus; //
	private boolean isTeamDeath;
	private Monster playerCurrentMonster;
	private Monster enemy;
	private List<Monster> playerTeam;
	private List<Monster> enemyTeam;
	private PlayerImpl trainer;
	private NpcImpl enemyTrainer;
	private int enemyMonsterCounter;

	private MonsterBattleImpl(PlayerImpl trainer, List<Monster> enemyTeam) {
		this.trainer = trainer;
		this.battleStatus = true;
		this.isTeamDeath = true;
		this.playerTeam = trainer.allMonster();
		this.playerCurrentMonster = playerTeam.get(0);
		this.enemyTeam = new ArrayList<>(enemyTeam);
		this.enemy = enemyTeam.get(0);
	}

	public MonsterBattleImpl(PlayerImpl trainer, NpcImpl enemyTrainer) {
		this(trainer, enemyTrainer.getMonstersOwned());SS
		this.enemyTrainer = enemyTrainer;
		this.enemyMonsterCounter = 1;

	}

	public MonsterBattleImpl(PlayerImpl trainer, Monster wildMonster) {
		this(trainer, List.of(wildMonster));
	}

	@Override
	public Moves enemyAttack() {
		int x = (int) (Math.random() * NUMBER_OF_ATTACKS);
		while (!enemy.getAttack(x).checkPP()) {
			x = (x + 1) % NUMBER_OF_ATTACKS;
		}
		;
		// TODO sostituire number of attacks con enemy.getNumberOfAttacks
		return enemy.getAttack(x);
	}

	@Override
	public boolean capture() {

		if (!enemy.getWild()) {
			return false;
		}

		int attempt = (int) (Math.random() * CAPTURE_RANGE);
		if (attempt <= CAPTURE_DIFFICULT) {
			// System.out.println(enemy.getName() + " è stato catturato");
			int expReached = enemy.getLevel() * EXP_MULTIPLER;
			playerCurrentMonster.incExp(expReached);
			this.battleStatus = false;
			return true;
		}
		// System.out.println("cattura fallita");
		return false;

	}

	@Override
	public void debug_print() {
		// TODO Auto-generated method stub
		System.out.println("------------------------");
		System.out.println(playerCurrentMonster.getName() + "->" + playerCurrentMonster.getHealth());
		System.out.println(enemy.getName() + "->" + enemy.getHealth());
		System.out.println("------------------------");
	}

	@Override
	public boolean escape() {
		if (!enemy.getWild()) {
			// System.out.println("Non puoi scappare");
			return false;
		}
		int attempt = (int) (Math.random() * ESCAPE_RANGE);
		if (attempt <= ESCAPE_DIFFICULT) {
			// System.out.println("Sei fuggito");
			this.battleStatus = false;
			return true;
		}
		// System.out.println("Fuga fallita");
		return false;

	}

	@Override
	public void end() {
		// TODO Auto-generated method stub

	}

	@Override
	public void lostMoney() {

		trainer.setMoney(trainer.getMoney() - MONEY_LOST);
	}

	@Override
	public void winMoney() {

		trainer.setMoney(trainer.getMoney() + MONEY_ADD);
	}

	@Override
	public boolean playerChangeMonster(int index) {
		// TODO Auto-generated method stub
		if (playerTeam.get(index) == playerCurrentMonster) {
			System.out.println("Il mostro è già in campo");
			return false;
		}
		if (playerTeam.get(index).isAlive()) {
			playerCurrentMonster = playerTeam.get(index);
			System.out.println("Cambio");
			return true;
		}
		System.out.println("Il mostro selezionato è morto");
		return false;

	}

	@Override
	public boolean enemyChangeMonster(int index) {
		if (enemyTeam.get(index) == enemy) {
			System.out.println("Il mostro è già in campo");
			return false;
		}
		if (enemyTeam.get(index).isAlive()) {
			enemy = enemyTeam.get(index);
			System.out.println("Cambio");
			return true;
		} else {
			System.out.println("Il mostro selezionato è morto");
			return false;
		}
	}

	@Override
	public boolean movesSelection(int moveIndex) {
		if (this.playerCurrentMonster.getAttack(moveIndex).checkPP() && this.battleStatus
				&& this.playerCurrentMonster.isAlive()) {
			this.playerCurrentMonster.getAttack(moveIndex).decPP();
			this.turn(this.playerCurrentMonster.getAttack(moveIndex));
			return true;
		}
		// System.out.println(playerCurrentMonster.getName() + " è troppo stanco per
		// usare questa mossa");
		return false;
	}

	private void turn(Moves monsterMove) {

		enemy.setHealth(enemy.getHealth() - monsterMove.getDamage(enemy.getType()));
		// System.out.println(playerCurrentMonster.getName() + " usa " + att.getName() +
		// " infliggendo "
		// + att.getDamage(enemy.getType()) + " danni");

		if (enemy.isAlive()) {

			this.enemyTurn();
			if (allPlayerMonsterDeafeted()) { // player's team defeated
				this.battleStatus = false;
				lostMoney();
			}
		} else {

			playerCurrentMonster.incExp(enemy.getLevel() * EXP_MULTIPLER);
			// System.out.println(enemy.getName() + " è morto "); //enemy's team defeated
			if (!areThereEnemies()) {
				// ending battle
				winMoney();
				this.battleStatus = false;
			} else {
				this.enemy = enemyTeam.stream().filter(m -> m.isAlive()).findAny().get();
			}

		}
		this.debug_print();

	}

	private void enemyTurn() {
		var att = this.enemyAttack();
		att.decPP();
		playerCurrentMonster
				.setHealth(playerCurrentMonster.getHealth() - att.getDamage(playerCurrentMonster.getType()));
		System.out.println(enemy.getName() + " usa " + att.getName() + " infliggendo "
				+ att.getDamage(playerCurrentMonster.getType()) + " danni");
	}

	private boolean areThereEnemies() {

		return enemyTeam.stream().filter(m -> m.isAlive()).count() > 0;
	}

	@Override
	public boolean isCurrentMonsterAlive() {
		return this.playerCurrentMonster.isAlive();
	}

	private boolean allPlayerMonsterDeafeted() {
		return this.playerTeam.stream().filter(m -> m.isAlive()).count() == 0;

	}
}
