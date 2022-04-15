package controller.json;

import java.util.List;

import controller.json.saver.NpcDataSaver;
import model.battle.Moves;
import model.gameevents.GameEvent;
import model.gameitem.GameItem;
import model.map.GameMapData;
import model.monster.Monster;
import model.monster.MonsterSpecies;
import model.npc.NpcSimple;
import model.player.MonsterStorage;
import model.player.Player;

public interface DataLoaderController {

    /**
     * 
     * @return a list of all moves existing
     */
    public List<Moves> getMoves();

    /**
     * 
     * @return a list of all npcs existing
     */
    public List<NpcSimple> getNpcs();

    /**
     * 
     * @return a list of all items existing
     */
    public List<GameItem> getGameItems();

    /**
     * 
     * @return a list of all Events
     */
    public List<GameEvent> getEvents();

    /**
     * 
     * @return a list of monsterSpecies
     */
    public List<MonsterSpecies> getMonstersSpecies();

    /**
     * 
     * @return the player
     */
    public Player getPlayer();

    /**
     * 
     * @return max column value
     */

    public int getMaximumBlockInColumn();

    /**
     * 
     * @return max row value
     */
    public int getMaximumBlockInRow();

    /**
     * 
     * @param name
     * @return a move
     */

    public Moves getMove(String name);

    /**
     * 
     * @param id
     * @return a monster
     */

    public Monster getMonster(int id);

    /**
     * 
     * @param name
     * @return a npc
     */

    public NpcSimple getNpc(String name);

    /**
     * 
     * @param name
     * @return an item
     */

    public GameItem getItem(String name);

    /**
     * 
     * @param id
     * @return an event
     */

    public GameEvent getEvent(int id);

}
