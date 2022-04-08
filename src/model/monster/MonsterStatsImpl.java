package model.monster;

import java.util.HashMap;
import java.util.Map;

public class MonsterStatsImpl implements MonsterStats {

    private static final String HEALTH = "HEALTH";
    private static final String ATTACK = "ATTACK";
    private static final String DEFENSE = "DEFENSE";
    private static final String SPEED = "SPEED";
    
    private final Map<String, Integer> statsMap = new HashMap<>();

    public MonsterStatsImpl(int health, int attack, int def, int speed) {
	this.statsMap.put(HEALTH, health);
	this.statsMap.put(ATTACK, attack);
	this.statsMap.put(DEFENSE, def);
	this.statsMap.put(SPEED, speed);
    }

    @Override
    public int getHealth() {
	return statsMap.get(HEALTH);
    }

    @Override
    public void setHealth(int health) {
	statsMap.put(HEALTH, health);
    }

    @Override
    public int getAttack() {
	return statsMap.get(ATTACK);
    }

    @Override
    public void setAttack(int attack) {
	statsMap.put(ATTACK, attack);
    }

    @Override
    public int getDefense() {
	return statsMap.get(DEFENSE);
    }

    @Override
    public void setDefense(int def) {
	statsMap.put(DEFENSE, def);
    }

    @Override
    public int getSpeed() {
	return statsMap.get(SPEED);
    }

    @Override
    public void setSpeed(int speed) {
	statsMap.put(SPEED, speed);
    }

    @Override
    public Map<String, Integer> getStatsAsMap() {
	return this.statsMap;
    }
}
