package controller;

import java.util.List;

import model.battle.Moves;
import model.gameitem.GameItems;
import model.monster.Monster;

public interface BattleController {
    /**
     * Choose the player move in the battle and starts the turn
     * 
     * @return true if you can choose the selected move
     */
    boolean chooseMove(int moveIndex);

    /**
     * Use an item
     * 
     * @param gameItem
     */
    void useItem(GameItems gameItem, int monsterIndex);

    /**
     * 
     * @return all player items
     */
    List<GameItems> getAllPlayerItems();

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
    List<Moves> getMoves();

    /**
     * Get current player's monster
     * 
     * @return current player's monster
     */
    Monster getPlayerCurrentMonster();

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
    List<Monster> getPlayerTeam();

    /**
     * Get current enemy's monster
     * 
     * @return current enemy's monster
     */
    Monster getEnemyCurrentMonster();

    /**
     * 
     * @return current enemy monster name
     */
    String getEnemyCurrentMonsterName();

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
    Moves getEnemyCurrentMove();

    /**
     * Get all enemy's monsters
     * 
     * @return enemy team
     */
    List<Monster> getEnemyTeam();
    
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
