package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.Pair;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.gameevents.GameEvent;
import model.gameevents.MonsterGift;
import model.gameevents.NpcActivityChanger;
import model.gameevents.NpcPositionChanger;
import model.gameevents.NpcTextChanger;
import model.gameevents.NpcVisibilityChanger;
import model.gameevents.UniqueMonsterEvent;
import model.gameitem.CaptureItem;
import model.gameitem.EvolutionItem;
import model.gameitem.GameItem;
import model.gameitem.HealingItem;
import model.map.GameMapData;
import model.map.GameMapDataImpl;
import model.map.GameMapImpl;
import model.map.MapBlockType;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesBuilderImpl;
import model.monster.MonsterType;
import model.npc.NpcHealerImpl;
import model.npc.NpcMerchantImpl;
import model.npc.NpcSimple;
import model.npc.NpcSimpleImpl;
import model.npc.NpcTrainerImpl;
import model.player.Gender;
import model.player.Player;
import model.player.PlayerImpl;

public class DataControllerImpl implements DataController {
    private final static int MAXIMUM_BLOCK_IN_COLUMN = 20;
    private final static int MAXIMUM_BLOCK_IN_ROW = 20;
    private final static int INITIAL_GAME_MAP_ID = 1;
    private final static int HOUSE_GAME_MAP_ID = 2;
    private final static Pair<Integer, Integer> INITIAL_PLAYER_POSITION = new Pair<>(13, 13);
    private final List<Pair<Integer, Integer>> map_1_angles = List.of(new Pair<>(1, 2), new Pair<>(18, 2),
	    new Pair<>(18, 17), new Pair<>(1, 17));
    private List<Moves> moves = new ArrayList<Moves>();
    private List<GameItem> gameItems = new ArrayList<GameItem>();
    private List<MonsterSpecies> monsterSpecies = new ArrayList<>();
    private List<Monster> monster = new ArrayList<>();
    private List<NpcSimple> npcs = new ArrayList<>();
    private Player player;
    private List<GameMapData> gameMapData = new ArrayList<>();

    public DataControllerImpl() {
	createItems();
	createMoves();
	createMonsterSpecies();
	createMapData();
    }

    private void createItems() {
	GameItem monsterBall = new CaptureItem("Monster Ball", "This item allows the player to capture a monster");
	GameItem healingPotion = new HealingItem("Healing potion", "This item heal a monster for 50 HP");
	GameItem superHealingPotion = new HealingItem("Super healing potion", "This item heal a monster for 250 HP",
		250);
	GameItem ultraHealingPotion = new HealingItem("Ultra healing potion", "This item heal a monster for 400 HP");
	GameItem kracStone = new EvolutionItem("KracezStone", "This item evolves krados");

	this.gameItems.add(monsterBall);
	this.gameItems.add(healingPotion);
	this.gameItems.add(superHealingPotion);
	this.gameItems.add(ultraHealingPotion);
	this.gameItems.add(kracStone);
    }

    private void createMoves() {
	Moves m1 = new MovesImpl("Fire punch", 50, MonsterType.FIRE, 10);
	Moves m2 = new MovesImpl("Water spear", 20, MonsterType.WATER, 5);
	Moves m3 = new MovesImpl("Fire breath", 50, MonsterType.FIRE, 15);
	Moves m4 = new MovesImpl("Water kick", 30, MonsterType.WATER, 10);
	Moves m5 = new MovesImpl("Grass cut", 40, MonsterType.GRASS, 10);
	Moves m6 = new MovesImpl("Grass headbutt", 80, MonsterType.GRASS, 5);
	Moves m7 = new MovesImpl("Grass shoot", 20, MonsterType.GRASS, 15);
	Moves m8 = new MovesImpl("Water bomb", 60, MonsterType.WATER, 5);
	moves.add(m1);
	moves.add(m2);
	moves.add(m3);
	moves.add(m4);
	moves.add(m5);
	moves.add(m6);
	moves.add(m7);
	moves.add(m8);
    }

    private void createMonsterSpecies() {
	MonsterSpecies species1 = new MonsterSpeciesBuilderImpl().name("bibol").info("His name is bibol")
		.monsterType(MonsterType.FIRE).health(60).attack(20).defense(5).speed(10)
		.movesList(getMovesByType(MonsterType.FIRE)).build();
	MonsterSpecies species2 = new MonsterSpeciesBuilderImpl().name("greyfish").info("is a fish")
		.monsterType(MonsterType.WATER).health(100).attack(8).defense(5).speed(8)
		.movesList(getMovesByType(MonsterType.WATER)).build();
	MonsterSpecies species6 = new MonsterSpeciesBuilderImpl().name("kratres").info("cute thing3")
		.monsterType(MonsterType.GRASS).health(70).attack(25).defense(2).speed(5)
		.movesList(getMovesByType(MonsterType.GRASS)).build();
	MonsterSpecies species4 = new MonsterSpeciesBuilderImpl().name("krados").info("cute thing 2")
		.monsterType(MonsterType.GRASS).health(70).attack(25).defense(2).speed(5)
		.movesList(getMovesByType(MonsterType.GRASS)).evolution(species6).gameItem(gameItems.get(4)).build();
	MonsterSpecies species3 = new MonsterSpeciesBuilderImpl().name("kracez").info("cute thing")
		.monsterType(MonsterType.GRASS).health(70).attack(25).defense(2).speed(5)
		.movesList(getMovesByType(MonsterType.GRASS)).evolution(species4).evolutionLevel(10).build();
	MonsterSpecies species5 = new MonsterSpeciesBuilderImpl().name("yepicon").info("cute thing")
		.monsterType(MonsterType.GRASS).health(70).attack(25).defense(2).speed(5)
		.movesList(getMovesByType(MonsterType.GRASS)).build();
	MonsterSpecies puppin = new MonsterSpeciesBuilderImpl().name("puppin").info("This is puppin")
		.monsterType(MonsterType.FIRE).health(60).attack(20).defense(5).speed(10)
		.movesList(getMovesByType(MonsterType.FIRE)).build();
	MonsterSpecies ponix = new MonsterSpeciesBuilderImpl().name("ponix").info("This is ponix")
		.monsterType(MonsterType.FIRE).health(60).attack(20).defense(5).speed(10)
		.movesList(getMovesByType(MonsterType.GRASS)).build();

	monsterSpecies.add(species1);
	monsterSpecies.add(species2);
	monsterSpecies.add(species3);
	monsterSpecies.add(species5);
	monsterSpecies.add(puppin);
	monsterSpecies.add(ponix);

    }

    private void createMapData() {
	GameMapData mapData = new GameMapDataImpl(INITIAL_GAME_MAP_ID, 1, 10, "MAP1",
		getMapBlocksById(INITIAL_GAME_MAP_ID),
		monsterSpecies.stream().filter(i -> !i.getName().equals("ponix") && !i.getName().equals("puppin"))
			.collect(Collectors.toList()));
	GameMapData house = new GameMapDataImpl(2, 1, 99, "MAP2", getMapBlocksById(2), new ArrayList<>());
	mapData.addMapLink(house, new Pair<>(5, 10), new Pair<>(10, 14));
	house.addMapLink(mapData, new Pair<>(10, 16), new Pair<>(5, 12));
	house.addMapLink(mapData, new Pair<>(9, 16), new Pair<>(5, 12));
	this.gameMapData.add(mapData);
	this.gameMapData.add(house);
    }

    @Override
    public void setPlayer(String name, Gender gender, int trainerNumber) {
	cleanData();
	this.player = new PlayerImpl(name, gender, trainerNumber, INITIAL_PLAYER_POSITION,
		new GameMapImpl(gameMapData.stream().filter(e -> e.getMapId() == INITIAL_GAME_MAP_ID).findAny().get()));
	createMonsters();
	createNpcs();
	addNpcsToMap();
	giftTest();
	npcGhostTest();
	uniqueMonsterTest();
	developerTextTest();
	npcRunnerTest();
	this.player.setMoney(60000);
	this.player.addMonster(this.monster.get(this.monster.size() - 1));
    }

    private void cleanData() {
	this.monster = new ArrayList<>();
	this.npcs = new ArrayList<>();
    }

    private void createMonsters() {
	Monster kracez = new MonsterBuilderImpl().species(getSpeciesByName("kracez")).level(100)
		.movesList(getMovesByTypeWithPP(MonsterType.GRASS)).build();
	Monster greyfish = new MonsterBuilderImpl().species(getSpeciesByName("greyfish")).level(5)
		.movesList(getMovesByTypeWithPP(MonsterType.WATER)).build();
	Monster bibol = new MonsterBuilderImpl().species(getSpeciesByName("bibol")).level(100)
		.movesList(getMovesByTypeWithPP(MonsterType.FIRE)).build();
	Monster puppin = new MonsterBuilderImpl().species(getSpeciesByName("puppin")).level(10)
		.movesList(getMovesByTypeWithPP(MonsterType.FIRE)).build();
	Monster ponix = new MonsterBuilderImpl().species(getSpeciesByName("ponix")).level(50)
		.movesList(getMovesByTypeWithPP(MonsterType.GRASS)).isWild(true).build();

	this.monster.add(puppin);
	this.monster.add(ponix);
	this.monster.add(greyfish);
	this.monster.add(kracez);
	this.monster.add(bibol);

    }

    private void createNpcs() {
	String s = "This game was developed by ";
	List<String> stringList = new ArrayList<>(List.of("Barattini Luca", "Carafassi Samuele", "Castorina Andrea",
		"Guo Jia Hao", "Pierantoni Michael"));
	stringList = stringList.stream().map(name -> s + name).collect(Collectors.toList());
	NpcSimple npc1 = new NpcSimpleImpl("Unibo", stringList, new Pair<>(15, 15), true, true);
	NpcSimple npc2 = new NpcMerchantImpl("Steve", List.of("Wanna buy something?"), new Pair<>(9, 6), true, true,
		getInventory());
	NpcSimple trainer = new NpcTrainerImpl("Giorgio", List.of("Let's battle", "I lost..."), new Pair<>(1, 10), true,
		true, List.of(this.monster.get(2), this.monster.get(3)), false);
	NpcSimple healerNpc = new NpcHealerImpl("Mom", List.of("Let me heal your Pokaiju"), new Pair<>(10, 6),
		this.player, true, true);
	NpcSimple npcGift = new NpcSimpleImpl("Puppin", List.of("I will come with you"), new Pair<>(15, 15), true,
		true);
	NpcSimple npcGhost = new NpcSimpleImpl("Pippo", List.of("How did you find me?", "I was hidden very well"),
		new Pair<>(17, 17), false, true);
	NpcSimple npcRunner = new NpcSimpleImpl("Bolt", List.of("Catch me!"), map_1_angles.get(0), true, true);
	this.npcs.add(healerNpc);
	npcs.add(npc1);
	npcs.add(npc2);
	npcs.add(trainer);
	npcs.add(npcGift); 
	npcs.add(npcGhost);
	npcs.add(npcRunner);
    }

    private void addNpcsToMap() {
	getMapDataByMapID(INITIAL_GAME_MAP_ID).addNpc(getNpcByName("Unibo"));
	getMapDataByMapID(INITIAL_GAME_MAP_ID).addNpc(getNpcByName("Steve"));
	getMapDataByMapID(INITIAL_GAME_MAP_ID).addNpc(getNpcByName("Giorgio"));
	getMapDataByMapID(INITIAL_GAME_MAP_ID).addNpc(getNpcByName("Pippo"));
	getMapDataByMapID(INITIAL_GAME_MAP_ID).addNpc(getNpcByName("Bolt"));
	getMapDataByMapID(HOUSE_GAME_MAP_ID).addNpc(getNpcByName("Puppin"));
	getMapDataByMapID(HOUSE_GAME_MAP_ID).addNpc(getNpcByName("Mom"));
    }

    private void giftTest() {
	NpcSimple npc = getNpcByName("Puppin");
	GameEvent g = new MonsterGift(4185, true, false, true, List.of(monster.get(0)), player);
	g.addSuccessiveGameEvent(new NpcVisibilityChanger(4584, false, false, true, npc, false));
	g.addSuccessiveGameEvent(new NpcActivityChanger(4585, false, false, true, npc, false));
	npc.addGameEvent(g);
    }

    private void npcGhostTest() {
	NpcSimple npc = getNpcByName("Pippo");
	GameEvent g = new NpcVisibilityChanger(165, true, false, false, npc, true);
	g.addSuccessiveGameEvent(new NpcTextChanger(465, false, false, true, npc, 1));
	npc.addGameEvent(g);
    }

    private void uniqueMonsterTest() {
	GameEvent g = new UniqueMonsterEvent(INITIAL_GAME_MAP_ID, true, true, monster.get(1));
	this.gameMapData.stream().filter(i -> i.getMapId() == 1).findAny().get().addEventAt(g, new Pair<>(16, 15));
    }

    private void developerTextTest() {
	NpcSimple npc = getNpcByName("Unibo");
	GameEvent g1 = new NpcTextChanger(123, true, true, false, npc, 1);
	GameEvent g2 = new NpcTextChanger(124, false, true, false, npc, 2);
	GameEvent g3 = new NpcTextChanger(125, false, true, false, npc, 3);
	GameEvent g4 = new NpcTextChanger(126, false, true, false, npc, 4);
	GameEvent g5 = new NpcTextChanger(127, false, true, false, npc, 0);
	getNpcByName("Unibo").addGameEvent(g1);
	npc.addGameEvent(g2);
	npc.addGameEvent(g3);
	npc.addGameEvent(g4);
	npc.addGameEvent(g5);
	g1.addSuccessiveGameEvent(g2);
	g2.addSuccessiveGameEvent(g3);
	g3.addSuccessiveGameEvent(g4);
	g4.addSuccessiveGameEvent(g5);
	g5.addSuccessiveGameEvent(g1);
    }

    private void npcRunnerTest() {
	NpcSimple npc = getNpcByName("Bolt");
	GameEvent prevEv = null;
	for (int i = 0; i < this.map_1_angles.size(); i++) {
	    GameEvent currEv = new NpcPositionChanger(300100 + i, i == 0, true, false, npc,
		    this.map_1_angles.get((i + 1) % this.map_1_angles.size()));
	    npc.addGameEvent(currEv);
	    if (prevEv != null) {
		prevEv.addSuccessiveGameEvent(currEv);
	    }
	    prevEv = currEv;
	}
	prevEv.addSuccessiveGameEvent(
		npc.getGameEvents().stream().filter(e -> e.getEventID() == 300100).findAny().get());
    }

    private List<Moves> getMovesByType(MonsterType type) {
	return this.moves.stream().filter(move -> move.getType() == type).collect(Collectors.toList());
    }

    private List<Pair<Moves, Integer>> getMovesByTypeWithPP(MonsterType type) {
	return getMovesByType(type).stream().map(i -> new Pair<>(i, i.getPP())).collect(Collectors.toList());
    }

    private GameMapData getMapDataByMapID(int id) {
	return this.gameMapData.stream().filter(map -> map.getMapId() == id).findAny().get();
    }

    private Map<GameItem, Integer> getInventory() {
	int moltiplicator = 100;
	Map<GameItem, Integer> inventory = new HashMap<>();
	for (int i = 0; i < this.gameItems.size(); i++) {
	    inventory.put(this.gameItems.get(i), moltiplicator * (i + 1));
	}
	return inventory;
    }

    private Map<Pair<Integer, Integer>, MapBlockType> getMapBlocksById(int Id) {
	String filePath = "data/maps/permissions/map_permissions" + Id + ".dat";
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

    private NpcSimple getNpcByName(String name) {
	return this.npcs.stream().filter(npc -> npc.getName().equals(name)).findAny().get();
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
    public MonsterSpecies getSpeciesByName(String name) {
	return this.monsterSpecies.stream().filter(i -> i.getName().equals(name)).findAny().get();
    }

}
