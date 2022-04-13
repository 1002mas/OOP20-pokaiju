package controller.json.loader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Pair;
import model.map.MapBlockType;
import model.monster.MonsterSpecies;
import model.npc.NpcSimple;

public class GameMapDataLoader {

	private int id;
	private int minimumMonsterLevel;
	private int maximumMonsterLevel;
	private String name;
	private Map<Pair<Integer, Integer>, MapBlockType> blocks;
	private Set<String> npcs;
	private List<String> wildMonsters;
	//TODO add GameEvent
	public GameMapDataLoader(int id, int minimumMonsterLevel, int maximumMonsterLevel, String name,
			Map<Pair<Integer, Integer>, MapBlockType> blocks, Set<String> npcs, List<String> wildMonsters) {

		this.id = id;
		this.minimumMonsterLevel = minimumMonsterLevel;
		this.maximumMonsterLevel = maximumMonsterLevel;
		this.name = name;
		this.blocks = blocks;
		this.npcs = npcs;
		this.wildMonsters = wildMonsters;

	}

	public int getId() {
		return this.id;
	}

	public int getMinimumMonsterLevel() {
		return this.minimumMonsterLevel;
	}

	public int getMaximumMonsterLevel() {
		return this.maximumMonsterLevel;
	}

	public String getName() {
		return this.name;
	}

	public Map<Pair<Integer, Integer>, MapBlockType> getBlocks() {
		return this.blocks;
	}

	public Set<NpcSimple> getTranslatedNpcs(List<NpcSimple> list) {
		Set<NpcSimple> npc = new HashSet<>();
		for (String s : this.npcs) {
			for (NpcSimple n : list) {
				if (s.equals(n.getName())) {
					npc.add(n);
					break;
				}
			}
		}
		return npc;
	}

	public List<MonsterSpecies> getTranslatedtWildMonsters(List<MonsterSpecies> list) {
		List<MonsterSpecies> monster = new ArrayList<>();
		for (String s : this.wildMonsters) {
			for (MonsterSpecies n : list) {
				if (s.equals(n.getName())) {
					monster.add(n);
					break;
				}
			}
		}
		return monster;
	}
}
