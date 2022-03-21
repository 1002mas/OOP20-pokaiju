package model.monster;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.GameItem.GameItemTypes;
import model.GameItem.GameItems;
import model.battle.Moves;

public class MonsterImpl implements Monster {

    private static final int EXP_CAP = 1000;
    private static final int MAX_LVL = 100;
    private static final int MAX_HP_STEP = 40;
    private static final int MIN_HP_STEP = 10;
    private static final int MAX_STAT_STEP = 10;
    private static final int MIN_STAT_STEP = 1;

    private int exp;
    private int level;
    private boolean isWild;
    private int maxHealth;
    private MonsterSpecies species;
    private List<Moves> movesList;
    private MonsterStats stats;

    public MonsterImpl(MonsterStats stats, int exp, int level, boolean isWild, MonsterSpecies species,
	    List<Moves> movesList) {
	this.stats = stats;
	this.maxHealth = this.stats.getHealth();
	this.exp = exp;
	this.level = level;
	this.isWild = isWild;
	this.species = species;
	this.movesList = new ArrayList<>(movesList);
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

    public String getInfo() {
	return this.species.getInfo();
    }

    @Override
    public int getLevel() {
	return this.level;
    }

    @Override
    public void setLevel(int level) {
	this.level = level <= MAX_LVL ? level : MAX_LVL;
	onLevelUp();
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
	    onLevelUp();
	}
    }

    private void onLevelUp() {
	this.stats.setHealth(this.stats.getHealth() + new Random().nextInt(MAX_HP_STEP - MIN_HP_STEP) + MIN_HP_STEP);
	this.stats.setAttack(this.stats.getAttack() + new Random().nextInt(MAX_STAT_STEP - MIN_STAT_STEP) + MIN_STAT_STEP);
	this.stats.setDefense(this.stats.getDefense() + new Random().nextInt(MAX_STAT_STEP - MIN_STAT_STEP) + MIN_STAT_STEP);
	this.stats.setSpeed(this.stats.getSpeed() + new Random().nextInt(MAX_STAT_STEP - MIN_STAT_STEP) + MIN_STAT_STEP);
	if (species.getEvolutionType() == EvolutionType.LEVEL && this.level >= ((MonsterSpeciesByLevel) species).getEvolutionLevel()) {
	    evolveByLevel();
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
	return this.stats.getHealth() <= 0;
    }

    @Override
    public Moves getMoves(int index) {
	if (index <= 0 || index > getNumberOfMoves()) {
	    throw new IllegalArgumentException();
	}
	return this.movesList.get(index);
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
		&& item.equals(((MonsterSpeciesByItem)species).getItem()) && item.getType() == GameItemTypes.EVOLUTIONTOOL) {
	    species = species.getEvolution().get();
	    return true;
	}
	return false;
    }

    @Override
    public MonsterSpecies getSpecies() {
	return this.species;
    }

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
