package model.monster;

import java.util.List;
import java.util.Optional;

import model.battle.Moves;
import model.gameitem.GameItem;

public class MonsterSpeciesByItem extends MonsterSpeciesSimple {

    private final GameItem evolutionItem;

    /**
     * MonsterSpeciesByItem constructor.
     * 
     * @param name
     * @param info
     * @param type
     * @param stats
     * @param evolution
     * @param evolutionItem
     * @param movesList
     */
    public MonsterSpeciesByItem(final String name, final String info, final MonsterType type, final MonsterStats stats,
            final MonsterSpecies evolution, final GameItem evolutionItem, final List<Moves> movesList) {
        super(name, info, type, stats, Optional.of(evolution), EvolutionType.ITEM, movesList);
        this.evolutionItem = evolutionItem;
    }

    /**
     * {@inheritDoc}
     */
    public GameItem getItem() {
        return this.evolutionItem;
    }
}
