package controller.json;

import java.util.List;

import model.battle.Moves;
import model.gameitem.GameItem;
import model.map.GameMapData;
import model.monster.Monster;
import model.monster.MonsterSpecies;
import model.npc.NpcSimple;
import model.player.Player;

public interface DataLoaderController {
	/**
	 * 
	 * @return a list of all moves existing
	 */
	List<Moves> getMoves();

	/**
	 * 
	 * @return a list of all npcs existing
	 */
	List<NpcSimple> getNpcs();

	/**
	 * 
	 * @return a list of all monster species existing
	 */
	List<MonsterSpecies> getMonsterSpecies();

	/**
	 * 
	 * @return a list of all items existing
	 */
	List<GameItem> getGameItems();

	/**
	 * 
	 * @return a list of all monsters existing
	 */
	List<Monster> getMonsters();
	
	/**
	 * 
	 * @return a list of all game maps
	 */
	List<GameMapData> getGameMapData();
	
	
	
	/**
	 * 
	 * @return true if exist player data saved, false otherwise
	 */
	boolean playerDataExist();

}
