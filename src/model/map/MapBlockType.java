package model.map;

/**
 * MapBlockType represents the map blocks accessibility. BORDER it's the map
 * limit, you can't go from there on OBSTACLE it's an obstacle you can't walk
 * through WALK it's an area where you can walk MAP_CHANGE it's a point of
 * connection to another map WILD_ZONE area where you can find random monster
 */
public enum MapBlockType {
    BORDER(false, false),
    MAP_CHANGE(true, false),
    OBSTACLE(false, false),
    WALK(true, false),
    WILD_ZONE(true, true);

    private final boolean canPassThrough;
    private final boolean canSpawnMonsters;

    private MapBlockType(boolean canPassThrough, boolean canSpawnMonsters) {
	this.canPassThrough = canPassThrough;
	this.canSpawnMonsters = canSpawnMonsters;
    }

    public boolean canPassThrough() {
	return this.canPassThrough;
    }

    public boolean canMonstersAppear() {
	return this.canSpawnMonsters;
    }
}
