package model.monster;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import model.battle.Moves;

public class MonsterSpeciesSimple implements MonsterSpecies {

    private final Optional<MonsterSpecies> evolution;
    private final String name;
    private final String info;
    private final MonsterType type;
    private final EvolutionType evolutionType;
    private final MonsterStats stats;
    private final List<Moves> movesList;

    /**
     * MonsterSpeciesSimple protected constructor.
     * 
     * @param name
     * @param info
     * @param type
     * @param stats
     * @param evolution
     * @param evolutionType
     * @param movesList
     */
    protected MonsterSpeciesSimple(final String name, final String info, final MonsterType type,
            final MonsterStats stats, final Optional<MonsterSpecies> evolution, final EvolutionType evolutionType,
            final List<Moves> movesList) {
        this.name = name;
        this.info = info;
        this.type = type;
        this.stats = stats;
        this.evolution = evolution;
        this.evolutionType = evolutionType;
        this.movesList = movesList;
    }

    /**
     * Constructor for MonsterSpeciesSimple.
     * 
     * @param name
     * @param info
     * @param type
     * @param stats
     * @param movesList
     */
    public MonsterSpeciesSimple(final String name, final String info, final MonsterType type, final MonsterStats stats,
            final List<Moves> movesList) {
        this(name, info, type, stats, Optional.empty(), EvolutionType.NONE, movesList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterType getType() {
        return this.type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterStats getBaseStats() {
        return this.stats;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvolutionType getEvolutionType() {
        return this.evolutionType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MonsterSpecies> getEvolution() {
        return this.evolution;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Moves> getAllLearnableMoves() {
        return Collections.unmodifiableList(this.movesList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Name: " + this.name + "\nType: " + this.type + "\nInfo: " + this.info;
    }

}
