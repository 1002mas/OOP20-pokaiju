package model.monster;

public class MonsterStatsImpl implements MonsterStats {

    private int health;
    private int attack;
    private int def;
    private int speed;

    public MonsterStatsImpl(int health, int attack, int def, int speed) {
	this.health = health;
	this.attack = attack;
	this.def = def;
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
	return def;
    }

    public void setDefense(int def) {
	this.def = def;
    }

    public int getSpeed() {
	return speed;
    }

    public void setSpeed(int speed) {
	this.speed = speed;
    }
}
