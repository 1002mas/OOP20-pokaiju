package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import model.battle.Moves;
import model.battle.MovesImpl;

import model.gameitem.GameItemImpl;
import model.gameitem.GameItem;
import model.map.GameMap;
import model.map.GameMapData;
import model.map.GameMapDataImpl;
import model.map.GameMapImpl;
import model.monster.Monster;
import model.monster.MonsterImpl;
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
	//TODO controllare se movsd e Movs funciona
	public List<Moves> getTranslatedMoves(List<Moves> list) {
		List<Moves> movesData = new ArrayList<>();
		for (String s : this.movesList) {
			for (Moves md : list) {
				if (md.getName().equals(s)) {
					movesData.add(md);
				}
			}
		}
		return movesData;
	}
}
