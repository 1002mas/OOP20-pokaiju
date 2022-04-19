package model.monster;

import java.util.List;
import java.util.Optional;

import model.battle.Moves;

public class MonsterSpeciesByLevel extends MonsterSpeciesSimple {

    private final int evolutionLevel;

    /**
     * MonsterSpeciesByLevel constructor.
     * 
     * @param name
     * @param info
     * @param type
     * @param stats
     * @param evolution
     * @param evolutionLevel
     * @param movesList
     */
    public MonsterSpeciesByLevel(final String name, final String info, final MonsterType type, final MonsterStats stats,
            final MonsterSpecies evolution, final int evolutionLevel, final List<Moves> movesList) {
        super(name, info, type, stats, Optional.of(evolution), EvolutionType.LEVEL, movesList);
        this.evolutionLevel = evolutionLevel;
    }

    /**
     * {@inheritDoc}
     */
    public int getEvolutionLevel() {
        return this.evolutionLevel;
    }
}
