package model.player;

import java.util.List;
import java.util.Map;

import model.Pair;
import model.gameitem.*;
import model.monster.Monster;

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

}
