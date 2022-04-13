package model.npc;

import java.util.List;
import java.util.Map;

import model.Pair;
import model.gameitem.GameItem;
import model.player.Player;

public class NpcMerchantImpl extends NpcSimpleImpl implements NpcMerchant {

	private Map<GameItem, Integer> inventory;

	public NpcMerchantImpl(String name, List<String> sentences, Pair<Integer, Integer> position, boolean isVisible,
			boolean isEnabled, Map<GameItem, Integer> inventory) {
		super(name, TypeOfNpc.MERCHANT, sentences, position, isVisible, isEnabled);
		this.inventory = inventory;
	}

	@Override
	public Map<GameItem, Integer> getInventory() {
		return this.inventory;
	}

	@Override
	public int getPrice(Pair<GameItem,Integer> itemList) {
		if (this.inventory.containsKey(itemList.getFirst())) {
			return (inventory.get(itemList.getFirst()) * itemList.getSecond());
		}
		return 0;
	}

	@Override
	public int getTotalPrice(List<Pair<GameItem,Integer>> list) {
		int sum = 0;
		for (Pair<GameItem,Integer> item : list) {
			sum = sum + getPrice(item);
		}
		return sum;
	}

	private void addItems(List<Pair<GameItem, Integer>> list, Player player) {
		for (Pair<GameItem, Integer> item : list) {
			for(int i=0; i< item.getSecond(); i++) {
				player.addItem(item.getFirst());
			}
			
		}
	}

	@Override
	public boolean buyItem(List<Pair<GameItem,Integer>> itemList, Player player) {
		if ((player.getMoney() - getTotalPrice(itemList)) >= 0) {
			player.setMoney(player.getMoney() - getTotalPrice(itemList));
			addItems(itemList, player);
			return true;
		}
		return false;
	}
}
