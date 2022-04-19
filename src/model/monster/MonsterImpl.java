package model.monster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import model.Pair;
import model.battle.Moves;
import model.gameitem.GameItem;
import model.gameitem.GameItemTypes;

public class MonsterImpl implements Monster {

    /**
     * Monster's maximum experience.
     */
    public static final int EXP_CAP = 1000;

    /**
     * Monster's maximum level.
     */
    public static final int MAX_LVL = 100;

    /**
     * Maximum number of moves of a monster.
     */
    public static final int NUM_MAX_MOVES = 4;

    private static final int MAX_HP_STEP = 40;
    private static final int MIN_HP_STEP = 10;
    private static final int MAX_STAT_STEP = 10;
    private static final int MIN_STAT_STEP = 1;

    private final int id;
    private int exp;
    private int level;
    private final boolean isWild;
    private MonsterSpecies species;
    private final List<Pair<Moves, Integer>> movesList;
    private final MonsterStats stats;
    private final MonsterStats maxStats;

    /**
     * Constructor for MonsterImpl.
     * 
     * @param id
     * @param stats
     * @param exp
     * @param level
     * @param isWild
     * @param species
     * @param movesList
     */
    public MonsterImpl(final int id, final MonsterStats stats, final int exp, final int level, final boolean isWild,
            final MonsterSpecies species, final List<Pair<Moves, Integer>> movesList) {
        this.id = id;
        this.maxStats = new MonsterStatsImpl(stats.getHealth(), stats.getAttack(), stats.getDefense(),
                stats.getSpeed());
        this.stats = new MonsterStatsImpl(maxStats.getHealth(), maxStats.getAttack(), maxStats.getDefense(),
                maxStats.getSpeed());
        this.exp = exp;
        this.level = level;
        this.isWild = isWild;
        this.species = species;
        this.movesList = new ArrayList<>(movesList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.species.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHealth(final int health) {
        this.stats.setHealth(health <= this.getMaxHealth() ? health : this.getMaxHealth());
        this.stats.setHealth(health <= 0 ? 0 : this.stats.getHealth());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restoreStats() {
        final Map<String, Integer> maxStatsMap = this.maxStats.getStatsAsMap();
        final Map<String, Integer> statsMap = this.stats.getStatsAsMap();
        maxStatsMap.entrySet().forEach(e -> statsMap.put(e.getKey(), e.getValue()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxHealth() {
        return this.maxStats.getHealth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInfo() {
        return this.species.getInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLevel() {
        return this.level;
    }

    private void setLevel(final int level) {
        this.level = level <= MAX_LVL ? level : MAX_LVL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void levelUp() {
        this.level++;
        this.exp = 0;
        onLevelUp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incExp(final int experience) {
        int incLevel = (this.exp + experience) / EXP_CAP;
        final int oldLevel = this.level;
        setLevel(incLevel + level);
        incLevel = this.level - oldLevel;
        this.exp = (this.exp + experience) % EXP_CAP;
        if (this.level == MAX_LVL) {
            this.exp = 0;
        }
        if (incLevel > 0) {
            onLevelUp();
        }
    }

    private void onLevelUp() {
        this.maxStats.setHealth(this.maxStats.getHealth()
                + ThreadLocalRandom.current().nextInt(MAX_HP_STEP - MIN_HP_STEP) + MIN_HP_STEP);
        this.maxStats.setAttack(this.maxStats.getAttack()
                + ThreadLocalRandom.current().nextInt(MAX_STAT_STEP - MIN_STAT_STEP) + MIN_STAT_STEP);
        this.maxStats.setDefense(this.maxStats.getDefense()
                + ThreadLocalRandom.current().nextInt(MAX_STAT_STEP - MIN_STAT_STEP) + MIN_STAT_STEP);
        this.maxStats.setSpeed(this.maxStats.getSpeed()
                + ThreadLocalRandom.current().nextInt(MAX_STAT_STEP - MIN_STAT_STEP) + MIN_STAT_STEP);
        restoreStats();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getExp() {
        return this.exp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getExpCap() {
        return EXP_CAP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWild() {
        return this.isWild;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return this.stats.getHealth() > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Moves getMoves(final int index) {
        if (index < 0 || index > getNumberOfMoves()) {
            throw new IllegalArgumentException();
        }
        return this.movesList.get(index).getFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Moves> getAllMoves() {
        return Collections.unmodifiableList(movesList.stream().map(i -> i.getFirst()).collect(Collectors.toList()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentPPByMove(final Moves move) {
        return movesList.stream().filter(i -> i.getFirst().equals(move)).findAny().get().getSecond();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOutOfPP(final Moves move) {
        return movesList.stream().filter(i -> i.getFirst().equals(move)).findAny().get().getSecond() <= 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restoreMovePP(final Moves move) {
        final int index = getIndexOfMove(move);
        final Pair<Moves, Integer> p = movesList.get(index);
        movesList.remove(index);
        movesList.add(index, new Pair<>(p.getFirst(), p.getFirst().getPP()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restoreAllMovesPP() {
        final List<Moves> moves = movesList.stream().map(i -> i.getFirst()).collect(Collectors.toList());
        for (final var p : moves) {
            restoreMovePP(p);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decMovePP(final Moves move) {
        final int index = getIndexOfMove(move);
        final Pair<Moves, Integer> p = movesList.get(index);
        movesList.remove(index);
        movesList.add(index, new Pair<>(p.getFirst(), p.getSecond() - 1));
    }

    private int getIndexOfMove(final Moves move) {
        for (int i = 0; i < this.movesList.size(); i++) {
            if (this.movesList.get(i).getFirst().equals(move)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMoveSetFull() {
        return this.movesList.size() == NUM_MAX_MOVES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfMoves() {
        return this.movesList.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterType getType() {
        return this.species.getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canEvolveByLevel() {
        return species.getEvolution().isPresent() && this.species.getEvolutionType() == EvolutionType.LEVEL
                && this.level >= ((MonsterSpeciesByLevel) species).getEvolutionLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canEvolveByItem(final GameItem item) {
        return species.getEvolution().isPresent() && this.species.getEvolutionType() == EvolutionType.ITEM
                && item.equals(((MonsterSpeciesByItem) species).getItem())
                && item.getType() == GameItemTypes.EVOLUTIONTOOL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void evolve() {
        if (this.species.getEvolution().isPresent()) {
            this.species = this.species.getEvolution().get();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterSpecies getSpecies() {
        return this.species;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterStats getStats() {
        return this.stats;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterStats getMaxStats() {
        return this.maxStats;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MonsterImpl other = (MonsterImpl) obj;
        return id == other.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.species.toString() + "\nHealth: " + this.stats.getHealth() + "\nAttack: " + this.stats.getAttack()
                + "\nDefense: " + this.stats.getDefense() + "\nSpeed: " + this.stats.getSpeed() + "\nLevel: "
                + this.level + "\nExp: " + this.exp + "\nMoves:" + this.movesList.toString() + "\nIsWild: "
                + this.isWild + "\n";
    }
}
