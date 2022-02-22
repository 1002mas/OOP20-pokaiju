package model;

import java.util.Optional;
/*
 * GameMapData contains all map logicals info as walkable blocks, npcs in the maps.
 * 
 * */
public interface GameMapData {
    MapBlockType getBlockType(Pair<Integer, Integer> block);

    Optional<NPC> getNPC(Pair<Integer, Integer> block);
    
    Optional<GameMapData> getNextMap(Pair<Integer, Integer> playerPosition);
}
