package controller.json.loader;

import java.util.ArrayList;
import java.util.List;

import model.Pair;
import model.battle.Moves;
import model.monster.MonsterSpecies;
import model.monster.MonsterStats;

public class MonsterLoader {

	private int id;
	private int exp;
	private int level;
	private String species;
	private List<Pair<String, Integer>> movesList;
	private int health;
	private int attack;
	private int def;
	private int speed;

	public MonsterLoader(int id, int exp, int level, String species, List<Pair<String, Integer>> movesList, int health,
			int attack, int def, int speed) {
		this.id = id;
		this.exp = exp;
		this.level = level;
		this.health = health;
		this.attack = attack;
		this.def = def;
		this.speed = speed;
		this.movesList = movesList;
		this.species = species;
	}

	public int getId() {
		return this.id;
	}

	public int getExp() {
		return this.exp;
	}

	public int getLevel() {
		return this.level;
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

	public MonsterSpecies getTranslatedMonsterSpecies(List<MonsterSpecies> list) {
		for (MonsterSpecies ms : list) {
			if (ms.getName().equals(this.species)) {
				return ms;
			}
		}
		return null;
	}

	public List<Pair<Moves, Integer>> getTranslatedMoves(List<Moves> list) {
		List<Pair<Moves, Integer>> moves = new ArrayList<>();
		for (Pair<String, Integer> s : this.movesList) {
			for (Moves md : list) {
				if (md.getName().equals(s.getFirst())) {
					moves.add(new Pair<Moves, Integer>(md, s.getSecond()));
					break;
				}
			}
		}
		return moves;
	}
}
