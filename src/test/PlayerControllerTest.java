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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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

public class PlayerControllerTest {

	public static void main(String[] args) throws InterruptedException
    {
		ArrayList<String> a = new ArrayList();
		a.add("Sciao bello");
		a.add("addio");
		Pair<Integer,Integer> pos =  new Pair<>(1,0);
		Pair<Integer,Integer> pos2 =  new Pair<>(2,0);
		NpcSimple ns = new  NpcSimpleImpl("Ru", TypeOfNpc.SIMPLE, a, pos);
		NpcSimple ns1 = new NpcSimpleImpl("BUbu", TypeOfNpc.SIMPLE, a, pos);
		NpcSimple nt =  new NpcTrainerImpl("saro",TypeOfNpc.TRAINER,a,null,pos);
		
		//Map
		
		Map<Pair<Integer, Integer>, MapBlockType> n = new  HashMap<>();
		n.put(pos, MapBlockType.WILD_ZONE);
		n.put(pos2, MapBlockType.BORDER);
		Map<Pair<Integer, Integer>, NpcSimple> npcs = new HashMap<>();
		npcs.put(new Pair<>(12,21), ns); npcs.put(new Pair<>(3,89), ns1); npcs.put(new Pair<>(9,76), nt);
		Map<Pair<Integer, Integer>, GameMapData> linkedmaps = new HashMap<>();
		linkedmaps.put(pos, new GameMapDataImpl(null,null,null,null, null));
		Map<GameMapData, Pair<Integer, Integer>> links = new HashMap<>();
		links.put(new GameMapDataImpl(null,null,null,null,null), pos);
		
		//System.out.println(npcs);
		
		GameMapData gmd = new GameMapDataImpl("Saro", n, npcs, linkedmaps, links);
		//System.out.println("gmd syso--> "+gmd);
		
		PlayerImpl p = new PlayerImpl("Luca",Gender.MAN, 6969669, pos);
		//System.out.println(p);
		//MonsterStats stats, int exp, int level, boolean isWild, MonsterSpecies species, List<Moves> movesList
		MonsterStats stats = new MonsterStatsImpl(300, 50, 220, 320); 
		
		MonsterSpecies species = new MonsterSpeciesSimple("Specie Viteddu", "Info vitello", MonsterType.WATER);
		List<Moves> listOfMoves =  List.of(new MovesImpl("Braciere", 50, MonsterType.FIRE, 10),
				new MovesImpl("Attacco", 10, MonsterType.FIRE, 10), new MovesImpl("Volo", 50, MonsterType.FIRE, 10),
				new MovesImpl("Fossa", 50, MonsterType.FIRE, 10));
		Monster m = new MonsterImpl(stats, 300, 6, false, species, listOfMoves); 
		
		p.addMonster(m);
		p.addMonster(m);
		
		
		/*
		String name, Map<Pair<Integer, Integer>, MapBlockType> blocks,
	    Map<Pair<Integer, Integer>, NpcSimple> npcs, Map<Pair<Integer, Integer>, GameMapData> linkedMaps,
	    Map<GameMapData, Pair<Integer, Integer>> linkedMapsStartingPosition
		*/
		
		//Gson g = new Gson();
		//Create our gson instance
		
		System.out.println("Inizio gson");
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(MonsterSpecies.class, new InterfaceAdapter());
		builder.registerTypeAdapter(Moves.class, new InterfaceAdapter());
		builder.registerTypeAdapter(MonsterStats.class, new InterfaceAdapter());
		Gson g = builder.create();
		System.out.println("Fine gson");
		
		

		
		/*
		String s =g.toJson(ns);
		System.out.println(s);
		String h =g.toJson(ns1);
		String chieti = g.toJson(nt);
		 */
		//String mapString = g.toJson(gmd);
		//System.out.println(mapString);
		
		/*Prova a uno a uno	"Saro", n, npcs, linkedmaps, links
		String nst = g.toJson(n);
		System.out.println(n);
		String npcst = g.toJson(npcs);
		System.out.println(npcst);
		String linkedmst = g.toJson(linkedmaps);
		System.out.println(linkedmst);
		String linkssp = g.toJson(links);
		System.out.println(linkssp);
		*/
		
		//Player
		
		String playerJson = g.toJson(p);
		System.out.println(playerJson);
		
		
		String namep = g.toJson(p.getName());
		
		String genderp = g.toJson(p.getGender());
		String positionp = g.toJson(p.getPosition());
		String moneyp= g.toJson(p.getMoney());
		String trainernp = g.toJson(p.getTrainerNumber());
		//String monsterp = g.toJson(p.getMonster());System.out.println(monsterp);
		String singolomostro = g.toJson(m);System.out.println("Singolo mostro--> "+ singolomostro);
		String itemsp = g.toJson(p.getItems());

		String specieprimo = g.toJson(m.getSpecies());
		
		String specie = "{\"CLASSNAME\":\""+ m.getSpecies().getClass().getName() + "\",\"DATA\":" + specieprimo + "}";
		
		
		
		System.out.println("specie--> " + specie);
		
		
		
		
		
		String listagay = g.toJson(listOfMoves);
		//System.out.println("species -->"+ m.getSpecies().getClass());
		//Class stocazzo = m.getSpecies().getClass();
		/*		*/
        try (BufferedWriter bf = new BufferedWriter( new FileWriter("lallero.json"))) {
          
        	/*
        	bf.write("SimpleNpc");bf.newLine();		//Potrei anche mettere un "----" alla fine di ogni riga ed inizrla con un "SimpleNpc" e poter avere tutte le righe disordinate e non raggruppate
            bf.write(s);bf.newLine(); bf.flush();
            bf.write(h);bf.newLine(); bf.flush();
            bf.write("-----");bf.newLine();
            bf.write("TrainerNpc");bf.newLine();
            bf.write(chieti);bf.newLine(); bf.flush();
            bf.write("-----");bf.newLine();
            */
        	
        	//bf.write(s);
        	//bf.write(mapString);
        	/*
        	bf.write(nst);bf.newLine();
        	bf.write(npcst);bf.newLine();
        	bf.write(linkedmst);bf.newLine();
        	bf.write(linkssp);bf.newLine();
        	*/
        	
        	/*
        	String namep = g.toJson(p.getName());
    		String genderp = g.toJson(p.getGender());
    		String positionp = g.toJson(p.getPosition());
    		String moneyp= g.toJson(p.getMoney());
    		String trainernp = g.toJson(p.getTrainerNumber());
    		String monsterp = g.toJson(p.getMonster());
    		String itemsp = g.toJson(p.getItems());
    		*/
        	
        	/*
        	bf.write(namep);bf.newLine();bf.flush();
        	bf.write(genderp);bf.newLine();bf.flush();
        	bf.write(positionp);bf.newLine();bf.flush();
        	bf.write(moneyp);bf.newLine();bf.flush();
        	bf.write(trainernp);bf.newLine();bf.flush();
        	//bf.write(monsterp);bf.newLine();bf.write("---");bf.newLine();bf.flush();
        	bf.write(singolomostro);bf.newLine();bf.write("---");bf.newLine();bf.flush();
        	bf.write(itemsp);bf.newLine();bf.write("---");bf.newLine();bf.flush();
        	*/
        	
        	bf.write(playerJson);
        	//bf.write(listagay);
        	
        	
        	
        } catch (IOException e) {
            e.printStackTrace();
        }
		
        
        //reading
        
        try (
    		    final BufferedReader r = new BufferedReader ( new FileReader ( "lallero.json" ))
    		    ) {
        			String first; //String sum = "";
        			String temp = r.readLine();
        			/*
        			
        			String jname = g.fromJson(temp, String.class);
        			temp = r.readLine();
        			Gender d = g.fromJson(temp, Gender.class);
        			System.out.println(d);
        			temp = r.readLine() + r.readLine() + r.readLine();
        			temp = r.readLine();
        			System.out.println(temp);
        			//ArrayList<Monster> ginob = g.fromJson(temp,ArrayList.class);
        		//	Type userListType = new TypeToken.get(class).getClass();
        					//TypeToken<ArrayList<Monster>>(){}.getType();
        			
        			
        			*/
        			/*
        			Type founderListType = new TypeToken<ArrayList<MonsterImpl>>(){}.getType();  
        			List<Monster> arraymostri = g.fromJson(temp, founderListType);
        			System.out.println(arraymostri);
        			
        			*/
        			
        				Monster newm = g.fromJson(temp,MonsterImpl.class);
        				//MonsterSpecies mspi = g.fromJson(temp, MonsterSpecies.class);
        				//System.out.println(mspi);
        			
        			
        			while ((first=r.readLine()) != null) {
        			
        				 // NpcSimple np2 =  g.fromJson(first, NpcSimpleImpl.class);   
        				//System.out.println("first--> "+first);
        				//sum=sum+first;
        				//GameMapData gmd2  = g.fromJson(first, GameMapDataImpl.class);
        				
        				//Player p2 = g.fromJson(first, PlayerImpl.class);
        			/*
    	   			String stu;
    	   			
    	   			if(first.equals("SimpleNpc")) {
    	   				//System.out.println("culoculoculo");
    	   			while((stu=r.readLine()).equals("-----") == false) {
    	   			  NpcSimple np2 =  g.fromJson(stu, NpcSimpleImpl.class);      	   			
    	   			  System.out.println("&&"+np2.getName()+"&&");
    	   			}
    	   			}
    	   		
    	   			if(first.equals("TrainerNpc")) {
        	   			while((stu=r.readLine()).equals("-----") == false) {
        	   			  NpcSimple np2 =  g.fromJson(stu, NpcTrainerImpl.class);      	   			
        	   			  System.out.println(np2.getName());
        	   			}
        	   			}
    	   		*/		
        				
        				
        				//----Monster species-----
        				//MonsterSpecies msp = g.fromJson(first,MonsterSpeciesSimple.class);
        				//System.out.println(msp);
        				
        				//<---LISTA DELLE MOSSE--->
        			/*
        				System.out.println("First--> "+first);
        				Type founderListType = new TypeToken<ArrayList<MovesImpl>>(){}.getType();  
            			List<Moves> arraymostri = g.fromJson(first, founderListType);
            			System.out.println("arraymostri--> "+arraymostri);
        				System.out.println("	0 --> " + arraymostri.get(0));
            		*/	
            			
    	   			  }
        		//	System.out.println("sum--> "+sum);
        			
    		    } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
        
        /*
        
        NpcSimple np2 =  g.fromJson(s, NpcSimpleImpl.class);  
        NpcSimple np3 =  g.fromJson(h, NpcSimpleImpl.class);
        
        System.out.println(np2.getName() + np3.getName());
        
        /*
        JsonReader jsonReader;
		try {
			jsonReader = new JsonReader(new FileReader("lallero.json"));
			 jsonReader.setLenient(true);
	         
		        try
		        {
		          while (jsonReader.hasNext()) 
		          {
		            JsonToken nextToken = jsonReader.peek();
		             
		            if (JsonToken.BEGIN_OBJECT.equals(nextToken)) {
		     
		              jsonReader.beginObject();
		     
		            } else if (JsonToken.NAME.equals(nextToken)) {
		     
		              String name = jsonReader.nextName();
		              System.out.println("Token KEY >>>> " + name);
		     
		            } else if (JsonToken.STRING.equals(nextToken)) {
		     
		              String value = jsonReader.nextString();
		              System.out.println("Token Value >>>> " + value );
		     
		            } else if (JsonToken.NUMBER.equals(nextToken)) {
		     
		              long value = jsonReader.nextLong();
		              System.out.println("Token Value >>>> " + value);
		     
		            } else if (JsonToken.BEGIN_ARRAY.equals(nextToken)) {
		   		     
			              //long value = jsonReader.nextLong();
		            	jsonReader.beginArray();
			              System.out.println("tstar array>>>> ");
			     
			        }else if (JsonToken.END_ARRAY.equals(nextToken)) {
					     
			             // long value = jsonReader.nextLong();
			        	//jsonReader.endArray();  
			        	System.out.println("end array>>>> ");
			              
			     
			        } else if (JsonToken.NULL.equals(nextToken)) {
		     
		              jsonReader.nextNull();
		              System.out.println("Token Value >>>> null");
		               
		            } else if (JsonToken.END_OBJECT.equals(nextToken)) {
		     
		              jsonReader.endObject();
		     
		            }
		          }
		        } catch (IOException e) {
		          e.printStackTrace();
		        } finally {
		          try {
					jsonReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        }
		        
		        
		        
			
			
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       */
        
        
        
    }
	
}
