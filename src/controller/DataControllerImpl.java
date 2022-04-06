package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.battle.Moves;
import model.map.GameMap;
import model.map.GameMapData;
import model.map.GameMapImpl;
import model.monster.Monster;
import model.monster.MonsterSpecies;
import model.monster.MonsterStats;
import model.npc.NpcTrainer;
import model.player.Player;
import model.player.PlayerImpl;

public class DataControllerImpl implements DataController {

	private Player player;
	private GameMap gameMap;
	private boolean hasPlayerMoved;
	private GsonBuilder gsonBuilder;
	private Gson gson;
	private final String playerDataPath = "res/Data/PlayerData.json";
	private final String NpcsDataPath = "res/Data/NpcsData.json";
	private List<NpcTrainer> npcsDefeated;
	
	//LOAD MAP DATA 
	
	public DataControllerImpl(PlayerController playerController) {	//COSI' OR IL CONTRARIO? CHI INCAPSULA CHI?
		
		//this.gameMap = 
		this.player = playerController.getPlayer();
		/*
		this.player = player;
		this.gameDataMap = gameDataMap;
		this.gameMap = gameMap;
		this.hasPlayerMoved = false;
		*/
		this.gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(MonsterSpecies.class, new TypeAdapterController());
		gsonBuilder.registerTypeAdapter(Moves.class, new TypeAdapterController());
		gsonBuilder.registerTypeAdapter(MonsterStats.class, new TypeAdapterController());
		gsonBuilder.registerTypeAdapter(Monster.class, new TypeAdapterController());
		this.gson = gsonBuilder.create();
	}
	
	@Override
	public void saveData() {
		
		String playerJson = gson.toJson(player);
		String npcsJson= gson.toJson(npcsDefeated);
		
		 try (BufferedWriter bf = new BufferedWriter( new FileWriter(playerDataPath))) {	
	        	bf.write(playerJson);	
	        } catch (IOException e) {
	            //e.printStackTrace();
	        }
		 
		 try (BufferedWriter bf = new BufferedWriter( new FileWriter(NpcsDataPath))) {	
	        	bf.write(npcsJson);	
	        } catch (IOException e) {
	            //e.printStackTrace();
	        }
		
	}

	@Override
	public boolean loadData() {
		File playerDataFile = new File(playerDataPath);
		if(playerDataFile.exists()) {
			try (final BufferedReader reader = new BufferedReader ( new FileReader ( playerDataPath ))){
		        			String stringReadPlayer; 
		        			while ((stringReadPlayer = reader.readLine()) != null) {
		        				player = gson.fromJson(stringReadPlayer,PlayerImpl.class);	
		    	   			  }
		        			
		    		    } catch (IOException e) {
							//e.printStackTrace();
							return false;
						} 
			//---ADD NPC loader---
			
			
			return true;
		}
		return false;
	}
	
	@Override
	public boolean dataExsist() {
		File playerDataFile = new File(playerDataPath);
		return playerDataFile.exists();
	}

	@Override
	public boolean loadMap() {
		// TODO Auto-generated method stub
		return false;
	}

}
