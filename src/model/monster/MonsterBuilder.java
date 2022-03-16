package model.monster;

import java.util.List;

import model.battle.Attack;

public interface MonsterBuilder {

	/**
	 * Build monster's level
	 * @param lvl
	 * @return a MonsterBuilder
	 */
	MonsterBuilder level(int lvl);
	
	/**
	 * Build monster's health
	 * @param hp
	 * @return a MonsterBuilder
	 */
	MonsterBuilder health(int hp);
	
	/**
	 * Build monster's experience
	 * @param exp
	 * @return a MonsterBuilder
	 */
	MonsterBuilder exp(int exp);
	
	/**
	 * Build monster isWild parameter
	 * @param isWild
	 * @return a MonsterBuilder
	 */
	MonsterBuilder isWild(boolean isWild);
	
	/**
	 * Build monster's moves
	 * @param a list of moves
	 * @return a MonsterBuilder
	 */
	MonsterBuilder attackList(List<Attack> attackList);
	
	/**
	 * Build monster's attack stat
	 * @param atk
	 * @return a MonsterBuilder
	 */
	MonsterBuilder attack(int atk);
	
	/**
	 * Build monster's defense stat
	 * @param dfs
	 * @return a MonsterBuilder
	 */
	MonsterBuilder defense(int dfs);
	
	/**
	 * Build monster's speed stat
	 * @param spd
	 * @return a MonsterBuilder
	 */
	MonsterBuilder speed(int spd);
	
	/**
	 * Build monster's species
	 * @param MonsterSpeciesImpl species
	 * @return a MonsterBuilder
	 */
	MonsterBuilder species(MonsterSpeciesImpl species);
	
	/**
	 * Build the monster
	 * @return new Monster
	 */
	Monster build();
	
}