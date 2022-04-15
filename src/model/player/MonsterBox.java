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
	 */
	public boolean addMonster(Monster monster);

	/**
	 * 
	 * @param toBox monster to put in the box
	 * @param monsterID monster to get from the box
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
	 * 
	 * @param monsterID to remove
	 */
	public void removeMonster(int monsterID);
}
