package model.monster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import model.battle.Moves;
import model.gameitem.GameItemTypes;
import model.gameitem.GameItems;

public class MonsterImpl implements Monster {

    private static final int EXP_CAP = 1000;
    private static final int MAX_LVL = 100;
    private static final int MAX_HP_STEP = 40;
    private static final int MIN_HP_STEP = 10;
    private static final int MAX_STAT_STEP = 10;
    private static final int MIN_STAT_STEP = 1;

    private int id;
    private int exp;
    private int level;
    private boolean isWild;
    private int maxHealth;
    private MonsterSpecies species;
    private List<Moves> movesList;
    private MonsterStats stats;
    private Set<Moves> movesToLearn;

    public MonsterImpl(int id, MonsterStats stats, int exp, int level, boolean isWild, MonsterSpecies species,
	    List<Moves> movesList) {
	this.id = id;
	this.stats = stats;
	this.maxHealth = this.stats.getHealth();
	this.exp = exp;
	this.level = level;
	this.isWild = isWild;
	this.species = species;
	this.movesList = new ArrayList<>(movesList);
	this.movesToLearn = new HashSet<>();
    }
    
    public int getId() {
	return this.id;
    }
    
    @Override
    public String getName() {
	return this.species.getName();
    }

    @Override
    public int getHealth() {
	return this.stats.getHealth();
    }

    @Override
    public void setHealth(int health) {
	this.stats.setHealth(health <= this.getMaxHealth() ? health : this.getMaxHealth());
    }

    @Override
    public int getMaxHealth() {
	return this.maxHealth;
    }

    @Override
    public String getInfo() {
	return this.species.getInfo();
    }

    @Override
    public int getLevel() {
	return this.level;
    }

    private void setLevel(int level) {
	this.level = level <= MAX_LVL ? level : MAX_LVL;
    }

    @Override
    public void incExp(int experience) {
	int incLevel = (this.exp + experience) / EXP_CAP;
	setLevel(incLevel + level);
	this.exp = (this.exp + experience) % EXP_CAP;
	if (this.level == MAX_LVL) {
	    this.exp = 0;
	}
	if (incLevel > 0) {
	    onLevelUp(incLevel);
	}
    }

    private void onLevelUp(int incLevel) {
	this.stats.setHealth(this.stats.getHealth() + new Random().nextInt(MAX_HP_STEP - MIN_HP_STEP) + MIN_HP_STEP);
	this.stats.setAttack(
		this.stats.getAttack() + new Random().nextInt(MAX_STAT_STEP - MIN_STAT_STEP) + MIN_STAT_STEP);
	this.stats.setDefense(
		this.stats.getDefense() + new Random().nextInt(MAX_STAT_STEP - MIN_STAT_STEP) + MIN_STAT_STEP);
	this.stats
		.setSpeed(this.stats.getSpeed() + new Random().nextInt(MAX_STAT_STEP - MIN_STAT_STEP) + MIN_STAT_STEP);
	if (species.getEvolutionType() == EvolutionType.LEVEL
		&& this.level >= ((MonsterSpeciesByLevel) species).getEvolutionLevel()) {
	    evolveByLevel();
	}
	for (; incLevel > 0; incLevel--) {
	    Optional<Moves> moves = species.learnNewMove(this.level - incLevel + 1);
	    if(moves.isPresent() && !movesList.contains(moves.get())) {
		this.movesToLearn.add(moves.get());
	    }
	}
    }

    @Override
    public int getExp() {
	return this.exp;
    }

    @Override
    public int getExpCap() {
	return EXP_CAP;
    }

    @Override
    public boolean getWild() {
	return this.isWild;
    }

    @Override
    public boolean isAlive() {
	return this.stats.getHealth() > 0;
    }

    @Override
    public Moves getMoves(int index) {
	if (index < 0 || index > getNumberOfMoves()) {
	    throw new IllegalArgumentException();
	}
	return this.movesList.get(index);
    }

    @Override
    public List<Moves> getAllMoves() {
	return Collections.unmodifiableList(movesList);
    }

    @Override
    public boolean canLearnNewMove() {
	return !movesToLearn.isEmpty();
    }

    public Moves getMoveToLearn() {
	if (!canLearnNewMove()) {
	    return null;
	}
	Moves m = movesToLearn.stream().findAny().get();
	movesToLearn.remove(m);
	return m;
    }

    @Override
    public int getNumberOfMoves() {
	return this.movesList.size();
    }

    @Override
    public MonsterType getType() {
	return this.species.getType();
    }

    private boolean evolveByLevel() {
	if (species.getEvolution().isPresent() && this.species.getEvolutionType() == EvolutionType.LEVEL
		&& this.level >= ((MonsterSpeciesByLevel) species).getEvolutionLevel()) {
	    species = species.getEvolution().get();
	    return true;
	}
	return false;
    }

    @Override
    public boolean evolveByItem(GameItems item) {
	if (species.getEvolution().isPresent() && this.species.getEvolutionType() == EvolutionType.ITEM
		&& item.equals(((MonsterSpeciesByItem) species).getItem())
		&& item.getType() == GameItemTypes.EVOLUTIONTOOL) {
	    species = species.getEvolution().get();
	    return true;
	}
	return false;
    }

    @Override
    public MonsterSpecies getSpecies() {
	return this.species;
    }

    @Override
    public MonsterStats getStats() {
	return this.stats;
    }

    @Override
    public String toString() {
	return this.species.toString() + "\nHealth: " + this.stats.getHealth() + "\nAttack: " + this.stats.getAttack()
		+ "\nDefense: " + this.stats.getDefense() + "\nSpeed: " + this.stats.getSpeed() + "\nLevel: "
		+ this.level + "\nExp: " + this.exp + "\nMoves:" + this.movesList.toString() + "\nIsWild: "
		+ this.isWild + "\n";
    }
}
