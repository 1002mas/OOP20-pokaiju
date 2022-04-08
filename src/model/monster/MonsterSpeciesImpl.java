package model.monster;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.Pair;
import model.battle.Moves;

//a single instance of this class represents a monster species  

public class MonsterSpeciesImpl implements MonsterSpecies {

    private final Optional<MonsterSpecies> evolution;
    private final String name;
    private final String info;
    private final MonsterType type;
    private final EvolutionType evolutionType;
    private final MonsterStats stats;
    private final List<Pair<Moves, Integer>> allMoves;

    protected MonsterSpeciesImpl(String name, String info, MonsterType type, MonsterStats stats,
	    Optional<MonsterSpecies> evolution, EvolutionType evolutionType, List<Pair<Moves, Integer>> allMoves) {
	this.name = name;
	this.info = info;
	this.type = type;
	this.stats = stats;
	this.evolution = evolution;
	this.evolutionType = evolutionType;
	this.allMoves = allMoves;
    }
    
    public MonsterSpeciesImpl(String name, String info, MonsterType type, MonsterStats stats, List<Pair<Moves, Integer>> allMoves) {
   	this(name, info, type, stats, Optional.empty(), EvolutionType.NONE, allMoves);
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
    public List<Moves> getAllLearnableMoves(int level) {
	return allMoves.stream().filter(p -> p.getSecond() <= level).map(p -> p.getFirst()).collect(Collectors.toList());
    }

    public Optional<Moves> learnNewMove(int level) {
	return allMoves.stream().filter(p -> p.getSecond() == level).map(p -> p.getFirst()).findAny();
    }

    @Override
    public String toString() {
	return "Name: " + this.name.toUpperCase() + "\nType: " + this.type + "\nInfo: " + this.info;
    }
}
