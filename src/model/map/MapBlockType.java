package model.map;

/**
 * MapBlockType represents the map blocks accessibility.
 */
public enum MapBlockType {
    /**
     * it's the map limit, you can't go from there on.
     */
    BORDER(false, false),
    /**
     * it's an obstacle you can't walk through
     **/
    MAP_CHANGE(true, false),
    /**
     * it's an area where you can walk
     */
    OBSTACLE(false, false),
    /**
     * it's a point of connection to another map
     */
    WALK(true, false),
    /**
     * area where you can find random monster
     */
    WILD_ZONE(true, true);

    private final boolean canPassThrough;
    private final boolean canSpawnMonsters;

    MapBlockType(boolean canPassThrough, boolean canSpawnMonsters) {
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
