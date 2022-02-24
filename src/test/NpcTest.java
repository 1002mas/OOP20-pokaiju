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
		Npc n1 = new NpcImpl("mario",k ,false);
		assertTrue("Should be 'false' ",!n1.isTrainer());
		System.out.println(n1.getName());
		assertTrue("Should be 'mario' ",n1.getName().equals("mario"));
		assertTrue("Shoul be 'false'",!n1.isDefeated());
		
		
		System.out.println("--------------------------");
		
		Npc n2 = new NpcImpl("saro", null, true);
		assertTrue("Should be 'false' ",n2.isTrainer());
		assertTrue("Should be 'mario' ",n1.getName()!="saro");
		assertTrue("Shoul be 'false'",!n1.isDefeated());
		n1.setDefeated();
		assertTrue("Should be 'true' ",n1.isDefeated());
	}
}
