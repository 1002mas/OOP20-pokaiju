package model.map;

import java.util.Optional;
import model.GameEvent;
import model.Pair;

/*
 * proxy to GameMapData
 * */
public interface GameMap {
    boolean canPassThrough(Pair<Integer, Integer> block);

    Optional<GameEvent> getEvent();

    void changeMap(Pair<Integer, Integer> playerPosition);

    boolean canChangeMap(Pair<Integer, Integer> playerPosition);
}
