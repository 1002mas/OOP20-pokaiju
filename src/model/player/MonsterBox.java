package model.player;

import java.util.List;
import java.util.Optional;

import model.monster.Monster;

public interface MonsterBox {

	/**
	 * 
	 * @return a list of all monsters in box
	 */
	public List<Monster> getAllMonsters();

	/**
	 * 
	 * @param monster to add
	 * @return true if monster was added, false otherwise
	 */
	public boolean addMonster(Monster monster);

	/**
	 * this function tries to exchange two monsters from storage to player team
	 * 
	 * @param toBox     monster to put in the box
	 * @param monsterID monster to get from the box
	 * @return an optional of monster if exchange has been done, an empty optional
	 *         otherwise
	 */
	public Optional<Monster> exchange(Monster toBox, int monsterID);

	/**
	 * 
	 * @param monsterID
	 * @return optional of a monster if is in box, an empty optional otherwise
	 */
	public Optional<Monster> getMonster(int monsterID);

	/**
	 * 
	 * @return box name
	 */
	public String getName();

	/**
	 * 
	 * @return true if box is full, false otherwise
	 */
	public boolean isFull();

	/**
	 * this function removes a monster in box if it is present
	 * 
	 * @param monsterID to remove
	 */
	public void removeMonster(int monsterID);
	
}