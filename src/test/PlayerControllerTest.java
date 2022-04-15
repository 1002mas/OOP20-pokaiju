package test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controller.json.DataLoaderControllerImpl;
import controller.json.loader.GameItemLoader;
import controller.json.loader.GameMapDataLoader;
import controller.json.loader.MonsterGiftLoader;
import controller.json.loader.MonsterLoader;
import controller.json.loader.MonsterSpeciesLoader;
import controller.json.loader.NpcBehaviorChangerLoader;
import controller.json.loader.NpcHealerSimpleLoader;
import controller.json.loader.NpcMerchantLoader;
import controller.json.loader.NpcTrainerLoader;
import controller.json.loader.UniqueMonsterEventLoader;
import model.Pair;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.gameitem.GameItemTypes;
import model.map.MapBlockType;
import model.monster.EvolutionType;
import model.monster.Monster;
import model.monster.MonsterStats;
import model.monster.MonsterStatsImpl;
import model.monster.MonsterType;
import model.npc.NpcHealerImpl;
import model.npc.NpcSimple;
import model.npc.NpcSimpleImpl;
import model.player.Player;

public class PlayerControllerTest {

	public static void main(String[] args) throws InterruptedException {

		DataLoaderControllerImpl dlc;
		GsonBuilder gsonBuilder = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting();
		;

		Gson g = gsonBuilder.create();
		;
		List<Monster> monsterList = new ArrayList<>();
		Player player = null;
		List<Integer> activate = new ArrayList<>();
		List<Integer> deactivate = new ArrayList<>();
		List<Integer> mList = new ArrayList<>();
		List<String> frasi = new ArrayList<>();

		frasi.add("prima frase");
		frasi.add("seconda frase");

		Pair<Integer, Integer> pos = new Pair<>(1, 1);

		
		/*
		*/
		Moves m1 = new MovesImpl("Braciere", 50, MonsterType.FIRE, 10);
		Moves m2 = new MovesImpl("Attacco", 10, MonsterType.FIRE, 10);
		Moves m3 = new MovesImpl("Volo", 50, MonsterType.FIRE, 10);
		GameItemLoader gil1 = new GameItemLoader("item senza heal", "descrizione", GameItemTypes.EVOLUTIONTOOL,
				Optional.empty());
		GameItemLoader gil2 = new GameItemLoader("item senza heal", "descrizione", GameItemTypes.MONSTERBALL,
				Optional.empty());
		GameItemLoader gil3 = new GameItemLoader("item senza heal", "descrizione", GameItemTypes.HEAL, Optional.of(12));
		MonsterStats ms = new MonsterStatsImpl(10, 10, 10, 10);
		List<Pair<String, Integer>> allMoves = new ArrayList<>();
		allMoves.add(new Pair<String, Integer>("Braciere", 50));
		allMoves.add(new Pair<String, Integer>("Attacco", 10));
		allMoves.add(new Pair<String, Integer>("Volo", 50));

		MonsterSpeciesLoader msl1 = new MonsterSpeciesLoader(Optional.of("nome3"), "nome", "info", MonsterType.FIRE,
				EvolutionType.ITEM, ms.getAttack(), ms.getAttack(), ms.getAttack(), ms.getAttack(), allMoves,
				Optional.of("evolutionItem"), Optional.of(2));
		MonsterSpeciesLoader msl2 = new MonsterSpeciesLoader(Optional.of("nome4"), "nome", "info", MonsterType.FIRE,
				EvolutionType.LEVEL, ms.getAttack(), ms.getAttack(), ms.getAttack(), ms.getAttack(), allMoves,
				Optional.of("evolutionItem"), Optional.of(4));
		MonsterSpeciesLoader msl3 = new MonsterSpeciesLoader(Optional.empty(), "nome", "info", MonsterType.FIRE,
				EvolutionType.NONE, ms.getAttack(), ms.getAttack(), ms.getAttack(), ms.getAttack(), allMoves,
				Optional.of("evolutionItem"), Optional.of(2));

		MonsterLoader monster = new MonsterLoader(1, 10, 2, "nome3", allMoves, 1, 1, 1, 1);

		activate.add(5);
		activate.add(4);

		deactivate.add(4);
		deactivate.add(5);

		mList.add(3000);
		mList.add(3001);

		MonsterGiftLoader mgl = new MonsterGiftLoader(1, true, false, false, activate, deactivate, mList);
		NpcBehaviorChangerLoader nbl = new NpcBehaviorChangerLoader(2, true, true, false, activate, deactivate);
		UniqueMonsterEventLoader umel = new UniqueMonsterEventLoader(3, false, true, activate, deactivate, 1);

		List<Integer> eventlist = new ArrayList<>();
		eventlist.add(1); eventlist.add(2); 
		
		NpcHealerSimpleLoader npc1 = new NpcHealerSimpleLoader("nomeS", frasi, pos, false, false, eventlist);
		
		NpcTrainerLoader npc2 = new NpcTrainerLoader("nomeT", frasi, mList, false, false, false,
				pos, eventlist);
		Map<String, Integer> npcItem = new HashMap<>();
		npcItem.put("item1", 5);
		npcItem.put("item2", 6);
		npcItem.put("item3", 2);
		NpcMerchantLoader npc3 = new NpcMerchantLoader("nomeM",frasi, pos, false, false, npcItem, eventlist);
		NpcHealerSimpleLoader npc4 = new NpcHealerSimpleLoader("nomeH", frasi, pos, false, false, eventlist);

		// map
		Map<Pair<Integer, Integer>, MapBlockType> blocks = new HashMap<>();
		blocks.put(new Pair<Integer, Integer>(1, 1), MapBlockType.WILD_ZONE);
		blocks.put(new Pair<Integer, Integer>(2, 1), MapBlockType.WALK);
		blocks.put(new Pair<Integer, Integer>(3, 1), MapBlockType.MAP_CHANGE);

		Map<Point, MapBlockType> blocks2 = new HashMap<>();
		blocks2.put(new Point(1, 1), MapBlockType.WILD_ZONE);
		blocks2.put(new Point(2, 2), MapBlockType.WALK);
		blocks2.put(new Point(3, 3), MapBlockType.MAP_CHANGE);

		Pair<Integer, Integer> coord1 = new Pair<Integer, Integer>(1, 1);
		Pair<Integer, Integer> coord2 = new Pair<Integer, Integer>(2, 2);

		Pair<Pair<Integer, Integer>, MapBlockType> ddff = new Pair<>(coord1, MapBlockType.BORDER);

		List<Pair<Pair<Integer, Integer>, MapBlockType>> blockList = new ArrayList<>();
		blockList.add(ddff);

		//
		Set<String> npcsString = new HashSet<>();
		npcsString.add("nomeS");
		npcsString.add("nomeT");

		List<String> monsterSpeciesList = new ArrayList<>();
		monsterSpeciesList.add("nome3");
		monsterSpeciesList.add("nome4");

		Map<Pair<Integer, Integer>, Integer> eventLocation = new HashMap<>();
		eventLocation.put(new Pair<Integer, Integer>(1, 2), 2);
		eventLocation.put(new Pair<Integer, Integer>(1, 4), 1);
		eventLocation.put(new Pair<Integer, Integer>(1, 3), 3);

		Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> coordGnerali = new Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>(
				coord1, coord2);
		Pair<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>, Integer> mapcoord1 = new Pair<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>, Integer>(
				coordGnerali, 7);

		/*
		 * Map<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>, Integer>
		 * linkedMapDatadd = new HashMap<>(); linkedMapDatadd.put(coordGnerali, 2);
		 * 
		 * /*
		 * 
		 * 
		 * /* (int id, int minimumMonsterLevel, int maximumMonsterLevel, String name,
		 * List<Pair<Pair<Integer, Integer>, MapBlockType>> blocks, Set<String> npcs,
		 * List<String> wildMonsters, Map<Pair<Integer, Integer>, Integer>
		 * eventLocation, Map<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>,
		 * Integer> linkedMapData)
		 * 
		 */
		Map<Integer, Pair<Integer, Integer>> linkedMapsStartingPosition = new HashMap<>();
		Map<Pair<Integer, Integer>, Integer> linkedMapData = new HashMap<>();

		GameMapDataLoader gmdl1 = new GameMapDataLoader(1, 3, 30, "nomeMappa1", blocks, npcsString,
				monsterSpeciesList, eventLocation, linkedMapsStartingPosition, linkedMapData);

		String s1 = g.toJson(npc4);
		String s2 = g.toJson(npc1);
		
		
		dlc = new DataLoaderControllerImpl();
		System.out.println(dlc.getMonsters());
		
		/*
		System.out.println(s1);
		System.out.println("----");
		System.out.println(s2);
		// String cm
		// ='{"{"first":1,"second":1}":"WILD_ZONE","{"first":2,"second":1}":"MAP_CHANGE","{"first":3,"second":1}":"WALK"}';
		// Map<Pair<Integer, Integer>, MapBlockType> blo = g.fromJson(m, HashMap.class);

		// String hh = g.toJson(msl3);
		// System.out.println(hh);

		// GameMapDataLoader test = g.fromJson(hh, GameMapDataLoader.class);
		// System.out.println(dlc.getMonstersSpecies());
		// System.out.println(g.toJson(npc2));
		// System.out.println(g.toJson(npc3));
		// System.out.println(g.toJson(npc4));

		// List<GameEvent> eventList = dlc.getNpcs();

		/*
		 * for(GameEvent n : dlc.getEvents()) { if(n.getEventID()==2) {
		 * NpcBehaviorChanger m = (NpcBehaviorChanger) n; System.out.println(m); } }
		 */

		/*
		 * dlc = new DataLoaderControllerImpl(); dlc.loadMoves();
		 */
	}

}
