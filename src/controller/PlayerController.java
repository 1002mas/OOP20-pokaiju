package controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.Pair;

public interface PlayerController {
	/**
	 * ask the model to move the player to coord
	 * 
	 * @return true if the player is moved, false otherwise.
	 */
	boolean movePlayer(Direction direction);

	/**
	 * Interacts with the object at coord position. If there is a trainer, it starts
	 * the battle. If there is a npc, some text appears.
	 */
	Optional<String> interact();

	/**
	 * If any event has been triggered it may be necessary reloading npc positions.
	 * 
	 * @return true if any event has been triggered
	 */
	boolean hasPlayerTriggeredEvent();

	/**
	 * While this is true the player won't be able to interact with other npcs or
	 * move around.
	 * 
	 * @return true if in the last interact() call the player interacted with a
	 *         merchant
	 */
	boolean hasMerchantInteractionOccurred();

	/**
	 * Get the sold items from the merchant if the player has interacted with the
	 * merchant.
	 * 
	 */
	List<String> getMerchantItems();

	/**
	 * 
	 * The merchant item price if the player has interacted with the merchant.
	 * 
	 * @param itemName the name of the item sold by the merchant
	 * 
	 * @return the item price.
	 */
	int getMerchantItemPrice(String itemName);

	/**
	 * 
	 * The item total price for the goods if the player has interacted with the
	 * merchant.
	 * 
	 * @param buyItem a map of item name and item quantity
	 * 
	 * @return the goods price.
	 */
	int getMerchantTotalPrice(Map<String, Integer> buyItem);

	/**
	 * the price are of the merchant that player has interacted with.
	 * 
	 * @param merchant merchant's name.
	 * @param buyItem  a map of item name and item quantity
	 * @return true if the total price is lower than player's balance. false if the
	 *         player cannot buy the goods or no merchant interaction is on going
	 */

	boolean canPlayerBuyFromMerchant(Map<String, Integer> buyItem);

	/**
	 * 
	 * The items are added to the player and the player balance is reduced.
	 * 
	 * @param merchant merchant's name.
	 * @param buyItem  a map of item name and item quantity
	 * 
	 * @return true if the player has enough money to buy it.
	 */

	boolean buyMerchantItems(Map<String, Integer> buyItem);

	/**
	 * 
	 * @return a list containing all visible npcs and their location
	 */
	Map<String, Pair<Integer, Integer>> getAllNpcs();

	/**
	 * @return player position
	 */
	Pair<Integer, Integer> getPlayerPosition();

	/**
	 * set new Player
	 * 
	 * @param new name, gender, trainerNumber
	 */

	boolean hasPlayerChangedMap();

	/**
	 * It may be turn true after calling interact() or walking in wild monsters area
	 * 
	 * @return true if a battle began
	 */
	boolean hasBattleStarted();

	/**
	 * It returns a BattleController only if hasBattleStarted returns true
	 * 
	 * @return a BattleController for the battle moment
	 */
	Optional<BattleController> getBattleController();

	/**
	 * 
	 * @return current map id
	 */
	int getCurrentMapID();

	/**
	 * This function asks model to use an item
	 * 
	 * @param item
	 */

	void useItem(String item);

	/**
	 * This function asks model to use anitem on a monster
	 * 
	 * @param i
	 * @param monsterId
	 */
	void useItemOnMonster(String i, int monsterId);

	/**
	 * This function asks model to remove an item from player's bag
	 * 
	 * @param i
	 */
	void removeItem(String i);

	/**
	 * This function adds a monster
	 * 
	 * @param idMonster
	 * @return true iff monster is added, false otherwise
	 */
	boolean addMonster(int idMonster);

	/**
	 * This function create a new player
	 * 
	 * @param name
	 * @param gender
	 * @param trainerNumber
	 */
	void createNewPlayer(String name, String gender, int trainerNumber);

	/**
	 * This function get player name
	 * 
	 * @return player name
	 */
	String getPlayerName();

	/**
	 * This function get player trainer number
	 * 
	 * @return trainer number
	 */
	int getTrainerNumber();

	/**
	 * This function get player gender
	 * 
	 * @return player gender
	 */
	String getPlayerGender();

	/**
	 * This function get player money
	 * 
	 * @return player money
	 */
	int getPlayerMoney();

	/**
	 * This function returns a List of player items name
	 * 
	 * @return list of player items name
	 */
	List<String> getPlayerItemsName();

	/**
	 * This function returns if player team is full
	 * 
	 * @return true if player team is full, false otherwise
	 */
	boolean isTeamFull();

	/**
	 * This function returns monster name
	 * 
	 * @param monsterId
	 * @return monster name
	 */
	String getMonsterNameById(int monsterId);

	/**
	 * This function get a list of player monsters id
	 * 
	 * @return player monster id list
	 */
	List<Integer> getMonstersId();

	/**
	 * This function returns monster exp
	 * 
	 * @param monsterId
	 * @return monster exp
	 */
	int getMonsterExp(int monsterId);

	/**
	 * This function returns monster level
	 * 
	 * @param monsterId
	 * @return monster level
	 */
	int getMonsterLevel(int monsterId);

	/**
	 * This function returns monster max health
	 * 
	 * @param monsterId
	 * @return monster max health
	 */
	int getMonsterMaxHealth(int monsterId);

	/**
	 * This function returns monster type
	 * 
	 * @param monsterId
	 * @return monster type
	 */
	String getMonsterType(int monsterId);

	/**
	 * This function returns a list of moves name of a monster
	 * 
	 * @param monsterId
	 * @return moves names list
	 */
	List<String> getMovesNames(int monsterId);

	/**
	 * This function returns monster health value
	 * 
	 * @param monsterId
	 * @return monster health value
	 */
	int getMonsterHealth(int monsterId);

	/**
	 * This function returns monster attack value
	 * 
	 * @param monsterId
	 * @return monster attack value
	 */
	int getMonsterAttack(int monsterId);

	/**
	 * This function returns monster defense value
	 * 
	 * @param monsterId
	 * @return monster defense value
	 */
	int getMonsterDefense(int monsterId);

	/**
	 * This function returns monster speed value
	 * 
	 * @param monsterId monster id
	 * @return monster speed value
	 */
	int getMonsterSpeed(int monsterId);

	/**
	 * 
	 * @return current box name
	 */
	String getCurrentBoxName();

	/**
	 * It puts the team monster in the box and it retrieves the given box monster.
	 * 
	 * @param teamMonsterId team monster id
	 * @param boxMonsterId  box monster id
	 */
	void exchangeMonster(int teamMonsterId, int boxMonsterId);

	/**
	 * It puts the team monster in the box.
	 * 
	 * @param teamMonsterId team monster id
	 */
	void depositMonster(int teamMonsterId);

	/**
	 * It retrieves the given box monster.
	 * 
	 * @param boxMonsterId box monster id
	 */
	void withdrawMonster(int boxMonsterId);

	/**
	 * Moves to the next box
	 */
	void nextBox();

	/**
	 * Moves to the previous box
	 */
	void previousBox();

	/**
	 * 
	 * @return box monster ID
	 */
	List<Integer> getBoxMonsters();

	/**
	 * 
	 * @return maximum boxes
	 */
	int getBoxNumbers();

	/**
	 * 
	 * @return maximum monster in a box
	 */
	int getMonstersForEachBox();

	/**
	 * 
	 * @return true if player has one monster left
	 */
	boolean isPlayerLastMonsterLeft();

	/**
	 * This function returns quantity of a player item
	 * 
	 * @param item
	 * @return item quantity
	 */
	int getItemQuantity(String item);

	/**
	 * This function returns an item description
	 * 
	 * @param item
	 * @return item quantity
	 */
	String getItemDescription(String item);

	/**
	 * This function returns a type of an item
	 * 
	 * @param item
	 * @return type of item
	 */
	String getItemtype(String item);

	/**
	 * This function adds an item to player's bag
	 * 
	 * @param item
	 */
	void addItem(String item);

	/**
	 * This function asks model to use an item
	 * 
	 * @param item
	 * @return true if item is used false otherwise
	 */
	boolean canUseItem(String item);

	/**
	 * This function returns max number of rows in a block
	 * 
	 * @return max number of rows
	 */
	int getMaximumBlocksInRow();

	/**
	 * This function returns max number of columns in a row
	 * 
	 * @return max number of columns
	 */
	int getMaximumBlocksInColumn();

	boolean canEvolveByItem(String nameItem, int monsterId);

	/**
	 * Evolve a monster by item
	 * 
	 * @param nameItem
	 * @param monsterId
	 * @return current name and evolution name
	 * @author Luca Barattini
	 */
	Optional<Pair<String, String>> evolveByItem(String nameItem, int monsterId);

	/**
	 * This function returns a move PPs
	 * 
	 * @param moveName
	 * @param monterID
	 * @return move PPs
	 * @author Andrea Castorina
	 */
	int getMovePP(String moveName, int monsterID);

	/**
	 * This function returns if an item is present
	 * 
	 * @param name
	 * @return true if item id present, false otherwise
	 */
	boolean isItemPresent(String name);
}
