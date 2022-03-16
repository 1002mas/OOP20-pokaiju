package model.monster;
import java.util.ArrayList;
import java.util.List;

import model.battle.Moves;


public class MonsterBuilderImpl implements MonsterBuilder {

	private static final int EXP_CAP = 1000;
	private int health;
	private int exp;
	private List<Moves> attackList;
	private boolean isWild;
	private int level;
	private MonsterSpeciesImpl species;
	private int attack;
	private int defense;
	private int speed;
	
	public MonsterBuilder species(MonsterSpeciesImpl species) {
		this.species = species;
		return this;
	}

	public MonsterBuilder level(int lvl) {
		if (lvl <= 0 || lvl > 100) {
			this.level = 1;
			return this;
		}
		this.level = lvl;
		return this;
	}

	public MonsterBuilder health(int hp) {
		if (hp < 0) {
			this.health = 1;
			return this;
		}
		this.health = hp;
		return this;
	}

	public MonsterBuilder exp(int exp) {
		if (exp >= EXP_CAP || exp < 0) {
			this.exp = EXP_CAP;
			return this;
		}
		this.exp = exp;
		return this;
	}

	public MonsterBuilder isWild(boolean isWild) {
		this.isWild = isWild;
		return this;
	}
	
	public MonsterBuilder attackList(List<Moves> attackList) {
		this.attackList = new ArrayList<>(attackList);
		return this;
	}
	
	public MonsterBuilder attack(int atk) {
		this.attack = atk;
		return this;
	}
	
	public MonsterBuilder defense(int dfs) {
		this.defense = dfs;
		return this;
	}
	
	public MonsterBuilder speed(int spd) {
		this.speed = spd;
		return this;
	}

	public Monster build() {
		return new MonsterImpl(this.health, this.exp, this.level, this.isWild, this.species, this.attackList, this.attack, this.defense, this.speed);
	}

}
