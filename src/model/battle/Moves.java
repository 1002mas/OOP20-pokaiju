package model.battle;

import model.monster.MonsterType;

public interface Moves {
	public void decPP();

	public boolean checkPP();

	public String getName();

	public int getDamage(MonsterType enemytype);
}
