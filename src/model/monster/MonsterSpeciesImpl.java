package model.monster;
import java.util.Optional;

import model.item.Item;

//a single instance of this class represents a monster species  

public class MonsterSpeciesImpl implements MonsterSpecies{
	
	private final Optional<MonsterSpeciesImpl> evolution;
	private final String name;
	private final String info;
	private final MonsterType type;
	private int evolutionLevel;
	private final EvolutionType evolutionType;
	private Item evolutionItem;
	
	private MonsterSpeciesImpl(String name, String info, MonsterType type, Optional<MonsterSpeciesImpl> evolution, 
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
	public MonsterSpeciesImpl(String name, String info, MonsterType type) {
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
	public MonsterSpeciesImpl(String name, String info, MonsterType type, MonsterSpeciesImpl evolution, int level) {
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
	public MonsterSpeciesImpl(String name, String info, MonsterType type, MonsterSpeciesImpl evolution, Item evolutionItem) {
		this(name, info, type, Optional.of(evolution), EvolutionType.ITEM, 0, evolutionItem);
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String getInfo() {
		return this.info;
	}
	
	@Override
	public MonsterType getType() {
		return this.type;
	}
	
	@Override
	public int getEvolutionLevel() {
		if(EvolutionType.LEVEL != this.evolutionType) {
			throw new IllegalStateException();
		}
		return this.evolutionLevel;
	}
	
	@Override
	public EvolutionType getEvolutionType() {
		return this.evolutionType;
	}
	
	@Override
	public Optional<MonsterSpeciesImpl> getEvolution(){
		return this.evolution;
	}
	
	@Override
	public Item getItem() {
		return this.evolutionItem;
	}
	
	@Override
	public String toString() {
		return "Name: " + this.name.toUpperCase() + "\nType: " + this.type + "\nInfo: " + this.info;
	}
	
}
