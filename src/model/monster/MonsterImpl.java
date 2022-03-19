package model.monster;

import java.util.ArrayList;
import java.util.List;
import model.GameItem.*;
import model.battle.Moves;

/* TODO: 
 * ~fare un'unica funzione getStats
 * ~aumentare il valore delle statistiche in onLevelup. Utilizza una solo costante del tipo "MAX_STAT_STEP" e
 * fai salire le statistiche in modo randomico con new Random().nextInt(MAX_STAT_STEP)
 */

public class MonsterImpl implements Monster {

    private static final int EXP_CAP = 1000;
    private static final int MAX_LVL = 100;

    
    private int exp;
    private int level;
    private boolean isWild;
    private int maxHealth;
    private MonsterSpeciesImpl species;
    private List<Moves> movesList;
    private MonsterStatsImpl stats;

    public MonsterImpl(int health, int attack, int def, int speed, int exp, int level, boolean isWild, MonsterSpeciesImpl species,
	    List<Moves> movesList) {
    this.stats = new MonsterStatsImpl(health,attack,def,speed);
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
	if (species.getEvolutionType() == EvolutionType.LEVEL && this.level >= species.getEvolutionLevel()) {
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
		&& this.level >= species.getEvolutionLevel()) {
	    species = species.getEvolution().get();
	    return true;
	}
	return false;
    }

    @Override
    public boolean evolveByItem(GameItems item) {
	if (species.getEvolution().isPresent() && this.species.getEvolutionType() == EvolutionType.ITEM
		&& item.equals(species.getItem()) && item.getType() == GameItemTypes.EVOLUTIONTOOL) {
	    species = species.getEvolution().get();
	    return true;
	}
	return false;
    }

    @Override
    public MonsterSpeciesImpl getSpecies() {
	return this.species;
    }

    @Override
    public String toString() {
	return this.species.toString() + "\nHealth: " + this.stats.getHealth() + "\nLevel: " + this.level + "\nExp: " + this.exp
		+ "\nMoves:" + this.movesList.toString() + "\nIsWild: " + this.isWild + "\n";

    }

}
