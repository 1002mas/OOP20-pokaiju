package model.monster;

import java.util.ArrayList;
import java.util.List;

import model.battle.Moves;

public class MonsterBuilderImpl implements MonsterBuilder {

    private static final int EXP_CAP = 1000;
    private static final int NUM_MAX_MOVES = 4;
    private int health;
    private int exp;
    private List<Moves> movesList;
    private boolean isWild;
    private int level;
    private MonsterSpeciesImpl species;
    /*
     * private int attack; private int defense; private int speed;
     */

    @Override
    public MonsterBuilder species(MonsterSpeciesImpl species) {
	this.species = species;
	return this;
    }

    @Override
    public MonsterBuilder level(int lvl) {
	if (lvl <= 0 || lvl > 100) {
	    this.level = 1;
	    return this;
	}
	this.level = lvl;
	return this;
    }

    @Override
    public MonsterBuilder health(int hp) {
	if (hp < 0) {
	    this.health = 1;
	    return this;
	}
	this.health = hp;
	return this;
    }

    @Override
    public MonsterBuilder exp(int exp) {
	if (exp >= EXP_CAP || exp < 0) {
	    this.exp = EXP_CAP;
	    return this;
	}
	this.exp = exp;
	return this;
    }

    @Override
    public MonsterBuilder isWild(boolean isWild) {
	this.isWild = isWild;
	return this;
    }

    @Override
    public MonsterBuilder movesList(List<Moves> movesList) {
	ArrayList<Moves> moves = new ArrayList<>();
	for (int i = 0; i < NUM_MAX_MOVES; i++) {
	    moves.add(movesList.get(i));
	}
	this.movesList = moves;
	return this;
    }

    /*
     * @Override public MonsterBuilder attack(int atk) { this.attack = atk; return
     * this; }
     * 
     * @Override public MonsterBuilder defense(int dfs) { this.defense = dfs; return
     * this; }
     * 
     * @Override public MonsterBuilder speed(int spd) { this.speed = spd; return
     * this; }
     */

    @Override
    public Monster build() {
	if (this.species == null || this.health < 0 || this.exp < 0 || this.movesList.isEmpty()
		|| /*
		    * this.attack <= 0 || this.defense <= 0 || this.speed <= 0 ||
		    */ this.level <= 0) {
	    throw new IllegalStateException();
	}
	return new MonsterImpl(this.health, this.exp, this.level, this.isWild, this.species, this.movesList
	/* , this.attack, this.defense, this.speed */);
    }

}
