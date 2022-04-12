package model.player;

import java.util.List;

import model.Pair;
import model.monster.Monster;

public interface MonsterStorage {

	/**
	 * 
	 * @return list of box name and all box monsters
	 */
	public List<Pair<String, List<Monster>>> getMonsterList();

	/**
	 * 
	 * @param monster to add
	 */
	public void addMonster(Monster monster);

	/**
	 * 
	 * @param monster to take
	 */
	public void takeMonster(Monster monster);

	/**
	 * 
	 * @param monsterToBox monster to put in the box
	 * @param monsterFromBox monster to get from the box
	 */
	public void exchange(Monster monsterToBox, Monster monsterFromBox);
}
