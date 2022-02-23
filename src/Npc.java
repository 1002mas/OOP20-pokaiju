import java.util.ArrayList;

public interface Npc {
	String interaction(String playerAnswer);
	int battle();
	//void setTypeOfNpc();
	String  getTypeOfNpc();
	String  getName(); 
	ArrayList<Monster> getMonstersOwned();
	
}
