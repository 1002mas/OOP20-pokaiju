package model.npc;

import java.util.ArrayList;
import java.util.Optional;

import model.monster.Monster;

public class NpcTrainerImpl extends NpcSimpleImpl implements NpcTrainer{

	ArrayList<Monster> monstersOwned;
	boolean isDefated = false;
	
	public NpcTrainerImpl(String name, String typeOfNpc, ArrayList<String> prhases, ArrayList<Monster> monstersOwned) {
		super(name, typeOfNpc, prhases);
		this.monstersOwned = monstersOwned;
	}

	@Override
	public ArrayList<Monster> getMonstersOwned() {
		return this.monstersOwned;
	}

	@Override
	public boolean isDefeated() {
		return this.isDefated;
	}

	@Override
	public void setDefeated() {
		this.isDefated = true;
	}

	@Override
	public void devolveToSimple() {
		this.typeOfNpc = "SimpleNpc";
	}
	
	int battle() {	//----- DA IMPLEMENTARE -----
		return 0;
	}
	
	public Optional<String> interactWith() {
		if(!this.isDefated) {
			int battleResult = battle();
			if(battleResult == 1) {			//----- FIXARE battleResult -----
				setDefeated();
			}
			return Optional.empty();
		}
		else {
			Optional<String> result = Optional.of("---");
			return result;
		}
	}

}
