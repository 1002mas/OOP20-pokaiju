package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.battle.MovesData;
import model.monster.MonsterSpecies;
import model.monster.MonsterStats;

public class MonsterSupport {

	private int id;
	private int exp;
	private int level;
	private String species; // TODO translate
	private List<String> movesList; // TODO translate
	private MonsterStats stats;
	private MonsterStats maxStats;

	public MonsterSupport(int id, int exp, int level, String species, List<String> movesList, MonsterStats stats,
			MonsterStats maxStats) {
		this.id = id;
		this.exp = exp;
		this.level = level;
		this.maxStats = maxStats;
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

	public MonsterStats getStats() {
		return this.stats;
	}

	public MonsterStats getMaxStats() {
		return this.maxStats;
	}

	public MonsterSpecies getTranslatedMonsterSpecies(List<MonsterSpecies> list) {
		for (MonsterSpecies ms : list) {
			if (ms.getName().equals(this.species)) {
				return ms;
			}
		}
		return null;
	}

	public List<MovesData> getTranslatedMoves(List<MovesData> list) {
		List<MovesData> movesData = new ArrayList<>();
		for (String s : this.movesList) {
			for (MovesData md : list) {
				if (md.getName().equals(s)) {
					movesData.add(md);
				}
			}
		}
		return movesData;
	}
}
