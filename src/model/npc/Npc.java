package model.npc;
import java.util.ArrayList;
import java.util.Optional;
import model.monster.Monster;

public interface Npc {
	Optional<String> interactWith();
	//int battle();
	boolean  isTrainer();
	String  getName(); 
	ArrayList<Monster> getMonstersOwned();
	//ArrayList<String> setPhrases(String speechFileName);
	public boolean isDefeated();
	public void setDefeated();
	
}
