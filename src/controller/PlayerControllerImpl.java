package controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import model.Pair;
import model.map.GameMap;
import model.map.GameMapDataImpl;
import model.map.GameMapImpl;
import model.npc.NpcSimple;
import model.player.Player;
import model.player.PlayerImpl;

public class PlayerControllerImpl implements PlayerController {
	
	PlayerImpl player;
	GameMapDataImpl gameDataMap;
	GameMapImpl gameMap;
	
	public PlayerControllerImpl(PlayerImpl player, GameMapDataImpl gameDataMap, GameMapImpl gameMap) {
		this.player = player;
		this.gameDataMap = gameDataMap;
		this.gameMap = gameMap;
		
	}
	@Override
	public void saveData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean loadData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean movePlayer(Pair<Integer, Integer> coord) {
		
		if(gameMap.canChangeMap(coord) || gameMap.canPassThrough(coord)) {
			player.setPosition(coord);
			return true;
		} 
		return false;
	}

	@Override
	public  Optional<String> interact(Pair<Integer, Integer> coord) {	//----Problema battaglia-----
		if(gameDataMap.getNPC(coord).isPresent()) {
			Optional<String> result = gameDataMap.getNPC(coord).get().interactWith();
			return result;
		}
		return Optional.empty();
	}

	@Override
	public Pair<Integer, Integer> getPlayerPosition() {
		return this.player.getPosition();
	}
	
	//-----add useItems function-----
		
}
