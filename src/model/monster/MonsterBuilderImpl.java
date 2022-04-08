package model.monster;

import java.util.List;
import model.battle.Moves;

public class MonsterBuilderImpl implements MonsterBuilder {

    private static final int MIN_LEVEL = 1;
    private int id;
    private int exp;
    private List<Moves> movesList;
    private boolean isWild;
    private int level = MIN_LEVEL;
    private MonsterSpecies species;
    private MonsterStats stats = new MonsterStatsImpl(-1, -1, -1, -1);

    @Override
    public MonsterBuilder monsterId(int id) {
	if (id <= 0) {
	    throw new IllegalArgumentException(); 
	}
	this.id = id;
	return this;
    }

    @Override
    public MonsterBuilder species(MonsterSpecies species) {
	this.species = species;
	return this;
    }

    @Override
    public MonsterBuilder level(int lvl) {
	if (lvl < MIN_LEVEL || lvl > MonsterImpl.MAX_LVL) {
	   throw new IllegalArgumentException(); 
	}
	this.level = lvl;
	return this;
    }
    
    @Override
    public MonsterBuilder health(int health) {
	this.stats.setHealth(health);
	return this;
    }
    
    @Override
    public MonsterBuilder attack(int atk) {
	this.stats.setAttack(atk);
	return this;
    }
    
    @Override
    public MonsterBuilder defense(int dfs) {
	this.stats.setDefense(dfs);
	return this;
    }
    
    @Override
    public MonsterBuilder speed(int spd) {
	this.stats.setSpeed(spd);
	return this;
    }

    @Override
    public MonsterBuilder exp(int exp) {
	if (exp < 0 || exp > MonsterImpl.EXP_CAP) {
	    throw new IllegalArgumentException(); 
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
	this.movesList = movesList.subList(0, MonsterImpl.NUM_MAX_MOVES);
	return this;
    }

    @Override
    public Monster build() {
	if (this.id <= 0 || this.species == null || this.movesList.isEmpty()) {
	    throw new IllegalStateException();
	}
	Monster monster = new MonsterImpl(id, this.species.getBaseStats(), 0, MIN_LEVEL, this.isWild, this.species, this.movesList);
	for(int i = MIN_LEVEL; i <= this.level; i++) {
	    monster.levelUp();
	    monster.getMoveToLearn();
	}
	monster.incExp(exp);
	stats.getStatsAsMap().entrySet().forEach(e -> {
	    if(e.getValue() > 0) {
		monster.getStats().getStatsAsMap().put(e.getKey(), e.getValue());
		monster.getMaxStats().getStatsAsMap().put(e.getKey(), e.getValue());
	    }
	});
	return monster;
    }
}
