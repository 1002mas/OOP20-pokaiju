
public interface Monster {
	
	public int getHealth();
	
	public void setHealth(int health);
	
	public String getName();
	
	public int getLevel();
	
	public void setLevel(int level);
	
	public int getExp();
	
	public void incExp(int exp);
	
	public int getExpCap();
	
	public String getType();
	
	public void setType(String type);
	
	Attack getAttack(int index); 
	
	public boolean isAlive();
	
	public boolean getWild();
}
