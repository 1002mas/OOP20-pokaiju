package model.monster;
import model.battle.Attack;

public class MonsterImpl implements Monster {

	private static final int EXP_CAP = 1000;
	private static final int MAX_LVL = 100;
	private static final int HEALTH_STEP = 10;
	private static final int LEVEL_STEP = 1;
	private static final int FIRST_EVOLUTION_LEVEL = 14;
	private static final int SECOND_EVOLUTION_LEVEL = 30;
	private static final int START_LEVEL = 1;
	private static final int START_EXP = 0;

	private int health;
	private String name;
	private int exp;
	private int level;
	private String info;
	private MonsterType type;

	// ************************************

	private String first_evolution_name;
	private String second_evolution_name;
	private String secondInfo;
	private String thirdInfo;

	// ************************************

	public MonsterImpl(String name, int hp, MonsterType type, String info) {
		this.name = name;
		this.health = hp;
		this.exp = 0;
		this.level = 1;
		this.info = info;
		this.type = type;
	}

	public MonsterImpl(String name, MonsterType type, int level, int health, int exp, String info,
			String firstEvolution, String secondInfo, String secondEvolution, String thirdInfo) {
		super();
		this.name = name;
		this.health = health;
		this.exp = exp;
		this.level = level;
		this.info = info;
		this.type = type;
		this.first_evolution_name = firstEvolution;
		this.second_evolution_name = secondEvolution;
		this.secondInfo = secondInfo;
		this.thirdInfo = thirdInfo;
	}

	// ******DA RIMUOVERE - ONLY DEBUG *********************

	public void setFirstEvolution(String secondName) {
		this.first_evolution_name = secondName;
	}

	public void setSecondEvolution(String thirdName) {
		this.second_evolution_name = thirdName;
	}

	public void setSecondInfo(String secondInfo) {
		this.secondInfo = secondInfo;
	}

	public void setThirdInfo(String thirdInfo) {
		this.thirdInfo = thirdInfo;
	}

	public String getSecondInfo() {
		return this.secondInfo;
	}

	public String getThirdInfo() {
		return this.thirdInfo;
	}
	
	public String getSecondName() {
		return this.first_evolution_name;
	}
	
	public String getThirdName() {
		return this.second_evolution_name;
	}

	// *****************************************************

	public int getHealth() {
		return this.health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
		if (this.level == MAX_LVL) {
			this.level = MAX_LVL;
		}
	}

	public void incExp(int experience) {
		this.exp += experience;
		//TODO use module and division
		if (this.level == MAX_LVL) {
			if (this.exp >= EXP_CAP) {
				this.exp = EXP_CAP;
			}
		}
	}

	public int getExp() {
		return this.exp;
	}
	
	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getExpCap() {
		return EXP_CAP;
	}

	public String toString() {
		return "Nome: " + name.toUpperCase() + "\nTipo: " + type + "\nLevel: " + level + "\nExp: " + exp + "\nHealth: "
				+ health + "\nInfo: " + info + "\n";
	}

	@Override
	public boolean getWild() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Attack getAttack(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
