
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
	private String type;
	
	//************************************
	
	private String first_evolution_name;
	private String second_evolution_name;
	private String secondInfo;
	private String thirdInfo;
	
	//************************************

	public MonsterImpl(String name, int hp, String type, String info) {
		this.name = name;
		this.health = hp;
		this.exp = START_EXP;
		this.level = START_LEVEL;
		this.info = info;
		this.type = type;
	}
	
	//******DA RIMUOVERE - ONLY DEBUG *********************
	
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

	//*****************************************************

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
		int temp;
		String currentName = this.name;
		this.exp += experience;
		if (this.exp >= EXP_CAP && this.level != MAX_LVL) {
			System.out.println("Il tuo mostro è salito di livello");
			while (this.exp >= EXP_CAP && this.level != MAX_LVL) {
				temp = exp;
				this.exp = temp - EXP_CAP;
				this.setLevel(this.level + LEVEL_STEP);
				this.setHealth(this.health + HEALTH_STEP);
				
				if(this.level == FIRST_EVOLUTION_LEVEL || this.level == SECOND_EVOLUTION_LEVEL) {
					System.out.println("IL TUO MOSTRO SI STA EVOLVENDO");
					if (this.level == FIRST_EVOLUTION_LEVEL) {
						this.setName(first_evolution_name);
						this.setInfo(this.getSecondInfo());
					}
					if (this.level == SECOND_EVOLUTION_LEVEL) {
						this.setName(second_evolution_name);
					}
					System.out.println("Il tuo " + currentName.toUpperCase() + " si è evoluto in " + this.name.toUpperCase()+"\n");
					currentName = this.getName();
					this.setInfo(this.getThirdInfo());
				}
				
				System.out.println("Il tuo mostro ora è livello " + this.level + "\n");
			}
			// System.out.println("Il tuo mostro ora è livello " + this.level + "\n");
		}
		if (this.level == MAX_LVL) {
			if (this.exp >= EXP_CAP) {
				this.exp = EXP_CAP;
			}
		}
	}

	public int getExp() {
		return this.exp;
	}

	public int getExpCap() {
		return EXP_CAP;
	}

	@Override
	public String toString() {
		return "Nome: " + name.toUpperCase() + "\nTipo: " + type.toUpperCase() + "\nLevel: " + level + "\nExp: " + exp + "\nHealth: " + health
				+ "\nInfo: " + info + "\n";
	}

}
