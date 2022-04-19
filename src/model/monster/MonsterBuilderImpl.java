package model.monster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import model.Pair;
import model.battle.Moves;

public class MonsterBuilderImpl implements MonsterBuilder {

    private static final String LASTIDPATH = "data/generatedId.dat";
    private static final int MIN_LEVEL = 1;
    private static int id;
    private int exp;
    private List<Pair<Moves, Integer>> movesList;
    private boolean isWild;
    private int level = MIN_LEVEL;
    private MonsterSpecies species;
    private MonsterStats stats = new MonsterStatsImpl(-1, -1, -1, -1);

    static {
        final InputStream fileStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(LASTIDPATH);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(fileStream));) {
            id = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterBuilder species(final MonsterSpecies species) {
        this.species = species;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterBuilder level(final int lvl) {
        if (lvl < MIN_LEVEL || lvl > MonsterImpl.MAX_LVL) {
            throw new IllegalArgumentException();
        }
        this.level = lvl;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterBuilder health(final int health) {
        this.stats.setHealth(health);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterBuilder attack(final int atk) {
        this.stats.setAttack(atk);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterBuilder defense(final int defense) {
        this.stats.setDefense(defense);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterBuilder speed(final int speed) {
        this.stats.setSpeed(speed);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterBuilder exp(final int exp) {
        if (exp < 0 || exp > MonsterImpl.EXP_CAP) {
            throw new IllegalArgumentException();
        }
        this.exp = exp;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterBuilder wild(final boolean isWild) {
        this.isWild = isWild;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterBuilder movesList(final List<Pair<Moves, Integer>> movesList) {
        this.movesList = new ArrayList<>(movesList);
        this.movesList = this.movesList.subList(0,
                movesList.size() < MonsterImpl.NUM_MAX_MOVES ? movesList.size() : MonsterImpl.NUM_MAX_MOVES);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Monster build() {
        if (id <= 0 || this.species == null || this.movesList.isEmpty()) {
            throw new IllegalStateException();
        }
        final Monster monster = new MonsterImpl(id, this.species.getBaseStats(), 0, MIN_LEVEL, this.isWild,
                this.species, this.movesList);
        id++;
        for (int i = MIN_LEVEL; i < this.level; i++) {
            monster.levelUp();
        }
        monster.incExp(exp);
        stats.getStatsAsMap().entrySet().forEach(e -> {
            if (e.getValue() > 0) {
                monster.getStats().getStatsAsMap().put(e.getKey(), e.getValue());
                monster.getMaxStats().getStatsAsMap().put(e.getKey(), e.getValue());
            }
        });
        return monster;
    }
}
