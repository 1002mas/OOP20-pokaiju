package model.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import model.Pair;
import model.gameevents.GameEvent;
import model.monster.MonsterSpecies;
import model.npc.NpcSimple;

public class GameMapDataImpl implements GameMapData {
    private final int id;
    private final int minimumMonsterLevel;
    private final int maximumMonsterLevel;
    private final String name;
    private final Map<Pair<Integer, Integer>, MapBlockType> blocks;
    private final Set<NpcSimple> npcs;
    private final Map<Pair<Integer, Integer>, GameMapData> linkedMaps;
    private final Map<Pair<Integer, Integer>, GameEvent> eventLocation;
    private final Map<GameMapData, Pair<Integer, Integer>> linkedMapsStartingPosition;
    private final List<MonsterSpecies> wildMonsters;

    public GameMapDataImpl(int id, int minimumMonsterLevel, int maximumMonsterLevel, String name,
	    Map<Pair<Integer, Integer>, MapBlockType> blocks, Set<NpcSimple> npcs, List<MonsterSpecies> wildMonsters) {
	this.id = id;
	this.minimumMonsterLevel = minimumMonsterLevel;
	this.maximumMonsterLevel = maximumMonsterLevel;
	this.name = name;
	this.blocks = blocks;
	this.npcs = npcs == null ? new HashSet<>() : npcs;
	this.wildMonsters = wildMonsters == null ? new ArrayList<>() : wildMonsters;
	this.linkedMaps = new HashMap<>();
	this.linkedMapsStartingPosition = new HashMap<>();
	this.eventLocation = new HashMap<>();
    }

    @Override
    public int getMapId() {
	return this.id;
    }

    @Override
    public void addMapLink(GameMapData map, Pair<Integer, Integer> mapLinkPosition,
	    Pair<Integer, Integer> characterSpawn) {
	this.linkedMaps.put(mapLinkPosition, map);
	this.linkedMapsStartingPosition.put(map, characterSpawn);
    }

    @Override
    public Pair<Integer, Integer> getWildMonsterLevelRange() {
	return new Pair<>(this.minimumMonsterLevel, this.maximumMonsterLevel);
    }

    @Override
    public MapBlockType getBlockType(Pair<Integer, Integer> block) {
	return blocks.containsKey(block) ? blocks.get(block) : MapBlockType.OBSTACLE;
    }

    @Override
    public Optional<NpcSimple> getNpc(Pair<Integer, Integer> block) {
	return npcs.stream().filter(npc -> npc.getPosition().equals(block)).findFirst();
    }

    @Override
    public Optional<GameEvent> getEvent(Pair<Integer, Integer> block) {
	return eventLocation.containsKey(block) ? Optional.of(eventLocation.get(block)) : Optional.empty();
    }

    @Override
    public Optional<Pair<GameMapData, Pair<Integer, Integer>>> getNextMap(Pair<Integer, Integer> playerPosition) {
	return linkedMaps.containsKey(playerPosition) ? Optional.of(new Pair<>(linkedMaps.get(playerPosition),
		linkedMapsStartingPosition.get(linkedMaps.get(playerPosition)))) : Optional.empty();
    }

    @Override
    public String getName() {
	return this.name;
    }

    @Override
    public List<MonsterSpecies> getMonstersInArea() {
	return Collections.unmodifiableList(this.wildMonsters);
    }

    @Override
    public List<NpcSimple> getAllNpcs() {
	return new ArrayList<NpcSimple>(npcs);
    }

    @Override
    public void addEventAt(GameEvent e, Pair<Integer, Integer> block) {
	this.eventLocation.put(block, e);
    }

    @Override
    public String toString() {
	return "MapID: " + id + ", name: " + name;
    }

    @Override
    public int hashCode() {
	return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	GameMapDataImpl other = (GameMapDataImpl) obj;
	return id == other.id;
    }

}
