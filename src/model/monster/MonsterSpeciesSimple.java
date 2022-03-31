package model.monster;


import java.util.List;
import java.util.Optional;

import model.Pair;
import model.battle.Moves;



public class MonsterSpeciesSimple extends AbstractMonsterSpecies {

    public MonsterSpeciesSimple(String name, String info, MonsterType type, MonsterStats stats, List<Pair<Moves, Integer>> allMoves) {
	super(name, info, type, stats, Optional.empty(), EvolutionType.NONE, allMoves);
    }
}
