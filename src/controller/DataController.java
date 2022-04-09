package controller;

import java.util.List;

import model.gameitem.GameItems;
import model.map.GameMap;
import model.map.GameMapData;
import model.monster.Monster;
import model.npc.NpcTrainer;
import model.player.Player;

public interface DataController {

	 /**
     * saves player game data
     * 
     */
    void saveData(Player player);

    /**
     * loads player game data
     * 
     * @return false if no data found, true if data loaded.
     */
    boolean loadData(Player player);
    
    /**
     * 
     * @return true if data exist, flase otherwise
     */
    boolean dataExsist();
    
    /**
     * 
     * @return true if maps data are loaded, flase otherwise
     */
    boolean loadMapData();
    
    //void setPlayer(Player player);
    
    GameMapData getGameMapData();
    
    GameMap getGameMap();
    
    void addNpcsDefeated(NpcTrainer npc);
    
    void setNpcDefeatedInMap();
    
    void setNpcDefeatedFromMap();
    
    List<Monster> loadMonsters();
    
    List<GameItems> loadItems();
    
    int getMaximumBlocksInRow();
    
    int getMaximumBlocksInColumn();
    
    void deleteNpcData();
}
