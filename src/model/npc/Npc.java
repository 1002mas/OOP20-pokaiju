package model.npc;
import java.util.ArrayList;

import model.monster.Monster;

public interface Npc {
	void interactWith();
	//int battle();
	boolean  isTrainer();
	String  getName(); 
	//ArrayList<Monster> getMonstersOwned();
	ArrayList<String> setPhrases(String speechFileName);
	public boolean isDefeated();
	public void setDefeated();
	
}
