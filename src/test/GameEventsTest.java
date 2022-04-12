package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import model.Pair;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.gameevents.GameEvent;
import model.gameevents.MonsterGift;
import model.gameevents.NpcBehaviorChanger;
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
	player = new PlayerImpl("Player", Gender.MAN, 4343, new Pair<>(1, 0));
	List<Pair<Moves, Integer>> moves = List.of(new Pair<>(new MovesImpl("Slap", 20, MonsterType.FIRE, 10), 2));

	MonsterSpecies monsterSpeciesA = new MonsterSpeciesBuilderImpl().name("MonsterA").allMoves(moves)
		.monsterType(MonsterType.FIRE).attack(5).speed(5).defense(5).health(5).info("Fire monsterA").build();
	this.monsterA = new MonsterBuilderImpl().level(5).species(monsterSpeciesA).movesList(moves).build();

	MonsterSpecies monsterSpeciesB = new MonsterSpeciesBuilderImpl().name("MonsterB").allMoves(moves)
		.monsterType(MonsterType.WATER).attack(5).speed(5).defense(5).health(5).info("Water monsterB").build();
	this.monsterB = new MonsterBuilderImpl().level(5).species(monsterSpeciesB).movesList(moves).build();

	MonsterSpecies monsterSpeciesC = new MonsterSpeciesBuilderImpl().name("MonsterA").allMoves(moves)
		.monsterType(MonsterType.GRASS).attack(5).speed(5).defense(5).health(5).info("Grass monsterC").build();
	this.monsterC = new MonsterBuilderImpl().level(5).species(monsterSpeciesC).movesList(moves).build();
    }

    @Test
    public void starterMonsterSelection() {
	List<String> teacherSentences = List.of("Choose a monster", "Congrats");
	NpcSimple teacher = new NpcSimpleImpl("Doc", teacherSentences, new Pair<>(0, 0), true, true);
	NpcSimple item1 = new NpcSimpleImpl("monsterAnpc", List.of("You choose monsterA"), new Pair<>(0, 1), true,
		true);
	NpcSimple item2 = new NpcSimpleImpl("monsterBnpc", List.of("You choose monsterB"), new Pair<>(0, 2), true,
		true);
	NpcSimple item3 = new NpcSimpleImpl("monsterCnpc", List.of("You choose monsterC"), new Pair<>(0, 3), true,
		true);

	NpcBehaviorChanger npcDisappearingEvent = new NpcBehaviorChanger(0, false, true, true);
	npcDisappearingEvent.addNpcDialogChange(teacher, 1);
	npcDisappearingEvent.addNpcVisibilityChange(item1, false);
	npcDisappearingEvent.addNpcVisibilityChange(item2, false);
	npcDisappearingEvent.addNpcVisibilityChange(item3, false);

	GameEvent eventA = new MonsterGift(1, true, true, false, List.of(this.monsterA), player);
	eventA.addSuccessiveGameEvent(npcDisappearingEvent);

	GameEvent eventB = new MonsterGift(1, true, true, false, List.of(this.monsterB), player);
	eventB.addSuccessiveGameEvent(npcDisappearingEvent);

	GameEvent eventC = new MonsterGift(1, true, true, false, List.of(this.monsterC), player);
	eventC.addSuccessiveGameEvent(npcDisappearingEvent);

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
	Pair<Integer, Integer> startingPosition = new Pair<>(10, 10);
	Pair<Integer, Integer> newPosition = new Pair<>(2, 0);
	NpcSimple mario = new NpcSimpleImpl("Mario", List.of("Licia? She is behind you"), new Pair<>(0, 0), true, true);
	NpcSimple licia = new NpcSimpleImpl("Licia", List.of("BOO! Scared, aren't you?"), startingPosition, true, true);

	NpcBehaviorChanger npcEvent = new NpcBehaviorChanger(0, true, true, false);
	npcEvent.addNpcPositionChange(licia, newPosition);

	assertEquals(licia.getPosition(), startingPosition);

	mario.addGameEvent(npcEvent);
	mario.interactWith();

	assertEquals(licia.getPosition(), newPosition);
	assertFalse(npcEvent.isActive());
	assertFalse(npcEvent.isPermanent());
    }

    private GameMap initMonsterSpawnMap(Pair<Integer, Integer> eventPosition, List<NpcSimple> npcs,
	    UniqueMonsterEvent monsterEvent) {
	Map<Pair<Integer, Integer>, MapBlockType> blocks = new HashMap<>();
	int rows = 20;
	int columns = 20;
	for (int r = 0; r < rows; r++) {
	    for (int c = 0; c < columns; c++) {
		blocks.put(new Pair<>(r, c), MapBlockType.WALK);
	    }
	}

	GameMapData map = new GameMapDataImpl(0, 0, 0, "test map", blocks, new HashSet<>(npcs), null);
	map.addEventAt(monsterEvent, eventPosition);
	return new GameMapImpl(map);
    }

    @Test
    public void uniqueMonsterSpawn() {
	// interaction with npc then monster spawn available
	UniqueMonsterEvent monsterEvent = new UniqueMonsterEvent(15, false, false, monsterA, player);
	player.addMonster(monsterB);

	Pair<Integer, Integer> eventPosition = new Pair<>(10, 10);

	NpcSimple mario = new NpcSimpleImpl("Mario", List.of("There is a special monster over there"), new Pair<>(0, 0),
		true, true);

	NpcBehaviorChanger npcEvent = new NpcBehaviorChanger(1, true, true, false);
	npcEvent.addSuccessiveGameEvent(monsterEvent);
	mario.addGameEvent(npcEvent);

	GameMap map = initMonsterSpawnMap(eventPosition, List.of(mario), monsterEvent);

	assertFalse(monsterEvent.isActive());
	assertEquals(map.getEventAt(eventPosition).get().getEventID(), monsterEvent.getEventID());

	mario.interactWith();

	assertTrue(map.getEventAt(eventPosition).get().isActive());
	monsterEvent.activate();
	assertEquals(monsterEvent.getMonsterBattle().get().getCurrentEnemyMonster(), monsterA);
    }

}
