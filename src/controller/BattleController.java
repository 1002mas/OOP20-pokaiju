package controller;

import java.util.List;

public interface BattleController {
    
    /**
     * Choose the player move in the battle and starts the turn
     * 
     * @return true if you can choose the selected move
     */
    boolean chooseMove(String moveName);
    
    /**
     * 
     * @param move
     * @return
     */
    int getCurrentPP(String moveName);
    
    /**
     * 
     * @param move
     * @return
     */
    boolean checkPP(String moveName);
    
    /**
     * 
     * @param monsterId
     * @return
     */
    boolean isAlive(int monsterId);
    
    /**
     * 
     * @param item
     * @return
     */
    int getItemNumber(String gameItemName);

    /**
     * Use an item
     * 
     * @param gameItem
     */
    void useItem(String gameItemName, int monsterIndex);

    /**
     * 
     * @return all player items
     */
    List<String> getAllPlayerItems();

    /**
     * Change monster
     * 
     * @param monsterIndex
     */
    void changeMonster(int monsterIndex);

    /**
     * Get monster's moves
     * 
     * @return current player's monster moves
     */
    List<String> getMoves();

    /**
     * Get current player's monster
     * 
     * @return current player's monster
     */
    String getPlayerCurrentMonster();

    /**
     * 
     * @return current monster name
     */
    String getPlayerCurrentMonsterName();

    /**
     * 
     * @return current monster health
     */
    int getPlayerCurrentMonsterHp();

    /**
     * 
     * @return
     */
    int getPlayerCurrentMonsterMaxHealth();

    /**
     * 
     * @return current monster level
     */
    int getPlayerCurrentMonsterLevel();

    /**
     * Get all player's monsters
     * 
     * @return player team
     */
    List<Integer> getPlayerTeam();

    /**
     * Get current enemy's monster
     * 
     * @return current enemy's monster
     */
    int getEnemyCurrentMonster();

    /**
     * 
     * @return current enemy monster name
     */
    int getEnemyCurrentMonsterName();

    /**
     * 
     * @return current enemy monster health
     */
    int getEnemyCurrentMonsterHp();

    /**
     * 
     * @return
     */
    int getEnemyCurrentMonsterMaxHealth();

    /**
     * 
     * @return current enemy monster level
     */
    int getEnemyCurrentMonsterLevel();

    /**
     * 
     * @return current enemy move
     */
    String getEnemyCurrentMove();

    /**
     * Get all enemy's monsters
     * 
     * @return enemy team
     */
    List<Integer> getEnemyTeam();
    
    /**
     * 
     * @return
     */
    int getPlayerCurrentMonsterId();
    
    /**
     * 
     * @return
     */
    int getEnemyCurrentMonsterId();
    
    /**
     * 
     * @param idMonster
     * @return
     */
    String getMonsterName(int idMonster);
    
    /**
     * 
     * @return
     */
    boolean isEnemyCaught();

    /**
     * The player tries to escape the battle
     * 
     * @return if fleeing is successful, false otherwise
     */
    boolean flee();

    /**
     * 
     * @return if the battle ends
     */
    boolean isOver();
}
