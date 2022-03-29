package controller;

import java.util.List;

import model.battle.Moves;
import model.gameitem.GameItems;
import model.monster.Monster;

public interface BattleController {
    /**
     * choose the player move in the battle and starts the turn
     * 
     * @return true if you can choose the selected move
     */
    boolean chooseMove(int moveIndex);

    /**
     * the player tries to escape the battle
     * 
     * @return if fleeing is successful, false otherwise
     */
    boolean flee();

    /**
     * 
     * @param gameItem
     */
    void useItem(GameItems gameItem);

    /**
     * 
     * @param monsterIndex
     */
    void changeMonster(int monsterIndex);

    /**
     * 
     * @return current player monster moves
     */
    List<Moves> getMoves();

    /**
     * 
     * @return player team
     */
    List<Monster> getPlayerTeam();
    
    /**
     * 
     * @return
     */
    Monster getCurrentPlayerMonster();
    
    /**
     * 
     * @return enemy team
     */
    List<Monster> getEnemyTeam();
    
    /**
     * 
     * @return
     */
    Monster getCurrentEnemyMonster();
}
