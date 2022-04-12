package controller.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
	private static final String PLAYER_DATA_PATH = SAVES_BASE_PATH + "player.json";
	private static final String GAME_DATA_PATH = SAVES_BASE_PATH + "gameData.json";
	private static final String NPC_DATA_PATH = SAVES_BASE_PATH + "npcData.json";
	private static final String MONSTER_GIFT_PATH = EVENTS_BASE_PATH + File.separator + "monsterGift" + File.separator;
	private static final String NPC_BEHAVIOR_PATH = EVENTS_BASE_PATH + File.separator + "npcBehavior" + File.separator;
	private static final String UNIQUE_MONSTER_EVENT_PATH = EVENTS_BASE_PATH + File.separator + "UniqueMonsterEvent"
			+ File.separator;
	// TODO java doc
	// TODO fix names
	// TODO add NpcData and NpcDefeated data loaded in this.npcs
	// TODO return loaded data
	private GsonBuilder gsonBuilder;
	private Gson gson;
	private List<Moves> moves;
	private List<Monster> monster;
	private List<MonsterSpecies> monsterSpecies;
	private List<NpcSimple> npcs;
	private List<GameItem> gameItems;
	private List<GameMapData> gameMapData;
	private List<NpcSimple> npcDefeated;
	private GeneralDataLoadSaver gameData;
	private NpcDataLoadSaver npcData;
	private Player player;
	private List<GameEvent> gameEvents;
	private List<GameEvent> events;
	// TODO load data in this.idBuilder
	private int idbuilder;

	public DataLoaderControllerImpl() {
		this.gsonBuilder = new GsonBuilder();
		this.gson = gsonBuilder.create();
		loadMoves();
		loadGameItems();
		loadMonsterSpecies();
		loadMonsters();
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

	@Override
	public List<Moves> getMoves() {
		if (this.moves == null) {
			moves = new ArrayList<>();
			this.loadMoves();
		}
		return this.moves;
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
				NpcTrainerLoadSaver n = gson.fromJson(stringRead, NpcTrainerLoadSaver.class);
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
				NpcMerchantLoadSaver n = gson.fromJson(stringRead, NpcMerchantLoadSaver.class);
				NpcMerchant npc = new NpcMerchantImpl(n.getName(), n.getSentences(), n.getPosition(), n.getIsVisible(),
						n.getisEnabled(), n.getTranslatedGameItem(gameItems));
				this.npcs.add(npc);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public List<NpcSimple> getNpcs() {
		if (this.npcs == null) {
			npcs = new ArrayList<>();
			this.loadNpcs();
		}
		return this.npcs;
	}

	private void loadMonsterSpecies() {
		File folder = new File(MONSTER_SPECIES_PATH);
		List<MonsterSpeciesLoadSaver> mssList = new ArrayList<>();
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();

			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();

				MonsterSpeciesLoadSaver mss = gson.fromJson(stringRead, MonsterSpeciesLoadSaver.class);
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

	private void loadEvolvableMonsters(List<MonsterSpeciesLoadSaver> mssList) {

		List<MonsterSpeciesLoadSaver> removedMonsters = new ArrayList<>();
		for (MonsterSpeciesLoadSaver ms : mssList) {
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

	private void loadSimpleMonsters(List<MonsterSpeciesLoadSaver> mssList) {
		List<MonsterSpeciesLoadSaver> removedMonsters = new ArrayList<>();
		for (MonsterSpeciesLoadSaver ms : mssList) {
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
				GameItemLoadSaver gis = gson.fromJson(stringRead, GameItemLoadSaver.class);
				GameItem gameItem;
				switch (gis.getType()) {

				case EVOLUTIONTOOL:
					gameItem = new EvolutionItem(gis.getNameItem(), gis.getQuantity(), gis.getDescription());
					break;

				case HEAL:
					gameItem = new HealingItem(gis.getNameItem(), gis.getQuantity(), gis.getDescription(),
							gis.getHealing().get());
					break;

				case MONSTERBALL:
					gameItem = new GameItemImpl(gis.getNameItem(), gis.getQuantity(), gis.getDescription());
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

	@Override
	public List<GameItem> getGameItems() {
		if (this.gameItems == null) {
			this.gameItems = new ArrayList<>();
			loadGameItems();

		}
		return this.gameItems;
	}

	private void loadMonsters() {
		File folder = new File(MONSTER_PATH);
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();
			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				MonsterLoadSaver m = gson.fromJson(stringRead, MonsterLoadSaver.class);
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

	@Override
	public List<Monster> getMonsters() {
		if (this.monster == null) {
			this.monster = new ArrayList<>();
			loadMonsters();

		}
		return this.monster;
	}

	private void loadGameMapData() {
		File folder = new File(GAME_MAP_DATA_PATH);
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();

			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				GameMapDataLoadSaver m = gson.fromJson(stringRead, GameMapDataLoadSaver.class);
				GameMapData map = new GameMapDataImpl(m.getId(), m.getMinimumMonsterLevel(), m.getMaximumMonsterLevel(),
						m.getName(), m.getBlocks(), m.getTranslatedNpcs(npcs),
						m.getTranslatedtWildMonsters(monsterSpecies));
				this.gameMapData.add(map);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<GameMapData> getGameMapData() {
		if (this.gameMapData == null) {
			this.gameMapData = new ArrayList<>();
			loadGameMapData();
		}
		return this.gameMapData;
	}

	private void loadPlayerData() {
		File filePath = new File(PLAYER_DATA_PATH);
		try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String stringRead = reader.readLine();
			PlayerLoadSaver playerLoadSaver = gson.fromJson(stringRead, PlayerLoadSaver.class);
			this.player = new PlayerImpl(playerLoadSaver.getName(), playerLoadSaver.getGender(),
					playerLoadSaver.getTrainerNumber(), playerLoadSaver.getPosition());
			this.player.setMoney(playerLoadSaver.getMoney());
			for (Monster monster : playerLoadSaver.getTranslatedMonster(this.monster)) {
				this.player.addMonster(monster);
			}

			for (GameItem gameItem : playerLoadSaver.getTranslatedGameItems(this.gameItems)) {
				this.player.addItem(gameItem);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean playerDataExist() {
		File playerDataFile = new File(PLAYER_DATA_PATH);
		return playerDataFile.exists();

	}

	private void loadGeneralGameData() {
		File filePath = new File(GAME_DATA_PATH);
		try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String stringRead = reader.readLine();
			// TODO load events in box
			this.gameData = gson.fromJson(stringRead, GeneralDataLoadSaver.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public GeneralDataLoadSaver getGeneralGameData() {
		return this.gameData;
	}

	private void loadNpcData() {
		File filePath = new File(NPC_DATA_PATH);
		try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String stringRead = reader.readLine();
			this.npcData = gson.fromJson(stringRead, NpcDataLoadSaver.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public NpcDataLoadSaver getNpcData() {
		return this.npcData;
	}

	private List<MonsterGiftLoadSaver> loadMonsterGift() {
		File folder = new File(MONSTER_GIFT_PATH);
		List<MonsterGiftLoadSaver> loadList = new ArrayList<>();
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();
			// TODO create getter
			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				MonsterGiftLoadSaver monsterGiftLoadSaver = gson.fromJson(stringRead, MonsterGiftLoadSaver.class);
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

	private List<NpcBehaviorChangerLoadSaver> loadNpcBehaviorChanger() {
		File folder = new File(NPC_BEHAVIOR_PATH);
		List<NpcBehaviorChangerLoadSaver> loadList = new ArrayList<>();
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();
			// TODO create getter
			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				NpcBehaviorChangerLoadSaver npcBehaviorChangerLoadSaver = gson.fromJson(stringRead,
						NpcBehaviorChangerLoadSaver.class);
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

	private List<UniqueMonsterEventLoadSaver> loadUniqueMonsterEvent() {
		File folder = new File(UNIQUE_MONSTER_EVENT_PATH);
		List<UniqueMonsterEventLoadSaver> loadList = new ArrayList<>();
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();
			// TODO create getter
			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				UniqueMonsterEventLoadSaver m = gson.fromJson(stringRead, UniqueMonsterEventLoadSaver.class);
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

	private void loadEvents() {
		List<MonsterGiftLoadSaver> monsterGiftList;
		List<NpcBehaviorChangerLoadSaver> npcBehaviorChangerList;
		List<UniqueMonsterEventLoadSaver> uniqueMonsterList;

		monsterGiftList = loadMonsterGift();
		npcBehaviorChangerList = loadNpcBehaviorChanger();
		uniqueMonsterList = loadUniqueMonsterEvent();
		// Compact repeated code
		for (MonsterGiftLoadSaver monsterGift : monsterGiftList) {

			GameEvent gameEvent = findGameEvent(monsterGift.getId());
			List<GameEvent> eventsToActivate = translateGameEvents(monsterGift.getEventsToActivate());
			for (GameEvent event : eventsToActivate) {
				gameEvent.addSuccessiveGameEvent(event);
			}
			List<GameEvent> eventsToDeactivate = translateGameEvents(monsterGift.getEventsToDeactivate());
			for (GameEvent event : eventsToDeactivate) {
				gameEvent.addDependentGameEvent(event);
			}

		}
		for (NpcBehaviorChangerLoadSaver npcBehaviorChangerLoadSaver : npcBehaviorChangerList) {
			GameEvent gameEvent = findGameEvent(npcBehaviorChangerLoadSaver.getId());
			List<GameEvent> eventsToActivate = translateGameEvents(npcBehaviorChangerLoadSaver.getEventsToActivate());
			for (GameEvent event : eventsToActivate) {
				gameEvent.addSuccessiveGameEvent(event);
			}
			List<GameEvent> eventsToDeactivate = translateGameEvents(
					npcBehaviorChangerLoadSaver.getEventsToDeactivate());
			for (GameEvent event : eventsToDeactivate) {
				gameEvent.addDependentGameEvent(event);
			}

		}
		for (UniqueMonsterEventLoadSaver uniqueMonsterEventLoadSaver : uniqueMonsterList) {
			GameEvent gameEvent = findGameEvent(uniqueMonsterEventLoadSaver.getId());
			List<GameEvent> eventsToActivate = translateGameEvents(uniqueMonsterEventLoadSaver.getEventsToActivate());
			for (GameEvent event : eventsToActivate) {
				gameEvent.addSuccessiveGameEvent(event);
			}
			List<GameEvent> eventsToDeactivate = translateGameEvents(
					uniqueMonsterEventLoadSaver.getEventsToDeactivate());
			for (GameEvent event : eventsToDeactivate) {
				gameEvent.addDependentGameEvent(event);
			}

		}
	}

}
