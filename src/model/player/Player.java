package model.player;

import java.util.ArrayList;

import model.Pair;
import model.GameItem.*;

import model.GameItem.GameItems;

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
    ArrayList<GameItems> allItems();

    /**
     * add Item
     */
    void addItem(GameItems i);

    /**
     * remove Item
     */
    void removeItem(GameItems i);

    /**
     * use Item
     */
    void useItem(GameItems i, Monster m);

    /**
     * buy Item
     */
    boolean buyItem(GameItems i, int price);

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
