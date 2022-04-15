package model.monster;

import java.util.List;
import java.util.Optional;

import model.battle.Moves;

public class MonsterSpeciesByLevel extends MonsterSpeciesImpl {

    private int evolutionLevel;

    public MonsterSpeciesByLevel(String name, String info, MonsterType type, MonsterStats stats, MonsterSpecies evolution, int evolutionLevel, List<Moves> movesList) {
	super(name, info, type, stats, Optional.of(evolution), EvolutionType.LEVEL, movesList);
	this.evolutionLevel = evolutionLevel;
    }

    public int getEvolutionLevel() {
	return this.evolutionLevel;
    }
}
