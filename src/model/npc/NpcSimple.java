package model.npc;

import java.util.Optional;

public interface NpcSimple {
	Optional<String> interactWith();
	//int battle();
	TypeOfNpc getTypeOfNpc();	//diventa un enum <---
	String  getName(); 
	//public enum nome {s, t, m};
	//ArrayList<Monster> getMonstersOwned();
	//ArrayList<String> setPhrases(String speechFileName);
	//public boolean isDefeated();
	//public void setDefeated();
	
}
