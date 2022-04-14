package controller.json.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Pair;
import model.battle.Moves;
import model.gameitem.GameItem;
import model.monster.EvolutionType;
import model.monster.MonsterStats;
import model.monster.MonsterType;

public class MonsterSpeciesLoader {

	private final Optional<String> evolution;
	private final String name;
	private final String info;
	private final MonsterType type;
	private final EvolutionType evolutionType;
	private final List<Pair<String, Integer>> allMoves;
	private Optional<String> evolutionItem;
	private Optional<Integer> evolutionLevel;
	private int health;
	private int attack;
	private int def;
	private int speed;

	public MonsterSpeciesLoader(Optional<String> evolution, String name, String info, MonsterType type,
			EvolutionType evolutionType, int health, int attack, int def, int speed,
			List<Pair<String, Integer>> allMoves, Optional<String> evolutionItem, Optional<Integer> evolutionLevel) {

		this.evolution = evolution;
		this.name = name;
		this.info = info;
		this.type = type;
		this.evolutionType = evolutionType;
		this.health = health;
		this.attack = attack;
		this.def = def;
		this.speed = speed;
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
		return this.attack;
	}

	public int getDefense() {
		return this.def;
	}

	public int getHealth() {
		return this.health;
	}

	public int getSpeed() {
		return this.speed;
	}

	public List<Pair<Moves, Integer>> getAllMoves(List<Moves> movesList) {
		List<Pair<Moves, Integer>> moves = new ArrayList<>();
		for (Pair<String, Integer> n : this.allMoves) {
			for (Moves movesInList : movesList) {
				if (movesInList.getName().equals(n.getFirst())) {
					moves.add(new Pair<Moves, Integer>(movesInList, n.getSecond()));
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
		for (GameItem gameItem : list) {
			if (gameItem.getNameItem().equals(this.evolutionItem.get())) {
				return Optional.of(gameItem);
			}
		}

		return Optional.empty();
	}

	public Optional<Integer> getEvolutionLevel() {
		return this.evolutionLevel;
	}

}
