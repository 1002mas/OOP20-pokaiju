package model.player;

import java.util.List;

import model.monster.Monster;

public interface MonsterStorage {

	/**
	 * 
	 * @param monster to add in storage
	 */
	public void addMonster(Monster monster);

	/**
	 * 
	 * @param monster to deposit
	 * @return true if is deposited, false otherwise
	 */
	public boolean depositMonster(Monster monster);

	/**
	 * 
	 * @param monsterID
	 * @return true if monster has been added in player team, false otherwise
	 */
	public boolean withdrawMonster(int monsterID);

	/**
	 * 
	 * @param monster   to add in storage
	 * @param monsterID to add in player team
	 * @return
	 */
	public boolean exchange(Monster monster, int monsterID);

	/**
	 * 
	 */
	public void nextBox();

	/**
	 * 
	 */
	public void previousBox();

	/**
	 * 
	 * @return current box name
	 */
	public String getCurrentBoxName();

	/**
	 * 
	 * @return current box monster list
	 */
	public List<Monster> getCurrentBoxMonsters();
}
