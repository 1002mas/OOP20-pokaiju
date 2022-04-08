package model.map;

import java.util.List;
import java.util.Optional;

import model.Pair;
import model.monster.MonsterSpecies;
import model.npc.NpcSimple;

/*
 * GameMapData contains all map info as walkable blocks, npcs in the maps.
 * 
 * */
public interface GameMapData {
    /**
     * @return map id
     */
    int getMapId();

    /**
     * @return a pair containing the minimum and maximum level for monsters in the area 
     */
    Pair<Integer, Integer> getWildMonsterLevelRange();

    /**
     * @return type of block (es. walkable)
     */
    MapBlockType getBlockType(Pair<Integer, Integer> block);

    /**
     * @return list of all wild monsters that may appears in the area
     */
    List<MonsterSpecies> getMonstersInArea();

    /**
     * @return a npc if there is in block position, otherwise Optional.empty
     */
    Optional<NpcSimple> getNPC(Pair<Integer, Integer> block);

    /**
     * @return get the near map linked to the position PlayerPosition and the place
     *         where the player appears. PlayerPosition if no other maps are linked
     */
    Optional<Pair<GameMapData, Pair<Integer, Integer>>> getNextMap(Pair<Integer, Integer> playerPosition);

    /**
     * @return map name
     */
    String getName();

    /**
     * @return a list containing all npcs in the map
     */
    List<NpcSimple> getAllNpcs();

}
