package model.map;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import model.Pair;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterSpecies;
import model.npc.NpcSimple;

public class GameMapImpl implements GameMap {
    private static final int MONSTER_SPAWN_RATE = 5;
    private static final int MAXIMUM_MONSTER_SPAWN_RATE = 10;
    private GameMapData map;
    private Optional<Pair<Integer, Integer>> enteringStartPosition;

    public GameMapImpl(GameMapData map) {
	this.map = map;
	enteringStartPosition = Optional.empty();
    }

    @Override
    public boolean canPassThrough(Pair<Integer, Integer> block) {
	return map.getBlockType(block).canPassThrough() && map.getNPC(block).isEmpty();
    }

    @Override
    public boolean canChangeMap(Pair<Integer, Integer> playerPosition) {
	return map.getBlockType(playerPosition) == MapBlockType.MAP_CHANGE;
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

    private void setMap(GameMapData map) {
	this.map = map;
    }

    @Override
    public Optional<Pair<Integer, Integer>> getPlayerMapPosition() {
	Optional<Pair<Integer, Integer>> temp = enteringStartPosition;
	enteringStartPosition = Optional.empty();
	return temp;
    }

    @Override
    public Optional<Monster> getWildMonster(Pair<Integer, Integer> pos) {
	List<MonsterSpecies> monsters = map.getMonstersInArea();
	if (!map.getBlockType(pos).canMonstersAppear() || monsters.size() < 1
		|| new Random().nextInt(MAXIMUM_MONSTER_SPAWN_RATE) > MONSTER_SPAWN_RATE) {
	    return Optional.empty();
	}
	MonsterSpecies species = monsters.get(new Random().nextInt(monsters.size()));
	Monster m = new MonsterBuilderImpl()
		.species(species)
		.isWild(true)
		.level(1)
		.exp(0)
		.stats(species.getStats())
		.movesList(null)//TODO use monster.getMoves
		.build();
	return Optional.of(m);
    }

    @Override
    public List<NpcSimple> getAllNpcsInCurrentMap() {
	return this.map.getAllNpcs();
    }

    @Override
    public Optional<NpcSimple> getNpcAt(Pair<Integer, Integer> position) {
	return map.getNPC(position);
    }

    @Override
    public int getCurrentMapId() {
	return map.getMapId();
    }

}
