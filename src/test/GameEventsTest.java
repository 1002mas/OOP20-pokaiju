package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import model.Pair;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.gameevents.GameEvent;
import model.gameevents.MonsterGift;
import model.gameevents.NpcActivityChanger;
import model.gameevents.NpcPositionChanger;
import model.gameevents.NpcTextChanger;
import model.gameevents.NpcVisibilityChanger;
import model.gameevents.UniqueMonsterEvent;
import model.map.GameMap;
import model.map.GameMapData;
import model.map.GameMapDataImpl;
import model.map.GameMapImpl;
import model.map.MapBlockType;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesBuilderImpl;
import model.monster.MonsterType;
import model.npc.NpcSimple;
import model.npc.NpcSimpleImpl;
import model.player.Gender;
import model.player.Player;
import model.player.PlayerImpl;

public class GameEventsTest {
    private Player player;
    private Monster monsterA;
    private Monster monsterB;
    private Monster monsterC;

    @Before
    public void initFactory() {
        final int baseStat = 5;
        final int monsterLevel = 5;
        final int playerID = 4343;
        player = new PlayerImpl("Player", Gender.MAN, playerID, new Pair<>(1, 0), null);
        final List<Moves> movesSpecies = List.of(new MovesImpl("Slap", 20, MonsterType.FIRE, 10));
        final List<Pair<Moves, Integer>> moves = List.of(new Pair<>(movesSpecies.get(0), 2));

        final MonsterSpecies monsterSpeciesA = new MonsterSpeciesBuilderImpl().name("MonsterA").movesList(movesSpecies)
                .monsterType(MonsterType.FIRE).attack(baseStat).speed(baseStat).defense(baseStat).health(baseStat)
                .info("Fire monsterA").build();
        this.monsterA = new MonsterBuilderImpl().level(monsterLevel).species(monsterSpeciesA).movesList(moves).build();

        final MonsterSpecies monsterSpeciesB = new MonsterSpeciesBuilderImpl().name("MonsterB").movesList(movesSpecies)
                .monsterType(MonsterType.WATER).attack(baseStat).speed(baseStat).defense(baseStat).health(baseStat)
                .info("Water monsterB").build();
        this.monsterB = new MonsterBuilderImpl().level(monsterLevel).species(monsterSpeciesB).movesList(moves).build();

        final MonsterSpecies monsterSpeciesC = new MonsterSpeciesBuilderImpl().name("MonsterA").movesList(movesSpecies)
                .monsterType(MonsterType.GRASS).attack(monsterLevel).speed(monsterLevel).defense(monsterLevel)
                .health(monsterLevel).info("Grass monsterC").build();
        this.monsterC = new MonsterBuilderImpl().level(monsterLevel).species(monsterSpeciesC).movesList(moves).build();
    }

    @Test
    public void starterMonsterSelection() {
        final List<String> teacherSentences = List.of("Choose a monster", "Congrats");
        final NpcSimple teacher = new NpcSimpleImpl("Doc", teacherSentences, new Pair<>(0, 0), true, true);
        final NpcSimple item1 = new NpcSimpleImpl("monsterAnpc", List.of("You choose monsterA"), new Pair<>(0, 1), true,
                true);
        final NpcSimple item2 = new NpcSimpleImpl("monsterBnpc", List.of("You choose monsterB"), new Pair<>(0, 2), true,
                true);
        final NpcSimple item3 = new NpcSimpleImpl("monsterCnpc", List.of("You choose monsterC"), new Pair<>(0, 3), true,
                true);

        final GameEvent teacherTextChange = new NpcTextChanger(1, false, true, true, teacher, 1);

        final GameEvent npcEventV1 = new NpcVisibilityChanger(2, false, true, true, item1, false);
        final GameEvent npcEventEn1 = new NpcActivityChanger(3, false, true, true, item1, false);

        final GameEvent npcEventV2 = new NpcVisibilityChanger(4, false, true, true, item2, false);
        final GameEvent npcEventEn2 = new NpcActivityChanger(5, false, true, true, item2, false);

        final GameEvent npcEventV3 = new NpcVisibilityChanger(6, false, true, true, item3, false);
        final GameEvent npcEventEn3 = new NpcActivityChanger(7, false, true, true, item3, false);

        teacherTextChange.addSuccessiveGameEvent(npcEventEn1);
        teacherTextChange.addSuccessiveGameEvent(npcEventEn2);
        teacherTextChange.addSuccessiveGameEvent(npcEventEn3);
        teacherTextChange.addSuccessiveGameEvent(npcEventV1);
        teacherTextChange.addSuccessiveGameEvent(npcEventV2);
        teacherTextChange.addSuccessiveGameEvent(npcEventV3);

        final GameEvent eventA = new MonsterGift(8, true, true, false, List.of(this.monsterA), player);
        eventA.addSuccessiveGameEvent(teacherTextChange);

        final GameEvent eventB = new MonsterGift(9, true, true, false, List.of(this.monsterB), player);
        eventB.addSuccessiveGameEvent(teacherTextChange);

        final GameEvent eventC = new MonsterGift(10, true, true, false, List.of(this.monsterC), player);
        eventC.addSuccessiveGameEvent(teacherTextChange);

        eventA.addDependentGameEvent(eventB);
        eventA.addDependentGameEvent(eventC);
        eventB.addDependentGameEvent(eventA);
        eventB.addDependentGameEvent(eventC);
        eventC.addDependentGameEvent(eventA);
        eventC.addDependentGameEvent(eventB);

        item1.addGameEvent(eventA);
        item2.addGameEvent(eventB);
        item3.addGameEvent(eventC);

        assertEquals(teacherSentences.get(0), teacher.interactWith().get());
        assertEquals(teacherSentences.get(0), teacher.interactWith().get());

        assertEquals(item1.interactWith().get(), "You choose monsterA");

        assertFalse(item1.isEnabled());
        assertFalse(item1.isVisible());
        assertFalse(item2.isEnabled());
        assertFalse(item2.isVisible());
        assertFalse(item3.isEnabled());
        assertFalse(item3.isVisible());

        assertFalse(eventA.isActive());
        assertFalse(eventB.isActive());
        assertFalse(eventC.isActive());

        assertEquals(teacherSentences.get(1), teacher.interactWith().get());

    }

    @Test
    public void npcChangePosition() {
        final Pair<Integer, Integer> startingPosition = new Pair<>(10, 10);
        final Pair<Integer, Integer> newPosition = new Pair<>(2, 0);
        final NpcSimple mario = new NpcSimpleImpl("Mario", List.of("Licia? She is behind you"), new Pair<>(0, 0), true,
                true);
        final NpcSimple licia = new NpcSimpleImpl("Licia", List.of("BOO! Scared, aren't you?"), startingPosition, true,
                true);

        final GameEvent npcEvent = new NpcPositionChanger(0, true, false, false, licia, newPosition);

        assertEquals(licia.getPosition(), startingPosition);

        mario.addGameEvent(npcEvent);
        mario.interactWith();

        assertEquals(licia.getPosition(), newPosition);
        assertFalse(npcEvent.isActive());
        assertFalse(npcEvent.isReactivable());
    }

    private GameMap initMonsterSpawnMap(final Pair<Integer, Integer> eventPosition, final List<NpcSimple> npcs,
            final GameEvent monsterEvent) {
        final Map<Pair<Integer, Integer>, MapBlockType> blocks = new HashMap<>();
        final int rows = 20;
        final int columns = 20;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                blocks.put(new Pair<>(r, c), MapBlockType.WALK);
            }
        }

        final GameMapData map = new GameMapDataImpl(0, 0, 0, "test map", blocks, null);
        npcs.forEach(npc -> map.addNpc(npc));
        map.addEventAt(monsterEvent, eventPosition);
        return new GameMapImpl(map);
    }

    @Test
    public void uniqueMonsterSpawn() {
        // interaction with npc then monster spawn available
        final GameEvent monsterEvent = new UniqueMonsterEvent(15, false, false, monsterA);
        player.addMonster(monsterB);

        final Pair<Integer, Integer> eventPosition = new Pair<>(10, 10);

        final NpcSimple mario = new NpcSimpleImpl("Mario", List.of("There is a special monster over there"), new Pair<>(0, 0),
                true, true);

        final GameEvent npcEvent = new NpcActivityChanger(1, true, true, false, mario, true);
        npcEvent.addSuccessiveGameEvent(monsterEvent);
        mario.addGameEvent(npcEvent);

        final GameMap map = initMonsterSpawnMap(eventPosition, List.of(mario), monsterEvent);

        assertFalse(monsterEvent.isActive());

        mario.interactWith();

        assertTrue(map.getEventAt(eventPosition).get().isActive());
        monsterEvent.activate();
        assertTrue(monsterEvent.isBattle());

        monsterEvent.setActivity(true);

        assertFalse(monsterEvent.isActive());
    }

}
