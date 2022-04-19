package model.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import model.Pair;
import model.gameevents.GameEvent;
import model.monster.MonsterSpecies;
import model.npc.NpcSimple;

public class GameMapDataImpl implements GameMapData {
    private final int id;
    private final int minimumMonsterLevel;
    private final int maximumMonsterLevel;
    private final String name;
    private final Map<Pair<Integer, Integer>, MapBlockType> blocks;
    private final Set<NpcSimple> npcs;
    private final Map<Pair<Integer, Integer>, GameMapData> linkedMaps;
    private final Map<Pair<Integer, Integer>, GameEvent> eventLocation;
    private final Map<GameMapData, Pair<Integer, Integer>> linkedMapsStartingPosition;
    private final List<MonsterSpecies> wildMonsters;

    /**
     * 
     * @param id                  map id
     * @param minimumMonsterLevel minimum wild monsters level.
     * @param maximumMonsterLevel maximum wild monsters level.
     * @param name                map name.
     * @param blocks              data about accessible zones.
     * @param wildMonsters        a list of wild monsters that may spawn in the map.
     */
    public GameMapDataImpl(final int id, final int minimumMonsterLevel, final int maximumMonsterLevel,
	    final String name, final Map<Pair<Integer, Integer>, MapBlockType> blocks,
	    final List<MonsterSpecies> wildMonsters) {
	this.id = id;
	this.minimumMonsterLevel = minimumMonsterLevel;
	this.maximumMonsterLevel = maximumMonsterLevel;
	this.name = name;
	this.blocks = blocks;
	this.wildMonsters = wildMonsters == null ? new ArrayList<>() : wildMonsters;
	this.linkedMapsStartingPosition = new HashMap<>();
	this.eventLocation = new HashMap<>();
	this.npcs = new HashSet<>();
	this.linkedMaps = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMapId() {
	return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMapLink(final GameMapData map, final Pair<Integer, Integer> mapLinkPosition,
	    final Pair<Integer, Integer> characterSpawn) {
	this.linkedMaps.put(mapLinkPosition, map);
	this.linkedMapsStartingPosition.put(map, characterSpawn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNpc(final NpcSimple npc) {
	if (this.npcs.contains(npc)) {
	    this.npcs.remove(npc);
	}
	this.npcs.add(npc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<Integer, Integer> getWildMonsterLevelRange() {
	return new Pair<>(this.minimumMonsterLevel, this.maximumMonsterLevel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MapBlockType getBlockType(final Pair<Integer, Integer> block) {
	return blocks.containsKey(block) ? blocks.get(block) : MapBlockType.OBSTACLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<NpcSimple> getNpc(final Pair<Integer, Integer> block) {
	return npcs.stream().filter(npc -> npc.getPosition().equals(block)).findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GameEvent> getEvent(final Pair<Integer, Integer> block) {
	return eventLocation.containsKey(block) ? Optional.of(eventLocation.get(block)) : Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Pair<GameMapData, Pair<Integer, Integer>>> getNextMap(final Pair<Integer, Integer> playerPosition) {
	return linkedMaps.containsKey(playerPosition) ? Optional.of(new Pair<>(linkedMaps.get(playerPosition),
		linkedMapsStartingPosition.get(linkedMaps.get(playerPosition)))) : Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
	return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MonsterSpecies> getMonstersInArea() {
	return Collections.unmodifiableList(this.wildMonsters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NpcSimple> getAllNpcs() {
	return new ArrayList<NpcSimple>(npcs);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void addEventAt(final GameEvent e, final Pair<Integer, Integer> block) {
	this.eventLocation.put(block, e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
	return "MapID: " + id + ", name: " + name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
	return Objects.hash(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final GameMapDataImpl other = (GameMapDataImpl) obj;
	return id == other.id;
    }

}
