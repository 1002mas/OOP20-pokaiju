package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Pair;
import model.battle.Moves;
import model.map.GameMap;
import model.map.GameMapDataImpl;
import model.map.GameMapImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterStats;
import model.npc.NpcSimple;
import model.player.Gender;
import model.player.Player;
import model.player.PlayerImpl;
import test.InterfaceAdapter;

public class PlayerControllerImpl implements PlayerController {

    PlayerImpl player;
    GameMapDataImpl gameDataMap;
    GameMapImpl gameMap;
    GsonBuilder gsonBuilder;
    Gson gson;

    public PlayerControllerImpl(PlayerImpl player, GameMapDataImpl gameDataMap, GameMapImpl gameMap) {
	this.player = player;
	this.gameDataMap = gameDataMap;
	this.gameMap = gameMap;
	this.gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(MonsterSpecies.class, new TypeAdapterController());
	gsonBuilder.registerTypeAdapter(Moves.class, new TypeAdapterController());
	gsonBuilder.registerTypeAdapter(MonsterStats.class, new TypeAdapterController());
	this.gson = gsonBuilder.create();
    }

    @Override
    public void saveData() {

	String playerJson = gson.toJson(player);

	try (BufferedWriter bf = new BufferedWriter(new FileWriter("PlayerData.json"))) {
	    bf.write(playerJson);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	// ---ADD NPC saver---
    }

    @Override
    public boolean loadData() {
	String playerNameFile = "PlayerData.json";
	File playerDataFile = new File(playerNameFile);
	if (playerDataFile.exists()) {

	    try (final BufferedReader reader = new BufferedReader(new FileReader("pirupiru.json"))) {
		String stringReadPlayer;
		while ((stringReadPlayer = reader.readLine()) != null) {
		    Player Loadedplayer = gson.fromJson(stringReadPlayer, PlayerImpl.class);
		}

	    } catch (IOException e) {
		// e.printStackTrace();
		return false;
	    }
	    // ---ADD NPC loader---

	    return true;
	}
	return false;
    }

    @Override
    public boolean movePlayer(Pair<Integer, Integer> coord) {

	if (gameMap.canChangeMap(coord) || gameMap.canPassThrough(coord)) {
	    player.setPosition(coord);
	    return true;
	}
	return false;
    }

    @Override
    public Optional<String> interact(Pair<Integer, Integer> coord) { // ----Problema battaglia-----
	if (gameDataMap.getNPC(coord).isPresent()) {
	    Optional<String> result = gameDataMap.getNPC(coord).get().interactWith();
	    return result;
	}
	return Optional.empty();
    }

    @Override
    public Pair<Integer, Integer> getPlayerPosition() {
	return this.player.getPosition();
    }

    public PlayerImpl getPlayer() {
	return player;
    }

    public void createNewPlayer(String name, Gender gender, int trainerNumber) {
	this.player = new PlayerImpl(name, gender, trainerNumber, new Pair<Integer, Integer>(0, 0));
    }

    // -----add useItems function-----

}
