package model.map;

import java.util.Optional;

import model.Pair;
import model.npc.NpcSimple;
/*
 * GameMapData contains all map logicals info as walkable blocks, npcs in the maps.
 * 
 * */
public interface GameMapData {
    MapBlockType getBlockType(Pair<Integer, Integer> block);

    Optional<NpcSimple> getNPC(Pair<Integer, Integer> block);
    
    Optional<GameMapData> getNextMap(Pair<Integer, Integer> playerPosition);
}
