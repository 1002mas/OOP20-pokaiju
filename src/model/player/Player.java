package model.player;

import java.util.ArrayList;

import model.monster.Monster;

public interface Player {
    /**
     * Position of the Player
     */
    PositionPlayer getPosition();

    /**
     * get a list of Monsters of the Player
     */
    ArrayList<Monster> allMonster();

    /**
     * get a list of Items of the Player
     */
    ArrayList<Item> allItems();

    /**
     * get Gender
     */
    String getGender();

    /**
     * get trainerNumber
     */
    int getTrainerNumber();

    /**
     * get Name
     */
    String getName();

    /**
     * add Item
     */
    void addItem(Item i);

    /**
     * remove Item
     */
    void removeItem(Item i);

    /**
     * catch Monster
     */
    void addMonster(Monster m);

}
