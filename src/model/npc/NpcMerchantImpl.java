package model.npc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class NpcMerchantImpl extends NpcSimpleImpl implements NpcMerchant{

	HashMap<Object,Integer> inventary;
	
	
	public NpcMerchantImpl(String name, TypeOfNpc typeOfNpc, ArrayList<String> sentences, HashMap<Object,Integer> inventary) {
		super(name, typeOfNpc, sentences);
		this.inventary = inventary;
	}

	public Optional<String> interactWith() {
		Optional<String> result = Optional.of(this.sentences.get(0));   //-----FIX SONO COLGIONE IO 
		return result;
	}
	
	@Override
	public HashMap<Object, Integer> getInventory() {
		return this.inventary;
	}

	
}