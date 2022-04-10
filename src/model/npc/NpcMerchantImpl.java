package model.npc;

import java.util.ArrayList;
import java.util.Map;

import model.Pair;
import model.gameitem.GameItem;

public class NpcMerchantImpl extends NpcSimpleImpl implements NpcMerchant {

    private Map<GameItem, Integer> inventory;

    public NpcMerchantImpl(String name, TypeOfNpc typeOfNpc, ArrayList<String> sentences,
	    Pair<Integer, Integer> position, boolean isVisible, boolean isEnabled, Map<GameItem, Integer> inventory) {
	super(name, typeOfNpc, sentences, position, isVisible, isEnabled);
	this.inventory = inventory;
    }

    @Override
    public Map<GameItem, Integer> getInventory() {
	return this.inventory;
    }
}
