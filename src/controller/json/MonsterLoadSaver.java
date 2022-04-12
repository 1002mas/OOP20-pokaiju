package controller.json;

import java.util.ArrayList;
import java.util.List;

import model.Pair;
import model.battle.Moves;
import model.monster.MonsterSpecies;
import model.monster.MonsterStats;

public class MonsterLoadSaver {

	private int id;
	private int exp;
	private int level;
	private String species;
	private List<Pair<String, Integer>> movesList;
	private MonsterStats stats;

	public MonsterLoadSaver(int id, int exp, int level, String species, List<Pair<String, Integer>> movesList,
			MonsterStats stats) {
		this.id = id;
		this.exp = exp;
		this.level = level;
		this.stats = stats;
		this.movesList = movesList;
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
