package model.monster;

import java.util.List;
import java.util.Optional;

import model.battle.Moves;
import model.gameitem.GameItem;

public class MonsterSpeciesByItem extends MonsterSpeciesImpl {

    private GameItem evolutionItem;

    public MonsterSpeciesByItem(String name, String info, MonsterType type, MonsterStats stats, MonsterSpecies evolution,
	    GameItem evolutionItem, List<Moves> movesList) {
	super(name, info, type, stats, Optional.of(evolution), EvolutionType.ITEM, movesList);
	this.evolutionItem = evolutionItem;
    }

    public GameItem getItem() {
	return this.evolutionItem;
    }
}
