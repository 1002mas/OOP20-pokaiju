package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.monster.Monster;
import model.npc.Npc;
import model.npc.NpcImpl;

import java.util.ArrayList;

import org.junit.Test;

public class NpcTest {
	
	
	@Test
	public void test1() {
		ArrayList<Monster> k = new ArrayList<Monster>();
		ArrayList<String> s = new ArrayList<String>();
		//NpcSimpleImpl n0 = new NpcSimpleImpl("",null,null);
		NpcSimple n1 = new NpcSimpleImpl("mario",TypeOfNpc.SIMPLE, s);
		System.out.println(n1.getTypeOfNpc());
		assertTrue("Should be 'mario' ",n1.getName().equals("mario"));
/*		assertTrue("Shoul be 'false'",!n1.isDefeated());
		
		
		System.out.println("--------------------------");
		
		NpcSimple n2 = new NpcImpl("saro", s, true);
		assertTrue("Should be 'true' ",n2.isTrainer());
		assertTrue("Should be 'saro' ",n2.getName().equals("saro"));
		assertTrue("Shoul be 'false'",!n1.isDefeated());
		n1.setDefeated();
		assertTrue("Should be 'true' ",n1.isDefeated());
*/
	}
}
