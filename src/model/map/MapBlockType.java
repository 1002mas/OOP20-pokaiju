package model.map;

/*
 * MapBlockType represents the value of the map blocks.
 * SOLID	it's an obstacle you can't walk through
 * WALK		it's an area where you can walk
 * MAP_CHANGE	it's a point of connection to another map
 * WILD_ZONE	area where you can find random monster
 * */
public enum MapBlockType {
    BORDER(true),
    MAP_CHANGE(false),
    OBSTACLE(true),
    WALK(false),
    WILD_ZONE(false);

    private final boolean canPassThrough;

    private MapBlockType(boolean canPassThrough) {
	this.canPassThrough = canPassThrough;
    }

    public boolean canPassThrough() {
	return !this.canPassThrough;
    }
}
