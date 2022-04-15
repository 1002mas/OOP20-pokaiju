package controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.Pair;
import model.player.Gender;

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

    void useItem(String item);

    void useItemOnMonster(String i, int monsterId);

    void removeItem(String i);

    boolean addMonster(int idMonster);

    void createNewPlayer(String name, Gender gender, int trainerNumber);

    String getPlayerName();

    int getTrainerNumber();

    String getPlayerGender();

    int getPlayerMoney();

    List<String> getPlayerItemsName();

    boolean isTeamFull();

    String getMonsterNameById(int monsterId);

    List<Integer> getMonstersId();

    int getMonsterExp(int monsterId);

    int getMonsterLevel(int monsterId);

    int getMonsterMaxHealth(int monsterId);

    String getMonsterType(int monsterId);

    List<String> getMovesNames(int monsterId);

    int getMonsterHealth(int monsterId);

    int getMonsterAttack(int monsterId);

    int getMonsterDefense(int monsterId);

    /**
     * 
     * @param monsterId monster id
     * @return monster speed
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

    int getItemQuantity(String item);

    String getItemDescription(String item);

    String getItemtype(String item);

    void addItem(String item);

    boolean canUseItem(String item);

    int getMaximumBlocksInRow();

    int getMaximumBlocksInColumn();

    boolean canEvolveByItem(String nameItem, int monsterId);

    /**
     * 
     * @param nameItem
     * @param monsterId
     * @return
     * @author Luca Barattini
     */
    Optional<Pair<String, String>> evolveByItem(String nameItem, int monsterId);

    /**
     * 
     * @param monsterId
     * @return
     * @author Luca Barattini
     */
    Optional<Pair<String, String>> evolveByLevel(int monsterId);

    /**
     * 
     * @return
     * @author Luca Barattini
     */
    boolean hasAnyMonsterEvolved();

    /**
     * 
     * @return
     * @author Luca Barattini
     */
    Pair<String, String> getEvolvedMonster();

}
