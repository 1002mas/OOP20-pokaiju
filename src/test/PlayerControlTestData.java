package test;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Pair;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.monster.MonsterType;
import model.npc.NpcSimple;

public class PlayerControlTestData {

	public static void main(String[] args) {
		
		//---DATA---
		
		private  Set<NpcSimple> npcset;
		
		final String movesPath = "res"+File.separator+"data"+File.separator+"Moves"+File.separator;
		
		ArrayList<String> a = new ArrayList();
		a.add("Sciao bello");
		a.add("addio");
		
		Pair<Integer,Integer> pos =  new Pair<>(1,0);
		Pair<Integer,Integer> pos2 =  new Pair<>(2,0);
		
		//DataLoaderController dlc = new DataLoaderControllerImpl();
		
		Moves m1 = new MovesImpl("uno", 23, MonsterType.FIRE, 2);
		Moves m2 = new MovesImpl("due", 12, MonsterType.NONE, 3);
		
		Moves m3 = new MovesImpl("tre", 11, MonsterType.WATER, 55);
		
		/*
		PlayerImpl p = new PlayerImpl("Luca",Gender.MAN, 6969669, pos); 
		System.out.println(p);
		
		//MonsterStats stats = new MonsterStatsImpl(300, 50, 220, 320); 
		//MonsterSpecies species = new MonsterSpeciesSimple("Specie Viteddu", "Info vitello", MonsterType.WATER);
		List<Moves> listOfMoves =  List.of(
				new MovesImpl("Braciere", 50, MonsterType.FIRE, 10),
				new MovesImpl("Attacco", 10, MonsterType.FIRE, 10), new MovesImpl("Volo", 50, MonsterType.FIRE, 10),
				new MovesImpl("Fossa", 50, MonsterType.FIRE, 10));
		//Monster m = new MonsterImpl(stats, 300, 6, false, species, listOfMoves); 
		
		p.addMonster(m);
		
		
		
		*/
		//---GSON---
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		//gsonBuilder.registerTypeAdapter(Moves.class, new InterfaceAdapter());
		//gsonBuilder.registerTypeAdapter(MovesData.class, new InterfaceAdapter());
		/*
		gsonBuilder.registerTypeAdapter(MonsterSpecies.class, new TypeAdapterController());
		gsonBuilder.registerTypeAdapter(Moves.class, new TypeAdapterController());
		gsonBuilder.registerTypeAdapter(MonsterStats.class, new TypeAdapterController());
		gsonBuilder.registerTypeAdapter(Monster.class, new TypeAdapterController());
		gsonBuilder.registerTypeAdapter(GameMapData.class, new TypeAdapterController());
		gsonBuilder.registerTypeAdapter(GameItems.class,new TypeAdapterController());
		*/
		Gson g = gsonBuilder.create();
		
		String m1name = g.toJson(m1.getName());
		String m1n1 = g.toJson(23);
		String m1t = g.toJson(MonsterType.FIRE);
		String m1n2 = g.toJson(2);
		
		String m3s =  g.toJson(m3);
		
		//"uno", 23, MonsterType.FIRE, 2
		String m2s = g.toJson(m2);
		System.out.println(m3s);
		/*
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
		
		*/
		
		//---WRITING---
		/*
		String newName;
		newName = movesPath+m1.getName()+".json";
		 try (BufferedWriter bf = new BufferedWriter( new FileWriter( newName ))) {
	          
	        	
	        	//bf.write(playerJson);
			 	
			 	bf.write(m1name);bf.newLine();
			 	bf.write(m1n1);bf.newLine();
			 	bf.write(m1t);bf.newLine();
			 	bf.write(m1n2);bf.newLine();
			 	
			// bf.write(m3s);
			 	
			 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		 */
		 /*
		 newName = movesPath + m2.getName()+".json";
		 try (BufferedWriter bf = new BufferedWriter( new FileWriter( newName ))) {
	          
	        	
	        	//bf.write(playerJson);
			 	bf.write(m2s);
			 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		 	*/
		 
		 //dlc.getMoves();	//ritornare lista
		 
	       /* 
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
	        
		
		*/
		

	}

}
