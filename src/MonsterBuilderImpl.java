
public class MonsterBuilderImpl implements MonsterBuilder{

	
	private static final int EXP_CAP = 1000;
	private String name;
	private MonsterType type;
	private int health;
	private int exp;
	private String info;
	private String secondInfo;
	private String thirdInfo;
	private String firstEvolution;
	private String secondEvolution;
	private int level;

	public MonsterBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public MonsterBuilder secondName(String name) {
		this.firstEvolution = name;
		return this;
	}
	
	public MonsterBuilder thirdName(String name) {
		this.secondEvolution = name;
		return this;
	}
	
	public MonsterBuilder monsterType(MonsterType type) {
		this.type = type;
		return this;
	}
	

	public MonsterBuilder level(int lvl) {
		if(lvl >= 100 || lvl < 0) {
			this.level = 1;
			return this;
		}
		this.level = lvl;
		return this;
	}
	
	public MonsterBuilder health(int hp) {
		if(hp >= 100 || hp < 0) {
			this.level = 1;
			return this;
		}
		this.level = hp;
		return this;
	}
	
	public MonsterBuilder exp(int exp) {
		if(exp >= EXP_CAP || exp < 0) {
			this.exp = EXP_CAP;
			return this;
		}
		this.level = exp;
		return this;
	}
	
	public MonsterBuilder info(String info) {
		this.info = info;
		return this;
	}
	
	public MonsterBuilder secondInfo(String info) {
		this.secondInfo = info;
		return this;
	}
	
	public MonsterBuilder thirdInfo(String info) {
		this.thirdInfo = info;
		return this;
	}
	
	public Monster build() throws IllegalStateException {
		if (this.name == null || this.type == null || this.info == null || this.secondInfo == null || this.thirdInfo == null || this.firstEvolution == null || this.secondEvolution == null) {
			throw new IllegalStateException("");
		}
		return new MonsterImpl(name, type, level, health, exp, info, firstEvolution, secondInfo, secondEvolution, thirdInfo);
	}

}
