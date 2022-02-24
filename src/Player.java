import java.util.ArrayList;

public interface Player {
    /**
     * Position of the Player
     */
    PositionPlayer getPosition();

    /**
     * get a list of Monsters of the Player
     */
    ArrayList<String> allMonster();

    /**
     * get a list of Items of the Player
     */
    ArrayList<Item> allItems();

    /**
     * get a list of Items of the Player
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
    void addMonster();

}
