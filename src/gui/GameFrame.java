package gui;

import controller.Direction;

public interface GameFrame {
    /**
     * 
     * @param dir player the direction is looking at
     */
    void movePlayer(Direction dir);

    /**
     * 
     * @return true player got response
     */
    boolean playerInteraction();

    /**
     * ends the interaction if it was talking
     */
    void endPlayerInteraction();

    /**
     * it is used to switch between game screen. (Example: from map to battle)
     * 
     * @param name which screen has to be shown
     */
    void updateView(String name);
}
