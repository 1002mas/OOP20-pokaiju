import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class NpcImpl implements Npc {

	private String name;
	private boolean trainer;
	private boolean defeated;
	private ArrayList<Monster> monstersOwned;
	private ArrayList<String> speechPhrases;
	
	
	public NpcImpl(String name, ArrayList<Monster> monsterOwned, String speechFileName, boolean trainer) {
	
		this.name =  name;
		this.trainer = trainer;
		this.monstersOwned = monsterOwned;
		this.speechPhrases = setPhrases(speechFileName);
	}
	
	private int battle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		return this.name;
	}

	private ArrayList<Monster> getMonstersOwned() {
	
		return monstersOwned;
	}

	public ArrayList<String> setPhrases(String speechFileName){		
		ArrayList<String> fileLines = new ArrayList<String>();
		return fileLines;		
	}

	@Override
	public void interactWith() {
		
		if(!isTrainer()) {
			makeSpeech();
		}
		else {
			if(!isDefeated()) {
				int isTheWinner = battle();
				if(isTheWinner == 0) {
					setDefeated();
				}
			}
		}
	}

	@Override
	public boolean isTrainer() {
		
		return this.isTrainer();
	}
	
	private boolean isDefeated() {
		return this.defeated;
	}
	
	private void setDefeated() {
		this.defeated = true;
	}
	
	private String makeSpeech() {
		String result = "";
		return result;
	}
}
