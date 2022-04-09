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
	
	public void useItem(String item);

	//public ArrayList<Monster> getPlayerMonstrers();
	
	//public ArrayList<GameItems> getPlayerItems();
	
	public void removeItem(String i);
	
	public boolean buyItem(String i, int price);
	
	public boolean addMonster(int idMonster);
	
	public String getPlayerName();
	
	public int getTrainerNumber();
	
	public String getGender();
	
	public int getMoney();
	
	public boolean isTeamFull();
	
	public void setMoney(int money);
	
	//public void addNpcTrainer(NpcTrainer npc);
     
	public String getMonsterNameById(int monsterId);
	
	public List<Integer> getMonstersId();
	
	public List<String> getPlayerItemsName();
	
	public void removeMonster(int monsterId);
	
	public int getMonsterExp(int monsterId);	

    public int getMonsterLevel(int monsterId);

    public boolean getMonsterIsWild(int monsterId);

    public int getMonsterMaxHealth(int monsterId);

    public String getMonsterType(int monsterId);

    public List<String> getMovesNames(int monsterId);	//??
    
    public int getMonsterHealth(int monsterId);
    
    public int getMonsterAttack(int monsterId);
    
    public int getMonsterDefense(int monsterId);
    
    public int getMonsterSpeed(int monsterId);

    public int getItemQuantity(String item);

    public String getItemDescription(String item);

    public String getItemtype(String item);

    public void addItem(String item);
    
    public void save();
    
    public boolean load();
    
    public boolean dataExist();

    public boolean usableItem(String item);
  
    public int getMaximumBlocksInRow();
    
    public int getMaximumBlocksInColumn();
    
    public boolean canEvolveByItem(String nameItem, int monsterId);
    
    public Optional<Pair<String, String>> evolveByItem(String nameItem, int monsterId);


}
