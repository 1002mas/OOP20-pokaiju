package model.map;

import java.util.Map;
import java.util.Optional;

import model.Pair;
import model.npc.NpcSimple;

public class GameMapDataImpl implements GameMapData {
    private final String name;
    private final Map<Pair<Integer, Integer>, MapBlockType> blocks;
    private final Map<Pair<Integer, Integer>, NpcSimple> npcs;
    private final Map<Pair<Integer, Integer>, GameMapData> linkedMaps;
    private final Map<GameMapData, Pair<Integer, Integer>> linkedMapsStartingPosition;

    public GameMapDataImpl(String name, Map<Pair<Integer, Integer>, MapBlockType> blocks,
	    Map<Pair<Integer, Integer>, NpcSimple> npcs, Map<Pair<Integer, Integer>, GameMapData> linkedMaps,
	    Map<GameMapData, Pair<Integer, Integer>> linkedMapsStartingPosition) {
	this.name = name;

	this.blocks = blocks;
	this.npcs = npcs;
	this.linkedMaps = linkedMaps;
	this.linkedMapsStartingPosition = linkedMapsStartingPosition;
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

}
