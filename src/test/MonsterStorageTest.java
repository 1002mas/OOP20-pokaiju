package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Pair;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesBuilderImpl;
import model.monster.MonsterType;
import model.player.Gender;
import model.player.MonsterBox;
import model.player.MonsterBoxImpl;
import model.player.MonsterStorage;
import model.player.MonsterStorageImpl;
import model.player.Player;
import model.player.PlayerImpl;

public class MonsterStorageTest {

    private MonsterStorage monsterStorage1;

    private MonsterBox monsterBox;
    private Monster monster1;
    private Monster monster2;
    private Monster monster3;
    private Monster monster4;
    private Monster monster5;
    private Monster monster6;
    private Monster monster7;
    private Player player;

    private void addMonsterList(final MonsterStorage storage, final int n, final Monster monster) {
        for (int i = 0; i < n; i++) {
            storage.addMonster(monster);
        }
    }

    @org.junit.Before
    public void initFactory() {
        final int posCoord = 1;
        final int genericNumber = 3;
        final Pair<Integer, Integer> position = new Pair<>(posCoord, posCoord);
        this.player = new PlayerImpl("player", Gender.WOMAN, 0, position, null);
        // monster
        final Moves m1 = new MovesImpl("mossa1", genericNumber, MonsterType.GRASS, genericNumber);
        final Moves m2 = new MovesImpl("mossa2", genericNumber, MonsterType.FIRE, genericNumber);
        final List<Pair<Moves, Integer>> listOfMoves = List.of(new Pair<>(m1, genericNumber),
                new Pair<>(m2, genericNumber));
        final List<Moves> listMoves = List.of(m1, m2);
        final MonsterSpecies species = new MonsterSpeciesBuilderImpl().name("nome1").info("Info1")
                .monsterType(MonsterType.FIRE).health(genericNumber).attack(genericNumber).defense(3).speed(1)
                .movesList(listMoves).build();

        this.monster1 = new MonsterBuilderImpl().health(genericNumber).attack(genericNumber).defense(genericNumber)
                .speed(genericNumber).exp(genericNumber).level(genericNumber).wild(false).species(species)
                .movesList(listOfMoves).build();
        // final MonsterSpecies species2 = new
        // MonsterSpeciesBuilderImpl().name("nome2").info("Info2")
        // .monsterType(MonsterType.FIRE).health(genericNumber).attack(genericNumber).defense(genericNumber)
        // .speed(genericNumber).movesList(listMoves).build();
        this.monster2 = new MonsterBuilderImpl().health(genericNumber).attack(genericNumber).defense(genericNumber)
                .speed(genericNumber).exp(genericNumber).level(genericNumber).wild(false).species(species)
                .movesList(listOfMoves).build();
        this.monster3 = new MonsterBuilderImpl().health(genericNumber).attack(genericNumber).defense(genericNumber)
                .speed(genericNumber).exp(genericNumber).level(genericNumber).wild(false).species(species)
                .movesList(listOfMoves).build();
        this.monster4 = new MonsterBuilderImpl().health(genericNumber).attack(genericNumber).defense(genericNumber)
                .speed(genericNumber).exp(genericNumber).level(genericNumber).wild(false).species(species)
                .movesList(listOfMoves).build();
        this.monster5 = new MonsterBuilderImpl().health(genericNumber).attack(genericNumber).defense(genericNumber)
                .speed(genericNumber).exp(genericNumber).level(genericNumber).wild(false).species(species)
                .movesList(listOfMoves).build();
        this.monster6 = new MonsterBuilderImpl().health(genericNumber).attack(genericNumber).defense(genericNumber)
                .speed(genericNumber).exp(genericNumber).level(genericNumber).wild(false).species(species)
                .movesList(listOfMoves).build();
        this.monster7 = new MonsterBuilderImpl().health(genericNumber).attack(genericNumber).defense(genericNumber)
                .speed(genericNumber).exp(genericNumber).level(genericNumber).wild(false).species(species)
                .movesList(listOfMoves).build();
        this.monster1 = new MonsterBuilderImpl().health(genericNumber).attack(genericNumber).defense(genericNumber)
                .speed(genericNumber).exp(genericNumber).level(genericNumber).wild(false).species(species)
                .movesList(listOfMoves).build();

        this.player.addMonster(this.monster2);
        this.monsterStorage1 = new MonsterStorageImpl(this.player);
        this.monsterBox = new MonsterBoxImpl("Box1", this.monsterStorage1.getMaxSizeOfBox());

    }

    @org.junit.Test
    public void monsterBox() {
        this.monsterBox.addMonster(this.monster1);
        assertEquals(this.monster1, this.monsterBox.getAllMonsters().get(0));
        assertEquals(Optional.of(monster1), this.monsterBox.getMonster(this.monster1.getId()));

        this.monsterBox.exchange(monster2, monster1.getId());
        assertEquals(1, this.monsterBox.getAllMonsters().size());
        assertEquals(Optional.of(this.monster2), this.monsterBox.getMonster(this.monster2.getId()));

        this.monsterBox.removeMonster(this.monster2.getId());
        assertFalse(this.monsterBox.getAllMonsters().contains(this.monster2));
        assertEquals(Optional.empty(), this.monsterBox.getMonster(this.monster2.getId()));
        assertEquals(0, this.monsterBox.getAllMonsters().size());

    }

    @org.junit.Test
    public void monsterStorage1() {
        // add monster
        assertEquals("BOX1", this.monsterStorage1.getCurrentBoxName());
        assertTrue(this.monsterStorage1.addMonster(this.monster1));
        assertTrue(this.monsterStorage1.getCurrentBoxMonsters().contains(this.monster1));
        assertEquals("BOX1", this.monsterStorage1.getCurrentBoxName());
        // previous box

        this.monsterStorage1.previousBox();
        assertEquals("BOX10", this.monsterStorage1.getCurrentBoxName());
        // next box
        this.monsterStorage1.nextBox();
        assertEquals("BOX1", this.monsterStorage1.getCurrentBoxName());

        addMonsterList(this.monsterStorage1, this.monsterStorage1.getMaxSizeOfBox(), this.monster1);
        this.monsterStorage1.nextBox();

        assertTrue(this.monsterStorage1.getCurrentBoxMonsters().contains(this.monster1));
        assertEquals(1, this.monsterStorage1.getCurrentBoxMonsters().size());
        // exchange player monster2 with box monster1
        assertTrue(this.monsterStorage1.exchange(this.monster2, this.monster1.getId()));
        assertTrue(this.monsterStorage1.getCurrentBoxMonsters().contains(this.monster2));
        assertTrue(this.player.getAllMonsters().contains(this.monster1));

        this.player.addMonster(this.monster3); // player team: monster3, monster1
        assertTrue(this.monsterStorage1.depositMonster(this.monster3));
        assertFalse(this.player.getAllMonsters().contains(this.monster3));
        assertTrue(this.monsterStorage1.getCurrentBoxMonsters().contains(this.monster3));

        // player team: monster1
        // withDrawMonster
        assertTrue(this.monsterStorage1.withdrawMonster(monster3.getId()));
        assertFalse(this.monsterStorage1.withdrawMonster(monster3.getId()));

        // player team: monster1, monster3
        this.player.addMonster(this.monster4);
        this.player.addMonster(this.monster5);
        this.player.addMonster(this.monster6);
        this.player.addMonster(this.monster7);
        // player team full

        // withDrawMonster
        assertFalse(this.monsterStorage1.withdrawMonster(this.monster2.getId()));
    }

    @org.junit.Test
    public void monsterStorageWithList() {
        MonsterStorage monsterStorage2;
        final MonsterBox box2 = new MonsterBoxImpl("NEWBOX1", 10);
        final List<MonsterBox> boxList = List.of(box2);
        monsterStorage2 = new MonsterStorageImpl(this.player, boxList);
        assertEquals("NEWBOX1", monsterStorage2.getCurrentBoxName());
    }

    @org.junit.Test
    public void monsterStorageWithList2() {
        MonsterStorage monsterStorage3;
        final int index = 12;
        final List<MonsterBox> boxList = new ArrayList<>();
        for (int i = 1; i < index; i++) {
            boxList.add(new MonsterBoxImpl("BOX" + i, 10));
        }
        monsterStorage3 = new MonsterStorageImpl(player, boxList);
        monsterStorage3.previousBox();
        assertEquals("BOX10", monsterStorage3.getCurrentBoxName());
    }

    @org.junit.Test
    public void fullMonsterStorage() {
        MonsterStorage monsterStorage4;
        monsterStorage4 = new MonsterStorageImpl(this.player);
        for (int i = 0; i < 10; i++) {
            addMonsterList(monsterStorage4, 10, this.monster1);
            monsterStorage4.nextBox();
        }

        assertFalse(monsterStorage4.addMonster(this.monster2));
        assertTrue(monsterStorage4.withdrawMonster(this.monster1.getId()));
        assertTrue(monsterStorage4.depositMonster(this.monster1));
        assertFalse(monsterStorage4.addMonster(this.monster3));
    }

}
