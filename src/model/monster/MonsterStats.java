package model.monster;

import java.util.Map;

public interface MonsterStats {
    
    /**
     * 
     * @return
     */
    int getHealth();

    /**
     * 
     * @param health
     */
    void setHealth(int health);

    /**
     * 
     * @return
     */
    int getAttack();

    /**
     * 
     * @param attack
     */
    void setAttack(int attack);

    /**
     * 
     * @return
     */
    int getDefense();

    /**
     * 
     * @param def
     */
    void setDefense(int def);

    /**
     * 
     * @return
     */
    int getSpeed();

    /**
     * 
     * @param speed
     */
    void setSpeed(int speed);

    /**
     * 
     * @return
     */
    Map<String, Integer> getStatsAsMap();
}
