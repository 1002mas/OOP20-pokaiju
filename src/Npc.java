import java.util.ArrayList;

public interface Npc {
	void interactWith();
	//int battle();
	boolean  isTrainer();
	String  getName(); 
	//ArrayList<Monster> getMonstersOwned();
	ArrayList<String> setPhrases(String speechFileName);
	
}
