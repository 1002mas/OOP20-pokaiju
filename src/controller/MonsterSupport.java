package controller;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import model.battle.Moves;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.battle.MovesImpl;
import model.gameitem.GameItemImpl;
import model.gameitem.GameItems;
import model.map.GameMap;
import model.map.GameMapData;
import model.map.GameMapDataImpl;
import model.map.GameMapImpl;
import model.monster.Monster;
import model.monster.MonsterImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterStats;
import model.monster.MonsterType;
import model.npc.NpcSimple;
import model.npc.NpcSimpleImpl;
import model.npc.NpcTrainer;
import model.npc.TypeOfNpc;
import model.player.Player;
import model.player.PlayerImpl;

public class MonsterSupport {
	private int id;
	private int exp;
	private int level;
	private String species; //
	//iswild = false quando ist.
	private List<String> movesList; //--> lista<String> ;	(claasea parte)
	private MonsterStats stats;
	private MonsterStats maxStats;
	private Set<String> movesToLearn; // --> lista<String> 
	
	
	public MonsterSupport(int id,int exp,int level,String species, List<String> movesList, 
						  MonsterStats stats, MonsterStats maxStats,Set<String> movesToLearn) {
		this.id = id;
		this.exp =  exp;
		this.level = level;
		this.maxStats = maxStats;
		this.stats = stats;
		this.movesList = movesList;
		this.movesToLearn = movesToLearn;
		
	}
	
	
	public void getMonster() {
		
	}
	
	
}
