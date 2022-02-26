import java.util.ArrayList;
import java.util.List;

public class MonsterBattleImpl implements MonsterBattle{
	private final static int EXP_MULTIPLER=5;
	private final static int ESCAPE_RANGE=10;
	private final static int ESCAPE_DIFFICULT=5;
	private final static int CAPTURE_RANGE=10;
	private final static int CAPTURE_DIFFICULT=3;
	private final static int NUMBER_OF_ATTACKS=4;
	
	private boolean battleStatus;
	private Monster yourMonster;
	private Monster enemy;
	private List<Monster> playerTeam;
	private List<Monster> enemyTeam;
	
	public MonsterBattleImpl(ArrayList<Monster> playerTeam, ArrayList<Monster> enemyTeam) { 
		//forse da cambiare passando il player al posto della squad x la questione items
		this.battleStatus=true;
		this.playerTeam=playerTeam;
		this.enemyTeam=enemyTeam;
		this.yourMonster=playerTeam.get(0);
		this.enemy=enemyTeam.get(0);
	}
	
	@Override
	public Attack enemyAttack() {
		int x=0;
		do {
			x =(int)(Math.random()*NUMBER_OF_ATTACKS);
		}while(!enemy.getAttack(x).checkPP());
		return enemy.getAttack(x);
	}
	
	@Override
	public void click(int choose) {
		
		switch(choose) {

			case 0 : {
				this.turn();
				break;
			}
			
			case 1 : {
				//change monster
				break;
			}
			
			case 2 : {
				this.capture();
				break;
			}
			
			case 3:{
				//equipment healing
				break;
			}
			
			case 4:{
				this.escape();
				break;
			}
		}
		//TODO check battleStatus per vedere se è finita la battaglia
	}
	
	@Override
	public boolean capture() {
		if(enemy.getWild()) {
			int attempt = (int) (Math.random()*CAPTURE_RANGE);
			if(attempt <= CAPTURE_DIFFICULT) {
				System.out.println(enemy.getName()+" è stato catturato");
				int expReached=enemy.getLevel()*EXP_MULTIPLER;
				yourMonster.incExp(expReached);
				this.battleStatus=false;
				return true;
			}
			System.out.println("cattura fallita");
			return false;
		}
		else {
			System.out.println("Non è un mostro selvaggio");
			return false;
		}	
	}
	
	@Override
	public void turn() {
		// TODO Auto-generated method stub
		Attack att = this.yourMonster.getAttack(1);
		if(!att.checkPP()) {
			System.out.println(yourMonster.getName() + " è troppo stanco per usare questa mossa");
		}
		else {
			att.decPP();
			enemy.setHealth(enemy.getHealth()-att.getDamage(enemy.getType()));
			System.out.println(yourMonster.getName() + " usa " + att.getName() + " infliggendo "+att.getDamage(enemy.getType()) + " danni");
			
		}
		if(enemy.isAlive()) {
			att = this.enemyAttack();
			att.decPP();
			yourMonster.setHealth(yourMonster.getHealth()-att.getDamage(yourMonster.getType()));
			System.out.println(enemy.getName() + " usa " + att.getName()+ " infliggendo "+att.getDamage(yourMonster.getType()) + " danni");
			
		}
		else {
			int expReached=enemy.getLevel()*EXP_MULTIPLER;
			yourMonster.incExp(expReached);
			System.out.println(enemy.getName() + " è morto ");
			//TODO: check se il nemico ha altri mostri
		}
		if(!yourMonster.isAlive()) {
			System.out.println(yourMonster.getName() + " è morto ");
			//TODO: check se il player ha altri mostri
			
		}
		this.debug_print();
		
		
	}
	
	@Override
	public void debug_print() {
		// TODO Auto-generated method stub
		System.out.println("------------------------");
		System.out.println(yourMonster.getName() +"->"+yourMonster.getHealth());
		System.out.println(enemy.getName() +"->"+enemy.getHealth());
		System.out.println("------------------------");
	}
	
	@Override
	public boolean escape() {
		if(enemy.getWild()) {
			int attempt = (int) (Math.random()*ESCAPE_RANGE);
			if(attempt <= ESCAPE_DIFFICULT) {
				System.out.println("Sei fuggito");
				this.battleStatus=false;
				return true;
			}
			System.out.println("Fuga fallita");
			return false;
		}
		else{
			System.out.println("Non puoi scappare");
			return false;
		}	
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}	
}
