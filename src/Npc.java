import java.util.ArrayList;

public interface Npc {
	String interaction(String playerAnswer);
	int battle();
	String  getTypeOfNpc();
	String  getName(); 
	ArrayList<Monster> getMonstersOwned();
	ArrayList<String> setPhrases(String speechFileName);
	
}
