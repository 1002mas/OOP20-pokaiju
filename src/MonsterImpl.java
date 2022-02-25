
public class MonsterImpl implements Monster{
	private int health;
	private String name;
	private int level;
	private int exp;
	private final static int EXP_CAP=100;
	private Attack[] attacks= new Attack[4]; 
	private String type;
	private boolean wild;
	public MonsterImpl(String name, int health, int level,  Attack[] attacks, String type,boolean wild) {
		this.name=name;
		this.health = health;
		this.level = level;
		this.exp = 0;
		this.attacks = attacks;
		this.type = type;
		this.wild=wild;
	}
	@Override
	
	
	
	public int getHealth() {
		
		return this.health;
	}
	@Override
	public void setHealth(int health) {
		// TODO Auto-generated method stub
		this.health=health;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return this.level;
	}
	@Override
	public void setLevel(int level) {
		// TODO Auto-generated method stub
		this.level=level;
	}
	@Override
	public int getExp() {
		// TODO Auto-generated method stub
		return this.exp;
	}
	@Override
	public void incExp(int exp) {
		// TODO Auto-generated method stub
		this.exp=exp;
	}
	@Override
	public int getExpCap() {
		// TODO Auto-generated method stub
		return this.EXP_CAP;
	}
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}
	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
		this.type=type;
	}
	@Override
	public Attack getAttack(int index) {
		// TODO Auto-generated method stub
		return this.attacks[index];
	}
	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		if(this.health<=0) {
			return false;
		}
		return true;
	}
	@Override
	public boolean getWild() {
		// TODO Auto-generated method stub
		return this.wild;
	}
	
}
