package model.monster;
import java.util.List;
import model.battle.Moves;

public class MonsterBuilderImpl implements MonsterBuilder {

    private static final int EXP_CAP = 1000;
    private static final int NUM_MAX_MOVES = 4;
    private int exp;
    private List<Moves> movesList;
    private boolean isWild;
    private int level;
    private MonsterSpecies species;
    private MonsterStats stats;

    @Override
    public MonsterBuilder species(MonsterSpecies species) {
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
    public MonsterBuilder stats(MonsterStats stats) {
	this.stats = stats;
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
	this.movesList = movesList.subList(0, NUM_MAX_MOVES);
	return this;
    }

    @Override
    public Monster build() {
	if (this.species == null || this.stats.getHealth() < 0 || this.stats.getAttack() < 0 || this.stats.getDefense() < 0
		|| this.stats.getSpeed() < 0 || this.exp < 0 || this.movesList.isEmpty() || this.level <= 0) {
	    throw new IllegalStateException();
	}
	return new MonsterImpl(this.stats, this.exp, this.level, this.isWild, this.species, this.movesList);
    }
}
