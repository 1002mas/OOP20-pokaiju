package model.monster;

import java.util.List;

import model.battle.Attack;

public interface MonsterBuilder {

	MonsterBuilder level(int lvl);
	
	MonsterBuilder health(int hp);
	
	MonsterBuilder exp(int exp);
	
	MonsterBuilder isWild(boolean isWild);
	
	MonsterBuilder attackList(List<Attack> attackList);
	
	MonsterBuilder species(MonsterSpecies species);
	
	Monster build();
	
}