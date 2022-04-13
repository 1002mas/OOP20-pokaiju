package controller;

import java.util.List;
import java.util.Optional;

import model.Pair;
import model.player.Gender;

public interface PlayerController {
    /**
     * ask the model to move the player to coord
     * 
     * @return true if the player is moved, false otherwise.
     */
    Pair<Integer, Integer> movePlayer(Direction direction);

    /**
     * Interacts with the object at coord position. If there is a trainer, it starts
     * the battle. If there is a npc, some text appears.
     */
    Optional<String> interact();

    /**
     * 
     * @return a list containing all visible npcs in the map
     */
    List<String> getAllNpcs();

    /**
     * @return player position
     */
    Pair<Integer, Integer> getPlayerPosition();

    /**
     * set new Player
     * 
     * @param new name, gender, trainerNumber
     */

    boolean hasPlayerMoved();

    boolean canPassThrough(Direction direction);

    boolean canChangeMap();

    /**
     * It may be turn true after calling interact() or walking in wild monsters area
     * 
     * @return true if a battle began
     */
    boolean hasBattleStarted();

    /**
     * It returns a BattleController only if hasBattleStarted returns true
     * 
     * @return a BattleController for the battle moment
     */
    Optional<BattleController> getBattleController();

    /**
     * 
     * @return current map id
     */
    int getCurrentMapID();

    void useItem(String item);

    void removeItem(String i);

    boolean buyItem(String i, int price);

    boolean addMonster(int idMonster);

    void createNewPlayer(String name, Gender gender, int trainerNumber);

    String getPlayerName();

    int getTrainerNumber();

    String getPlayerGender();

    int getPlayerMoney();

    List<String> getPlayerItemsName();

    boolean isTeamFull();

    void setPlayerMoney(int money);

    String getMonsterNameById(int monsterId);

    List<Integer> getMonstersId();

    void removeMonster(int monsterId);

    int getMonsterExp(int monsterId);

    int getMonsterLevel(int monsterId);

    boolean getMonsterIsWild(int monsterId);

    int getMonsterMaxHealth(int monsterId);

    String getMonsterType(int monsterId);

    List<String> getMovesNames(int monsterId);

    int getMonsterHealth(int monsterId);

    int getMonsterAttack(int monsterId);

    int getMonsterDefense(int monsterId);

    int getMonsterSpeed(int monsterId);

    int getItemQuantity(String item);

    String getItemDescription(String item);

    String getItemtype(String item);

    void addItem(String item);

    void save();

    boolean load();

    boolean dataExist();

    boolean canUseItem(String item);

    int getMaximumBlocksInRow();

    int getMaximumBlocksInColumn();

    boolean canEvolveByItem(String nameItem, int monsterId);

    /**
     * 
     * @param nameItem
     * @param monsterId
     * @return
     */
    Optional<Pair<String, String>> evolveByItem(String nameItem, int monsterId);
    
    /**
     * 
     * @param monsterId
     * @return
     */
    Optional<Pair<String, String>> evolveByLevel(int monsterId);

}
