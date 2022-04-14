package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controller.json.DataLoaderControllerImpl;
import controller.json.loader.GameItemLoader;
import controller.json.loader.MonsterLoader;
import controller.json.loader.MonsterSpeciesLoader;
import model.Pair;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.gameitem.GameItemTypes;
import model.monster.EvolutionType;
import model.monster.MonsterSpecies;
import model.monster.MonsterStats;
import model.monster.MonsterStatsImpl;
import model.monster.MonsterType;

public class PlayerControllerTest {

	
	
	public static void main(String[] args) throws InterruptedException
    {
		
		DataLoaderControllerImpl dlc;
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson g = gsonBuilder.create();;
		
		
		
		dlc = new DataLoaderControllerImpl();
		/*
		*/
		Moves m1 = new MovesImpl("Braciere", 50, MonsterType.FIRE, 10);
		Moves m2 = new MovesImpl("Attacco", 10, MonsterType.FIRE, 10);
		Moves m3 = new MovesImpl("Volo", 50, MonsterType.FIRE, 10);
		GameItemLoader gil1 = new GameItemLoader("item senza heal", "descrizione", GameItemTypes.EVOLUTIONTOOL, Optional.empty());
		GameItemLoader gil2 = new GameItemLoader("item senza heal", "descrizione", GameItemTypes.MONSTERBALL, Optional.empty());
		GameItemLoader gil3 = new GameItemLoader("item senza heal", "descrizione", GameItemTypes.HEAL, Optional.of(12));
		MonsterStats ms = new MonsterStatsImpl(10, 10, 10,10);
		List<Pair<String, Integer>> allMoves = new ArrayList<>();
		allMoves.add(new Pair<String, Integer>("Braciere",50));
		allMoves.add(new Pair<String, Integer>("Attacco",10));
		allMoves.add(new Pair<String, Integer>("Volo",50));
		
		
		MonsterSpeciesLoader msl1 = new MonsterSpeciesLoader(Optional.of("Optioanl:evolution"),"nome","info",MonsterType.FIRE,
				EvolutionType.ITEM, ms.getAttack(),ms.getAttack(), ms.getAttack(), ms.getAttack(),allMoves,Optional.of("evolutionItem"), Optional.of(2));
		MonsterSpeciesLoader msl2 = new MonsterSpeciesLoader(Optional.of("Optioanl:evolution"),"nome","info",MonsterType.FIRE,
				EvolutionType.LEVEL, ms.getAttack(),ms.getAttack(), ms.getAttack(), ms.getAttack(),allMoves,Optional.of("evolutionItem"), Optional.of(4));
		MonsterSpeciesLoader msl3 = new MonsterSpeciesLoader(Optional.of("Optioanl:evolution"),"nome","info",MonsterType.FIRE,
				EvolutionType.NONE,ms.getAttack(),ms.getAttack(), ms.getAttack(), ms.getAttack(),allMoves,Optional.of("evolutionItem"), Optional.of(2));
		
		MonsterLoader monster = new MonsterLoader(1, 10, 2, "nome3", allMoves, 1,1,1,1);
		//System.out.println(g.toJson(msl2));
		//System.out.println(g.toJson(msl1));
		//System.out.println(g.toJson(monster));
		System.out.println(dlc.getMonsters());
		
		/*
		dlc = new DataLoaderControllerImpl();
		dlc.loadMoves();
		*/
    }
	
}
