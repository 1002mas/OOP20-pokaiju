package model.npc;

import java.util.List;
import java.util.Map;

import model.Pair;
import model.gameitem.GameItem;
import model.player.Player;

public interface NpcMerchant extends NpcSimple {
	/**
	 * 
	 * @return list of sold game items
	 */
	public Map<GameItem, Integer> getInventory();

	/**
	 *
	 * @return the amount of same item price 
	 * @param item
	 */
	public int getPrice(Pair<GameItem,Integer>  item);

	/**
	 * 
	 * @param list of items
	 * @return sum of items price
	 */
	public int getTotalPrice(List<Pair<GameItem,Integer>>  list);

	/**
	 * 
	 * @param player
	 * @param itemList to buy
	 * @return true if item is bought, false otherwise
	 */
	public boolean buyItem(List<Pair<GameItem,Integer>>  itemList, Player player);
}
