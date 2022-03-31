package model.monster;

import java.util.List;
import java.util.Optional;

import model.Pair;
import model.battle.Moves;

public class MonsterSpeciesByLevel extends AbstractMonsterSpecies {

    private int evolutionLevel;

    public MonsterSpeciesByLevel(String name, String info, MonsterType type, MonsterStats stats, MonsterSpecies evolution, int evolutionLevel, List<Pair<Moves, Integer>> allMoves) {
	super(name, info, type, stats, Optional.of(evolution), EvolutionType.LEVEL, allMoves);
	this.evolutionLevel = evolutionLevel;
    }

    public int getEvolutionLevel() {
	return this.evolutionLevel;
    }
}
