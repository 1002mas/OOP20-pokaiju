package controller.json;

import java.util.ArrayList;
import java.util.List;

import model.battle.Moves;
import model.gameevents.GameEvent;
import model.gameitem.GameItem;
import model.monster.Monster;
import model.monster.MonsterSpecies;
import model.npc.NpcSimple;
import model.player.Player;

public class DataControllerImpl implements DataLoaderController {
    private List<Moves> moves = new ArrayList<Moves>();
    private List<GameItem> gameItems = new ArrayList<GameItem>();
    private List<MonsterSpecies> monsterSpecies = new ArrayList<>();
    private List<Monster> monster = new ArrayList<>();
    private List<NpcSimple> npcs;
    private List<GameEvent> events;
    private Player player;

    public DataControllerImpl() {
	createGameEvent();
	createGameItem();
	createMonster();
	createMonsterSpecies();
	createNpc();
	createplayer();

    }

    public void createGameItem() {

    }

    public void createMonsterSpecies() {

    }

    public void createMonster() {

    }

    public void createNpc() {

    }

    public void createGameEvent() {

    }

    public void createplayer() {

    }

    @Override
    public List<Moves> getMoves() {
	// TODO Auto-generated method stub
	return this.moves;
    }

    @Override
    public List<NpcSimple> getNpcs() {
	// TODO Auto-generated method stub
	return this.npcs;
    }

    @Override
    public List<GameItem> getGameItems() {
	// TODO Auto-generated method stub
	return this.gameItems;
    }

    @Override
    public List<GameEvent> getEvents() {
	// TODO Auto-generated method stub
	return this.events;
    }

    @Override
    public List<MonsterSpecies> getMonstersSpecies() {
	// TODO Auto-generated method stub
	return this.monsterSpecies;
    }

    @Override
    public Player getPlayer() {
	// TODO Auto-generated method stub
	return this.player;
    }

    @Override
    public int getMaximumBlockInColumn() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int getMaximumBlockInRow() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public Moves getMove(String name) {
	// TODO Auto-generated method stub
	return this.moves.stream().filter(e -> e.getName().equals(name)).findFirst().get();
    }

    @Override
    public Monster getMonster(int id) {
	// TODO Auto-generated method stub
	return this.monster.stream().filter(e -> e.getId() == id).findFirst().get();
    }

    @Override
    public NpcSimple getNpc(String name) {
	// TODO Auto-generated method stub
	return this.npcs.stream().filter(e -> e.getName().equals(name)).findFirst().get();
    }

    @Override
    public GameItem getItem(String name) {
	// TODO Auto-generated method stub
	return this.gameItems.stream().filter(e -> e.getNameItem().equals(name)).findFirst().get();
    }

    @Override
    public GameEvent getEvent(int id) {
	// TODO Auto-generated method stub
	return this.events.stream().filter(e -> e.getEventID() == id).findFirst().get();
    }

}
