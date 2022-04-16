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

    @Override
    public MonsterSpeciesBuilder name(String name) {
	this.name = name;
	return this;
    }

    @Override
    public MonsterSpeciesBuilder info(String info) {
	this.info = info;
	return this;
    }

    @Override
    public MonsterSpeciesBuilder monsterType(MonsterType type) {
	this.type = type;
	return this;
    }

    @Override
    public MonsterSpeciesBuilder evolution(MonsterSpecies evolution) {
	this.evolution = evolution;
	return this;
    }

    @Override
    public MonsterSpeciesBuilder evolutionLevel(int level) {
	this.evolutionLevel = Optional.of(level);
	return this;
    }

    @Override
    public MonsterSpeciesBuilder health(int health) {
	this.stats.setHealth(health);
	return this;
    }

    @Override
    public MonsterSpeciesBuilder attack(int attack) {
	this.stats.setAttack(attack);
	return this;
    }

    @Override
    public MonsterSpeciesBuilder defense(int defense) {
	this.stats.setDefense(defense);
	return this;
    }

    @Override
    public MonsterSpeciesBuilder speed(int speed) {
	this.stats.setSpeed(speed);
	return this;
    }

    @Override
    public MonsterSpeciesBuilder gameItem(GameItem gameItem) {
	this.gameItem = Optional.of(gameItem);
	return this;
    }
    
    @Override
    public MonsterSpeciesBuilder movesList(List<Moves> movesList) {
	this.movesList = movesList;
	return this;
    }

    @Override
    public MonsterSpecies build() {
	if (name == null || this.info == null || this.type == null || (this.evolution != null && this.evolutionLevel.isEmpty() && this.gameItem.isEmpty()) || this.movesList.isEmpty()) {
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
