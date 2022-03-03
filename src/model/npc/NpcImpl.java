package model.npc;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import model.monster.Monster;

public class NpcImpl implements Npc {

	private final String name;
	private boolean trainer;
	private boolean defeated;
	private final ArrayList<Monster> monstersOwned;
	private final ArrayList<String> phrases;
	
	
	public NpcImpl(String name, ArrayList<Monster> monsterOwned, ArrayList<String> phrases, boolean trainer) {
	
		this.name =  name;
		this.trainer = trainer;
		this.monstersOwned = monsterOwned;
		this.phrases = phrases;
	}
	
	public NpcImpl(String name, ArrayList<String> phrases,boolean trainer) {
		
		this(name, null, phrases, trainer);
	}
	
	private int battle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Optional<String> interactWith() {
		if(!isTrainer() || isDefeated()) {
			
			return  getPhrase();
		}
		else {
				int isTheWinner = battle();
				if(isTheWinner == 0) {
					setDefeated();
			}
		}
		return Optional.empty();  
	}

	@Override
	public boolean isTrainer() {
		
		return this.trainer;
	}
	
	public boolean isDefeated() {
		return this.defeated;
	}
	
	public void setDefeated() {
		this.defeated = true;
	}
	
	private Optional<String> getPhrase() {
		//sceglie la frase dall'array
		Optional<String> result = Optional.of("---");
		return result;
	}

	@Override
	public ArrayList<Monster> getMonstersOwned() {
		return this.monstersOwned;
	}
}