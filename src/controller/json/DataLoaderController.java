package controller.json;

import model.monster.MonsterSpecies;
import model.player.Player;

public interface DataLoaderController {

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