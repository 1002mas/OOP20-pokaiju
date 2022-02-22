package model;

import java.util.Optional;
/*
 * proxy to GameMapData
 * */
public interface GameMap {
    boolean canPassThrough(Pair<Integer, Integer> block);

    Optional<GameEvent> getEvent();

    void changeMap(Pair<Integer, Integer> playerPosition);

    boolean canChangeMap(Pair<Integer, Integer> playerPosition);
}