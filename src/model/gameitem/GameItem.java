package model.gameitem;

import model.monster.Monster;

public interface GameItem {
    /**
     * This function returns the name of GameItem.
     * 
     * @return GameItem's name
     */
    String getNameItem();

    /**
     * This function returns the type of GameItem.
     *
     * @return GameItem's type
     */
    GameItemTypes getType();

    /**
     * This function returns GameItem's Description.
     *
     * @return GameItem's Description
     */
     String getDescription();

    /**
     * This function returns if GameItem is used.
     * 
     * @param m
     * @return if GameItem is used
     */
    boolean use(Monster m);

}
