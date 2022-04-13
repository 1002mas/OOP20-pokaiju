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
	 * @return a list of all monster species existing
	 */
	public List<MonsterSpecies> getMonsterSpecies();

	/**
	 * 
	 * @return a list of all items existing
	 */
	public List<GameItem> getGameItems();

	/**
	 * 
	 * @return a list of all monsters existing
	 */
	public List<Monster> getMonsters();

	/**
	 * 
	 * @return a list of all game maps
	 */

	public List<GameMapData> getGameMapData();

	/**
	 * 
	 * @return true if exist game data saved, false otherwise
	 */
	public boolean gameSaveExist();

	/**
	 * 
	 * @param idBuilder
	 * @param idCurrentMap
	 * @param monsterStorage
	 * @param player
	 * @return true if data is saved, false otherwise
	 */
	public boolean saveData(int idBuilder, int idCurrentMap, MonsterStorage monsterStorage, Player player);

	/**
	 * loads data saved
	 */
	public void loadGameData();

	/**
	 * 
	 * @return a list of all Events
	 */
	public List<GameEvent> getEvents();

	/**
	 * 
	 * @return true if data is deleted, false otherwise
	 */
	public boolean deleteData();

	/**
	 * 
	 * @return a list of monsterSpecies
	 */
	public List<MonsterSpecies> getMonstersSpecies();

	/**
	 * 
	 * @return a list of npcs Trainer defeated
	 */
	public List<String> getNpcsDefeated();

	/**
	 * 
	 * @return the player
	 */
	public Player getPlayer();

	/**
	 * 
	 * @return a list of npcs data saved
	 */
	public List<NpcDataSaver> getNpcData();

	/**
	 * 
	 * @return id builder
	 */
	public int getIdBuilder();

	/**
	 * 
	 * @return id current map used
	 */

	public int getIdCurrentMap();

	/**
	 * 
	 * @return player monster storage
	 */

	public MonsterStorage monsterStorage();

	/**
	 * 
	 * @param npcName name of the npc
	 */

	public void addTrainerDefeated(String npcName);

	/**
	 * 
	 * @param name       of the npc
	 * @param idSentence
	 */

	public void addNpcData(String name, int idSentence);

	/**
	 * 
	 * @param idBuilder new builder id
	 */

	public void setIdBuilder(int idBuilder);

	/**
	 * 
	 * @param idCurrentMap map id
	 */

	public void setIdCurrentMap(int idCurrentMap);

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
	 * @return a map
	 */

	public GameMapData getGameMapData(int id);

	/**
	 * 
	 * @param id
	 * @return an event
	 */

	public GameEvent getEvent(int id);

}
