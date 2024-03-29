package controller;

import java.util.List;

import model.monster.MonsterType;

public interface BattleController {

    /**
     * Get the id of the current player monster.
     * 
     * @return id of the current monster
     */
    int getPlayerCurrentMonsterId();

    /**
     * Get current player's monster name.
     * 
     * @return current player's monster
     */
    String getPlayerCurrentMonsterName();

    /**
     * Get current player's monster health points.
     * 
     * @return current monster health
     */
    int getPlayerCurrentMonsterHp();

    /**
     * Get current player's monster max health points.
     * 
     * @return monster max health points
     */
    int getPlayerCurrentMonsterMaxHealth();

    /**
     * Get current player's monster level.
     * 
     * @return current monster level
     */
    int getPlayerCurrentMonsterLevel();

    /**
     * Get all the names of the player's monsters.
     * 
     * @return all the names of the player's monsters
     */
    List<Integer> getPlayerTeam();

    /**
     * Change monster.
     * 
     * @param monsterIndex
     */
    void changeMonster(int monsterIndex);

    /**
     * Get the id of the current enemy monster.
     * 
     * @return current enemy's monster id
     */
    int getEnemyCurrentMonsterId();

    /**
     * Get the name of the current enemy monster.
     * 
     * @return current enemy monster name
     */
    String getEnemyCurrentMonsterName();

    /**
     * Get the health points of the current enemy monster.
     * 
     * @return current enemy monster health
     */
    int getEnemyCurrentMonsterHp();

    /**
     * Get the max health points of the current enemy monster.
     * 
     * @return monster max health points
     */
    int getEnemyCurrentMonsterMaxHealth();

    /**
     * Get the type of the current enemy monster.
     * 
     * @return monster type
     */
    MonsterType getEnemyCurrentMonsterType();

    /**
     * Get the level of the current enemy monster.
     * 
     * @return current enemy monster level
     */
    int getEnemyCurrentMonsterLevel();

    /**
     * Get the move of the current enemy monster.
     * 
     * @return current enemy move
     */
    String getEnemyCurrentMove();

    /**
     * Get all enemy's monsters id.
     * 
     * @return enemy team
     */
    List<Integer> getEnemyTeam();

    /**
     * Get the name of the monster with id equals idMonster.
     * 
     * @param idMonster
     * @return name of the monster
     */
    String getMonsterName(int idMonster);

    /**
     * Choose the player move in the battle and starts the turn.
     * 
     * @param moveName
     * @return true if you can choose the selected move
     */
    boolean chooseMove(String moveName);

    /**
     * Check the PP of a move.
     * 
     * @param moveName
     * @return if PP are below zero returns false, true otherwise
     */
    boolean checkPP(String moveName);

    /**
     * This function returns if monster has finished all PP of the moves.
     * 
     * @return if monster has finished all PP of the moves return true, false
     *         otherwise
     */
    boolean isOverOfPP();

    /**
     * The Monster attacks with extra move.
     * 
     * @return true if attacks with extra move, false otherwise
     */
    boolean attackWithExtraMove();

    /**
     * Get a list with all the monster's moves.
     * 
     * @return current player's monster moves
     */
    List<String> getMoves();

    /**
     * Use a GameItem.
     * 
     * @param gameItemName
     * @param monsterId
     */
    void useItem(String gameItemName, int monsterId);

    /**
     * Returns the number of GameItems that have the name gameItemName.
     * 
     * @param gameItemName
     * @return items number
     */
    int getItemNumber(String gameItemName);

    /**
     * Returns a list with all the GameItem names.
     * 
     * @return all player items
     */
    List<String> getAllPlayerItems();

    /**
     * Returns if an item is a capture item.
     * @param gameItemName
     * @return true if is a capture item, false otherwise
     */
    boolean isCaptureItem(String gameItemName);

    /**
     * Check the health of the monster.
     * 
     * @param monsterId
     * @return if HP are below zero returns false, true otherwise
     */
    boolean isAlive(int monsterId);

    /**
     * Returns true if the monster is caught, false otherwise.
     * 
     * @return if the monster is caught
     */
    boolean isEnemyCaught();

    /**
     * The player tries to escape the battle.
     * 
     * @return if fleeing is successful, false otherwise
     */
    boolean flee();

    /**
     * Returns true if the battle is over, false otherwise.
     * 
     * @return if the battle ends
     */
    boolean isOver();

    /**
     * Returns true if the player has lost the battle, false otherwise.
     * 
     * @return if the player has lost the battle
     */
    boolean hasPlayerLost();

    /**
     * This function sets money and restore statistics at the end of the battle.
     */
    void endingBattle();
}
