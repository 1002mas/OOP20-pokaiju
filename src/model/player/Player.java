package model.player;

import java.util.ArrayList;
import java.util.List;

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
    List<Monster> allMonster();

    /**
     * This function returns all player's GameItems
     * 
     * @return Player's list of GameItem
     */
    List<GameItem> allItems();

    /**
     * This function adds new GameItems to player's bag
     * 
     * @param new GameItems
     */
    void addItem(GameItem i);

    /**
     * This function removes GameItems from player's bag
     * 
     * @param GameItem
     */
    void removeItem(GameItem i);

    /**
     * This function uses a GameItems 
     * 
     * @param GameItem
     * 
     */
    void useItem(GameItem i);

    /**
     * This function applies a GameItems on Monster
     * 
     * @param GameItems, Monster
     * 
     */
    void useItemOnMonster(GameItem i, Monster m);
    
    /**
     * This function returns if buy GameItems is successfully or not
     * 
     * @param Gameitems, price
     * @return true if player has enought money
     */
    boolean buyItem(GameItem i, int price);

    /**
     * This function returns if add Monster is successfully or not
     * 
     * @param Monster
     * @return true if add is successfully
     */
    boolean addMonster(Monster m);

    /**
     * This function returns if remove Monster is successfully or not
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

}
