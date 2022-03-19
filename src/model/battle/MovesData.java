package model.battle;

import model.monster.MonsterType;

public interface MovesData {
	
	String getName();
	
	int getBase();
	
	MonsterType getType();
	
	int getPP();
}
