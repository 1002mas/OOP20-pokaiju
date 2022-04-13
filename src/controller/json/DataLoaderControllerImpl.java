package controller.json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controller.json.loader.GameItemLoader;
import controller.json.loader.GameMapDataLoader;
import controller.json.loader.MonsterGiftLoader;
import controller.json.loader.MonsterLoader;
import controller.json.loader.MonsterSpeciesLoader;
import controller.json.loader.NpcBehaviorChangerLoader;
import controller.json.loader.NpcMerchantLoader;
import controller.json.loader.NpcTrainerLoader;
import controller.json.loader.UniqueMonsterEventLoader;
import controller.json.saver.GameDataSaver;
import controller.json.saver.NpcDataSaver;
import controller.json.saver.PlayerSaver;
import model.Pair;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.gameevents.GameEvent;
import model.gameevents.MonsterGift;
import model.gameevents.NpcBehaviorChanger;
import model.gameevents.UniqueMonsterEvent;
import model.gameitem.EvolutionItem;
import model.gameitem.GameItem;
import model.gameitem.GameItemImpl;
import model.gameitem.HealingItem;
import model.map.GameMapData;
import model.map.GameMapDataImpl;
import model.monster.EvolutionType;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesBuilder;
import model.monster.MonsterSpeciesBuilderImpl;
import model.npc.NpcMerchant;
import model.npc.NpcMerchantImpl;
import model.npc.NpcSimple;
import model.npc.NpcSimpleImpl;
import model.npc.NpcTrainer;
import model.npc.NpcTrainerImpl;
import model.npc.TypeOfNpc;
import model.player.MonsterBox;
import model.player.MonsterBoxImpl;
import model.player.MonsterStorage;
import model.player.Player;
import model.player.PlayerImpl;

public class DataLoaderControllerImpl implements DataLoaderController {

	private static final String BASE_PATH = "res" + File.separator + "data" + File.separator;
	private static final String NPC_BASE_PATH = BASE_PATH + File.separator + "npc" + File.separator;
	private static final String SAVES_BASE_PATH = BASE_PATH + File.separator + "saves" + File.separator;
	private static final String EVENTS_BASE_PATH = SAVES_BASE_PATH + File.separator + "event" + File.separator;
	private static final String MOVES_PATH = BASE_PATH + "moves" + File.separator;

	private static final String NPC_SIMPLE_PATH = NPC_BASE_PATH + "npcSimple" + File.separator;
	private static final String NPC_TRAINER_PATH = NPC_BASE_PATH + "npcTrainer" + File.separator;
	private static final String NPC_MERCHANT_PATH = NPC_BASE_PATH + "npcMerchant" + File.separator;
	private static final String MONSTER_SPECIES_PATH = BASE_PATH + File.separator + "monsterSpecies" + File.separator;
	private static final String GAME_ITEM_PATH = BASE_PATH + File.separator + "gameItem" + File.separator + "evolution"
			+ File.separator;
	private static final String MONSTER_PATH = BASE_PATH + "monster" + File.separator;
	private static final String GAME_MAP_DATA_PATH = BASE_PATH + "gameMapData" + File.separator;
	private static final String GAME_DATA_PATH = SAVES_BASE_PATH + "gameData.json";
	private static final String MONSTER_GIFT_PATH = EVENTS_BASE_PATH + File.separator + "monsterGift" + File.separator;
	private static final String NPC_BEHAVIOR_PATH = EVENTS_BASE_PATH + File.separator + "npcBehavior" + File.separator;
	private static final String UNIQUE_MONSTER_EVENT_PATH = EVENTS_BASE_PATH + File.separator + "UniqueMonsterEvent"
			+ File.separator;
	private static final int MAXIMUM_BLOCK_IN_ROW = 20;
	private static final int MAXIMUM_BLOCK_IN_COLUMN = 20;
	// TODO java doc
	// TODO fix names
	// TODO check private
	// commit
	// TODO check boolean returns
	// commit
	// TODO npcHealer
	// commit
	// TODO Merchant add
	// commit
	// TODO SetList in loadGameMapData
	private GsonBuilder gsonBuilder;
	private Gson gson;
	private List<Moves> moves;
	private List<Monster> monster;
	private List<MonsterSpecies> monsterSpecies;
	private List<NpcSimple> npcs;
	private List<GameItem> gameItems;
	private List<GameMapData> gameMapData;
	private List<GameEvent> events;
	private List<String> npcDefeated;
	private Player player;
	private List<NpcDataSaver> npcData;
	private int idBuilder;
	private int idCurrentMap;
	private MonsterStorage monsterStorage;
	private List<Pair<Integer, Boolean>> eventsList;

	public DataLoaderControllerImpl() {
		this.gsonBuilder = new GsonBuilder();
		this.gson = gsonBuilder.create();
		loadMoves();
		loadGameItems();
		loadMonsterSpecies();
		loadMonsters();
		loadEvents();
		loadNpcs();
		loadGameMapData();
	}

	private void loadMoves() {
		File folder = new File(MOVES_PATH);
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();

			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				Moves m = gson.fromJson(stringRead, MovesImpl.class);
				this.moves.add(m);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void loadNpcs() {
		File folder;
		folder = new File(NPC_SIMPLE_PATH);
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();
			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				NpcSimple npc = gson.fromJson(stringRead, NpcSimpleImpl.class);
				this.npcs.add(npc);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		folder = new File(NPC_TRAINER_PATH);
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();
			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				NpcTrainerLoader n = gson.fromJson(stringRead, NpcTrainerLoader.class);
				NpcTrainer npc = new NpcTrainerImpl(n.getName(), n.getSentences(), n.getPosition(), n.getIsVisible(),
						n.getIsEnabled(), n.getTranslatedMonsterList(monster), n.getIsVisible());
				this.npcs.add(npc);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		folder = new File(NPC_MERCHANT_PATH);
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();
			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				NpcMerchantLoader n = gson.fromJson(stringRead, NpcMerchantLoader.class);
				NpcMerchant npc = new NpcMerchantImpl(n.getName(), n.getSentences(), n.getPosition(), n.getIsVisible(),
						n.getisEnabled(), n.getTranslatedGameItem(gameItems));
				this.npcs.add(npc);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private void loadMonsterSpecies() {
		File folder = new File(MONSTER_SPECIES_PATH);
		List<MonsterSpeciesLoader> mssList = new ArrayList<>();
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();

			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();

				MonsterSpeciesLoader mss = gson.fromJson(stringRead, MonsterSpeciesLoader.class);
				mssList.add(mss);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		int oldListSize = mssList.size();
		loadSimpleMonsters(mssList);

		while (!mssList.isEmpty() && oldListSize != mssList.size()) {
			oldListSize = mssList.size();
			loadEvolvableMonsters(mssList);
		}

	}

	private void loadEvolvableMonsters(List<MonsterSpeciesLoader> mssList) {

		List<MonsterSpeciesLoader> removedMonsters = new ArrayList<>();
		for (MonsterSpeciesLoader ms : mssList) {
			Optional<MonsterSpecies> evolution = this.monsterSpecies.stream()
					.filter(m -> m.getName().equals(ms.getEvolution().get())).findAny();
			if (evolution.isPresent()) {
				MonsterSpeciesBuilder mb = new MonsterSpeciesBuilderImpl().name(ms.getName()).info(ms.getInfo())
						.monsterType(ms.getMonsterType()).health(ms.getHealth()).attack(ms.getAttack())
						.defense(ms.getDefense()).speed(ms.getSpeed()).evolution(evolution.get())
						.allMoves(ms.getAllMoves(moves));
				if (ms.getEvolutionLevel().isPresent()) {
					mb = mb.evolutionLevel(ms.getEvolutionLevel().get());
				} else if (ms.getEvolutionGameItem(this.gameItems).isPresent()) {
					mb = mb.gameItem(ms.getEvolutionGameItem(this.gameItems).get());
				}
				this.monsterSpecies.add(mb.build());
				removedMonsters.add(ms);
			}

		}
		mssList.removeAll(removedMonsters);

	}

	private void loadSimpleMonsters(List<MonsterSpeciesLoader> mssList) {
		List<MonsterSpeciesLoader> removedMonsters = new ArrayList<>();
		for (MonsterSpeciesLoader ms : mssList) {
			if (ms.getEvolutionType().equals(EvolutionType.NONE)) {
				MonsterSpecies m = new MonsterSpeciesBuilderImpl().name(ms.getName()).info(ms.getInfo())
						.monsterType(ms.getMonsterType()).health(ms.getHealth()).attack(ms.getAttack())
						.defense(ms.getDefense()).speed(ms.getSpeed()).allMoves(ms.getAllMoves(moves)).build();
				this.monsterSpecies.add(m);
				removedMonsters.add(ms);
			}
		}
		mssList.removeAll(removedMonsters);
	}

	@Override
	public List<MonsterSpecies> getMonsterSpecies() {
		if (this.monsterSpecies == null) {
			this.monsterSpecies = new ArrayList<>();
			loadMonsterSpecies();
		}
		return this.monsterSpecies;
	}

	private void loadGameItems() {
		File folder = new File(GAME_ITEM_PATH);
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();

			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				GameItemLoader gis = gson.fromJson(stringRead, GameItemLoader.class);
				GameItem gameItem;
				switch (gis.getType()) {

				case EVOLUTIONTOOL:
					gameItem = new EvolutionItem(gis.getNameItem(), gis.getDescription());
					break;

				case HEAL:
					gameItem = new HealingItem(gis.getNameItem(), gis.getDescription(), gis.getHealing().get());
					break;

				case MONSTERBALL:
					gameItem = new GameItemImpl(gis.getNameItem(), gis.getDescription());
					break;
				default:
					throw new IllegalStateException();
				}

				this.gameItems.add(gameItem);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void loadMonsters() {
		File folder = new File(MONSTER_PATH);
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();
			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				MonsterLoader m = gson.fromJson(stringRead, MonsterLoader.class);
				Monster monster = new MonsterBuilderImpl().health(m.getHealth()).attack(m.getAttack())
						.defense(m.getDefense()).speed(m.getSpeed()).exp(m.getExp()).level(m.getLevel()).isWild(false)
						.species(m.getTranslatedMonsterSpecies(monsterSpecies)).movesList(m.getTranslatedMoves(moves))
						.build();
				this.monster.add(monster);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//TODO npc problem
	private void loadGameMapData() {
		File folder = new File(GAME_MAP_DATA_PATH);
		List<GameMapDataLoader> mapLoader = null;
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();

			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				mapLoader = new ArrayList<>();
				String stringRead = reader.readLine();
				GameMapDataLoader m = gson.fromJson(stringRead, GameMapDataLoader.class);
				GameMapData map = new GameMapDataImpl(m.getId(), m.getMinimumMonsterLevel(), m.getMaximumMonsterLevel(),
						m.getName(), m.getBlocks(), m.getTranslatedNpcs(npcs),
						m.getTranslatedtWildMonsters(monsterSpecies));
				this.gameMapData.add(map);
				Map<Pair<Integer, Integer>, GameEvent> eventLocationMap = m.getTranslatedEventLocation(this.events);
				for (Entry<Pair<Integer, Integer>, GameEvent> event : eventLocationMap.entrySet()) {
					map.addEventAt(event.getValue(), event.getKey());
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (this.gameMapData != null) {
			for (GameMapData mapData : this.gameMapData) {
				for (GameMapDataLoader mapInList : mapLoader) {
					if (mapInList.getId() == mapData.getMapId()) {
						Map<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>, GameMapData> linkedMapData = mapInList
								.getLinkedMapData(this.gameMapData);
						for (Entry<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>, GameMapData> map : linkedMapData
								.entrySet()) {
							mapData.addMapLink(map.getValue(), map.getKey().getFirst(), map.getKey().getSecond());
						}
					}

				}
			}

		}

	}

	private List<MonsterGiftLoader> loadMonsterGift() {
		File folder = new File(MONSTER_GIFT_PATH);
		List<MonsterGiftLoader> loadList = new ArrayList<>();
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();
			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				MonsterGiftLoader monsterGiftLoadSaver = gson.fromJson(stringRead, MonsterGiftLoader.class);
				MonsterGift monsterGift = new MonsterGift(monsterGiftLoadSaver.getId(),
						monsterGiftLoadSaver.getIsActive(), monsterGiftLoadSaver.getIsDeactivable(),
						monsterGiftLoadSaver.getIsToActiveImmediatly(),
						monsterGiftLoadSaver.getTranslatedMonsters(monster), this.player);
				this.events.add(monsterGift);
				loadList.add(monsterGiftLoadSaver);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return loadList;
	}

	private List<NpcBehaviorChangerLoader> loadNpcBehaviorChanger() {
		File folder = new File(NPC_BEHAVIOR_PATH);
		List<NpcBehaviorChangerLoader> loadList = new ArrayList<>();
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();
			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				NpcBehaviorChangerLoader npcBehaviorChangerLoadSaver = gson.fromJson(stringRead,
						NpcBehaviorChangerLoader.class);
				NpcBehaviorChanger npcBehaviorChanger = new NpcBehaviorChanger(npcBehaviorChangerLoadSaver.getId(),
						npcBehaviorChangerLoadSaver.getIsActive(), npcBehaviorChangerLoadSaver.getIsDeactivable(),
						npcBehaviorChangerLoadSaver.getIsToActiveImmediatly());
				this.events.add(npcBehaviorChanger);
				loadList.add(npcBehaviorChangerLoadSaver);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return loadList;
	}

	private List<UniqueMonsterEventLoader> loadUniqueMonsterEvent() {
		File folder = new File(UNIQUE_MONSTER_EVENT_PATH);
		List<UniqueMonsterEventLoader> loadList = new ArrayList<>();
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();
			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				UniqueMonsterEventLoader m = gson.fromJson(stringRead, UniqueMonsterEventLoader.class);
				UniqueMonsterEvent uniqueMonsterEvent = new UniqueMonsterEvent(m.getId(), m.getIsActive(),
						m.getIsToActiveImmediatly(), m.getTranslatedMonster(monster), this.player);
				this.events.add(uniqueMonsterEvent);
				loadList.add(m);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return loadList;
	}

	private List<GameEvent> translateGameEvents(List<Integer> listId) {
		List<GameEvent> eventToActivate = new ArrayList<>();
		for (int id : listId) {
			for (GameEvent event : this.events) {
				if (id == event.getEventID()) {
					eventToActivate.add(event);
				}
			}
		}
		return eventToActivate;
	}

	private GameEvent findGameEvent(int id) {
		for (GameEvent event : this.events) {
			if (event.getEventID() == id) {
				return event;
			}
		}
		return null;
	}

	private void fillEventList(List<Integer> eventsToActivate, List<Integer> eventsToDeactivate, int id) {
		GameEvent gameEvent = findGameEvent(id);
		List<GameEvent> eventsActivate = translateGameEvents(eventsToActivate);
		for (GameEvent event : eventsActivate) {
			gameEvent.addSuccessiveGameEvent(event);
		}
		List<GameEvent> eventsDeactivate = translateGameEvents(eventsToDeactivate);
		for (GameEvent event : eventsDeactivate) {
			gameEvent.addDependentGameEvent(event);
		}
	}

	private void loadEvents() {
		List<MonsterGiftLoader> monsterGiftList;
		List<NpcBehaviorChangerLoader> npcBehaviorChangerList;
		List<UniqueMonsterEventLoader> uniqueMonsterList;

		monsterGiftList = loadMonsterGift();
		npcBehaviorChangerList = loadNpcBehaviorChanger();
		uniqueMonsterList = loadUniqueMonsterEvent();

		for (MonsterGiftLoader monsterGift : monsterGiftList) {
			fillEventList(monsterGift.getEventsToActivate(), monsterGift.getEventsToDeactivate(), monsterGift.getId());
		}
		for (NpcBehaviorChangerLoader npcBehaviorChangerLoadSaver : npcBehaviorChangerList) {
			fillEventList(npcBehaviorChangerLoadSaver.getEventsToActivate(),
					npcBehaviorChangerLoadSaver.getEventsToDeactivate(), npcBehaviorChangerLoadSaver.getId());
		}
		for (UniqueMonsterEventLoader uniqueMonsterEventLoadSaver : uniqueMonsterList) {
			fillEventList(uniqueMonsterEventLoadSaver.getEventsToActivate(),
					uniqueMonsterEventLoadSaver.getEventsToDeactivate(), uniqueMonsterEventLoadSaver.getId());
		}
	}

	// LOAD DATA SAVED

	private List<Monster> translateMonsterId(List<Integer> monstersId) {
		List<Monster> monsters = new ArrayList<>();

		for (int id : monstersId) {
			for (Monster monster : this.monster) {
				if (id == monster.getId()) {
					monsters.add(monster);
				}
			}
		}

		return monsters;
	}

	@Override
	public void loadGameData() {
		File filePath = new File(GAME_DATA_PATH);
		try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String stringRead = reader.readLine();
			gameDataUnPack(gson.fromJson(stringRead, GameDataSaver.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean saveData(int idBuilder, int idCurrentMap, MonsterStorage monsterStorage, Player player) {
		GameDataSaver generalDataLoadSaver;
		List<Pair<String, List<Integer>>> boxData = new ArrayList<>();
		List<Integer> monsterId = new ArrayList<>();
		Map<String, Integer> gameItemsName = new HashMap<>();

		for (Pair<String, List<Monster>> list : monsterStorage.getMonsterList()) {
			List<Integer> monstersId = new ArrayList<>();
			for (Monster monster : list.getSecond()) {
				monstersId.add(monster.getId());
			}
			boxData.add(new Pair<String, List<Integer>>(list.getFirst(), monstersId));
		}

		for (Monster monster : player.getAllMonsters()) {
			monsterId.add(monster.getId());
		}
		for (Entry<GameItem, Integer> gameItem : player.getAllItems().entrySet()) {
			gameItemsName.put(gameItem.getKey().getNameItem(), gameItem.getValue());
		}

		generalDataLoadSaver = new GameDataSaver(this.npcDefeated, this.idBuilder, boxData, this.idCurrentMap,
				this.npcData, this.eventsList, new PlayerSaver(player.getName(), player.getGender(),
						player.getTrainerNumber(), player.getPosition(), monsterId, gameItemsName, player.getMoney()));

		String dataJson = gson.toJson(generalDataLoadSaver);

		try (BufferedWriter bf = new BufferedWriter(new FileWriter(GAME_DATA_PATH))) {
			bf.write(dataJson);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	private void gameDataUnPack(GameDataSaver gameData) {
		translatePlayerSaver(gameData.getPlayerSaver());
		translateNpcData(gameData.getNpcDatatSaver());
		this.idCurrentMap = gameData.getMapId();
		this.idBuilder = gameData.getIdBuilder();
		translateNpcDefeated(gameData.getNpcDefeatedList());
		List<MonsterBox> monsterBox = gameData.getTranslatedMonsterBox(this.monster);
		// save in mosterStorage
		this.eventsList = gameData.getEventsList();
		gameData.setTranslatedEvents(this.events);
	}

	private void translateMonsterBox(List<Pair<String, List<Integer>>> boxData) {
		List<MonsterBox> monsterBox = new ArrayList<>();
		for (Pair<String, List<Integer>> box : boxData) {
			List<Monster> monsters = translateMonsterId(box.getSecond());
			monsterBox.add(new MonsterBoxImpl(box.getFirst(), monsters));
		}
		// this.monsterStorage;
		// TODO fix
	}

	private void translatePlayerSaver(PlayerSaver playerSaver) {
		List<Monster> monsters;
		Map<GameItem, Integer> items;
		this.player = new PlayerImpl(playerSaver.getName(), playerSaver.getGender(), playerSaver.getTrainerNumber(),
				playerSaver.getPosition());
		monsters = playerSaver.getTranslatedMonster(this.monster);
		items = playerSaver.getTranslatedGameItems(this.gameItems);

		for (Monster monster : monsters) {
			this.player.addMonster(monster);
		}
		for (Entry<GameItem, Integer> item : items.entrySet()) {
			this.player.addItem(item.getKey(), item.getValue());
		}

	}

	// TODO Fix in good code
	private void translateNpcDefeated(List<String> names) {
		for (NpcSimple npc : this.npcs) {
			for (String name : names) {
				if (npc.getTypeOfNpc() == TypeOfNpc.TRAINER && npc.getName().equals(name)) {
					NpcTrainer npcTrainer = (NpcTrainer) npc;
					npcTrainer.setDefeated(true);
					npc = npcTrainer;
				}
			}
		}
	}

	private void translateNpcData(List<NpcDataSaver> list) {
		for (NpcDataSaver npcDataSaver : list) {
			for (NpcSimple npc : this.npcs) {
				if (npc.getName().equals(npcDataSaver.getName())) {
					npc.setDialogueText(npcDataSaver.getIdSentence());
					npc.setEnabled(npcDataSaver.isEnable());
					npc.setVisible(npcDataSaver.isVisible());
				}
			}
		}
	}

	@Override
	public boolean gameSaveExist() {
		File playerDataFile = new File(GAME_DATA_PATH);
		return playerDataFile.exists();

	}

	@Override
	public boolean deleteData() {
		File file;
		if (gameSaveExist()) {
			file = new File(GAME_DATA_PATH);
			if (file.delete()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Moves> getMoves() {
		if (this.moves == null) {
			moves = new ArrayList<>();
			this.loadMoves();
		}
		return this.moves;
	}

	@Override
	public Moves getMove(String name) {
		for (Moves move : this.moves) {
			if (move.getName().equals(name)) {
				return move;
			}
		}
		return null;
	}

	@Override
	public List<Monster> getMonsters() {
		if (this.monster == null) {
			this.monster = new ArrayList<>();
			loadMonsters();

		}
		return this.monster;
	}

	@Override
	public Monster getMonster(int id) {
		for (Monster monster : this.monster) {
			if (id == monster.getId()) {
				return monster;
			}
		}
		return null;
	}

	@Override
	public List<MonsterSpecies> getMonstersSpecies() {
		if (this.monsterSpecies == null) {
			this.monsterSpecies = new ArrayList<>();
			loadMonsterSpecies();
		}
		return this.monsterSpecies;
	}

	@Override
	public List<NpcSimple> getNpcs() {
		if (this.npcs == null) {
			npcs = new ArrayList<>();
			this.loadNpcs();
		}
		return this.npcs;
	}

	@Override
	public NpcSimple getNpc(String name) {
		for (NpcSimple npc : this.npcs) {
			if (name.equals(npc.getName())) {
				return npc;
			}
		}
		return null;
	}

	@Override
	public List<GameItem> getGameItems() {
		if (this.gameItems == null) {
			this.gameItems = new ArrayList<>();
			loadGameItems();

		}
		return this.gameItems;
	}

	@Override
	public GameItem getItem(String name) {
		for (GameItem item : this.gameItems) {
			if (item.getNameItem().equals(name)) {
				return item;
			}
		}
		return null;
	}

	@Override
	public List<GameMapData> getGameMapData() {
		if (this.gameMapData == null) {
			this.gameMapData = new ArrayList<>();
			loadGameMapData();
		}
		return this.gameMapData;
	}

	@Override
	public GameMapData getGameMapData(int id) {
		for (GameMapData gameMapData : this.gameMapData) {
			if (id == gameMapData.getMapId()) {
				return gameMapData;
			}
		}
		return null;
	}

	@Override
	public List<GameEvent> getEvents() {
		if (this.events == null) {
			this.events = new ArrayList<>();
			loadEvents();
		}
		return this.events;
	}

	@Override
	public GameEvent getEvent(int id) {
		for (GameEvent event : this.events) {
			if (id == event.getEventID()) {
				return event;
			}
		}
		return null;
	}

	@Override
	public List<String> getNpcsDefeated() {
		return this.npcDefeated;
	}

	@Override
	public void addTrainerDefeated(String npcName) {
		if (!this.npcDefeated.contains(npcName)) {
			this.npcDefeated.add(npcName);
		}
	}

	@Override
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public List<NpcDataSaver> getNpcData() {
		return this.npcData;
	}

	// TODO
	@Override
	public void addNpcData(String name, int idSentence) {
		for (NpcSimple npcSimple : this.npcs) {
			if (npcSimple.getName().equals(name)) {
				npcSimple.setDialogueText(idSentence);
			}
		}
	}

	@Override
	public int getIdBuilder() {
		return this.idBuilder;
	}

	@Override
	public void setIdBuilder(int idBuilder) {
		this.idBuilder = idBuilder;
	}

	@Override
	public int getIdCurrentMap() {
		return this.idCurrentMap;
	}

	@Override
	public void setIdCurrentMap(int idCurrentMap) {
		this.idCurrentMap = idCurrentMap;
	}

	@Override
	public MonsterStorage monsterStorage() {
		return this.monsterStorage;
	}

	@Override
	public int getMaximumBlockInColumn() {
		return MAXIMUM_BLOCK_IN_COLUMN;
	}

	@Override
	public int getMaximumBlockInRow() {
		return MAXIMUM_BLOCK_IN_ROW;
	}

}
