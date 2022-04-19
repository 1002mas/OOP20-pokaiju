package model.map;

/**
 * MapBlockType represents the map blocks accessibility.
 */
public enum MapBlockType {
    /**
     * it's an obstacle you can't walk through.
     **/
    MAP_CHANGE(true, false),
    /**
     * it's an area where you can't walk.
     */
    OBSTACLE(false, false),
    /**
     * it's a point of connection to another map.
     */
    WALK(true, false),
    /**
     * area where you can find random monster.
     */
    WILD_ZONE(true, true);

    private final boolean canPassThrough;
    private final boolean canSpawnMonsters;

    MapBlockType(final boolean canPassThrough, final boolean canSpawnMonsters) {
        this.canPassThrough = canPassThrough;
        this.canSpawnMonsters = canSpawnMonsters;
    }

    /**
     * 
     * @return true if you can go to the block
     */
    public boolean canPassThrough() {
        return this.canPassThrough;
    }

    /**
     * @return true if it is an a block where wild monsters may appear
     */
    public boolean canMonstersAppear() {
        return this.canSpawnMonsters;
    }
}
