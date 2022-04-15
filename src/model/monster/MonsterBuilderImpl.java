package model.monster;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import model.Pair;
import model.battle.Moves;

public class MonsterBuilderImpl implements MonsterBuilder {

    private static final String LASTIDPATH = "res" + File.separator + "data" + File.separator + "generatedId.dat";
    private static final int MIN_LEVEL = 1;
    private static int id;
    private int exp;
    private List<Pair<Moves, Integer>> movesList;
    private boolean isWild;
    private int level = MIN_LEVEL;
    private MonsterSpecies species;
    private MonsterStats stats = new MonsterStatsImpl(-1, -1, -1, -1);

    static {
	try {
	    String lastId = Files.readAllLines(Path.of(LASTIDPATH)).get(0);
	    id = Integer.parseInt(lastId);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public MonsterBuilder species(MonsterSpecies species) {
	this.species = species;
	return this;
    }

    @Override
    public MonsterBuilder level(int lvl) {
	if (lvl < MIN_LEVEL || lvl > MonsterImpl.MAX_LVL) {
	    throw new IllegalArgumentException();
	}
	this.level = lvl;
	return this;
    }

    @Override
    public MonsterBuilder health(int health) {
	this.stats.setHealth(health);
	return this;
    }

    @Override
    public MonsterBuilder attack(int atk) {
	this.stats.setAttack(atk);
	return this;
    }

    @Override
    public MonsterBuilder defense(int dfs) {
	this.stats.setDefense(dfs);
	return this;
    }

    @Override
    public MonsterBuilder speed(int spd) {
	this.stats.setSpeed(spd);
	return this;
    }

    @Override
    public MonsterBuilder exp(int exp) {
	if (exp < 0 || exp > MonsterImpl.EXP_CAP) {
	    throw new IllegalArgumentException();
	}
	this.exp = exp;
	return this;
    }

    @Override
    public MonsterBuilder isWild(boolean isWild) {
	this.isWild = isWild;
	return this;
    }

    @Override
    public MonsterBuilder movesList(List<Pair<Moves, Integer>> movesList) {
	this.movesList = new ArrayList<>(movesList);
	this.movesList = this.movesList.subList(0, movesList.size() < MonsterImpl.NUM_MAX_MOVES ? movesList.size() : MonsterImpl.NUM_MAX_MOVES);
	return this;
    }

    @Override
    public Monster build() {
	if (id <= 0 || this.species == null || this.movesList.isEmpty()) {
	    throw new IllegalStateException();
	}
	Monster monster = new MonsterImpl(id, this.species.getBaseStats(), 0, MIN_LEVEL, this.isWild, this.species,
		this.movesList);
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
