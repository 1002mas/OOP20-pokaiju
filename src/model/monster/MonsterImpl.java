package model.monster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import model.Pair;
import model.battle.Moves;
import model.gameitem.GameItem;
import model.gameitem.GameItemTypes;

public class MonsterImpl implements Monster {

    /**
     * Monster's maximum experience
     */
    public static final int EXP_CAP = 1000;
    
    /**
     * Monster's maximum level
     */
    public static final int MAX_LVL = 100;
    
    /**
     * Maximum number of moves of a monster
     */
    public static final int NUM_MAX_MOVES = 4;

    private static final int MAX_HP_STEP = 40;
    private static final int MIN_HP_STEP = 10;
    private static final int MAX_STAT_STEP = 10;
    private static final int MIN_STAT_STEP = 1;

    private int id;
    private int exp;
    private int level;
    private boolean isWild;
    private MonsterSpecies species;
    private List<Pair<Moves, Integer>> movesList;
    private MonsterStats stats;
    private MonsterStats maxStats;

    public MonsterImpl(int id, MonsterStats stats, int exp, int level, boolean isWild, MonsterSpecies species,
	    List<Pair<Moves, Integer>> movesList) {
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

    public int getId() {
	return this.id;
    }

    @Override
    public String getName() {
	return this.species.getName();
    }

    @Override
    public void setHealth(int health) {
	this.stats.setHealth(health <= this.getMaxHealth() ? health : this.getMaxHealth());
	this.stats.setHealth(health <= 0 ? 0 : health);
	System.out.println(this.stats.getHealth());
    }

    @Override
    public void restoreStats() {
	Map<String, Integer> maxStatsMap = this.maxStats.getStatsAsMap();
	Map<String, Integer> statsMap = this.stats.getStatsAsMap();
	maxStatsMap.entrySet().forEach(e -> statsMap.put(e.getKey(), e.getValue()));
    }

    @Override
    public int getMaxHealth() {
	return this.maxStats.getHealth();
    }

    @Override
    public String getInfo() {
	return this.species.getInfo();
    }

    @Override
    public int getLevel() {
	return this.level;
    }

    private void setLevel(int level) {
	this.level = level <= MAX_LVL ? level : MAX_LVL;
    }

    @Override
    public void levelUp() {
	this.level++;
	this.exp = 0;
	onLevelUp(1);
    }

    @Override
    public void incExp(int experience) {
	int incLevel = (this.exp + experience) / EXP_CAP;
	setLevel(incLevel + level);
	this.exp = (this.exp + experience) % EXP_CAP;
	if (this.level == MAX_LVL) {
	    this.exp = 0;
	}
	if (incLevel > 0) {
	    onLevelUp(incLevel);
	}
    }

    private void onLevelUp(int incLevel) {
	Random rand = new Random();
	this.maxStats
		.setHealth(this.maxStats.getHealth() + rand.nextInt(MAX_HP_STEP - MIN_HP_STEP) + MIN_HP_STEP);
	this.maxStats.setAttack(
		this.maxStats.getAttack() + rand.nextInt(MAX_STAT_STEP - MIN_STAT_STEP) + MIN_STAT_STEP);
	this.maxStats.setDefense(
		this.maxStats.getDefense() + rand.nextInt(MAX_STAT_STEP - MIN_STAT_STEP) + MIN_STAT_STEP);
	this.maxStats.setSpeed(
		this.maxStats.getSpeed() + rand.nextInt(MAX_STAT_STEP - MIN_STAT_STEP) + MIN_STAT_STEP);
	restoreStats();
    }

    @Override
    public int getExp() {
	return this.exp;
    }

    @Override
    public int getExpCap() {
	return EXP_CAP;
    }

    @Override
    public boolean getWild() {
	return this.isWild;
    }

    @Override
    public boolean isAlive() {
	return this.stats.getHealth() > 0;
    }

    @Override
    public Moves getMoves(int index) {
	if (index < 0 || index > getNumberOfMoves()) {
	    throw new IllegalArgumentException();
	}
	return this.movesList.get(index).getFirst();
    }

    @Override
    public List<Moves> getAllMoves() {
	return Collections.unmodifiableList(movesList.stream().map(i -> i.getFirst()).collect(Collectors.toList()));
    }

    @Override
    public int getCurrentPPByMove(Moves move) {
	return movesList.stream().filter(i -> i.getFirst().equals(move)).findAny().get().getSecond();
    }

    @Override
    public boolean isOutOfPP(Moves move) {
	return movesList.stream().filter(i -> i.getFirst().equals(move)).findAny().get().getSecond() <= 0;
    }

    @Override
    public void restoreMovePP(Moves move) {
	int index = getIndexOfMove(move);
	Pair<Moves, Integer> p = movesList.get(index);
	movesList.remove(index);
	movesList.add(index, new Pair<>(p.getFirst(), p.getFirst().getPP()));
    }

    @Override
    public void restoreAllMovesPP() {
	for(var p : movesList) {
	    restoreMovePP(p.getFirst());
	}
    }

    @Override
    public void decMovePP(Moves move) {
	int index = getIndexOfMove(move);
	Pair<Moves, Integer> p = movesList.get(index);
	movesList.remove(index);
	movesList.add(index, new Pair<>(p.getFirst(), p.getSecond() - 1));
    }

    private int getIndexOfMove(Moves move) {
	for (int i = 0; i < this.movesList.size(); i++) {
	    if (this.movesList.get(i).getFirst().equals(move)) {
		return i;
	    }
	}
	return -1;
    }

    @Override
    public boolean isMoveSetFull() {
	return this.movesList.size() == NUM_MAX_MOVES;
    }

    @Override
    public int getNumberOfMoves() {
	return this.movesList.size();
    }

    @Override
    public MonsterType getType() {
	return this.species.getType();
    }

    @Override
    public boolean canEvolveByLevel() {
	return species.getEvolution().isPresent() && this.species.getEvolutionType() == EvolutionType.LEVEL
		&& this.level >= ((MonsterSpeciesByLevel) species).getEvolutionLevel();
    }

    @Override
    public boolean canEvolveByItem(GameItem item) {
	return species.getEvolution().isPresent() && this.species.getEvolutionType() == EvolutionType.ITEM
		&& item.equals(((MonsterSpeciesByItem) species).getItem())
		&& item.getType() == GameItemTypes.EVOLUTIONTOOL;
    }

    @Override
    public void evolve() {
	if (this.species.getEvolution().isPresent()) {
	    this.species = this.species.getEvolution().get();
	}
    }

    @Override
    public MonsterSpecies getSpecies() {
	return this.species;
    }

    @Override
    public MonsterStats getStats() {
	return this.stats;
    }

    @Override
    public MonsterStats getMaxStats() {
	return this.maxStats;
    }

    @Override
    public int hashCode() {
	return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	MonsterImpl other = (MonsterImpl) obj;
	return id == other.id;
    }

    @Override
    public String toString() {
	return this.species.toString() + "\nHealth: " + this.stats.getHealth() + "\nAttack: " + this.stats.getAttack()
		+ "\nDefense: " + this.stats.getDefense() + "\nSpeed: " + this.stats.getSpeed() + "\nLevel: "
		+ this.level + "\nExp: " + this.exp + "\nMoves:" + this.movesList.toString() + "\nIsWild: "
		+ this.isWild + "\n";
    }
}
