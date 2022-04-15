package model.player;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.Pair;
import model.battle.MonsterBattle;
import model.gameitem.*;
import model.map.GameMap;
import model.monster.Monster;
import model.monster.MonsterSpecies;
import model.npc.NpcSimple;

public interface Player {
    /**
     * This function returns the position of the Player
     * 
     * @return Player's position
     */
    Pair<Integer, Integer> getPosition();

    /**
     * This function returns all player's monsters
     * 
     * @return Player's list of monster
     */
    List<Monster> getAllMonsters();

    /**
     * This function returns all player's GameItems
     * 
     * @return Player's list of GameItem
     */
    Map<GameItem, Integer> getAllItems();

    /**
     * This function adds new GameItem to player's bag
     * 
     * @param new GameItem
     */
    void addItem(GameItem i);

    /**
     * This function adds new GameItems to player's bag
     * 
     * @param GameItem, quantity
     */
    void addItem(GameItem i, int quantity);

    /**
     * This function returns GameItem's quantity
     * 
     * @return GameItem
     */
    int getItemQuantity(GameItem i);

    /**
     * This function removes GameItem from player's bag
     * 
     * @param GameItem
     */
    void removeItem(GameItem i);

    /**
     * This function uses a GameItem
     * 
     * @param GameItem
     * 
     */
    void useItem(GameItem i);

    /**
     * This function uses a GameItems on Monster
     * 
     * @param GameItem, Monster
     * 
     */
    void useItemOnMonster(GameItem i, Monster m);

    /**
     * This function returns if buy GameItems is successfully or not
     * 
     * @param Gameitem, price
     * @return true if player has enough money
     */
    boolean buyItem(GameItem i, int price);

    /**
     * This function returns if add Monster is successful or not
     * 
     * @param Monster
     * @return true if add is successfully
     */
    boolean addMonster(Monster m);

    /**
     * This function returns if remove Monster is successful or not
     * 
     * @param Monster
     * @return true if remove is successfully
     */
    boolean removeMonster(Monster m);

    /**
     * This function returns Player's name
     * 
     * @return Player's name
     */
    String getName();

    /**
     * This function returns Player's TrainerNumber
     * 
     * @return Player's TrainerNumber
     */
    int getTrainerNumber();

    /**
     * This function returns Player's gender
     * 
     * @return Player's gender
     */
    Gender getGender();

    /**
     * This function returns Player's Money
     * 
     * @return Player's Money
     */
    int getMoney();

    /**
     * This function returns if team is full or not
     * 
     * @return team's state
     */
    boolean isTeamFull();

    /**
     * This function sets Player's Money
     * 
     * @param money to be set
     */
    void setMoney(int money);

    /**
     * This function sets Player's position
     * 
     * @param position to be set
     */
    void setPosition(Pair<Integer, Integer> position);

    /**
     * This function evolves Monsters after a battle
     * 
     * @param position to be set
     */
    void evolveMonsters();

    /**
     * This function evolves Monster using giving GameItem
     * 
     * @param Monster, GameItem to be used
     */
    void evolveMonster(Monster monster, GameItem i);

    /**
     * This function gets the EvolutionList
     * 
     * @return List of evolvedMonsters and base
     */
    List<Pair<MonsterSpecies, MonsterSpecies>> getEvolutionList();

    /**
     * Moves Player up
     * 
     */
    boolean moveUp();

    /**
     * Moves Player down
     * 
     */
    boolean moveDown();

    /**
     * Moves Player left
     * 
     */
    boolean moveLeft();

    /**
     * Moves Player right
     * 
     */
    boolean moveRight();

    /**
     * This function gets the GameMap
     * 
     * @return map
     */
    GameMap getMap();

    /**
     * This function returns if Player has changed Map after movement
     * 
     * @return true if changed map
     */
    boolean hasPlayerChangedMap();

    /**
     * This function returns if Player has interaction with Npc
     * 
     * @return true if there is a Npc
     */
    boolean interactAt(Pair<Integer, Integer> pos);

    /**
     * This function returns if there was a interaction with Npc
     * 
     * @return last interacted Npc
     */
    Optional<NpcSimple> getLastInteractionWithNpc();

    /**
     * This function returns a battle if a wild monster attacked while player was
     * moving or the Player talked with a Npc
     * 
     * @return a battle if any is present
     */
    Optional<MonsterBattle> getPlayerBattle();

    /**
     * This function updates storage
     * 
     * @param storage
     */
    void setStorage(MonsterStorage storage);

    /**
     * This function gets storage
     * 
     * @return storage
     */
    MonsterStorage getStorage();

    boolean isTriggeredEvent();

}
