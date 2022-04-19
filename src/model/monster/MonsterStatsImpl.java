package model.monster;

import java.util.HashMap;
import java.util.Map;

public class MonsterStatsImpl implements MonsterStats {

    private static final String HEALTH = "HEALTH";
    private static final String ATTACK = "ATTACK";
    private static final String DEFENSE = "DEFENSE";
    private static final String SPEED = "SPEED";
    private final Map<String, Integer> statsMap = new HashMap<>();

    /**
     * MonsterStats constructor.
     * 
     * @param health
     * @param attack
     * @param defense
     * @param speed
     */
    public MonsterStatsImpl(final int health, final int attack, final int defense, final int speed) {
        this.statsMap.put(HEALTH, health);
        this.statsMap.put(ATTACK, attack);
        this.statsMap.put(DEFENSE, defense);
        this.statsMap.put(SPEED, speed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHealth() {
        return statsMap.get(HEALTH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHealth(final int health) {
        statsMap.put(HEALTH, health);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAttack() {
        return statsMap.get(ATTACK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAttack(final int attack) {
        statsMap.put(ATTACK, attack);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDefense() {
        return statsMap.get(DEFENSE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDefense(final int def) {
        statsMap.put(DEFENSE, def);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSpeed() {
        return statsMap.get(SPEED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpeed(final int speed) {
        statsMap.put(SPEED, speed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Integer> getStatsAsMap() {
        return this.statsMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "MonsterStatsImpl [stats=" + statsMap + "]";
    }
}
