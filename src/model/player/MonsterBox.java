package model.player;

import java.util.List;

import model.monster.Monster;

public interface MonsterBox {

	/**
	 * 
	 * @return a map of all monsters with box coordinates
	 */
	public List<Monster> getAllMonsters();

	/**
	 * 
	 * @param monster to add
	 */
	public void addMonster(Monster monster);

	/**
	 * 
	 * @param monster to remove from box
	 * @return true if monster was removed, false otherwise
	 */
	public boolean takeMonster(Monster monster);

	/**
	 * 
	 * @return if monster map is full
	 */
	public boolean isFull();

	/**
	 * 
	 * @param monsterToBox monster to put in the box
	 * @param monsterFromBox monster to get from the box
	 */
	public void exchange(Monster monsterToBox, Monster monsterFromBox);
	
	/**
	 * 
	 * @return box name
	 */
	public String getName();
}
