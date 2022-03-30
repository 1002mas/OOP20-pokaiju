package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import model.battle.Moves;
import model.battle.MovesImpl;
import model.gameitem.*;

import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesByItem;
import model.monster.MonsterSpeciesByLevel;
import model.monster.MonsterSpeciesSimple;
import model.monster.MonsterStatsImpl;
import model.monster.MonsterType;

public class TestMonster {

    private static final int FIRST_EVOLUTION_LEVEL = 14;
    private static final int SECOND_EVOLUTION_LEVEL = 30;
    private static final int EXP_CAP = 1000;
    private static final int MAX_LVL = 100;
    private static final int HEALTH = 50;

    private Monster monster;
    private MonsterSpecies species;
    private MonsterSpecies firstEvolution;
    private MonsterSpecies secondEvolution;
    private List<Moves> listOfMoves;

    private Monster monsterByItem;
    private MonsterSpecies speciesByItem;
    private MonsterSpecies firstEvolutionByItem;
    private GameItems holdedItemWrong;
    private GameItems holdedItemRight;
    private GameItems neededItem;

    @org.junit.Before
    public void initFactory() {
	this.listOfMoves = List.of(new MovesImpl("Braciere", 50, MonsterType.FIRE, 10),
		new MovesImpl("Attacco", 10, MonsterType.FIRE, 10), new MovesImpl("Volo", 50, MonsterType.FIRE, 10),
		new MovesImpl("Fossa", 50, MonsterType.FIRE, 10));
	// Level test initialization
	this.secondEvolution = new MonsterSpeciesSimple("Pippo3", "Info3", MonsterType.FIRE,
		new MonsterStatsImpl(50, 10, 10, 10));
	this.firstEvolution = new MonsterSpeciesByLevel("Pippo2", "Info2", MonsterType.FIRE,
		new MonsterStatsImpl(50, 10, 10, 10), secondEvolution, SECOND_EVOLUTION_LEVEL);
	this.species = new MonsterSpeciesByLevel("Pippo", "Info", MonsterType.FIRE,
		new MonsterStatsImpl(50, 10, 10, 10), firstEvolution, FIRST_EVOLUTION_LEVEL);
	this.monster = new MonsterBuilderImpl().stats(new MonsterStatsImpl(50, 20, 20, 20)).exp(0).level(1)
		.isWild(false).species(species).movesList(listOfMoves).build();
	// Item test initialization
	this.holdedItemWrong = new EvolutionItem("PietraPippo", 1, "desc", GameItemTypes.EVOLUTIONTOOL);
	this.holdedItemRight = new EvolutionItem("PietraPaperino", 1, "desc", GameItemTypes.EVOLUTIONTOOL);
	this.neededItem = new EvolutionItem("PietraPaperino", 1, "desc", GameItemTypes.EVOLUTIONTOOL);
	this.firstEvolutionByItem = new MonsterSpeciesSimple("Paperino2", "Info2", MonsterType.WATER,
		new MonsterStatsImpl(50, 10, 10, 10));
	this.speciesByItem = new MonsterSpeciesByItem("Paperino", "Info", MonsterType.WATER,
		new MonsterStatsImpl(50, 10, 10, 10), firstEvolutionByItem, neededItem);
	this.monsterByItem = new MonsterBuilderImpl().stats(new MonsterStatsImpl(50, 20, 20, 20)).exp(0).level(1)
		.isWild(false).species(speciesByItem).movesList(listOfMoves).build();
    }

    @org.junit.Test
    public void firstStats() {
	assertEquals(1, monster.getLevel());
	assertEquals(0, monster.getExp());
	assertEquals(HEALTH, monster.getHealth());
    }

    @org.junit.Test
    public void levelingByLevel() {
	System.out.println(monster.toString());
	monster.setLevel(FIRST_EVOLUTION_LEVEL - 1);
	assertEquals("Pippo", monster.getName());
	monster.incExp(EXP_CAP);
	assertEquals("Pippo2", monster.getName());
	monster.setLevel(SECOND_EVOLUTION_LEVEL - 1);
	monster.incExp(EXP_CAP);
	assertEquals("Pippo3", monster.getName());
	assertEquals(30, monster.getLevel());
	assertEquals(0, monster.getExp());
	monster.incExp(5863655);
	assertEquals(MAX_LVL, monster.getLevel());
	System.out.println(monster.toString());
	assertEquals(0, monster.getExp());
    }

    @org.junit.Test
    public void evolutionByRightItem() {
	assertEquals("Paperino", monsterByItem.getName());
	assertTrue(this.monsterByItem.evolveByItem(holdedItemRight));
	assertEquals("Paperino2", monsterByItem.getName());
    }

    @org.junit.Test
    public void evolutionByWrongItem() {
	assertEquals("Paperino", monsterByItem.getName());
	assertFalse(this.monsterByItem.evolveByItem(holdedItemWrong));
	assertNotEquals("Paperino2", monsterByItem.getName());
	assertEquals("Paperino", monsterByItem.getName());

    }

}
