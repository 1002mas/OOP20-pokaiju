package controller;
import java.io.File;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import model.battle.Moves;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.gameitem.GameItemImpl;
import model.gameitem.*;
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

public interface DataLoaderController {

	List<Moves> getMoves();
	
	List<NpcSimple> getNpcSimple();
	
	List<MonsterSpecies> getMonsterSpecies();
}
