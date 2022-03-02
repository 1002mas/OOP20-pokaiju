package model.battle;
public interface MonsterBattle {
	
	Attack enemyAttack();
	
	void click(int choose);
	
	void turn();
	
	void debug_print();
	
	boolean escape();
	
	boolean capture();
	
	void end();
	
	
}
