package model.monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.battle.Moves;
import model.gameitem.GameItem;

public class MonsterSpeciesBuilderImpl implements MonsterSpeciesBuilder {

    private String name;
    private String info;
    private MonsterType type;
    private Optional<Integer> evolutionLevel = Optional.empty();
    private MonsterStats stats = new MonsterStatsImpl(1, 1, 1, 1);
    private MonsterSpecies evolution;
    private Optional<GameItem> gameItem = Optional.empty();
    private List<Moves> movesList = new ArrayList<>();;

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterSpeciesBuilder name(final String name) {
        this.name = name;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterSpeciesBuilder info(final String info) {
        this.info = info;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterSpeciesBuilder monsterType(final MonsterType type) {
        this.type = type;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterSpeciesBuilder evolution(final MonsterSpecies evolution) {
        this.evolution = evolution;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterSpeciesBuilder evolutionLevel(final int level) {
        this.evolutionLevel = Optional.of(level);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterSpeciesBuilder health(final int health) {
        this.stats.setHealth(health);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterSpeciesBuilder attack(final int attack) {
        this.stats.setAttack(attack);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterSpeciesBuilder defense(final int defense) {
        this.stats.setDefense(defense);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterSpeciesBuilder speed(final int speed) {
        this.stats.setSpeed(speed);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterSpeciesBuilder gameItem(final GameItem gameItem) {
        this.gameItem = Optional.of(gameItem);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterSpeciesBuilder movesList(final List<Moves> movesList) {
        this.movesList = movesList;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterSpecies build() {
        if (name == null || this.info == null || this.type == null
                || this.evolution != null && this.evolutionLevel.isEmpty() && this.gameItem.isEmpty()
                || this.movesList.isEmpty()) {
            throw new IllegalStateException();
        }
        if (this.evolutionLevel.isPresent()) {
            return new MonsterSpeciesByLevel(this.name, this.info, this.type, this.stats, this.evolution,
                    this.evolutionLevel.get(), this.movesList);
        }
        if (this.gameItem.isPresent()) {
            return new MonsterSpeciesByItem(name, info, type, stats, evolution, this.gameItem.get(), this.movesList);
        }
        return new MonsterSpeciesSimple(this.name, this.info, this.type, this.stats, this.movesList);

    }
}
