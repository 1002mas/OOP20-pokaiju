import java.util.*;

public class AttackImpl implements Attack{
	private String name;
	private int base;
	private String type;
	private int pp;
	Map<String,Map<String,Double>> buff= new HashMap<>();
	
	public AttackImpl(String name, int base, String type, int pp) {
		
		this.name = name;
		this.base = base;
		this.type = type;
		this.pp=pp;
		Map<String,Double> temp= new HashMap<>();
		temp.put("Water",0.50);
		temp.put("Fire",0.75);
		temp.put("Grass",1.50);
		buff.put("Fire", temp);
		temp=new HashMap<>();
		temp.put("Water",0.75);
		temp.put("Fire",1.25);
		temp.put("Grass",0.50);
		buff.put("Water", temp);
		temp=new HashMap<>();
		temp.put("Water",1.25);
		temp.put("Fire",0.50);
		temp.put("Grass",0.75);
		buff.put("Grass",temp);
		
		
		
		
		
		
	}

	@Override
	public boolean checkPP() {
		// TODO Auto-generated method stub
		if(this.pp<=0) {
			return false;
		}
		return true;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public int getDamage(String enemytype) {
		// TODO Auto-generated method stub
		return (int) (this.base*buff.get(this.type).get(enemytype));
	}

	@Override
	public void decPP() {
		// TODO Auto-generated method stub
		this.pp--;
	}
	
	
}
