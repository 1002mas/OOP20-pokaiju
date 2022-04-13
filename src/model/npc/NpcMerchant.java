package model.npc;

import java.util.List;
import java.util.Map;

import model.gameitem.GameItem;
import model.player.Player;

public interface NpcMerchant extends NpcSimple {
	/**
	 * 
	 * @return list of sold game items
	 */
	Map<GameItem, Integer> getInventory();

	/**
	 * 
	 * @return price of an item
	 * @param item
	 */
	int getPrice(GameItem item);

	/**
	 * 
	 * @param list of items
	 * @return sum of items price
	 */
	public int getTotalPrice(List<GameItem> list);

	/**
	 * 
	 * @param player
	 * @param item   to buy
	 * @return true if item is bought
	 */
	public boolean buyItem(Player player, List<GameItem> itemList);
}
