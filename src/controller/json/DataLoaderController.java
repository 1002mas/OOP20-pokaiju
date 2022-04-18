package controller.json;

import java.util.List;

import model.battle.Moves;
import model.gameevents.GameEvent;
import model.gameitem.GameItem;
import model.monster.Monster;
import model.monster.MonsterSpecies;
import model.npc.NpcSimple;
import model.player.Player;

public interface DataLoaderController {

	/**
	 * This function returns a list of all moves existing
	 * 
	 * @return a list of moves
	 */
	public List<Moves> getMoves();

	/**
	 * This function returns a list of all npc existing
	 * 
	 * @return a list of npcs
	 */
	public List<NpcSimple> getNpcs();

	/**
	 * This function returns a list of items existing
	 * 
	 * @return a list of items
	 */
	public List<GameItem> getGameItems();

	/**
	 * This function returns a list of all events
	 * 
	 * @return a list of Events
	 */
	public List<GameEvent> getEvents();

	/**
	 * This function returns a list of monsterSpecies
	 * 
	 * @return a list of monsterSpecies
	 */
	public List<MonsterSpecies> getMonstersSpecies();

	/**
	 * This function returns the player
	 * 
	 * @return the player
	 */
	public Player getPlayer();

	/**
	 * This function returns max column value
	 * 
	 * @return max column value
	 */

	public int getMaximumBlockInColumn();

	/**
	 * This function returns max row value
	 * 
	 * @return max row value
	 */
	public int getMaximumBlockInRow();

	/**
	 * This function returns a move
	 * 
	 * @param name
	 * @return a move
	 */

	public Moves getMove(String name);

	/**
	 * This function returns a monster
	 * 
	 * @param id
	 * @return a monster
	 */

	public Monster getMonster(int id);

	/**
	 * This function returns a npc
	 * 
	 * @param name
	 * @return a npc
	 */

	public NpcSimple getNpc(String name);

	/**
	 * This function returns an item
	 * 
	 * @param name
	 * @return an item
	 */

	public GameItem getItem(String name);

	/**
	 * This function returns an event
	 * 
	 * @param id
	 * @return an event
	 */

	public GameEvent getEvent(int id);

	/**
	 * This function set a new player
	 * 
	 * @param name,gender,trainer
	 * 
	 */
	public void setPlayer(String name, String gender, int trainerNumber);
	
	/**
	 * This function returns a monster species from the name
	 * 
	 * @param name
	 * @return monsterSpecies by name
	 */
	MonsterSpecies getSpeciesByName(String name);

}
