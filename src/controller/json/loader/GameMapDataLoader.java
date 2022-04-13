package controller.json.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.Pair;
import model.gameevents.GameEvent;
import model.map.GameMapData;
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
	private Map<Pair<Integer, Integer>, Integer> eventLocation;
	private Map<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>, Integer> linkedMapData;

	public GameMapDataLoader(int id, int minimumMonsterLevel, int maximumMonsterLevel, String name,
			Map<Pair<Integer, Integer>, MapBlockType> blocks, Set<String> npcs, List<String> wildMonsters,
			Map<Pair<Integer, Integer>, Integer> eventLocation,
			Map<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>, Integer> linkedMapData) {

		this.id = id;
		this.minimumMonsterLevel = minimumMonsterLevel;
		this.maximumMonsterLevel = maximumMonsterLevel;
		this.name = name;
		this.blocks = blocks;
		this.npcs = npcs;
		this.wildMonsters = wildMonsters;
		this.eventLocation = eventLocation;
		this.linkedMapData = linkedMapData;

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

	public Map<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>, GameMapData> getLinkedMapData(
			List<GameMapData> list) {
		Map<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>, GameMapData> linkedMaps = new HashMap<>();
		for (Entry<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>, Integer> map : this.linkedMapData.entrySet()) {
			for (GameMapData mapInList : list) {
				if (mapInList.getMapId() == map.getValue()) {
					linkedMaps.put(map.getKey(), mapInList);
				}

			}
		}
		return linkedMaps;

	}

	public List<MonsterSpecies> getTranslatedtWildMonsters(List<MonsterSpecies> list) {
		List<MonsterSpecies> monsterSpecies = new ArrayList<>();
		for (String s : this.wildMonsters) {
			for (MonsterSpecies monster : list) {
				if (s.equals(monster.getName())) {
					monsterSpecies.add(monster);
					break;
				}
			}
		}
		return monsterSpecies;
	}

	public Map<Pair<Integer, Integer>, GameEvent> getTranslatedEventLocation(List<GameEvent> list) {
		Map<Pair<Integer, Integer>, GameEvent> events = new HashMap<>();
		for (Entry<Pair<Integer, Integer>, Integer> event : this.eventLocation.entrySet()) {
			for (GameEvent eventInList : list) {
				if (eventInList.getEventID() == event.getValue()) {
					events.put(event.getKey(), eventInList);
				}

			}
		}
		return events;
	};

}
