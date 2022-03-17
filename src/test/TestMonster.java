package test;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.item.Item;
import model.item.ItemTypes;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterSpeciesImpl;
import model.monster.MonsterType;

public class TestMonster {

	private static final int FIRST_EVOLUTION_LEVEL = 14;
	private static final int SECOND_EVOLUTION_LEVEL = 30;
	private static final int EXP_CAP = 1000;
	private static final int MAX_LVL = 100;
	private static final int HEALTH = 50;
	
	private Monster monster;
	private MonsterSpeciesImpl species;
	private MonsterSpeciesImpl firstEvolution;
	private MonsterSpeciesImpl secondEvolution;
	private List<Moves> listOfMoves;
	
	private Monster monsterByItem;
	private MonsterSpeciesImpl speciesByItem;
	private MonsterSpeciesImpl firstEvolutionByItem;
	private Item holdedItemWrong;
	private Item holdedItemRight;
	private Item neededItem;

	@org.junit.Before
	public void initFactory() {	
		this.listOfMoves = List.of(new MovesImpl("Braciere", 50, MonsterType.FIRE, 10), new MovesImpl("Attacco", 10, MonsterType.FIRE, 10),
				new MovesImpl("Volo", 50, MonsterType.FIRE, 10), new MovesImpl("Fossa", 50, MonsterType.FIRE, 10));
		//Level test initialization
		this.secondEvolution = new MonsterSpeciesImpl("Pippo3", "Info3", MonsterType.FIRE);
		this.firstEvolution = new MonsterSpeciesImpl("Pippo2", "Info2", MonsterType.FIRE, secondEvolution, SECOND_EVOLUTION_LEVEL);
		this.species = new MonsterSpeciesImpl("Pippo", "Info", MonsterType.FIRE, firstEvolution, FIRST_EVOLUTION_LEVEL);
		this.monster = new MonsterBuilderImpl().health(50).exp(0).level(1).isWild(false).species(species).movesList(listOfMoves)/*.attack(5).defense(5).speed(5)*/.build();
		//Item test initialization
		this.holdedItemWrong = new Item("PietraPippo", 1, 5, "desc", ItemTypes.EVOLUTIONTOOL);
		this.holdedItemRight = new Item("PietraPaperino", 1, 5, "desc", ItemTypes.EVOLUTIONTOOL);
		this.neededItem = new Item("PietraPaperino", 1, 5, "desc", ItemTypes.EVOLUTIONTOOL);
		this.firstEvolutionByItem = new MonsterSpeciesImpl("Paperino2", "Info2", MonsterType.WATER);
		this.speciesByItem = new MonsterSpeciesImpl("Paperino", "Info", MonsterType.WATER, firstEvolutionByItem, neededItem);
		this.monsterByItem = new MonsterBuilderImpl().health(50).exp(0).level(1).isWild(false).species(speciesByItem).movesList(listOfMoves)/*.attack(5).defense(5).speed(5)*/.build();
	}

	@org.junit.Test
	public void firstStats() {
		assertEquals(1, monster.getLevel());
		assertEquals(0, monster.getExp());
		assertEquals(HEALTH, monster.getHealth());
	}

	@org.junit.Test
	public void levelingByLevel() {
		monster.setLevel(FIRST_EVOLUTION_LEVEL-1);
		assertEquals("Pippo", monster.getName());
		monster.incExp(EXP_CAP);
		assertEquals("Pippo2", monster.getName());
		monster.setLevel(SECOND_EVOLUTION_LEVEL-1); 
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
