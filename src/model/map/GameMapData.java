package model.map;

import java.util.Optional;

import model.Pair;
import model.npc.Npc;

/*
 * GameMapData contains all map info as walkable blocks, npcs in the maps.
 * 
 * */
public interface GameMapData {
    /*
     * @return type of block (es. walkable)
     */
    MapBlockType getBlockType(Pair<Integer, Integer> block);

    /*
     * @return npc in position block if it exists, otherwise Optional.empty
     */
    Optional<Npc> getNPC(Pair<Integer, Integer> block);

    /*
     * @return get the near map linked to the position PlayerPosition and the place where the player appears.
     * PlayerPosition if no other maps are linked
     */
    Optional<Pair<GameMapData, Pair<Integer, Integer>>> getNextMap(Pair<Integer, Integer> playerPosition);

    /*
     * @return map name
     */
    String getName();
}
