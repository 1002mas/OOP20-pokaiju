package model.npc;

import java.util.Optional;

import model.Pair;

public interface NpcSimple {
	Optional<String> interactWith();
	//int battle();
	TypeOfNpc getTypeOfNpc();	//diventa un enum <---
	String  getName(); 
	Pair<Integer,Integer> getPosition();
	//public enum nome {s, t, m};
	//ArrayList<Monster> getMonstersOwned();
	//ArrayList<String> setPhrases(String speechFileName);
	//public boolean isDefeated();
	//public void setDefeated();
	
}
