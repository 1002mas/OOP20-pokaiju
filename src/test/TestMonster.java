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
import model.gameitem.GameItemTypes;
import model.gameitem.GameItems;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesByItem;
import model.monster.MonsterSpeciesByLevel;
import model.monster.MonsterSpeciesSimple;
import model.monster.MonsterStats;
import model.monster.MonsterStatsImpl;
import model.monster.MonsterType;

public class TestMonster {

    private static final int FIRST_EVOLUTION_LEVEL = 14;
    private static final int SECOND_EVOLUTION_LEVEL = 30;
    private static final int EXP_CAP = 1000;
    private static final int MAX_LVL = 100;
    private static final int HEALTH = 50;

    private Monster monster;
    private Monster monsterByItem;
    private Monster monsterByLevelAndItem;

    @org.junit.Before
    public void initFactory() {
	List<Pair<Moves, Integer>> allMoves;
	GameItems neededItem;
	List<Moves> listOfMoves;
	MonsterSpecies species;
	MonsterSpecies firstEvolution;
	MonsterSpecies secondEvolution;
	MonsterSpecies speciesByItem;
	MonsterSpecies firstEvolutionByItem;
	Moves m1 = new MovesImpl("Braciere", 50, MonsterType.FIRE, 10);
	Moves m2 = new MovesImpl("Attacco", 10, MonsterType.FIRE, 10);
	Moves m3 = new MovesImpl("Volo", 50, MonsterType.FIRE, 10);
	Moves m4 = new MovesImpl("Fossa", 50, MonsterType.FIRE, 10);
	Moves m5 = new MovesImpl("Surf", 50, MonsterType.WATER, 10);
	Moves m6 = new MovesImpl("Idropompa", 50, MonsterType.WATER, 10);
	MonsterStats stats = new MonsterStatsImpl(50, 10, 10, 10);
	
	listOfMoves = List.of(m1, m2, m3, m4);
	allMoves = List.of(new Pair<>(m5, 10), new Pair<>(m6, 25));
	
	// Level test initialization
	secondEvolution = new MonsterSpeciesSimple("Pippo3", "Info3", MonsterType.FIRE, stats, allMoves);
	firstEvolution = new MonsterSpeciesByLevel("Pippo2", "Info2", MonsterType.FIRE, stats, secondEvolution,
		SECOND_EVOLUTION_LEVEL, allMoves);
	species = new MonsterSpeciesByLevel("Pippo", "Info", MonsterType.FIRE, stats, firstEvolution,
		FIRST_EVOLUTION_LEVEL, allMoves);
	this.monster = new MonsterBuilderImpl().stats(stats).exp(0).level(1).isWild(false).species(species)
		.movesList(listOfMoves).build();
	
	// Item test initialization
	neededItem = new EvolutionItem("PietraPaperino", 1, "desc", GameItemTypes.EVOLUTIONTOOL);
	firstEvolutionByItem = new MonsterSpeciesSimple("Paperino2", "Info2", MonsterType.WATER, stats, allMoves);
	speciesByItem = new MonsterSpeciesByItem("Paperino", "Info", MonsterType.WATER, stats, firstEvolutionByItem,
		neededItem, allMoves);
	monsterByItem = new MonsterBuilderImpl().stats(stats).exp(0).level(1).isWild(false).species(speciesByItem)
		.movesList(listOfMoves).build();
	
	// Level -> Item initialization
	MonsterSpecies secondEvolutionByLevelAndItem = new MonsterSpeciesSimple("Topolino3", "Info3", MonsterType.GRASS, stats, allMoves);
	MonsterSpecies firstEvolutionByLevelAndItem = new MonsterSpeciesByItem("Topolino2", "Info", MonsterType.GRASS, stats, secondEvolutionByLevelAndItem,
		neededItem, allMoves);
	MonsterSpecies speciesByLevelAndItem = new MonsterSpeciesByLevel("Topolino", "Info", MonsterType.GRASS, stats, firstEvolutionByLevelAndItem,
		FIRST_EVOLUTION_LEVEL, allMoves);
	this.monsterByLevelAndItem = new MonsterBuilderImpl().stats(stats).exp(0).level(1).isWild(false).species(speciesByLevelAndItem)
		.movesList(listOfMoves).build();
    }

    @org.junit.Test
    public void firstStats() {
	assertEquals(1, monster.getLevel());
	assertEquals(0, monster.getExp());
	assertEquals(HEALTH, monster.getHealth());
    }

    @org.junit.Test
    public void evolvingByLevel() {
	monster.incExp((FIRST_EVOLUTION_LEVEL - 2) * EXP_CAP);
	assertEquals("Pippo", monster.getName());
	monster.incExp(EXP_CAP);
	assertEquals("Pippo2", monster.getName());
	monster.incExp(((SECOND_EVOLUTION_LEVEL - monster.getLevel() - 1) * EXP_CAP));
	monster.incExp(EXP_CAP);
	assertEquals("Pippo3", monster.getName());
	assertEquals(30, monster.getLevel());
	assertEquals(0, monster.getExp());
	monster.incExp(5863655);
	assertEquals(MAX_LVL, monster.getLevel());
	assertEquals(0, monster.getExp());
    }

    @org.junit.Test
    public void evolutionByRightItem() {
	GameItems holdedItemRight = new EvolutionItem("PietraPaperino", 1, "desc", GameItemTypes.EVOLUTIONTOOL);
	assertEquals("Paperino", monsterByItem.getName());
	assertTrue(this.monsterByItem.evolveByItem(holdedItemRight));
	assertEquals("Paperino2", monsterByItem.getName());
    }

    @org.junit.Test
    public void evolutionByWrongItem() {
	GameItems holdedItemWrong = new EvolutionItem("PietraPippo", 1, "desc", GameItemTypes.EVOLUTIONTOOL);
	assertEquals("Paperino", monsterByItem.getName());
	assertFalse(this.monsterByItem.evolveByItem(holdedItemWrong));
	assertNotEquals("Paperino2", monsterByItem.getName());
	assertEquals("Paperino", monsterByItem.getName());
    }
    
    public void evolveByLevelAndItem() {
	GameItems holdedItem = new EvolutionItem("PietraPaperino", 1, "desc", GameItemTypes.EVOLUTIONTOOL);
	monsterByLevelAndItem.incExp((FIRST_EVOLUTION_LEVEL - 2) * EXP_CAP);
	assertEquals("Topolino", monster.getName());
	monsterByLevelAndItem.incExp(EXP_CAP);
	assertEquals("Topolino2", monster.getName());
	monsterByLevelAndItem.incExp(((SECOND_EVOLUTION_LEVEL - monster.getLevel() - 1) * EXP_CAP));
	monsterByLevelAndItem.incExp(EXP_CAP);
	assertEquals("Topolino2", monster.getName());
	assertEquals(30, monster.getLevel());
	assertEquals(0, monster.getExp());
	monsterByLevelAndItem.incExp(5863655);
	assertEquals(MAX_LVL, monster.getLevel());
	assertEquals(0, monster.getExp());
	monsterByLevelAndItem.evolveByItem(holdedItem);
	assertEquals("Topolino3", monster.getName());
    }

    
    

}
