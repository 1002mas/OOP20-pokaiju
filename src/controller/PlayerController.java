package controller;

import java.util.Optional;

import model.Pair;
import model.player.Gender;
import model.player.Player;
import model.player.PlayerImpl;

public interface PlayerController {
    /**
     * saves player game data
     * 
     */
    void saveData();

    /**
     * loads player game data
     * 
     * @return false if no data found, true if data loaded.
     */
    boolean loadData();

    /**
     * ask the model to move the player to coord
     * 
     * @return true if the player is moved, false otherwise.
     */
    boolean movePlayer(Pair<Integer, Integer> coord);

    //TODO how to manage battle, npcs,... (consequences of interaction)
    /**
     * Interacts with the object at coord position.
     * If there is a trainer, it starts the battle.
     * If there is a npc, some text appears.
     * */
    Optional<String> interact(Pair<Integer, Integer> coord);
    
    /**
     * @return player position
     * */
    Pair<Integer, Integer> getPlayerPosition();
    
    /**
     * set new Player
     * 
     * @param new name, gender, trainerNumber
     */
    public void setNewPlayer(String name, Gender gender, int trainerNumber);
    
    /**
     * get Player
     * 
     * @return Player
     */
    public Player getPlayer();
     
}
