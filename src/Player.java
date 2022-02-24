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
     * load Player data from json file
     */
    Player getData(String filename);

}
