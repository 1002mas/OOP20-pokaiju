package controller;

import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gui.Direction;
import model.Pair;
import model.battle.Moves;
import model.gameitem.GameItemTypes;
import model.gameitem.GameItems;
import model.map.GameMap;
import model.map.GameMapData;
import model.map.GameMapDataImpl;
import model.map.GameMapImpl;
import model.monster.Monster;
import model.monster.MonsterSpecies;
import model.monster.MonsterStats;
import model.player.Gender;
import model.player.Player;
import model.player.PlayerImpl;
import test.InterfaceAdapter;
import model.npc.NpcTrainer;

public class PlayerControllerImpl implements PlayerController {
	
	private final int MAX_MONSTERS_IN_PLAYER_TEAM = 6;
	private Player player;
	//private GameMapData gameDataMap;
	private GameMap gameMap;
	private boolean hasPlayerMoved;
	private GsonBuilder gsonBuilder;
	private Gson gson;
	private List<NpcTrainer> npcsDefeated;
	private List<Monster> gameMonster;
	private List<GameItems> gameItems;
	private String gameItemsPath = "res/Data/ItemsData.json";
	private String gameMosterPath = "res/Data/MonstersData.json";
	private String gamenpcsDefeatedPath= "res/Data/NpcsData.json";
	
												//CREARE LISTA MOSTRI NEL SAVE
	
	
	public PlayerControllerImpl(GameMap gameMap) {
		
		this.gameMap = gameMap;
		loadGameData();
		/*
		this.player = player;
		this.gameDataMap = gameDataMap;		
		*/
		this.gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(MonsterSpecies.class, new TypeAdapterController());
		gsonBuilder.registerTypeAdapter(Moves.class, new TypeAdapterController());
		gsonBuilder.registerTypeAdapter(MonsterStats.class, new TypeAdapterController());
		gsonBuilder.registerTypeAdapter(Monster.class, new TypeAdapterController());
		this.gson = gsonBuilder.create();
		
	}
	
	
	private void loadGameData() {
		//load di lista npc, mostri e items.	//CERCARE COME SALVARE LISTA
		File pathFile;
		
		pathFile = new File(gameItemsPath);
		if(pathFile.exists()) {
			
		}
		pathFile = new File(gameItemsPath);
		if(pathFile.exists()) {
			
		}
		pathFile = new File(gameItemsPath);
		if(pathFile.exists()) {
			
		}
	}
	
	//--PLAYER--
	@Override
	public  Optional<String> interact(Pair<Integer, Integer> coord) {	//----Problema battaglia-----
		if(gameMap.getNpcAt(coord).isPresent()) {
			Optional<String> result = gameMap.getNpcAt(coord).get().interactWith();
			return result;
		}
		return Optional.empty();
	}

	@Override
	public Pair<Integer, Integer> getPlayerPosition() {		//--
		return this.player.getPosition();
	}

	@Override
	public Pair<Integer, Integer> movePlayer(Direction direction) {	//--
		if(canChangeMap(direction)) {
			gameMap.changeMap(getPlayerPosition());
			setPlayerPosition(gameMap.getPlayerMapPosition().get());
			setHasPlayerMoved(true);
		}else if(canPassThrough(direction)){
			Pair<Integer, Integer> newPosition = generateCoordinates(direction);
			player.setPosition(newPosition);
			setHasPlayerMoved(true);
		}
		setHasPlayerMoved(false);
		return getPlayerPosition();
	}

	private void setPlayerPosition(Pair<Integer, Integer> position) { //--
		this.player.setPosition(position);
	}

	@Override
	public boolean hasPlayerMoved() {	//--
		return hasPlayerMoved;
	}
	
	private void setHasPlayerMoved(boolean value) {	//--
		hasPlayerMoved = value;
	}

	@Override
	public boolean canPassThrough(Direction direction) {		//--?--
		Pair<Integer, Integer> newPosition = generateCoordinates(direction);
		return gameMap.canPassThrough(newPosition);
	}
	
	@Override
	public boolean canChangeMap(Direction direction) {	//--?--
		Pair<Integer, Integer> newPosition = generateCoordinates(direction);
		return gameMap.canChangeMap(newPosition);
	}
	
	@Override
	public Player getPlayer() {
		return player;
	    }

	
	private Pair<Integer, Integer> generateCoordinates(Direction direction) { //--
		
		Pair<Integer, Integer> newPosition = null;
		if(direction == Direction.DOWN) {
			newPosition = new Pair<>(player.getPosition().getFirst(),player.getPosition().getSecond() - 1);
		}
		if(direction == Direction.UP) {
			newPosition = new Pair<>(player.getPosition().getFirst(),player.getPosition().getSecond() + 1);
		}
		if(direction == Direction.LEFT) {
			newPosition = new Pair<>(player.getPosition().getFirst() - 1,player.getPosition().getSecond());
		}
		if(direction == Direction.RIGHT) {
			newPosition = new Pair<>(player.getPosition().getFirst() + 1,player.getPosition().getSecond());
		}
		return newPosition;
	}

	@Override
	public String getPlayerName() {	//--
		return player.getName();
	}

	@Override
	public int getTrainerNumber() {	//--
		return player.getTrainerNumber();
	}

	@Override
	public Gender getGender() {	//--
		return player.getGender();
	}
	
	@Override
	public void createNewPlayer(String name, Gender gender, int trainerNumber) {	//--
		this.player = new PlayerImpl(name, gender, trainerNumber, new Pair<Integer, Integer>(0, 0));
		this.hasPlayerMoved = false;
	}
	
	@Override
	public void addNpcTraier(NpcTrainer npc) {	
		this.npcsDefeated.add(npc);
	}

	//--MONSTERS--
	
	@Override
	public List<String> getMonstersNames() {	//--
		List<String> playerMonster = new ArrayList<String>();
		for (Monster monster : player.allMonster()) {
			playerMonster.add(monster.getName());
		} 
		return playerMonster;
	}

	private Monster getMonster(String name) {	//--
		for (Monster monster : player.allMonster()) {
			if(monster.getName().equals(name)) {
				return monster;
			}
		}
		return null;
	}
	
	@Override
	public boolean addMonster(String m) {	//--
		
		if(!player.isTeamFull()) {
			for (Monster monster : gameMonster) {
				if(monster.getName().equals(m)) {
					player.addMonster(monster);
				}
				return true;
			}									
		}
		return false;
	}
	
	
	
	@Override
	public boolean isTeamFull() {			//--
		List<Monster> playerMonsters = player.allMonster();
		return (playerMonsters.size() == MAX_MONSTERS_IN_PLAYER_TEAM);
	}
	

	@Override
	public void removeMonster(String monster) {		//--
		player.removeMonster(getMonster(monster));
	}


	@Override
	public int getMonsterExp(String monster) {	//--
		return getMonster(monster).getExp();
	}


	@Override
	public int getMonsterLevel(String monster) {	//--
		return getMonster(monster).getLevel();
	}


	@Override
	public boolean getMonsterIsWild(String monster) {	//--
		return getMonster(monster).getWild();
	}
	
	@Override
	public int getMonsterMaxHealth(String monster) {	//--
		return getMonster(monster).getMaxHealth();
	}


	@Override
	public String getMonsterType(String monster) {	//--
		return getMonster(monster).getSpecies().getType().toString();
	}


	@Override
	public List<String> getMovesNames(String monster) {	//--
		 Monster m = getMonster(monster);
		 List<String> moves =  new ArrayList<>();
		 for (Moves mov : m.getAllMoves()) {
			moves.add(mov.getName());
		}
		 
		return moves;
	}

	@Override
	public int getHealth(String monster) {	//--
		return getMonster(monster).getStats().getHealth();
	}


	@Override
	public int getAttack(String monster) {	//--
		return getMonster(monster).getStats().getAttack();
	}


	@Override
	public int getDefense(String monster) {	//--
		return getMonster(monster).getStats().getDefense();
	}


	@Override
	public int getSpeed(String monster) {	//--
		return getMonster(monster).getStats().getSpeed();
	}


	//--ITEMS--
	
	@Override
	public void useItem(String i, String m) {	//--
		Monster monster;
		GameItems item;
		if( (monster = getMonster(m)) != null && (item = getItem(i)) != null ) {
			player.useItem(item, monster);
		}
		
		
	}
	
	private GameItems getItem(String name) {	//--
		for (GameItems item : player.allItems()) {
			if(item.getNameItem().equals(name)) {
				return item;
			}
		}
		return null;
	}

	@Override
	public void removeItem(String i) {	//--
		player.removeItem(getItem(i));
	}

	@Override
	public boolean buyItem(String i, int price) { //--
		return player.buyItem(getItem(i), price);		
	}

	@Override
	public List<String> getPlayerItemsName() {	//--
		 List<String> items =  new ArrayList<>();
		 for (GameItems i : player.allItems()) {
			items.add(i.getNameItem());
		 }
		return items;
	}

	@Override
	public int getMoney() {	//--
		return player.getMoney();
	}

	@Override
	public void setMoney(int money) {	//--
		player.setMoney(money);
	}

	@Override
	public int getItemQuantity(String item) {	//--
		return getItem(item).getNumber();
	}

	@Override
	public String getItemDescription(String item) {	//--
		return getItem(item).getDescription();
	}

	@Override
	public String getItemtype(String item) {	//--
		return getItem(item).getType().toString();
	}

	@Override
	public void addItem(String item) {	//--
		
		for (GameItems i: gameItems) {
			if(i.getNameItem().equals(item)) {
				player.addItem(i);
			}
		}
		
	}
}
