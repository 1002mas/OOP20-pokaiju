package controller;
import java.util.List;


import model.gameitem.GameItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import model.battle.Moves;
import model.battle.MovesImpl;
import model.gameitem.GameItemImpl;
import model.map.GameMap;
import model.map.GameMapData;
import model.map.GameMapDataImpl;
import model.map.GameMapImpl;
import model.monster.Monster;
import model.monster.MonsterImpl;
import model.monster.MonsterSpecies;
import model.npc.NpcSimple;

public interface DataLoaderController {

	List<Moves> getMoves();
	
	List<NpcSimple> getNpcs();
	
	List<MonsterSpecies> getMonsterSpecies();
	
	List<GameItem> getGameEvolution();
}
