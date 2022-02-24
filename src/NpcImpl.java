import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class NpcImpl implements Npc {

	private final String name;
	private boolean trainer;
	private boolean defeated;
	private final ArrayList<Monster> monstersOwned;
	private final ArrayList<String> speechPhrases;
	
	
	public NpcImpl(String name, ArrayList<Monster> monsterOwned, String speechFileName, boolean trainer) {
	
		this.name =  name;
		this.trainer = trainer;
		this.monstersOwned = monsterOwned;
		this.speechPhrases = setPhrases(speechFileName);
	}
	
	public NpcImpl(String name, String speechFileName, boolean trainer) {
		
		this(name, null, speechFileName, trainer);
	}
	
	private int battle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public ArrayList<String> setPhrases(String speechFileName){		
		ArrayList<String> fileLines = new ArrayList<String>();
		return fileLines;		
	}

	@Override
	public void interactWith() {
		
		if(!isTrainer() || isDefeated()) {
			talk();
		}
		else {
				int isTheWinner = battle();
				if(isTheWinner == 0) {
					setDefeated();
			}
		}
	}

	@Override
	public boolean isTrainer() {
		
		return this.trainer;
	}
	
	private boolean isDefeated() {
		return this.defeated;
	}
	
	private void setDefeated() {
		this.defeated = true;
	}
	
	private String talk() {
		String result = "";
		return result;
	}
}
