package model.battle;
public interface MonsterBattle {
	
	Moves enemyAttack();
	
	
	
	void debug_print();
	
	boolean escape();
	
	boolean capture();
	
	void end();
	
	void lostMoney();
	
	void winMoney();
	
	boolean playerChangeMonster(int index);
	
	boolean enemyChangeMonster(int index);
	
	boolean movesSelection(int moveIndex);
	
	boolean isCurrentMonsterAlive();
	
}
