package model.player;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import model.Pair;
import model.battle.MonsterBattle;
import model.gameitem.GameItem;
import model.map.GameMap;
import model.monster.Monster;
import model.npc.NpcSimple;

public interface Player {
    /**
     * This function returns the position of the Player.
     * 
     * @return Player's position
     */
    Pair<Integer, Integer> getPosition();

    /**
     * This function returns all player's monsters.
     * 
     * @return Player's list of monster
     */
    List<Monster> getAllMonsters();

    /**
     * This function returns all player's GameItems.
     * 
     * @return Player's list of GameItem
     */
    Map<GameItem, Integer> getAllItems();

    /**
     * This function adds new GameItem to player's bag.
     * 
     * @param i
     */
    void addItem(GameItem i);

    /**
     * This function adds new GameItems to player's bag.
     * 
     * @param i
     * @param quantity
     */
    void addItem(GameItem i, int quantity);

    /**
     * This function returns GameItem's quantity.
     * 
     * @param gameItem
     * @return gameItem's quantity
     */
    int getItemQuantity(GameItem gameItem);

    /**
     * This function removes GameItem from player's bag.
     * 
     * @param gameItem
     */
    void removeItem(GameItem gameItem);

    /**
     * This function uses a GameItem.
     * 
     * @param gameItem
     * 
     */
    void useItem(GameItem gameItem);

    /**
     * This function uses a GameItems on Monster.
     * 
     * @param gameItem
     * @param m
     */
    void useItemOnMonster(GameItem gameItem, Monster m);

    /**
     * This function returns if add Monster is successful or not.
     * 
     * @param m
     * @return true if add is successfully
     */
    boolean addMonster(Monster m);

    /**
     * This function returns if remove Monster is successful or not.
     * 
     * @param m
     * @return true if remove is successfully
     */
    boolean removeMonster(Monster m);

    /**
     * This function returns Player's name.
     * 
     * @return Player's name
     */
    String getName();

    /**
     * This function returns Player's TrainerNumber.
     * 
     * @return Player's TrainerNumber
     */
    int getTrainerNumber();

    /**
     * This function returns Player's gender.
     * 
     * @return Player's gender
     */
    Gender getGender();

    /**
     * This function returns Player's Money.
     * 
     * @return Player's Money
     */
    int getMoney();

    /**
     * This function returns if team is full or not.
     * 
     * @return team's state
     */
    boolean isTeamFull();

    /**
     * This function sets Player's Money.
     * 
     * @param money to be set
     */
    void setMoney(int money);

    /**
     * This function sets Player's position.
     * 
     * @param position to be set
     */
    void setPosition(Pair<Integer, Integer> position);

    /**
     * This function evolves Monsters after a battle.
     * 
     */
    void evolveMonsters();

    /**
     * This function evolves Monster using giving GameItem.
     * 
     * @param monster
     * @param gameItem
     */
    void evolveMonster(Monster monster, GameItem gameItem);

    /**
     * Moves Player up.
     * 
     * @return true if moved up
     */
    boolean moveUp();

    /**
     * Moves Player down.
     * 
     * @return true if moved down
     */
    boolean moveDown();

    /**
     * Moves Player left.
     * 
     * @return true if moved left
     */
    boolean moveLeft();

    /**
     * Moves Player right.
     * 
     * @return true if moved right
     */
    boolean moveRight();

    /**
     * This function gets the GameMap.
     * 
     * @return map
     */
    GameMap getMap();

    /**
     * This function returns if Player has changed Map after movement.
     * 
     * @return true if changed map
     */
    boolean hasPlayerChangedMap();

    /**
     * This function returns if Player has interaction with Npc.
     * 
     * @param pos
     * @return true if there is a Npc
     */
    boolean interactAt(Pair<Integer, Integer> pos);

    /**
     * This function returns if there was a interaction with Npc.
     * 
     * @return last interacted Npc
     */
    Optional<NpcSimple> getLastInteractionWithNpc();

    /**
     * This function returns a battle if a wild monster attacked while player was
     * moving or the Player talked with a Npc.
     * 
     * @return a battle if any is present
     */
    Optional<MonsterBattle> getPlayerBattle();

    /**
     * This function updates storage.
     * 
     * @param storage
     */
    void setStorage(MonsterStorage storage);

    /**
     * This function gets storage.
     * 
     * @return storage
     */
    MonsterStorage getStorage();

    /**
     * This function returns if the player has triggered an event.
     * 
     * @return true if the player has triggered an event
     */
    boolean isTriggeredEvent();

    /**
     * This function sets player's gender.
     * 
     * @param gender
     */
    void setGender(Gender gender);

    /**
     * This function sets player's trainerNumber.
     * 
     * @param trainerNumber
     */
    void setTrainerNumber(int trainerNumber);

    /**
     * This function gets a list of Monsters.
     * 
     * @return list of Monster
     */
    List<Monster> getMonster();

    /**
     * This function sets a list of Monsters.
     * 
     * @param monster List of monster to set
     */
    void setMonster(List<Monster> monster);

    /**
     * This function sets a list of GameItems.
     * 
     * @return list of GameItems
     */
    List<GameItem> getItems();

    /**
     * This function sets Player's name.
     * 
     * @param name
     */
    void setName(String name);

}
