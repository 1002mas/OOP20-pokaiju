package model.monster;

import java.util.Optional;

import model.gameitem.GameItems;

public class MonsterSpeciesByItem extends AbstractMonsterSpecies {

    private GameItems evolutionItem;

    public MonsterSpeciesByItem(String name, String info, MonsterType type, MonsterSpecies evolution,
	    GameItems evolutionItem) {
	super(name, info, type, Optional.of(evolution), EvolutionType.ITEM);
	this.evolutionItem = evolutionItem;
    }

    public GameItems getItem() {
	return this.evolutionItem;
    }

}
