
public class MonsterImpl implements Monster {

	private static final int EXP_CAP = 1000;
	private static final int MAX_LVL = 100;
	private static final int HEALTH_STEP = 10;
	private static final int LEVEL_STEP = 1;

	private int health;
	private String name; // sistemare
	private int exp;
	private int level;
	private String info;
	private String type;

	public MonsterImpl(String name, int hp, String type, String info) {
		this.name = name;
		this.health = hp;
		this.exp = 0;
		this.level = 1;
		this.info = info;
		this.type = type;
	}

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
		this.exp += experience;
		if (this.exp >= EXP_CAP && this.level != MAX_LVL) {
			System.out.println("Il tuo mostro è salito di livello");
			while (this.exp >= EXP_CAP && this.level != MAX_LVL) {
				temp = exp;
				this.exp = temp - EXP_CAP;
				this.setLevel(this.level + LEVEL_STEP);
				this.setHealth(this.health + HEALTH_STEP);
				System.out.println("exp: " + this.exp);
				System.out.println("Il tuo mostro ora è livello " + this.level + "\n");
			}
			// System.out.println("Il tuo mostro ora è livello " + this.level + "\n");
		}
		if (this.level == MAX_LVL) {
			if (this.exp > EXP_CAP) {
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

	public String toString() {
		return "Nome: " + name + "\nTipo: " + type + "\nLevel: " + level + "\nExp: " + exp + "\nHealth: " + health
				+ "\nInfo: " + info + "\n";

	}

}
