package test;
import static org.junit.Assert.assertEquals;
import java.util.List;

import model.battle.Attack;
import model.battle.AttackImpl;
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
	private List<Attack> attackList;

	@org.junit.Before
	public void initFactory() {
		Attack a1 = new AttackImpl("Braciere", 50, "fuoco", 10);
		Attack a2 = new AttackImpl("Attacco", 10, "normale", 10);
		Attack a3 = new AttackImpl("Volo", 50, "volante", 10);
		Attack a4 = new AttackImpl("Fossa", 50, "roccia", 10);
		this.attackList = List.of(a1, a2, a3, a4);
		this.secondEvolution = new MonsterSpeciesImpl("Pippo3", "Info3", MonsterType.FIRE);
		this.firstEvolution = new MonsterSpeciesImpl("Pippo2", "Info2", MonsterType.FIRE, secondEvolution, SECOND_EVOLUTION_LEVEL);
		this.species = new MonsterSpeciesImpl("Pippo", "Info", MonsterType.FIRE, firstEvolution, FIRST_EVOLUTION_LEVEL);
		this.monster = new MonsterBuilderImpl().health(50).exp(0).level(1).isWild(false).species(species).attackList(attackList).build();
	}

	@org.junit.Test
	public void firstStats() {
		assertEquals(1, monster.getLevel());
		assertEquals(0, monster.getExp());
		assertEquals(HEALTH, monster.getHealth());
	}

	@org.junit.Test
	public void leveling() {
		
		monster.setLevel(FIRST_EVOLUTION_LEVEL-1);
		System.out.println("BEFORE FIRST EVOLUTION:\n" + monster.toString());
		monster.incExp(EXP_CAP);
		System.out.println("AFTER FIRST EVOLUTION:\n" + monster.toString());
		
		monster.setLevel(SECOND_EVOLUTION_LEVEL-1); 
		System.out.println("BEFORE SECOND EVOLUTION:\n" + monster.toString());
		monster.incExp(EXP_CAP);
		assertEquals(30, monster.getLevel());
		assertEquals(0, monster.getExp());
		System.out.println("AFTER SECOND EVOLUTION:\n" + monster.toString());
		
		monster.incExp(5863655);
		assertEquals(MAX_LVL, monster.getLevel());
		System.out.println("MONSTER AT MAX LEVEL:\n" + monster.toString());
	}

}
