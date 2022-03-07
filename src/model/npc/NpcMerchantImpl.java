package model.npc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class NpcMerchantImpl extends NpcSimpleImpl implements NpcMerchant{

	HashMap<Object,Integer> inventary;
	
	
	public NpcMerchantImpl(String name, String typeOfNpc, ArrayList<String> prhases, HashMap<Object,Integer> inventary) {
		super(name, typeOfNpc, prhases);
		this.inventary = inventary;
	}

	public Optional<String> interactWith() {
		Optional<String> result = Optional.of("---");
		return result;
	}
	
	@Override
	public HashMap<Object, Integer> getInventory() {
		return this.inventary;
	}

	
}
