
public interface MonsterBattle {
	Attack enemyAttack();
	
	void start();
	
	boolean capture(Monster enemy);
}
