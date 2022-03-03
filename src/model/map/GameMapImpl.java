package model.map;

import java.util.Optional;

import model.GameEvent;
import model.Pair;

public class GameMapImpl implements GameMap {
    private GameMapData map;
    private Optional<Pair<Integer, Integer>> enteringStartPosition;

    public GameMapImpl(GameMapData map) {
	this.map = map;
	enteringStartPosition = Optional.empty();
    }

    @Override
    public boolean canPassThrough(Pair<Integer, Integer> block) {
	return map.getBlockType(block).canPassThrough();
    }

    /* TODO add events */
    @Override
    public Optional<GameEvent> getEvent() {
	return null;
    }

    private void setMap(GameMapData map) {
	this.map = map;
    }

    @Override
    public void changeMap(Pair<Integer, Integer> playerPosition) {
	if (canChangeMap(playerPosition) && map.getNextMap(playerPosition).isPresent()) {
	    Pair<GameMapData, Pair<Integer, Integer>> p = map.getNextMap(playerPosition).get();
	    enteringStartPosition = Optional.of(p.getSecond());
	    setMap(p.getFirst());
	} else {
	    throw new IllegalStateException();
	}

    }

    @Override
    public boolean canChangeMap(Pair<Integer, Integer> playerPosition) {
	return map.getBlockType(playerPosition) == MapBlockType.MAP_CHANGE;
    }

    @Override
    public Optional<Pair<Integer, Integer>> getPlayerMapPosition() {
	Optional<Pair<Integer, Integer>> temp = enteringStartPosition;
	enteringStartPosition = Optional.empty();
	return temp;
    }

}
