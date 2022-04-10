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
    // private static final int HEALTH = 50;

    private Monster monster;
    private Monster monsterByItem;
    private Monster monsterByLevelAndItem;

    @org.junit.Before
    public void initFactory() {

	List<Pair<Moves, Integer>> allMoves;
	List<Pair<Moves, Integer>> listOfMoves;

	Moves m1 = new MovesImpl("Braciere", 50, MonsterType.FIRE, 10);
	Moves m2 = new MovesImpl("Attacco", 10, MonsterType.FIRE, 10);
	Moves m3 = new MovesImpl("Volo", 50, MonsterType.FIRE, 10);
	Moves m4 = new MovesImpl("Fossa", 50, MonsterType.FIRE, 10);
	Moves m5 = new MovesImpl("Surf", 50, MonsterType.WATER, 10);
	Moves m6 = new MovesImpl("Idropompa", 50, MonsterType.WATER, 10);

	listOfMoves = List.of(new Pair<>(m1, 10), new Pair<>(m2, 10), new Pair<>(m3, 10), new Pair<>(m4, 10));
	allMoves = List.of(new Pair<>(m5, 10), new Pair<>(m6, 25));

	// LEVEL TEST INITIALIZATION
	MonsterSpecies secondEvolution = new MonsterSpeciesBuilderImpl().name("Pippo3").info("Info3")
		.monsterType(MonsterType.FIRE).health(50).attack(10).defense(10).speed(10).allMoves(allMoves).build();

	MonsterSpecies firstEvolution = new MonsterSpeciesBuilderImpl().name("Pippo2").info("Info2")
		.monsterType(MonsterType.FIRE).health(50).attack(10).defense(10).speed(10).evolution(secondEvolution)
		.evolutionLevel(SECOND_EVOLUTION_LEVEL).allMoves(allMoves).build();

	MonsterSpecies species = new MonsterSpeciesBuilderImpl().name("Pippo").info("Info").monsterType(MonsterType.FIRE)
		.health(50).attack(10).defense(10).speed(10).evolution(firstEvolution)
		.evolutionLevel(FIRST_EVOLUTION_LEVEL).allMoves(allMoves).build();

	this.monster = new MonsterBuilderImpl().health(50).attack(50).defense(50).speed(50).exp(0).level(1)
		.isWild(false).species(species).movesList(listOfMoves).build();

	// ITEM TEST INITIALIZATION
	GameItem neededItem = new EvolutionItem("PietraPaperino", 1, "desc");

	MonsterSpecies firstEvolutionByItem = new MonsterSpeciesBuilderImpl().name("Paperino2").info("Info2")
		.monsterType(MonsterType.WATER).health(50).attack(10).defense(10).speed(10).allMoves(allMoves).build();

	MonsterSpecies speciesByItem = new MonsterSpeciesBuilderImpl().name("Paperino").info("Info")
		.monsterType(MonsterType.WATER).health(50).attack(10).defense(10).speed(10).evolution(firstEvolutionByItem)
		.gameItem(neededItem).allMoves(allMoves).build();

	monsterByItem = new MonsterBuilderImpl().health(50).attack(50).defense(50).speed(50).exp(0).level(1)
		.isWild(false).species(speciesByItem).movesList(listOfMoves).build();

	// FIRST EVOLUTION WITH LEVEL, SECOND WITH ITEM INITIALIZATION

	MonsterSpecies secondEvolutionByLevelAndItem = new MonsterSpeciesBuilderImpl().name("Topolino3").info("Info3")
		.monsterType(MonsterType.GRASS).health(50).attack(10).defense(10).speed(10).allMoves(allMoves).build();

	MonsterSpecies firstEvolutionByLevelAndItem = new MonsterSpeciesBuilderImpl().name("Topolino2").info("Info2")
		.monsterType(MonsterType.GRASS).health(50).attack(10).defense(10).speed(10)
		.evolution(secondEvolutionByLevelAndItem).gameItem(neededItem).allMoves(allMoves).build();

	MonsterSpecies speciesByLevelAndItem = new MonsterSpeciesBuilderImpl().name("Topolino").info("Info")
		.monsterType(MonsterType.GRASS).health(50).attack(10).defense(10).speed(10)
		.evolution(firstEvolutionByLevelAndItem).evolutionLevel(FIRST_EVOLUTION_LEVEL).allMoves(allMoves)
		.build();

	this.monsterByLevelAndItem = new MonsterBuilderImpl().health(50).attack(50).defense(50).speed(50).exp(0)
		.level(1).isWild(false).species(speciesByLevelAndItem).movesList(listOfMoves).build();
    }

    @org.junit.Test
    public void firstStats() {
	assertEquals(1, monster.getLevel());
	assertEquals(0, monster.getExp());
	assertEquals(50, monster.getStats().getHealth());
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
	monster.incExp(((SECOND_EVOLUTION_LEVEL - monster.getLevel() - 1) * MonsterImpl.EXP_CAP));
	monster.incExp(MonsterImpl.EXP_CAP);
	if (monster.canEvolveByLevel()) {
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
	GameItem holdedItemRight = new EvolutionItem("PietraPaperino", 1, "desc");
	assertEquals("Paperino", monsterByItem.getName());
	assertTrue(this.monsterByItem.canEvolveByItem(holdedItemRight));
	if (monsterByItem.canEvolveByItem(holdedItemRight)) {
	    monsterByItem.evolve();
	}
	assertEquals("Paperino2", monsterByItem.getName());
    }

    @org.junit.Test
    public void evolutionByWrongItem() {
	GameItem holdedItemWrong = new EvolutionItem("PietraPippo", 1, "desc");
	assertEquals("Paperino", monsterByItem.getName());
	assertFalse(this.monsterByItem.canEvolveByItem(holdedItemWrong));
	if (monsterByItem.canEvolveByItem(holdedItemWrong)) {
	    monsterByItem.evolve();
	}
	assertNotEquals("Paperino2", monsterByItem.getName());
	assertEquals("Paperino", monsterByItem.getName());
    }

    @org.junit.Test
    public void evolveByLevelAndItem() {
	GameItem holdedItem = new EvolutionItem("PietraPaperino", 1, "desc");
	monsterByLevelAndItem.incExp((FIRST_EVOLUTION_LEVEL - 2) * MonsterImpl.EXP_CAP);
	assertEquals("Topolino", monsterByLevelAndItem.getName());
	monsterByLevelAndItem.incExp(MonsterImpl.EXP_CAP);
	if (monsterByLevelAndItem.canEvolveByLevel()) {
	    monsterByLevelAndItem.evolve();
	}
	assertEquals("Topolino2", monsterByLevelAndItem.getName());
	monsterByLevelAndItem
		.incExp(((SECOND_EVOLUTION_LEVEL - monsterByLevelAndItem.getLevel() - 1) * MonsterImpl.EXP_CAP));
	monsterByLevelAndItem.incExp(MonsterImpl.EXP_CAP);
	assertEquals("Topolino2", monsterByLevelAndItem.getName());
	assertEquals(30, monsterByLevelAndItem.getLevel());
	assertEquals(0, monsterByLevelAndItem.getExp());
	monsterByLevelAndItem.incExp(5863655);
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
