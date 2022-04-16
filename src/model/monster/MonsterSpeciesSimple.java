package model.monster;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import model.battle.Moves;

//a single instance of this class represents a monster species  

public class MonsterSpeciesSimple implements MonsterSpecies {

    private final Optional<MonsterSpecies> evolution;
    private final String name;
    private final String info;
    private final MonsterType type;
    private final EvolutionType evolutionType;
    private final MonsterStats stats;
    private final List<Moves> movesList;

    protected MonsterSpeciesSimple(String name, String info, MonsterType type, MonsterStats stats,
	    Optional<MonsterSpecies> evolution, EvolutionType evolutionType, List<Moves> movesList) {
	this.name = name;
	this.info = info;
	this.type = type;
	this.stats = stats;
	this.evolution = evolution;
	this.evolutionType = evolutionType;
	this.movesList = movesList;
    }
    
    public MonsterSpeciesSimple(String name, String info, MonsterType type, MonsterStats stats, List<Moves> movesList) {
   	this(name, info, type, stats, Optional.empty(), EvolutionType.NONE, movesList);
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
    public MonsterStats getBaseStats() {
	return this.stats;
    }

    @Override
    public EvolutionType getEvolutionType() {
	return this.evolutionType;
    }

    @Override
    public Optional<MonsterSpecies> getEvolution() {
	return this.evolution;
    }
    
    @Override
    public List<Moves> getAllLearnableMoves() {
	return Collections.unmodifiableList(this.movesList);
    }

    @Override
    public String toString() {
	return "Name: " + this.name.toUpperCase() + "\nType: " + this.type + "\nInfo: " + this.info;
    }

}
