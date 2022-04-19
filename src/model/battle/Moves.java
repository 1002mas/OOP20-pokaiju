package model.battle;

import model.monster.MonsterType;

public interface Moves {
    /**
     * 
     * this function returns the name of the move.
     * 
     * @return Move's name
     */
    String getName();

    /**
     * 
     * this function returns the basic attack of the move.
     * 
     * @return Move's basic attack
     */
    int getBase();

    /**
     * 
     * this function returns the type of the move.
     * 
     * @return Move's type
     */
    MonsterType getType();

    /**
     * 
     * this function returns the max value of PP of the move.
     * 
     * @return Move's max PP
     */
    int getPP();
    /**
     * 
     *  this function returns additional damage based on enemy's type and move's type.
     * 
     * @param type enemy's type
     * 
     * @return additional damage
     */
    int getDamage(MonsterType type);
}
