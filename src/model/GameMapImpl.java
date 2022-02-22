package model;

import java.util.Optional;

public class GameMapImpl implements GameMap {
    private GameMapData map;

    public GameMapImpl(GameMapData map) {
	super();
	this.map = map;
    }

    @Override
    public boolean canPassThrough(Pair<Integer, Integer> block) {
	return map.getBlockType(block) != MapBlockType.SOLID;
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
	if(canChangeMap(playerPosition) && map.getNextMap(playerPosition).isPresent()) {
	    setMap(map.getNextMap(playerPosition).get());
	}
    }

    @Override
    public boolean canChangeMap(Pair<Integer, Integer> playerPosition) {
	return map.getBlockType(playerPosition) == MapBlockType.MAP_CHANGE;
    }

}
