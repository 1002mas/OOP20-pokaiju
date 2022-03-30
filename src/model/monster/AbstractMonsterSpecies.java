package model.monster;

import java.util.Optional;

//a single instance of this class represents a monster species  

public abstract class AbstractMonsterSpecies implements MonsterSpecies {

    private final Optional<MonsterSpecies> evolution;
    private final String name;
    private final String info;
    private final MonsterType type;
    private final EvolutionType evolutionType;
    private final MonsterStats stats;

    protected AbstractMonsterSpecies(String name, String info, MonsterType type, MonsterStats stats,
	    Optional<MonsterSpecies> evolution, EvolutionType evolutionType) {
	this.name = name;
	this.info = info;
	this.type = type;
	this.stats = stats;
	this.evolution = evolution;
	this.evolutionType = evolutionType;

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
    public MonsterStats getStats() {
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
    public String toString() {
	return "Name: " + this.name.toUpperCase() + "\nType: " + this.type + "\nInfo: " + this.info;
    }

}
