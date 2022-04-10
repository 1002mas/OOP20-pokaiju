package model.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import model.Pair;
import model.monster.MonsterSpecies;
import model.npc.NpcSimple;

public class GameMapDataImpl implements GameMapData {
    private final int id;
    private final int minimumMonsterLevel;
    private final int maximumMonsterLevel;
    private final String name;
    private final Map<Pair<Integer, Integer>, MapBlockType> blocks;
    private final Map<Pair<Integer, Integer>, NpcSimple> npcs;
    private final Map<Pair<Integer, Integer>, GameMapData> linkedMaps;
    private final Map<GameMapData, Pair<Integer, Integer>> linkedMapsStartingPosition;
    private final List<MonsterSpecies> wildMonsters;

    public GameMapDataImpl(int id, int minimumMonsterLevel, int maximumMonsterLevel, String name,
	    Map<Pair<Integer, Integer>, MapBlockType> blocks, Map<Pair<Integer, Integer>, NpcSimple> npcs,
	    List<MonsterSpecies> wildMonsters) {
	this.id = id;
	this.minimumMonsterLevel = minimumMonsterLevel;
	this.maximumMonsterLevel = maximumMonsterLevel;
	this.name = name;
	this.blocks = blocks;
	this.npcs = npcs == null ? new HashMap<>() : npcs;
	this.linkedMaps = new HashMap<>();
	this.linkedMapsStartingPosition = new HashMap<>();
	this.wildMonsters = wildMonsters == null ? new ArrayList<>() : wildMonsters;
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
	return blocks.containsKey(block) ? blocks.get(block) : MapBlockType.BORDER;
    }

    @Override
    public Optional<NpcSimple> getNPC(Pair<Integer, Integer> block) {
	return npcs.containsKey(block) ? Optional.of(npcs.get(block)) : Optional.empty();
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

    @Override
    public List<NpcSimple> getAllNpcs() {
	return Collections.unmodifiableList(this.npcs.values().stream().collect(Collectors.toList()));
    }

}
