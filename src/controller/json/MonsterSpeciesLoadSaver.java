package controller.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Pair;
import model.battle.Moves;
import model.gameitem.GameItem;
import model.monster.EvolutionType;
import model.monster.MonsterStats;
import model.monster.MonsterType;

public class MonsterSpeciesLoadSaver {

	private final Optional<String> evolution;
	private final String name;
	private final String info;
	private final MonsterType type;
	private final EvolutionType evolutionType;
	private final MonsterStats stats;
	private final List<Pair<String, Integer>> allMoves;
	private Optional<String> evolutionItem;
	private Optional<Integer> evolutionLevel;

	public MonsterSpeciesLoadSaver(Optional<String> evolution, String name, String info, MonsterType type,
			EvolutionType evolutionType, MonsterStats stats, List<Pair<String, Integer>> allMoves,
			Optional<String> evolutionItem, Optional<Integer> evolutionLevel) {

		this.evolution = evolution;
		this.name = name;
		this.info = info;
		this.type = type;
		this.evolutionType = evolutionType;
		this.stats = stats;
		this.allMoves = allMoves;
		this.evolutionItem = evolutionItem;
		this.evolutionLevel = evolutionLevel;

	}

	public EvolutionType getEvolutionType() {
		return this.evolutionType;
	}

	public String getName() {
		return this.name;
	}

	public String getInfo() {
		return this.info;
	}

	public MonsterType getMonsterType() {
		return this.type;
	}

	public int getAttack() {
		return this.stats.getAttack();
	}
	
	public int getDefense() {
		return this.stats.getDefense();
	}
	
	public int getHealth() {
		return this.stats.getHealth();
	}
	
	public int getSpeed() {
		return this.stats.getSpeed();
	}
	public List<Pair<Moves, Integer>> getAllMoves(List<Moves> movesd) {
		List<Pair<Moves, Integer>> moves = new ArrayList<>();
		for (Pair<String, Integer> n : this.allMoves) {
			for (Moves md : movesd) {
				if (md.getName().equals(n.getFirst())) {
					moves.add(new Pair<Moves, Integer>(md, n.getSecond()));
					break;
				}
			}
		}
		return moves;
	}

	public Optional<String> getEvolution() {
		return this.evolution;
	}

	public Optional<GameItem> getEvolutionGameItem(List<GameItem> list) {
		for(GameItem gi: list) {
			if(gi.getNameItem().equals(this.evolutionItem.get())) {
				return Optional.of(gi);
			}
		}
		return Optional.empty();
	}

	public Optional<Integer> getEvolutionLevel() {
		return this.evolutionLevel;
	}

}
