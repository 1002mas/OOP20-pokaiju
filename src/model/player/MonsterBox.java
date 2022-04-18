package model.player;

import java.util.List;
import java.util.Optional;

import model.monster.Monster;

public interface MonsterBox {

	/**
	 * This function returns a list of all monster in box
	 * 
	 * @return list of monsters
	 */
	public List<Monster> getAllMonsters();

	/**
	 * This function tries to add a monster in box
	 * 
	 * @param monster to add
	 * @return true if monster was added, false otherwise
	 */
	public boolean addMonster(Monster monster);

	/**
	 * this function tries to exchange two monsters, one from the box to player team
	 * with one from player team to the box
	 * 
	 * @param toBox     monster to put in the box
	 * @param monsterID monster to get from the box
	 * @return an optional of monster if exchange has been done, an empty optional
	 *         otherwise
	 */
	public Optional<Monster> exchange(Monster toBox, int monsterID);

	/**
	 * This function returns a monster if it is in the box
	 * 
	 * @param monsterID
	 * @return optional of a monster if it is in box, an empty optional otherwise
	 */
	public Optional<Monster> getMonster(int monsterID);

	/**
	 * This function returns box name
	 * 
	 * @return box name
	 */
	public String getName();

	/**
	 * This function returns if the box is full
	 * 
	 * @return true if box is full, false otherwise
	 */
	public boolean isFull();

	/**
	 * This function removes a monster in box if it is present
	 * 
	 * @param monsterID to remove
	 */
	public void removeMonster(int monsterID);

}
