package model.monster;

import model.gameitem.*;

import java.util.List;

import model.battle.Moves;

public interface Monster {

    /**
     * This function returns the id of the monster
     * 
     * @return monster id
     */
    int getId();

    /**
     * This function returns the name of the monster
     * 
     * @return monster's name
     */
    String getName();

    /**
     * This function set the health of the monster
     * 
     * @param health
     */
    void setHealth(int health);

    /**
     * This function restore the stats of the monster
     */
    void restoreStats();

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
     * This function gives one level to the monster
     */
    void levelUp();

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
    boolean isWild();

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
     * @throws IllegalArgumentException if index is below zero or over max number of
     *                                  moves
     * @return a move
     */
    Moves getMoves(int index);

    /**
     * This function returns the list of all moves
     * 
     * @return all monster moves
     */
    List<Moves> getAllMoves();

    /**
     * This function returns the current PP of the move
     * 
     * @param move
     * @return current PP of the move
     */
    int getCurrentPPByMove(Moves move);

    /**
     * This function returns if the monster have finished move's PP
     * 
     * @param move
     * @return if the monster have finished move's PP
     */
    boolean isOutOfPP(Moves move);

    /**
     * This function restore all PP of a move
     * 
     * @param move
     */
    void restoreMovePP(Moves move);

    /**
     * This function restore all PP of all moves
     */
    void restoreAllMovesPP();

    /**
     * This function decrease move's PP
     * 
     * @param move
     */
    void decMovePP(Moves move);

    /**
     * This function return true if the move set is full, false otherwise
     * 
     * @return if the move set is full
     */
    public boolean isMoveSetFull();

    /**
     * This function returns the numbers of moves owned by the monster
     * 
     * @return numbers of moves owned by the monster
     */
    int getNumberOfMoves();

    /**
     * This function returns the type of the monster
     * 
     * @return type of the monster
     */
    MonsterType getType();

    /**
     * This function returns true if monster evolve by level, false otherwise
     * 
     * @return if monster can evolve by level
     */
    boolean canEvolveByLevel();

    /**
     * This function returns if the monster is evolving by item
     * 
     * @param item
     * @return if monster evolves by item
     */
    boolean canEvolveByItem(GameItem item);

    /**
     * This function evolve the monster
     */
    void evolve();

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

    /**
     * This function returns the max stats of the monster
     * 
     * @return max stats of the monster
     */
    MonsterStats getMaxStats();
}
