package controller;

import java.util.List;
import java.util.Optional;

import gui.Direction;
import model.Pair;
import model.player.Gender;
import model.player.Player;

public interface PlayerController {
    /**
     * ask the model to move the player to coord
     * 
     * @return true if the player is moved, false otherwise.
     */
    public Pair<Integer, Integer> movePlayer(Direction direction);

    /**
     * Interacts with the object at coord position. If there is a trainer, it starts
     * the battle. If there is a npc, some text appears.
     */
    Optional<String> interact(Direction direction);

    /**
     * @return player position
     */
    Pair<Integer, Integer> getPlayerPosition();

    /**
     * set new Player
     * 
     * @param new name, gender, trainerNumber
     */
    public void createNewPlayer(String name, Gender gender, int trainerNumber);

    /**
     * get Player
     * 
     * @return Player
     */
    public Player getPlayer();

    public boolean hasPlayerMoved();
	
	public boolean canPassThrough(Direction direction);
	
	public boolean canChangeMap();
	
	public void useItem(String item, String m);

	//public ArrayList<Monster> getPlayerMonstrers();
	
	//public ArrayList<GameItems> getPlayerItems();
	
	public void removeItem(String i);
	
	public boolean buyItem(String i, int price);
	
	public boolean addMonster(String m);
	
	public String getPlayerName();
	
	public int getTrainerNumber();
	
	public String getGender();
	
	public int getMoney();
	
	public boolean isTeamFull();
	
	public void setMoney(int money);
	
	//public void addNpcTrainer(NpcTrainer npc);
     
	public List<String> getMonstersNames();
	
	public List<String> getPlayerItemsName();
	
	public void removeMonster(String monster);
	
	public int getMonsterExp(String monster);	

    public int getMonsterLevel(String monster);

    public boolean getMonsterIsWild(String monster);

    public int getMonsterMaxHealth(String monster);

    public String getMonsterType(String monster);

    public List<String> getMovesNames(String monster);
    
    public int getMonsterHealth(String monster);
    
    public int getMonsterAttack(String monster);
    
    public int getMonsterDefense(String monster);
    
    public int getMonsterSpeed(String monster);

    public int getItemQuantity(String item);

    public String getItemDescription(String item);

    public String getItemtype(String item);

    public void addItem(String item);
    
    public void save(Player player);
    
    public boolean load();
    
    public boolean dataExist();

    public boolean usableItem(String item);
    
    int getMaximumBlocksInRow();
    
    int getMaximumBlocksInColumn();
    

}
