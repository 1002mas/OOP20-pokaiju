package model.monster;
import static org.junit.Assert.*;

public class TestMonsterImpl {

	private static final int FIRST_EVOLUTION_LEVEL = 14;
	private static final int SECOND_EVOLUTION_LEVEL = 30;
	private static final int EXP_CAP = 1000;
	private static final int MAX_LVL = 100;
	private static final int HEALTH = 50;
	
	
	private Monster p = null;

	@org.junit.Before
	public void initFactory() {
		this.p = new MonsterBuilderImpl().name("Mario").monsterType(MonsterType.FIRE).level(1).health(50).exp(0)
				.info("supermario").secondName("SuperMario").secondInfo("Info2").thirdName("SuperMegaMario")
				.thirdInfo("Info3").build();
	}

	@org.junit.Test
	public void firstStats() {
		//System.out.println(p.toString());
		assertEquals(1, p.getLevel());
		assertEquals(0, p.getExp());
		assertEquals(HEALTH, p.getHealth());
	}

	@org.junit.Test
	public void leveling() {
		
		p.setLevel(FIRST_EVOLUTION_LEVEL-1);
		System.out.println(p.toString());
		p.incExp(EXP_CAP);
		assertEquals(p.getSecondName(), p.getName());
		System.out.println(p.toString());
		
		p.setLevel(SECOND_EVOLUTION_LEVEL-1); 
		//System.out.println(p.toString());
		p.incExp(EXP_CAP);
		assertEquals(p.getThirdName(), p.getName());
		assertEquals(30, p.getLevel());
		assertEquals(0, p.getExp());
		
		System.out.println(p.toString());
		
		/*assertEquals(p.getExpCap(), p.getExp());
		assertEquals(100, p.getLevel());
		System.out.println(p.toString());*/
	}

}
