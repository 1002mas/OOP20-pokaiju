package model.monster;

import java.util.ArrayList;
import java.util.List;

import model.battle.Attack;
import model.item.Item;

//STATISTICHE MOSTRI IN BATTAGLIA ATK, DFS, VEL

public class MonsterImpl implements Monster {

	private static final int EXP_CAP = 1000;
	private static final int MAX_LVL = 100;
	private static final int HEALTH_STEP = 10;
	private static final int LEVEL_STEP = 1;
	private static final int FIRST_EVOLUTION_LEVEL = 14;

	private int health;
	private int exp;
	private int level;
	private boolean isWild;
	private int maxHealth;
	private MonsterSpecies species;
	private List<Attack> attackList;

	public MonsterImpl(int health, int exp, int level, boolean isWild, MonsterSpecies species,
			List<Attack> attackList) {
		this.health = health;
		this.exp = exp;
		this.level = level;
		this.isWild = isWild;
		this.species = species;
		this.attackList = new ArrayList<>(attackList);
	}

	public int getHealth() {
		return this.health;
	}

	public void setHealth(int health) {
		this.health = health <= this.getMaxHealth() ? health : this.getMaxHealth();
	}

	public int getMaxHealth() {
		return this.maxHealth;
	}

	public String getInfo() {
		return this.species.getInfo();
	}

	public String getName() {
		return this.species.getName();
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level <= MAX_LVL ? level : MAX_LVL;
	}

	public void incExp(int experience) {
		int newLevel = level + (this.exp + experience) / EXP_CAP;
		setLevel(newLevel);
		this.exp = (this.exp + experience) % EXP_CAP;
	}

	public int getExp() {
		return this.exp;
	}

	public int getExpCap() {
		return EXP_CAP;
	}

	public boolean getWild() {
		return false;
	}

	public boolean isAlive() {
		return this.health <= 0;
	}

	public Attack getAttack(int index) {
		if (index <= 0 || index > getNumberOfAttacks()) {
			throw new IllegalArgumentException();
		}
		return this.attackList.get(index);
	}

	public int getNumberOfAttacks() {
		return this.attackList.size();
	}

	public MonsterType getType() {
		return this.species.getType();
	}

	public boolean evolveByLevel() {
		if(species.getEvolution().isPresent() && this.species.getEvolutionType() == EvolutionType.LEVEL
				&& this.level >= species.getEvolutionLevel()) {
			species = species.getEvolution().get();
			return true;
		}
		return false;
	}
	
	public boolean evolveByItem(Item item) {
		if(species.getEvolution().isPresent() && this.species.getEvolutionType() == EvolutionType.ITEM
				&& item == species.getItem()) {
			species = species.getEvolution().get();
			return true;
		}
		return false;
	}
	
	public String toString() { 
		return this.species.toString() + "\nHealth: " + health + "\nLevel: " + level + "\nExp: " + exp + "\nIsWild: " + isWild + "\n"; 
	}
	
	
}
