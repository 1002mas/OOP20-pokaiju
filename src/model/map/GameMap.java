package model.map;

import java.util.Optional;
import model.GameEvent;
import model.Pair;

/*
 * GameMapData decorator
 * */
public interface GameMap {
    /**
     * @return true if the player can access the block, false if it can't go to
     * position block
     */
    boolean canPassThrough(Pair<Integer, Integer> block);

    Optional<GameEvent> getEvent();

    /**
     * it changes the map if the player is near to another map
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
}
