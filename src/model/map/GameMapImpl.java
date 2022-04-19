package model.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

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

    /**
     * 
     * @param map the map data used by this class.
     */
    public GameMapImpl(final GameMapData map) {
        this.map = map;
        enteringStartPosition = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentMapId() {
        return map.getMapId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canPassThrough(final Pair<Integer, Integer> block) {
        final Optional<NpcSimple> npc = map.getNpc(block);
        return map.getBlockType(block).canPassThrough() && (npc.isEmpty() || npc.isPresent() && !npc.get().isEnabled());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canChangeMap(final Pair<Integer, Integer> playerPosition) {
        return map.getBlockType(playerPosition) == MapBlockType.MAP_CHANGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeMap(final Pair<Integer, Integer> playerPosition) {
        if (canChangeMap(playerPosition) && map.getNextMap(playerPosition).isPresent()) {
            final Pair<GameMapData, Pair<Integer, Integer>> p = map.getNextMap(playerPosition).get();
            enteringStartPosition = Optional.of(p.getSecond());
            setMap(p.getFirst());
        } else {
            throw new IllegalStateException();
        }

    }

    private void setMap(final GameMapData map) {
        this.map = map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Pair<Integer, Integer>> getPlayerMapPosition() {
        final Optional<Pair<Integer, Integer>> temp = enteringStartPosition;
        enteringStartPosition = Optional.empty();
        return temp;
    }

    private List<Pair<Moves, Integer>> getRandomListMoves(final MonsterSpecies species) {
        final int maxProb = 10;
        final int prob = 5;
        final List<Pair<Moves, Integer>> movesList = new ArrayList<>();
        final List<Moves> learnableMoves = species.getAllLearnableMoves();
        for (final Moves m : learnableMoves) {
            if (movesList.size() < MonsterImpl.NUM_MAX_MOVES && ThreadLocalRandom.current().nextInt(maxProb) < prob) {
                movesList.add(new Pair<>(m, m.getPP()));
            } else {
                break;
            }
        }
        if (movesList.isEmpty() && !learnableMoves.isEmpty()) {
            movesList.add(new Pair<>(learnableMoves.get(0), learnableMoves.get(0).getPP()));
        }
        return movesList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Monster> getWildMonster(final Pair<Integer, Integer> pos) {
        final List<MonsterSpecies> monsters = map.getMonstersInArea();
        if (!map.getBlockType(pos).canMonstersAppear() || monsters.isEmpty()
                || ThreadLocalRandom.current().nextInt(MAXIMUM_MONSTER_SPAWN_RATE) > MONSTER_SPAWN_RATE) {
            return Optional.empty();
        }
        final MonsterSpecies species = monsters.get(ThreadLocalRandom.current().nextInt(monsters.size()));
        final int monsterLevel = ThreadLocalRandom.current().nextInt(map.getWildMonsterLevelRange().getSecond() - map.getWildMonsterLevelRange().getFirst())
                + map.getWildMonsterLevelRange().getFirst();
        final Monster m = new MonsterBuilderImpl().species(species).wild(true)
                .level(map.getWildMonsterLevelRange().getFirst()).exp(0).level(monsterLevel)
                .movesList(getRandomListMoves(species)).build();
        return Optional.of(m);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NpcSimple> getAllNpcsInCurrentMap() {
        return this.map.getAllNpcs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<NpcSimple> getNpcAt(final Pair<Integer, Integer> position) {
        final Optional<NpcSimple> npc = map.getNpc(position);
        if (npc.isPresent() && !npc.get().isEnabled()) {
            return Optional.empty();
        }
        return npc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GameEvent> getEventAt(final Pair<Integer, Integer> position) {
        final Optional<GameEvent> event = this.map.getEvent(position);
        return event.isPresent() && event.get().isActive() ? event : Optional.empty();
    }

}
