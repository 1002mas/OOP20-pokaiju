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
import model.gameitem.GameItems;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesByItem;
import model.monster.MonsterSpeciesByLevel;
import model.monster.MonsterSpeciesImpl;
import model.monster.MonsterStats;
import model.monster.MonsterStatsImpl;
import model.monster.MonsterType;

public class TestMonster {

    private static final int FIRST_EVOLUTION_LEVEL = 14;
    private static final int SECOND_EVOLUTION_LEVEL = 30;
    //private static final int HEALTH = 50;

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
	secondEvolution = new MonsterSpeciesImpl("Pippo3", "Info3", MonsterType.FIRE, stats, allMoves);
	firstEvolution = new MonsterSpeciesByLevel("Pippo2", "Info2", MonsterType.FIRE, stats, secondEvolution,
		SECOND_EVOLUTION_LEVEL, allMoves);
	species = new MonsterSpeciesByLevel("Pippo", "Info", MonsterType.FIRE, stats, firstEvolution,
		FIRST_EVOLUTION_LEVEL, allMoves);
	this.monster = new MonsterBuilderImpl().health(50).attack(50).defense(50).speed(50).exp(0).level(1).isWild(false).species(species)
		.movesList(listOfMoves).build();
	
	// Item test initialization
	neededItem = new EvolutionItem("PietraPaperino", 1, "desc");
	firstEvolutionByItem = new MonsterSpeciesImpl("Paperino2", "Info2", MonsterType.WATER, stats, allMoves);
	speciesByItem = new MonsterSpeciesByItem("Paperino", "Info", MonsterType.WATER, stats, firstEvolutionByItem,
		neededItem, allMoves);
	monsterByItem = new MonsterBuilderImpl().health(50).attack(50).defense(50).speed(50).exp(0).level(1).isWild(false).species(speciesByItem)
		.movesList(listOfMoves).build();
	
	// Level -> Item initialization
	MonsterSpecies secondEvolutionByLevelAndItem = new MonsterSpeciesImpl("Topolino3", "Info3", MonsterType.GRASS, stats, allMoves);
	MonsterSpecies firstEvolutionByLevelAndItem = new MonsterSpeciesByItem("Topolino2", "Info", MonsterType.GRASS, stats, secondEvolutionByLevelAndItem,
		neededItem, allMoves);
	MonsterSpecies speciesByLevelAndItem = new MonsterSpeciesByLevel("Topolino", "Info", MonsterType.GRASS, stats, firstEvolutionByLevelAndItem,
		FIRST_EVOLUTION_LEVEL, allMoves);
	this.monsterByLevelAndItem = new MonsterBuilderImpl().health(50).attack(50).defense(50).speed(50).exp(0).level(1).isWild(false).species(speciesByLevelAndItem)
		.movesList(listOfMoves).build();
    }

    @org.junit.Test
    public void firstStats() {
	/*assertEquals(1, monster.getLevel());
	assertEquals(0, monster.getExp());
	assertEquals(HEALTH, monster.getStats().getHealth());*/
    }

    @org.junit.Test
    public void evolvingByLevel() {
	monster.incExp((FIRST_EVOLUTION_LEVEL - 2) * MonsterImpl.EXP_CAP);
	assertEquals("Pippo", monster.getName());
	monster.incExp(MonsterImpl.EXP_CAP);
	if(monster.canEvolveByLevel()) {
	    monster.evolve();
	}
	assertEquals("Pippo2", monster.getName());
	monster.incExp(((SECOND_EVOLUTION_LEVEL - monster.getLevel() - 1) * MonsterImpl.EXP_CAP));
	monster.incExp(MonsterImpl.EXP_CAP);
	if(monster.canEvolveByLevel()) {
	    monster.evolve();
	}
	assertEquals("Pippo3", monster.getName());
	assertEquals(30, monster.getLevel());
	assertEquals(0, monster.getExp());
	monster.incExp(5863655);
	assertEquals(MonsterImpl.MAX_LVL, monster.getLevel());
	assertEquals(0, monster.getExp());
    }

    @org.junit.Test
    public void evolutionByRightItem() {
	GameItems holdedItemRight = new EvolutionItem("PietraPaperino", 1, "desc");
	assertEquals("Paperino", monsterByItem.getName());
	assertTrue(this.monsterByItem.canEvolveByItem(holdedItemRight));
	if(monsterByItem.canEvolveByItem(holdedItemRight)) {
	    monsterByItem.evolve();
	}
	assertEquals("Paperino2", monsterByItem.getName());
    }

    @org.junit.Test
    public void evolutionByWrongItem() {
	GameItems holdedItemWrong = new EvolutionItem("PietraPippo", 1, "desc");
	assertEquals("Paperino", monsterByItem.getName());
	assertFalse(this.monsterByItem.canEvolveByItem(holdedItemWrong));
	if(monsterByItem.canEvolveByItem(holdedItemWrong)) {
	    monsterByItem.evolve();
	}
	assertNotEquals("Paperino2", monsterByItem.getName());
	assertEquals("Paperino", monsterByItem.getName());
    }
    
    @org.junit.Test
    public void evolveByLevelAndItem() {
	GameItems holdedItem = new EvolutionItem("PietraPaperino", 1, "desc");
	monsterByLevelAndItem.incExp((FIRST_EVOLUTION_LEVEL - 2) * MonsterImpl.EXP_CAP);
	assertEquals("Topolino", monsterByLevelAndItem.getName());
	monsterByLevelAndItem.incExp(MonsterImpl.EXP_CAP);
	if(monsterByLevelAndItem.canEvolveByLevel()) {
	    monsterByLevelAndItem.evolve();
	}	
	assertEquals("Topolino2", monsterByLevelAndItem.getName());
	monsterByLevelAndItem.incExp(((SECOND_EVOLUTION_LEVEL - monsterByLevelAndItem.getLevel() - 1) * MonsterImpl.EXP_CAP));
	monsterByLevelAndItem.incExp(MonsterImpl.EXP_CAP);
	assertEquals("Topolino2", monsterByLevelAndItem.getName());
	assertEquals(30, monsterByLevelAndItem.getLevel());
	assertEquals(0, monsterByLevelAndItem.getExp());
	monsterByLevelAndItem.incExp(5863655);
	assertEquals(MonsterImpl.MAX_LVL, monsterByLevelAndItem.getLevel());
	assertEquals(0, monsterByLevelAndItem.getExp());
	if(monsterByLevelAndItem.canEvolveByItem(holdedItem)) {
	    monsterByLevelAndItem.evolve();
	}	
	assertEquals("Topolino3", monsterByLevelAndItem.getName());
    }

    
    

}
