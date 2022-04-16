package controller.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import model.Pair;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.gameevents.GameEvent;
import model.gameitem.EvolutionItem;
import model.gameitem.GameItem;
import model.gameitem.GameItemImpl;
import model.gameitem.HealingItem;
import model.map.GameMapData;
import model.map.GameMapDataImpl;
import model.map.GameMapImpl;
import model.map.MapBlockType;
import model.monster.Monster;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesBuilderImpl;
import model.monster.MonsterType;
import model.npc.NpcHealerImpl;
import model.npc.NpcMerchantImpl;
import model.npc.NpcSimple;
import model.npc.NpcSimpleImpl;
import model.player.Gender;
import model.player.Player;
import model.player.PlayerImpl;

public class DataControllerImpl implements DataLoaderController {
    private final static int MAXIMUM_BLOCK_IN_COLUMN = 20;
    private final static int MAXIMUM_BLOCK_IN_ROW = 20;
    private final static int INITIAL_GAME_MAP_ID = 1;
    private final static Pair<Integer, Integer> INITIAL_PLAYER_POSITION = new Pair<>(13, 13);
    private List<Moves> moves = new ArrayList<Moves>();
    private List<GameItem> gameItems = new ArrayList<GameItem>();
    private List<MonsterSpecies> monsterSpecies = new ArrayList<>();
    private List<Monster> monster = new ArrayList<>();
    private List<NpcSimple> npcs = new ArrayList<>();
    private List<GameEvent> events = new ArrayList<>();
    private Player player;
    private List<GameMapData> gameMapData = new ArrayList<>();

    public DataControllerImpl() {

	NpcSimple npc1 = new NpcSimpleImpl("Bob", List.of("Hola", "Hello", "Konichiwa"), new Pair<>(15, 15), true,
		true);
	NpcSimple npc2 = new NpcMerchantImpl("Steve", List.of("Wanna buy something?"), new Pair<>(9, 6), true, true,
		getInventory());

	npcs.add(npc1);
	npcs.add(npc2);

	Moves m1 = new MovesImpl("Fire punch", 50, MonsterType.FIRE, 10);
	Moves m2 = new MovesImpl("Water spear", 20, MonsterType.WATER, 5);
	Moves m3 = new MovesImpl("Fire breath", 50, MonsterType.FIRE, 15);
	Moves m4 = new MovesImpl("Water kick", 30, MonsterType.WATER, 10);
	Moves m5 = new MovesImpl("Grass cut", 40, MonsterType.GRASS, 10);
	Moves m6 = new MovesImpl("Grass headbutt", 80, MonsterType.GRASS, 5);
	Moves m7 = new MovesImpl("Grass shoot", 20, MonsterType.GRASS, 15);
	Moves m8 = new MovesImpl("Water bomb", 60, MonsterType.WATER, 5);

	MonsterSpecies species1 = new MonsterSpeciesBuilderImpl().name("Bibol").info("His name is bibol")
		.monsterType(MonsterType.FIRE).health(60).attack(20).defense(5).speed(10).movesList(List.of(m1, m3, m8))
		.build();

	MonsterSpecies species2 = new MonsterSpeciesBuilderImpl().name("Greyfish").info("is a fish")
		.monsterType(MonsterType.WATER).health(100).attack(8).defense(5).speed(8).movesList(List.of(m2, m4, m8))
		.build();

	MonsterSpecies species3 = new MonsterSpeciesBuilderImpl().name("Kracez").info("cute thing")
		.monsterType(MonsterType.GRASS).health(70).attack(25).defense(2).speed(5).movesList(List.of(m5, m6, m7))
		.build();

	monsterSpecies.add(species1);
	monsterSpecies.add(species2);
	monsterSpecies.add(species3);

	GameMapData mapData = new GameMapDataImpl(INITIAL_GAME_MAP_ID, 1, 10, "MAP1",
		getMapBlocksById(INITIAL_GAME_MAP_ID), new HashSet<>(npcs), monsterSpecies);

	this.gameMapData.add(mapData);
    }

    @Override
    public void setPlayer(String name, String gender, int trainerNumber) {
	gender = gender.toLowerCase();
	Gender playerGender = null;
	for (Gender g : Gender.values()) {
	    if (g.toString().equals(gender)) {
		playerGender = g;
	    }
	}
	this.player = new PlayerImpl(name, playerGender, trainerNumber, INITIAL_PLAYER_POSITION,
		new GameMapImpl(gameMapData.stream().filter(e -> e.getMapId() == INITIAL_GAME_MAP_ID).findAny().get()));
	setHealerNpcAndMap();
    }

    private void setHealerNpcAndMap() {
	NpcSimple healerNpc = new NpcHealerImpl("Mom", List.of("I can heal your team"), new Pair<>(10, 5), this.player,
		true, true);
	GameMapData house = new GameMapDataImpl(2, 1, 99, "MAP2", getMapBlocksById(2),
		new HashSet<>(List.of(healerNpc)), new ArrayList<>());
	this.gameMapData.get(0).addMapLink(house, new Pair<>(5, 10), new Pair<>(10, 10));
	house.addMapLink(this.gameMapData.get(0), new Pair<>(10, 15), new Pair<>(5, 12));
	this.gameMapData.add(house);
	this.npcs.add(healerNpc);
    }

    private Map<GameItem, Integer> getInventory() {
	Map<GameItem, Integer> sellingItems = new HashMap<GameItem, Integer>();
	GameItem monsterBall = new GameItemImpl("Monster Ball", "This item allows the player to capture a monster");
	GameItem healingPotion = new HealingItem("Healing potion", "This item heal a monster for 50 HP");
	GameItem superHealingPotion = new HealingItem("Super healing potion", "This item heal a monster for 250 HP",
		250);
	GameItem ultraHealingPotion = new HealingItem("Ultra healing potion",
		"This item heal a monster for 400 HP,400");
	GameItem EvolvingItem = new EvolutionItem("Evolving item", "This item evolves a monster");

	this.gameItems.add(monsterBall);
	this.gameItems.add(healingPotion);
	this.gameItems.add(superHealingPotion);
	this.gameItems.add(ultraHealingPotion);
	this.gameItems.add(EvolvingItem);

	for (GameItem item : this.gameItems) {
	    sellingItems.put(item, 250);
	}
	return sellingItems;
    }

    private Map<Pair<Integer, Integer>, MapBlockType> getMapBlocksById(int Id) {
	String filePath = "data" + File.separator + "maps" + File.separator + "permissions" + File.separator
		+ "map_permissions" + Id + ".dat";
	InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
	Map<Pair<Integer, Integer>, MapBlockType> map = new HashMap<>();
	try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));) {
	    String line;
	    int x = 0;
	    int y = 0;
	    while ((line = reader.readLine()) != null) {
		int blockId = Integer.parseInt(line);
		switch (blockId) {
		case 0:
		    map.put(new Pair<>(x, y), MapBlockType.MAP_CHANGE);
		    break;
		case 1:
		    map.put(new Pair<>(x, y), MapBlockType.WALK);
		    break;
		case 2:
		    map.put(new Pair<>(x, y), MapBlockType.OBSTACLE);
		    break;
		case 3:
		    map.put(new Pair<>(x, y), MapBlockType.WILD_ZONE);
		    break;
		default:
		    break;
		}
		y = y + x / (this.getMaximumBlockInColumn() - 1);
		x = (x + 1) % this.getMaximumBlockInColumn();

	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return map;
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
	return MAXIMUM_BLOCK_IN_COLUMN;
    }

    @Override
    public int getMaximumBlockInRow() {
	return MAXIMUM_BLOCK_IN_ROW;
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
