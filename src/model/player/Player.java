package model.player;

import java.util.ArrayList;

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
    ArrayList<Monster> allMonster();

    /**
     * This function returns all player's GameItems
     * 
     * @return Player's list of GameItem
     */
    ArrayList<GameItems> allItems();

    /**
     * This function adds new GameItems to player's bag
     * 
     * @param new GameItems
     */
    void addItem(GameItems i);

    /**
     * This function removes GameItems from player's bag
     * 
     * @param GameItems
     */
    void removeItem(GameItems i);

    /**
     * This function applies a GameItems on Monster
     * 
     * @param GameItems, Monster
     * 
     */
    void useItem(GameItems i, Monster m);

    /**
     * This function returns if buy GameItems is successfully or not
     * 
     * @param Gameitems, price
     * @return true if player has enought money
     */
    boolean buyItem(GameItems i, int price);

    /**
     * This function returns if add Monster is successfully or not
     * 
     * @param Monster
     * @return true if add is successfully
     */
    boolean addMonster(Monster m);

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
     * This function set Player's Money
     * 
     * @param money to be set
     */
    void setMoney(int money);

}
