package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import model.Pair;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.gameitem.EvolutionItem;
import model.gameitem.GameItem;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesBuilderImpl;
import model.monster.MonsterType;

public class TestMonster {

    private static final int FIRST_EVOLUTION_LEVEL = 14;
    private static final int SECOND_EVOLUTION_LEVEL = 30;
    private static final int HLT = 50;
    private static final int ATK = 10;
    private static final int DFS = 10;
    private static final int SPD = 10;
    private static final int HIGH_EXP = 5_863_655;
    private static final String MONSTER_NAME = "Paperino";

    private Monster monster;
    private Monster monsterByItem;
    private Monster monsterByLevelAndItem;

    @org.junit.Before
    public void initFactory() {

        List<Pair<Moves, Integer>> listOfMoves;
        List<Moves> monsterSpeciesMoves;
        final Moves m1 = new MovesImpl("Braciere", 50, MonsterType.FIRE, 10);
        final Moves m2 = new MovesImpl("Attacco", 10, MonsterType.FIRE, 10);
        final Moves m3 = new MovesImpl("Volo", 50, MonsterType.FIRE, 10);
        final Moves m4 = new MovesImpl("Fossa", 50, MonsterType.FIRE, 10);

        listOfMoves = List.of(new Pair<>(m1, 10), new Pair<>(m2, 10), new Pair<>(m3, 10), new Pair<>(m4, 10));
        monsterSpeciesMoves = List.of(m1, m2, m3, m4);

        // LEVEL TEST INITIALIZATION
        final MonsterSpecies secondEvolution = new MonsterSpeciesBuilderImpl().name("Pippo3").info("Info3")
                .monsterType(MonsterType.FIRE).health(HLT).attack(ATK).defense(DFS).speed(SPD)
                .movesList(monsterSpeciesMoves).build();

        final MonsterSpecies firstEvolution = new MonsterSpeciesBuilderImpl().name("Pippo2").info("Info2")
                .monsterType(MonsterType.FIRE).health(HLT).attack(ATK).defense(DFS).speed(SPD)
                .evolution(secondEvolution).evolutionLevel(SECOND_EVOLUTION_LEVEL).movesList(monsterSpeciesMoves)
                .build();

        final MonsterSpecies species = new MonsterSpeciesBuilderImpl().name("Pippo").info("Info")
                .monsterType(MonsterType.FIRE).health(HLT).attack(ATK).defense(DFS).speed(SPD).evolution(firstEvolution)
                .evolutionLevel(FIRST_EVOLUTION_LEVEL).movesList(monsterSpeciesMoves).build();

        this.monster = new MonsterBuilderImpl().health(HLT).attack(ATK).defense(DFS).speed(SPD).wild(false)
                .species(species).movesList(listOfMoves).build();

        // ITEM TEST INITIALIZATION
        final GameItem neededItem = new EvolutionItem("Pietra" + MONSTER_NAME, "desc1");

        final MonsterSpecies firstEvolutionByItem = new MonsterSpeciesBuilderImpl().name(MONSTER_NAME + "2").info("Info2")
                .monsterType(MonsterType.WATER).health(HLT).attack(10).defense(10).speed(10)
                .movesList(monsterSpeciesMoves).build();

        final MonsterSpecies speciesByItem = new MonsterSpeciesBuilderImpl().name(MONSTER_NAME).info("Info")
                .monsterType(MonsterType.WATER).health(HLT).attack(ATK).defense(DFS).speed(SPD)
                .evolution(firstEvolutionByItem).gameItem(neededItem).movesList(monsterSpeciesMoves).build();

        monsterByItem = new MonsterBuilderImpl().health(HLT).attack(ATK).defense(DFS).speed(SPD).wild(false)
                .species(speciesByItem).movesList(listOfMoves).build();

        // FIRST EVOLUTION WITH LEVEL, SECOND WITH ITEM INITIALIZATION
        final MonsterSpecies secondEvolutionByLevelAndItem = new MonsterSpeciesBuilderImpl().name("Topolino3")
                .info("Info3").monsterType(MonsterType.GRASS).health(HLT).attack(10).defense(10).speed(10)
                .movesList(monsterSpeciesMoves).build();

        final MonsterSpecies firstEvolutionByLevelAndItem = new MonsterSpeciesBuilderImpl().name("Topolino2")
                .info("Info2").monsterType(MonsterType.GRASS).health(HLT).attack(10).defense(10).speed(10)
                .evolution(secondEvolutionByLevelAndItem).gameItem(neededItem).movesList(monsterSpeciesMoves).build();

        final MonsterSpecies speciesByLevelAndItem = new MonsterSpeciesBuilderImpl().name("Topolino").info("Info")
                .monsterType(MonsterType.GRASS).health(HLT).attack(10).defense(10).speed(10)
                .evolution(firstEvolutionByLevelAndItem).evolutionLevel(FIRST_EVOLUTION_LEVEL)
                .movesList(monsterSpeciesMoves).build();

        this.monsterByLevelAndItem = new MonsterBuilderImpl().health(HLT).attack(ATK).defense(DFS).speed(SPD)
                .wild(false).species(speciesByLevelAndItem).movesList(listOfMoves).build();
    }

    @org.junit.Test
    public void firstStats() {
        assertEquals(1, monster.getLevel());
        assertEquals(0, monster.getExp());
        assertEquals(HLT, monster.getStats().getHealth());
    }

    @org.junit.Test
    public void evolvingByLevel() {
        monster.incExp((FIRST_EVOLUTION_LEVEL - 2) * MonsterImpl.EXP_CAP);
        assertEquals("Pippo", monster.getName());
        monster.incExp(MonsterImpl.EXP_CAP);
        if (monster.canEvolveByLevel()) {
            monster.evolve();
        }
        assertEquals("Pippo2", monster.getName());
        monster.incExp((SECOND_EVOLUTION_LEVEL - monster.getLevel() - 1) * MonsterImpl.EXP_CAP);
        monster.incExp(MonsterImpl.EXP_CAP);
        if (monster.canEvolveByLevel()) {
            monster.evolve();
        }
        assertEquals("Pippo3", monster.getName());
        assertEquals(SECOND_EVOLUTION_LEVEL, monster.getLevel());
        assertEquals(0, monster.getExp());
        monster.incExp(HIGH_EXP);
        assertEquals(MonsterImpl.MAX_LVL, monster.getLevel());
        assertEquals(0, monster.getExp());
    }

    @org.junit.Test
    public void evolutionByRightItem() {
        final GameItem holdedItemRight = new EvolutionItem("PietraPaperino", "desc2");
        assertEquals("Paperino", monsterByItem.getName());
        assertTrue(this.monsterByItem.canEvolveByItem(holdedItemRight));
        if (monsterByItem.canEvolveByItem(holdedItemRight)) {
            monsterByItem.evolve();
        }
        assertEquals(MONSTER_NAME + "2", monsterByItem.getName());
    }

    @org.junit.Test
    public void evolutionByWrongItem() {
        final GameItem holdedItemWrong = new EvolutionItem("PietraPippo", "desc3");
        assertEquals("Paperino", monsterByItem.getName());
        assertFalse(this.monsterByItem.canEvolveByItem(holdedItemWrong));
        if (monsterByItem.canEvolveByItem(holdedItemWrong)) {
            monsterByItem.evolve();
        }
        assertNotEquals(MONSTER_NAME + "2", monsterByItem.getName());
        assertEquals(MONSTER_NAME, monsterByItem.getName());
    }

    @org.junit.Test
    public void evolveByLevelAndItem() {
        final GameItem holdedItem = new EvolutionItem("Pietra" + MONSTER_NAME, "desc4");
        monsterByLevelAndItem.incExp((FIRST_EVOLUTION_LEVEL - 2) * MonsterImpl.EXP_CAP);
        assertEquals("Topolino", monsterByLevelAndItem.getName());
        monsterByLevelAndItem.incExp(MonsterImpl.EXP_CAP);
        if (monsterByLevelAndItem.canEvolveByLevel()) {
            monsterByLevelAndItem.evolve();
        }
        assertEquals("Topolino2", monsterByLevelAndItem.getName());
        monsterByLevelAndItem
                .incExp((SECOND_EVOLUTION_LEVEL - monsterByLevelAndItem.getLevel() - 1) * MonsterImpl.EXP_CAP);
        monsterByLevelAndItem.incExp(MonsterImpl.EXP_CAP);
        assertEquals("Topolino2", monsterByLevelAndItem.getName());
        assertEquals(SECOND_EVOLUTION_LEVEL, monsterByLevelAndItem.getLevel());
        assertEquals(0, monsterByLevelAndItem.getExp());
        monsterByLevelAndItem.incExp(HIGH_EXP);
        assertEquals(MonsterImpl.MAX_LVL, monsterByLevelAndItem.getLevel());
        assertEquals(0, monsterByLevelAndItem.getExp());
        if (monsterByLevelAndItem.canEvolveByItem(holdedItem)) {
            monsterByLevelAndItem.evolve();
        }
        assertEquals("Topolino3", monsterByLevelAndItem.getName());
    }

    @org.junit.Test
    public void speciesBuilderTest() {
        assertEquals(this.monster.getSpecies().getName(), "Pippo");
    }

}
