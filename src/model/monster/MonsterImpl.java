package model.monster;
import java.util.ArrayList;
import java.util.List;
import model.item.Item;
import model.item.ItemTypes;
import model.battle.Moves;

public class MonsterImpl implements Monster {

	private static final int EXP_CAP = 1000;
	private static final int MAX_LVL = 100;

	private int health;
	private int attack;
	private int defense;
	private int speed;
	private int exp;
	private int level;
	private boolean isWild;
	private int maxHealth;
	private MonsterSpeciesImpl species;
	private List<Moves> movesList;

	public MonsterImpl(int health, int exp, int level, boolean isWild, MonsterSpeciesImpl species,
			List<Moves> movesList, int attack, int defense, int speed) {
		this.health = health;
		this.exp = exp;
		this.level = level;
		this.isWild = isWild;
		this.species = species;
		this.movesList = new ArrayList<>(movesList);
		this.attack = attack;
		this.defense = defense;
		this.speed = speed;
	}

	public String getName() {
		return this.species.getName();
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

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level <= MAX_LVL ? level : MAX_LVL;
	}

	public void incExp(int experience) {
		int incLevel = (this.exp + experience) / EXP_CAP;
		setLevel(incLevel + level);
		this.exp = (this.exp + experience) % EXP_CAP;
		if(this.level == MAX_LVL) {
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

	public int getExp() {
		return this.exp;
	}

	public int getExpCap() {
		return EXP_CAP;
	}

	public int getAttack() {
		return this.attack;
	}

	public int getDefense() {
		return this.defense;
	}

	public int getSpeed() {
		return this.speed;
	}

	public boolean getWild() {
		return this.isWild;
	}

	public boolean isAlive() {
		return this.health <= 0;
	}

	public Moves getMoves(int index) {
		if (index <= 0 || index > getNumberOfMoves()) {
			throw new IllegalArgumentException();
		}
		return this.movesList.get(index);
	}

	public int getNumberOfMoves() {
		return this.movesList.size();
	}

	public MonsterType getType() {
		return this.species.getType();
	}

	public boolean evolveByLevel() {
		if (species.getEvolution().isPresent() && this.species.getEvolutionType() == EvolutionType.LEVEL
				&& this.level >= species.getEvolutionLevel()) {
			species = species.getEvolution().get();
			return true;
		}
		return false;
	}

	public boolean evolveByItem(Item item) {
		if (species.getEvolution().isPresent() && this.species.getEvolutionType() == EvolutionType.ITEM
				&& item.equals(species.getItem()) && item.getType() == ItemTypes.EVOLUTIONTOOL) {
			species = species.getEvolution().get();
			return true;
		}
		return false;
	}

	public String toString() {
		return this.species.toString() + "\nHealth: " + this.health + "\nLevel: " + this.level + "\nExp: " + this.exp + "\nAtk: "
				+ this.attack + "\nDfs: " + this.defense + "\nSpd: " + this.speed + "\nMoves:" + this.movesList.toString() +"\nIsWild: " + this.isWild + "\n";

	}

}
