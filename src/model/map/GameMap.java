package model.map;

import java.util.List;
import java.util.Optional;

import model.Pair;
import model.gameevents.GameEvent;
import model.monster.Monster;
import model.npc.NpcSimple;

public interface GameMap {
    /**
     * @return current map id
     */
    int getCurrentMapId();

    /**
     * @return true if the player can access the block, false if it can't go to
     *         position block
     */
    boolean canPassThrough(Pair<Integer, Integer> block);

    /**
     * @param pos map block position where the player is
     * @return it may return a monster if the player is in an area where wild
     *         monsters can appear. It returns Optional.empty if no monster appears
     */
    Optional<Monster> getWildMonster(Pair<Integer, Integer> pos);

    /**
     * it changes the map if the player is near to another map
     * 
     * @throws IllegalStateException if the map can't be changed
     */
    void changeMap(Pair<Integer, Integer> playerPosition);

    /**
     * @return true if the player is in a block linked to another map
     */
    boolean canChangeMap(Pair<Integer, Integer> playerPosition);

    /**
     * @return true if the player is in a block linked to another map
     */
    Optional<Pair<Integer, Integer>> getPlayerMapPosition();

    /**
     * @return npc at the given position if it is present, Optional.empty otherwise
     */
    Optional<NpcSimple> getNpcAt(Pair<Integer, Integer> position);

    /**
     * @return GameEvent at the given position if it is present, Optional.empty
     *         otherwise
     */
    Optional<GameEvent> getEventAt(Pair<Integer, Integer> position);

    /**
     * @return all npcs in the current map
     */
    List<NpcSimple> getAllNpcsInCurrentMap();
}
