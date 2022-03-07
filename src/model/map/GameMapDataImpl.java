package model.map;

import java.util.Map;
import java.util.Optional;

import model.Pair;
import model.npc.NpcSimple;

public class GameMapDataImpl implements GameMapData {
    private final Map<Pair<Integer, Integer>, MapBlockType> blocks;
    private final Map<Pair<Integer, Integer>, NpcSimple> npcs;
    private final Map<Pair<Integer, Integer>, GameMapData> linkedMaps;

    public GameMapDataImpl(Map<Pair<Integer, Integer>, MapBlockType> blocks, Map<Pair<Integer, Integer>, NpcSimple> npcs,
	    Map<Pair<Integer, Integer>, GameMapData> linkedMaps) {
	this.blocks = blocks;
	this.npcs = npcs;
	this.linkedMaps = linkedMaps;
    }

    @Override
    public MapBlockType getBlockType(Pair<Integer, Integer> block) {
	return blocks.get(block);
    }

    @Override
    public Optional<NpcSimple> getNPC(Pair<Integer, Integer> block) {
	return npcs.containsKey(block) ? Optional.of(npcs.get(block)) : Optional.empty();
    }

    @Override
    public Optional<GameMapData> getNextMap(Pair<Integer, Integer> playerPosition) {
	return linkedMaps.containsKey(playerPosition) ? Optional.of(linkedMaps.get(playerPosition)) : Optional.empty();
    }

}
