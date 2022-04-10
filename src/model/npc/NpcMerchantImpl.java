package model.npc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.Pair;
import model.gameitem.GameItems;

public class NpcMerchantImpl extends NpcSimpleImpl implements NpcMerchant{

	Map<GameItems, Integer>inventary;
	
	
	public NpcMerchantImpl(String name, TypeOfNpc typeOfNpc, List<String> sentences, Pair<Integer,Integer> position, Map<GameItems,Integer> inventary) {
		super(name, typeOfNpc, sentences, position);
		this.inventary = inventary;
	}

	public Optional<String> interactWith() {
		Optional<String> result = Optional.of(this.sentences.get(0));   
		return result;
	}
	
	@Override
	public Map<GameItems, Integer> getInventory() {
		return this.inventary;
	}

	
}
