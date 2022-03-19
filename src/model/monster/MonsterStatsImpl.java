package model.monster;

public class MonsterStatsImpl implements MonsterStats{
	
	private int health;
	private int attack;
	private int defense;
	private int speed;
	
	public MonsterStatsImpl(int health, int attack, int defense, int speed) {
		this.health = health;
		this.attack = attack;
		this.defense = defense;
		this.speed = speed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	public int getDefense() {
		return defense;
	}

	public void setDefense(int def) {
		this.defense = def;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	
	
	
}
