package test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import model.Pair;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.map.GameMapData;
import model.map.GameMapDataImpl;
import model.map.MapBlockType;
import model.monster.Monster;
import model.monster.MonsterImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesSimple;
import model.monster.MonsterStats;
import model.monster.MonsterStatsImpl;
import model.monster.MonsterType;
import model.npc.NpcSimple;
import model.npc.NpcSimpleImpl;
import model.npc.NpcTrainerImpl;
import model.npc.TypeOfNpc;
import model.player.Gender;
import model.player.Player;
import model.player.PlayerImpl;

public class PlayerControlTestData {

	public static void main(String[] args) {
		
		//---DATA---
		
		ArrayList<String> a = new ArrayList();
		a.add("Sciao bello");
		a.add("addio");
		
		Pair<Integer,Integer> pos =  new Pair<>(1,0);
		Pair<Integer,Integer> pos2 =  new Pair<>(2,0);
		
		
		PlayerImpl p = new PlayerImpl("Luca",Gender.MAN, 6969669, pos); 
		System.out.println(p);
		
		MonsterStats stats = new MonsterStatsImpl(300, 50, 220, 320); 
		MonsterSpecies species = new MonsterSpeciesSimple("Specie Viteddu", "Info vitello", MonsterType.WATER);
		List<Moves> listOfMoves =  List.of(
				new MovesImpl("Braciere", 50, MonsterType.FIRE, 10),
				new MovesImpl("Attacco", 10, MonsterType.FIRE, 10), new MovesImpl("Volo", 50, MonsterType.FIRE, 10),
				new MovesImpl("Fossa", 50, MonsterType.FIRE, 10));
		Monster m = new MonsterImpl(stats, 300, 6, false, species, listOfMoves); 
		
		p.addMonster(m);
		
		
		
		
		//---GSON---
		
		Gson g = new Gson();
		
		String namep = g.toJson(p.getName());
		String genderp = g.toJson(p.getGender());
		String positionp = g.toJson(p.getPosition());
		String moneyp= g.toJson(p.getMoney());
		String trainernp = g.toJson(p.getTrainerNumber());
		
		// <--player monsters-->
		
		//Creare funzione che per ogni mostro nell'array esegue -->
		
			String monsterstats = g.toJson(m.getStats());
			String monsteExp = g.toJson(m.getExp());
			String monsterLevel= g.toJson(m.getLevel());
			String monstrInfo= g.toJson(m.getWild());
			String specie = g.toJson(m.getSpecies());
			String listagay = g.toJson(listOfMoves);
			
			
		String itemsp = g.toJson(p.getItems());
		
		
		
		//---WRITING---
		
		 try (BufferedWriter bf = new BufferedWriter( new FileWriter("pirupiru.json"))) {
	          
	        	
	        	//bf.write(playerJson);
	        	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
	        
	      //---RREADIG---
	        
	        try (
	    		    final BufferedReader r = new BufferedReader ( new FileReader ( "pirupiru.json" ))
	    		    ) {
	        			String first; 

	        			while ((first=r.readLine()) != null) {
	        			
	        				//Player np = g.fromJson(playerJson,PlayerImpl.class);
	        			
	            			
	    	   			  }
	        			
	    		    } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
	        
		
		
		

	}

}
