package model.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import model.Pair;
import model.battle.Moves;
import model.gameevents.GameEvent;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterImpl;
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
    public int getCurrentMapId() {
	return map.getMapId();
    }

    @Override
    public boolean canPassThrough(Pair<Integer, Integer> block) {
	return map.getBlockType(block).canPassThrough() && map.getNpc(block).isEmpty();
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

    private List<Pair<Moves, Integer>> getRandomListMoves(MonsterSpecies species, int level) {
	final int maxProb = 10;
	final int prob = 5;
	List<Pair<Moves, Integer>> movesList = new ArrayList<>();
	List<Moves> learnableMoves = species.getAllLearnableMoves(level);
	for (Moves m : learnableMoves) {
	    if (movesList.size() < MonsterImpl.NUM_MAX_MOVES && new Random().nextInt(maxProb) < prob) {
		movesList.add(new Pair<>(m, m.getPP()));
	    } else {
		break;
	    }
	}
	if (movesList.size() == 0 && learnableMoves.size() > 0) {
	    movesList.add(new Pair<>(learnableMoves.get(0), learnableMoves.get(0).getPP()));
	}
	return movesList;
    }

    @Override
    public Optional<Monster> getWildMonster(Pair<Integer, Integer> pos) {
	List<MonsterSpecies> monsters = map.getMonstersInArea();
	if (!map.getBlockType(pos).canMonstersAppear() || monsters.size() < 1
		|| new Random().nextInt(MAXIMUM_MONSTER_SPAWN_RATE) > MONSTER_SPAWN_RATE) {
	    return Optional.empty();
	}
	MonsterSpecies species = monsters.get(new Random().nextInt(monsters.size()));
	int monsterLevel = new Random()
		.nextInt(map.getWildMonsterLevelRange().getSecond() - map.getWildMonsterLevelRange().getFirst())
		+ map.getWildMonsterLevelRange().getFirst();
	Monster m = new MonsterBuilderImpl().species(species).isWild(true)
		.level(map.getWildMonsterLevelRange().getFirst()).exp(0)
		.movesList(getRandomListMoves(species, monsterLevel)).build();
	return Optional.of(m);
    }

    @Override
    public List<NpcSimple> getAllNpcsInCurrentMap() {
	return this.map.getAllNpcs();
    }

    @Override
    public Optional<NpcSimple> getNpcAt(Pair<Integer, Integer> position) {
	Optional<NpcSimple> npc = map.getNpc(position);
	if (npc.isPresent() && !npc.get().isEnabled()) {
	    return Optional.empty();
	}
	return npc;
    }

    @Override
    public Optional<GameEvent> getEventAt(Pair<Integer, Integer> position) {
	return this.map.getEvent(position);
    }

}
