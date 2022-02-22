
public interface Attack {
	public void decPP();
	public boolean checkPP();
	
	public String getName();
	
	public int getDamage(String enemytype);
}
