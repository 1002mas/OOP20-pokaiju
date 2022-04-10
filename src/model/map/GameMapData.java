package model.map;

import java.util.List;
import java.util.Optional;

import model.Pair;
import model.gameevents.GameEvent;
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
     * links a map to this map. If you want a bidirectional link, you need to link
     * this to the other one as well
     * 
     * @param map             the linked map
     * @param mapLinkPosition the position in the current map to reach the given map
     * @param characterSpawn  the player position when the map is changed to map
     */
    void addMapLink(GameMapData map, Pair<Integer, Integer> mapLinkPosition, Pair<Integer, Integer> characterSpawn);

    /**
     * @return a pair containing the minimum and maximum level for monsters in the
     *         area
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
    Optional<NpcSimple> getNpc(Pair<Integer, Integer> block);

    /**
     * 
     * @param block place where the event may happens
     * @return if there is an event it is returned, otherwise Optional.empty is
     *         returned
     */
    Optional<GameEvent> getEvent(Pair<Integer, Integer> block);

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
