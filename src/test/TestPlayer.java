package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import model.Pair;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.gameitem.CaptureItem;
import model.gameitem.GameItem;
import model.gameitem.HealingItem;
import model.map.GameMapData;
import model.map.GameMapDataImpl;
import model.map.GameMapImpl;
import model.map.MapBlockType;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesBuilderImpl;
import model.monster.MonsterType;
import model.player.Gender;
import model.player.Player;
import model.player.PlayerImpl;

public class TestPlayer {
    private Player player;
    private Monster monsterA;
    private Monster monsterB;
    private Monster monsterC;
    private GameItem monsterBall;
    private GameItem healingPotion;
    private static final int DEFAULT_STATS = 50;
    private static final int PP1 = 20;
    private static final int PP2 = 10;
    private static final int START_EXP = 0;
    private static final int START_LEVEL = 1;

    @Before
    public void init() {
        final int trainerNumber = 100_001;
        MonsterSpecies species;
        List<Moves> firstMonsterSpeciesMoves;
        List<Moves> secondMonsterSpeciesMoves;
        List<Pair<Moves, Integer>> firstListOfMoves;
        List<Pair<Moves, Integer>> secondListOfMoves;
        Map<Pair<Integer, Integer>, MapBlockType> map;
        map = new HashMap<>();
        final int rows = 21;
        final int columns = 21;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                map.put(new Pair<>(r, c), MapBlockType.WALK);
            }
        }

        final GameMapData firstMap = new GameMapDataImpl(1, 1, 10, "MAP1", map, new ArrayList<>());
        player = new PlayerImpl("Joker", Gender.MAN, trainerNumber, new Pair<>(1, 0), new GameMapImpl(firstMap));

        final Moves m1 = new MovesImpl("Water tornado", DEFAULT_STATS, MonsterType.WATER, PP2);
        final Moves m2 = new MovesImpl("Flame punch", PP2, MonsterType.FIRE, PP1);
        firstMonsterSpeciesMoves = List.of(m1);
        secondMonsterSpeciesMoves = List.of(m2);
        firstListOfMoves = List.of(new Pair<>(m1, PP2));
        secondListOfMoves = List.of(new Pair<>(m2, PP1));
        monsterBall = new CaptureItem("Monster Ball", "Can capture a monster");
        healingPotion = new HealingItem("Healing potion", "Restore 50Hp");

        species = new MonsterSpeciesBuilderImpl().name("bibol").info("Info1").monsterType(MonsterType.FIRE)
                .health(DEFAULT_STATS).attack(PP2).defense(PP2).speed(PP2).movesList(firstMonsterSpeciesMoves).build();

        monsterA = new MonsterBuilderImpl().health(DEFAULT_STATS).attack(DEFAULT_STATS).defense(DEFAULT_STATS)
                .speed(DEFAULT_STATS).exp(START_EXP).level(START_LEVEL).wild(false).species(species)
                .movesList(firstListOfMoves).build();

        species = new MonsterSpeciesBuilderImpl().name("greyfish").info("Info2").monsterType(MonsterType.WATER)
                .health(DEFAULT_STATS).attack(10).defense(10).speed(10).movesList(secondMonsterSpeciesMoves).build();

        monsterB = new MonsterBuilderImpl().health(DEFAULT_STATS).attack(DEFAULT_STATS).defense(DEFAULT_STATS)
                .speed(DEFAULT_STATS).exp(START_EXP).level(START_LEVEL).wild(false).species(species)
                .movesList(secondListOfMoves).build();

        species = new MonsterSpeciesBuilderImpl().name("Pipochu").info("Info3").monsterType(MonsterType.GRASS)
                .health(DEFAULT_STATS).attack(10).defense(10).speed(10).movesList(secondMonsterSpeciesMoves).build();

        monsterC = new MonsterBuilderImpl().health(DEFAULT_STATS).attack(DEFAULT_STATS).defense(DEFAULT_STATS)
                .speed(DEFAULT_STATS).exp(START_EXP).level(START_LEVEL).wild(false).species(species)
                .movesList(secondListOfMoves).build();
    }

    @Test
    public void monsterListCheck() {
        this.player.addMonster(monsterA);
        this.player.addMonster(monsterB);
        assertEquals(2, this.player.getAllMonsters().size());
        assertFalse(this.player.getAllMonsters().contains(monsterC));
        assertEquals("bibol", this.player.getAllMonsters().get(0).getName());
        assertNotEquals("bibol", this.player.getAllMonsters().get(1).getName());
        assertTrue(this.player.removeMonster(monsterB));
        assertFalse(this.player.removeMonster(monsterC));
    }

    @Test
    public void itemListCheck() {
        this.player.addItem(monsterBall);
        assertTrue("Monster Ball",
                this.player.getAllItems().entrySet().stream().anyMatch(e -> e.getKey().equals(monsterBall)));
        assertFalse(this.player.getAllItems().containsKey(healingPotion));
        this.player.addItem(healingPotion);
        assertTrue(this.player.getAllItems().containsKey(healingPotion));
        this.player.useItem(monsterBall);
        assertFalse(this.player.getAllItems().containsKey(monsterBall));
        this.player.addItem(healingPotion);
        assertEquals(2, this.player.getAllItems().get(healingPotion).intValue());
    }

    @Test
    public void useItemCheck() {
        this.player.addMonster(monsterA);
        this.player.addItem(healingPotion);
        this.player.getAllMonsters().get(0).setHealth(0);
        assertEquals(0, this.player.getAllMonsters().get(0).getStats().getHealth());
        this.player.useItemOnMonster(healingPotion, monsterA);
        assertEquals(this.player.getAllMonsters().get(0).getMaxHealth(),
                this.player.getAllMonsters().get(0).getStats().getHealth());
        this.player.addItem(healingPotion);
        this.player.useItemOnMonster(healingPotion, monsterA);
        assertTrue(this.player.getAllItems().containsKey(healingPotion));
    }

    @Test
    public void playerPositionCheck() {
        final Pair<Integer, Integer> limitPosition = new Pair<>(20, 20);
        assertEquals(new Pair<>(1, 0), this.player.getPosition());
        this.player.moveUp();
        assertNotEquals(new Pair<>(1, -1), this.player.getPosition());
        this.player.moveRight();
        assertEquals(new Pair<>(2, 0), this.player.getPosition());
        this.player.moveDown();
        assertEquals(new Pair<>(2, 1), this.player.getPosition());
        this.player.moveLeft();
        assertEquals(new Pair<>(1, 1), this.player.getPosition());
        assertNotEquals(new Pair<>(10, 10), this.player.getPosition());
        this.player.setPosition(limitPosition);
        assertEquals(limitPosition, this.player.getPosition());
        assertFalse(this.player.moveDown());
        assertFalse(this.player.moveRight());
        assertTrue(this.player.moveUp());
        assertTrue(this.player.moveLeft());

    }
}
