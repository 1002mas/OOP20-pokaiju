package model.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import model.Pair;
import model.monster.MonsterSpecies;
import model.npc.NpcSimple;

public class GameMapDataImpl implements GameMapData {
    private final int id;
    private final String name;
    private final Map<Pair<Integer, Integer>, MapBlockType> blocks;
    private final Map<Pair<Integer, Integer>, NpcSimple> npcs;
    private final Map<Pair<Integer, Integer>, GameMapData> linkedMaps;
    private final Map<GameMapData, Pair<Integer, Integer>> linkedMapsStartingPosition;
    private final List<MonsterSpecies> wildMonsters;

    public GameMapDataImpl(int id, String name, Map<Pair<Integer, Integer>, MapBlockType> blocks,
	    Map<Pair<Integer, Integer>, NpcSimple> npcs, Map<Pair<Integer, Integer>, GameMapData> linkedMaps,
	    Map<GameMapData, Pair<Integer, Integer>> linkedMapsStartingPosition, List<MonsterSpecies> wildMonsters) {
	this.id = id;
	this.name = name;
	this.blocks = blocks;
	this.npcs = npcs == null ? new HashMap<>() : npcs;
	this.linkedMaps = linkedMaps == null ? new HashMap<>() : linkedMaps;
	this.linkedMapsStartingPosition = linkedMapsStartingPosition == null ? new HashMap<>()
		: linkedMapsStartingPosition;
	this.wildMonsters = wildMonsters == null ? new ArrayList<>() : wildMonsters;
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

}
