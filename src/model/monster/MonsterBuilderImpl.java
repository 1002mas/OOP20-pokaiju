package model.monster;

import java.util.ArrayList;
import java.util.List;

import model.battle.Attack;

//TODO: MODIFY BUILDER

public class MonsterBuilderImpl implements MonsterBuilder {

	private static final int EXP_CAP = 1000;
	private int health;
	private int exp;
	private List<Attack> attackList;
	private boolean isWild;
	private int level;
	private MonsterSpecies species;
	
	public MonsterBuilder species(MonsterSpecies species) {
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
	
	public MonsterBuilder attackList(List<Attack> attackList) {
		this.attackList = new ArrayList<>(attackList);
		return this;
	}

	public Monster build() {
		return new MonsterImpl(this.health, this.exp, this.level, this.isWild, this.species, this.attackList);
	}

}
