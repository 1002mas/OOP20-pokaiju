import java.util.concurrent.TimeUnit;

public class MonsterBattleImpl implements MonsterBattle{
	private Monster yourMonster;
	private Monster enemy;
	public MonsterBattleImpl(Monster yourMonster, Monster enemy) {
		this.yourMonster=yourMonster;
		this.enemy=enemy;
	}
	@Override
	public Attack enemyAttack() {
		int x=0;
		do {
			x =(int)(Math.random()*4);
		}while(!enemy.getAttack(x).checkPP());
		return enemy.getAttack(x);
	}
	@Override
	public void start() {
		boolean turn=true;
		while(yourMonster.checkHealth() && enemy.checkHealth()) {
			if(turn) {
				Attack att = this.yourMonster.getAttack(1);
				if(!att.checkPP()) {
					System.out.println(yourMonster.getName() + " è troppo stanco per usare questa mossa");
				}
				else {
					att.decPP();
					enemy.setHealth(enemy.getHealth()-att.getDamage(enemy.getType()));
					System.out.println(yourMonster.getName() + " usa " + att.getName() + " infliggendo "+att.getDamage(enemy.getType()) + " danni");	
					
				}
				if(this.capture(enemy)) {
					break;
				}
				turn = false;
			}
			else {
				Attack att = this.enemyAttack();
				att.decPP();
				yourMonster.setHealth(yourMonster.getHealth()-att.getDamage(yourMonster.getType()));
				System.out.println(enemy.getName() + " usa " + att.getName()+ " infliggendo "+att.getDamage(yourMonster.getType()) + " danni");
				
				turn = true;
			}
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(!yourMonster.checkHealth()) {
			System.out.println(yourMonster.getName() + " è morto ");
		}
		if(!enemy.checkHealth()) {
			System.out.println(enemy.getName() + " è morto ");
		}
		
	}
	@Override
	public boolean capture(Monster enemy) {
		int x = (int) (Math.random()*10);
		if(x<=3) {
			System.out.println(enemy.getName()+" è stato catturato");
			return true;
		}
		System.out.println("cattura fallita");
		return false;
		
		
	}
	
	
}
