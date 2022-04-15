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
	return this.moves;
    }

    @Override
    public List<NpcSimple> getNpcs() {
	return this.npcs;
    }

    @Override
    public List<GameItem> getGameItems() {
	return this.gameItems;
    }

    @Override
    public List<GameEvent> getEvents() {
	return this.events;
    }

    @Override
    public List<MonsterSpecies> getMonstersSpecies() {
	return this.monsterSpecies;
    }

    @Override
    public Player getPlayer() {
	return this.player;
    }

    @Override
    public int getMaximumBlockInColumn() {
	return 0;
    }

    @Override
    public int getMaximumBlockInRow() {
	return 0;
    }

    @Override
    public Moves getMove(String name) {
	return this.moves.stream().filter(e -> e.getName().equals(name)).findFirst().get();
    }

    @Override
    public Monster getMonster(int id) {
	return this.monster.stream().filter(e -> e.getId() == id).findFirst().get();
    }

    @Override
    public NpcSimple getNpc(String name) {
	return this.npcs.stream().filter(e -> e.getName().equals(name)).findFirst().get();
    }

    @Override
    public GameItem getItem(String name) {
	return this.gameItems.stream().filter(e -> e.getNameItem().equals(name)).findFirst().get();
    }

    @Override
    public GameEvent getEvent(int id) {
	return this.events.stream().filter(e -> e.getEventID() == id).findFirst().get();
    }

}
