package model.monster;

import java.util.List;
import java.util.Optional;
import model.Pair;
import model.battle.Moves;
import model.gameitem.GameItems;

public class MonsterSpeciesByItem extends MonsterSpeciesImpl {

    private GameItems evolutionItem;

    public MonsterSpeciesByItem(String name, String info, MonsterType type, MonsterStats stats, MonsterSpecies evolution,
	    GameItems evolutionItem, List<Pair<Moves, Integer>> allMoves) {
	super(name, info, type, stats, Optional.of(evolution), EvolutionType.ITEM, allMoves);
	this.evolutionItem = evolutionItem;
    }

    public GameItems getItem() {
	return this.evolutionItem;
    }
}
