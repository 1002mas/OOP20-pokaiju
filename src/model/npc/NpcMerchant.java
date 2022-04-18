package model.npc;

import java.util.List;
import java.util.Map;

import model.Pair;
import model.gameitem.GameItem;
import model.player.Player;

public interface NpcMerchant extends NpcSimple {
	/**
	 * this function returns a map of items and relatives prices
	 * 
	 * @return map of game items and price
	 */
	public Map<GameItem, Integer> getInventory();

	/**
	 * this function returns the price of an item
	 *
	 * @return the amount of same item price
	 * @param item
	 */
	public int getPrice(GameItem item);

	/**
	 * this function returns the total price amount of items to buy
	 * 
	 * @param list of items and quantity to buy
	 * @return sum of items price
	 */
	public int getTotalPrice(List<Pair<GameItem, Integer>> itemList);

	/**
	 * this function tries to make player buy a list of items
	 * 
	 * @param player
	 * @param itemList to buy
	 * @return true if item is bought, false otherwise
	 */
	public boolean buyItem(List<Pair<GameItem, Integer>> itemList, Player player);
}
