package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import model.battle.Moves;
import model.gameitem.GameItemImpl;
import model.gameitem.GameItem;
import model.map.GameMap;
import model.map.GameMapData;
import model.map.GameMapDataImpl;
import model.map.GameMapImpl;
import model.monster.Monster;
import model.monster.MonsterImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterStats;
import model.npc.NpcSimple;
import model.npc.NpcSimpleImpl;
import model.npc.NpcTrainer;
import model.npc.TypeOfNpc;
import model.player.Player;
import model.player.PlayerImpl;

public class DataControllerImpl implements DataController {				
											
	private final int MAXIMUM_BLOCK_IN_ROW = 20;
	private final int MAXIMU_BLOCK_IN_COLUMN = 20;
	
	private GameMapData gameMapData;			
	private GameMap gameMap;			
	private GsonBuilder gsonBuilder;
	private Gson gson;
	private final String playerDataPath = "res"+File.separator+"Data"+File.separator+"PlayerData.json";	
	private final String NpcsDataPath = "res"+File.separator+"Data"+File.separator+"NpcsData.json";
	private final String mapDataPath = "res"+File.separator+"Data"+File.separator+"MapData.json";		
	private final String gameMosterPath = "res"+File.separator+"Data"+File.separator+"MonstersData.json";	
	private final String gameItemsPath = "res"+File.separator+"Data"+File.separator+"ItemsData.json";	
	private List<NpcTrainer> npcsDefeated = new ArrayList<>();
	
	
	
	public DataControllerImpl() {
		this.gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(MonsterSpecies.class, new TypeAdapterController());
		gsonBuilder.registerTypeAdapter(Moves.class, new TypeAdapterController());
		gsonBuilder.registerTypeAdapter(MonsterStats.class, new TypeAdapterController());
		gsonBuilder.registerTypeAdapter(Monster.class, new TypeAdapterController());
		gsonBuilder.registerTypeAdapter(GameMapData.class, new TypeAdapterController());
		gsonBuilder.registerTypeAdapter(GameItem.class,new TypeAdapterController());
		this.gson = gsonBuilder.create();
		loadMapData();
		gameMap = new GameMapImpl(gameMapData);
	}
	
	@Override
	public void saveData(Player player) {	//--
		
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
	public boolean loadData(Player player) {	//--
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
			
			File dataFile = new File(NpcsDataPath);
			if(dataFile.exists()) {
				try (final BufferedReader reader = new BufferedReader ( new FileReader ( dataFile))){
			        			String stringRead; 
			        			while ((stringRead= reader.readLine()) != null) {
			        				Type type = new TypeToken<ArrayList<NpcSimpleImpl>>(){}.getType();
			            			npcsDefeated = gson.fromJson(stringRead, type);  
			            			
			    	   			  }
			        			
			    		    } catch (IOException e) {
								//e.printStackTrace();
								return false;
							} 
			return true;
			}	
		}
		return false;
	}
	
	@Override
	public boolean dataExsist() {	//--
		File playerDataFile = new File(playerDataPath);
		return playerDataFile.exists();
	}

	@Override
	public boolean loadMapData() {	//--
		File mapDataFile = new File(mapDataPath);
		if(mapDataFile.exists()) {
			try (final BufferedReader reader = new BufferedReader ( new FileReader ( mapDataFile))){
		        			String stringReadMap; 
		        			while ((stringReadMap= reader.readLine()) != null) {
		        				gameMapData = gson.fromJson(stringReadMap,GameMapDataImpl.class);	//sost. gameMapData
		        				//set
		        				setNpcDefeatedInMap();
		    	   			  }
		        			
		    		    } catch (IOException e) {
							//e.printStackTrace();
							return false;
						} 
		return true;
		}
	return false;
	}
	
	@Override
	public List<Monster> loadMonsters() {	//--
		List<Monster> m = new ArrayList<>();
		File dataFile = new File(gameMosterPath);
		if(dataFile.exists()) {
			try (final BufferedReader reader = new BufferedReader ( new FileReader ( gameMosterPath))){
		        			String stringReadMonster; 
		        			while ((stringReadMonster= reader.readLine()) != null) {
		        				Type type = new TypeToken<ArrayList<MonsterImpl>>(){}.getType();		           			 
		            			m = gson.fromJson(stringReadMonster, type);
		    	   			  }
		        			
		    		    } catch (IOException e) {
							//e.printStackTrace();
						} 
		}
		return m;
	}

	@Override
	public List<GameItem> loadItems() {	//--
		List<GameItem> i = new ArrayList<>();
		File dataFile = new File(gameItemsPath);
		if(dataFile.exists()) {
			try (final BufferedReader reader = new BufferedReader ( new FileReader ( gameItemsPath))){
		        			String stringReadItem; 
		        			while ((stringReadItem= reader.readLine()) != null) {
		        				Type type = new TypeToken<ArrayList<GameItemImpl>>(){}.getType();		           			 
		            			i = gson.fromJson(stringReadItem, type);
		    	   			  }
		        			
		    		    } catch (IOException e) {
							//e.printStackTrace();
						} 
		}
		return i;
	}
	

	@Override
	public GameMapData getGameMapData() {	//--	
		return this.gameMapData;				
	}

	@Override
	public GameMap getGameMap() {	//--			
		return this.gameMap;
	}
	
	@Override
	public void addNpcsDefeated(NpcTrainer npc) {	//--
		this.npcsDefeated.add(npc);		
	}

	@Override
	public void setNpcDefeatedInMap() {		//--
		if(!npcsDefeated.isEmpty()) {
			for (NpcSimple npc : gameMapData.getAllNpcs()) {		
				for(NpcTrainer npcTrainer : npcsDefeated) {
					if(npcTrainer.getName().equals(npc.getName())) {
						npc = npcTrainer;
				 	}
				}
			}
		}
	}

	@Override
	public void setNpcDefeatedFromMap() {		//--
		NpcTrainer temp;
		for (NpcSimple npc : gameMapData.getAllNpcs()) {	
			if(npc.getTypeOfNpc() == TypeOfNpc.TRAINER ) {
				temp = (NpcTrainer) npc;
					if(!temp.isDefeated() && !npcsDefeated.contains(temp)) {
						npcsDefeated.add(temp);
					}
			}
		}
		
	}

	@Override
	public int getMaximumBlocksInRow() {
		return this.MAXIMUM_BLOCK_IN_ROW;
	}

	@Override
	public int getMaximumBlocksInColumn() {
		return this.MAXIMU_BLOCK_IN_COLUMN;
	}

	@Override
	public void deleteNpcData() {
		File file = new File(NpcsDataPath);
		if(file.exists()) {
			file.delete();
		}
	}

	
	
	

}
