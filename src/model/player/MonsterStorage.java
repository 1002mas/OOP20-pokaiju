package model.player;

import java.util.List;

import model.monster.Monster;

public interface MonsterStorage {

	/**
	 * This function adds a monster in storage if current box isn't full
	 * 
	 * @param monster to add in storage
	 */
	public void addMonster(Monster monster);

	/**
	 * This function tries to deposit a monster in the current monster box and to
	 * remove the monster form player team
	 * 
	 * @param monster to deposit
	 * @return true if is deposited, false otherwise
	 */
	public boolean depositMonster(Monster monster);

	/**
	 * This function tries to remove a monster from the current box and to add it in
	 * player team
	 * 
	 * @param monsterID
	 * @return true if monster has been added in player team and removed from box,
	 *         false otherwise
	 */
	public boolean withdrawMonster(int monsterID);

	/**
	 * This function tries to exchange two monsters, one from player team to the
	 * storage and one from the storage to player team
	 * 
	 * @param monster   to add in storage
	 * @param monsterID to add in player team
	 * @return true if the exchange was completed, false otherwise
	 */
	public boolean exchange(Monster monster, int monsterID);

	/**
	 * This function changes current box with the next one
	 * 
	 */
	public void nextBox();

	/**
	 * This function changes current box with the previous one
	 * 
	 */
	public void previousBox();

	/**
	 * This function returns current box name
	 * 
	 * @return box name
	 */
	public String getCurrentBoxName();

	/**
	 * This function returns the size of current box
	 * 
	 * @return size of current box
	 */
	public int getCurrentBoxSize();

	/**
	 * This function returns current box monsters list
	 * 
	 * @return monsters list
	 */
	public List<Monster> getCurrentBoxMonsters();

	/**
	 * This function returns maximum number of boxes in storage
	 * 
	 * @return maximum number of a box
	 */
	int getMaxSizeOfBox();

	/**
	 * This function returns maximum number of monsters in each box
	 * 
	 * @return maximum number of monsters
	 */
	int getMaxNumberOfBox();

}
