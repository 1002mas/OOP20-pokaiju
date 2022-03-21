package model.monster;

import model.GameItem.*;

import model.battle.Moves;

public interface Monster {

    /**
     * This function returns the name of the monster
     * 
     * @return monster's name
     */
    String getName();

    /**
     * This function returns the health of the monster
     * 
     * @return monster's health
     */
    int getHealth();

    /**
     * This function set the health of the monster
     * 
     * @param health
     */
    void setHealth(int health);

    /**
     * This function returns monster's max health
     * 
     * @return monster's max health
     */
    int getMaxHealth();

    /**
     * This function returns all the info of the monster
     * 
     * @return monster's info
     */
    String getInfo();

    /**
     * This function returns the level of the monster
     * 
     * @return monster's level
     */
    int getLevel();

    /**
     * This function set the monster's level
     * 
     * @param level
     */
    void setLevel(int level);

    /**
     * This function increase the monster's experience points
     * 
     * @param experience
     */
    void incExp(int experience);

    /**
     * This function returns the monster's experience points
     * 
     * @return monster's experience
     */
    int getExp();

    /**
     * This function returns the experience point cap
     * 
     * @return experience cap
     */
    int getExpCap();

    /**
     * This function returns if the monster is wild
     * 
     * @return if the monster is wild
     */
    boolean getWild();

    /**
     * This function returns if the monster is alive
     * 
     * @return if the monster is alive
     */
    boolean isAlive();

    /**
     * This function returns a move from a list of moves
     * 
     * @param index of the moves
     * @return a move
     */
    Moves getMoves(int index);

    /**
     * This function returns the numbers of moves owned by the monster
     * 
     * @return
     */
    int getNumberOfMoves();

    /**
     * This function returns the type of the monster
     * 
     * @return
     */
    MonsterType getType();

    /**
     * This function returns if the monster is evolving by item
     * 
     * @param item
     * @return if monster evolves by item
     */
    boolean evolveByItem(GameItems item);

    /**
     * This function returns monster's species
     * 
     * @return a monsterSpecies
     */
    MonsterSpecies getSpecies();

    /**
     * This function returns monster's stats
     * 
     * @return monster's stats
     */
    MonsterStats getStats();

}
