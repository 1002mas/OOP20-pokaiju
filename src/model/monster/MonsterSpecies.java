package model.monster;
import java.util.Optional;

import model.item.Item;

//a single instance of this class represents a monster species  
//TODO: interface

public class MonsterSpecies {
	
	private final Optional<MonsterSpecies> evolution;
	private final String name;
	private final String info;
	private final MonsterType type;
	private int evolutionLevel;
	private final EvolutionType evolutionType;
	private Item evolutionItem;
	
	private MonsterSpecies(String name, String info, MonsterType type, Optional<MonsterSpecies> evolution, 
			EvolutionType evolutionType, int level, Item evolutionItem) {
		this.name = name;
		this.info = info;
		this.type = type;
		this.evolution = evolution;
		this.evolutionType = evolutionType;
		this.evolutionLevel = level;
		this.evolutionItem = evolutionItem;
	}
	
	/**
	 * Builder for a monster that doesn't evolve
	 * @param name
	 * @param info
	 * @param type
	 */
	public MonsterSpecies(String name, String info, MonsterType type) {
		this(name, info, type, Optional.empty(), EvolutionType.NONE, 0, null);
	}
	
	/**
	 * Builder for a monster that evolves by level
	 * @param name
	 * @param info
	 * @param type
	 * @param evolution
	 * @param level
	 */
	public MonsterSpecies(String name, String info, MonsterType type, MonsterSpecies evolution, int level) {
		this(name, info, type, Optional.of(evolution), EvolutionType.LEVEL, level, null);
	} 
	
	/**
	 * Builder for a monster that evolves by item
	 * @param name
	 * @param info
	 * @param type
	 * @param evolution
	 * @param evolutionItem
	 */
	public MonsterSpecies(String name, String info, MonsterType type, MonsterSpecies evolution, Item evolutionItem) {
		this(name, info, type, Optional.of(evolution), EvolutionType.ITEM, 0, evolutionItem);
	}

	public String getInfo() {
		return this.info;
	}

	public String getName() {
		return this.name;
	}
	
	public MonsterType getType() {
		return this.type;
	}
	
	public int getEvolutionLevel() {
		if(EvolutionType.LEVEL != this.evolutionType) {
			throw new IllegalStateException();
		}
		return this.evolutionLevel;
	}
	
	public EvolutionType getEvolutionType() {
		return this.evolutionType;
	}
	
	public Optional<MonsterSpecies> getEvolution(){
		return this.evolution;
	}
	
	public Item getItem() {
		return this.evolutionItem;
	}
	
	public String toString() {
		return "Name: " + this.name.toUpperCase() + "\nType: " + this.type + "\nInfo: " + this.info + "\n";
	}
	
}
