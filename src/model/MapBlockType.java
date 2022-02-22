package model;
/*
 * MapBlockType represents the value of the map blocks.
 * SOLID	it's an obstacle you can't walk through
 * WALK		it's an area where you can walk
 * MAP_CHANGE	it's a point of connection to another map
 * WILD_ZONE	area where you can find random monster
 * */
public enum MapBlockType {
    SOLID, WALK, MAP_CHANGE, WILD_ZONE
}
