package model.player;

import java.util.ArrayList;

import model.Pair;
import model.monster.Monster;

public interface Player {
    /**
     * Position of the Player
     */
    Pair<Integer, Integer> getPosition();

    /**
     * get a list of Monsters of the Player
     */
    ArrayList<Monster> allMonster();

    /**
     * get a list of Items of the Player
     */
    ArrayList<Item> allItems();

    /**
     * add Item
     */
    void addItem(Item i);

    /**
     * remove Item
     */
    void removeItem(Item i);

    /**
     * use Item
     */
    void useItem(Item i, Monster m);
    
    /**
     * buy Item
     */
    boolean buyItem(Item i, int price);

    /**
     * catch Monster
     */
    void addMonster(Monster m);
    
    /**
     * get Player's name
     */
    String getName();

    /**
     * get TrainerNumber
     */
    int getTrainerNumber();
    
    /**
     * get Player's gender
     */
    Gender getGender();
    
    /**
     * get Player's Money
     */
    int getMoney();

   

}
