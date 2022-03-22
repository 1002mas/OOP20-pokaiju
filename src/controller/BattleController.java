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

    /***/
    void useItem(GameItems gameItem);

    /***/
    void changeMonster();

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
}
