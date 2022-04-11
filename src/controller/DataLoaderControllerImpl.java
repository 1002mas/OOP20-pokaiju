package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Pair;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.gameitem.EvolutionItem;
import model.gameitem.GameItem;
import model.gameitem.GameItemImpl;
import model.gameitem.HealingItem;
import model.map.GameMapData;
import model.map.GameMapDataImpl;
import model.map.MapBlockType;
import model.monster.EvolutionType;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesBuilderImpl;
import model.monster.MonsterSpeciesByItem;
import model.monster.MonsterSpeciesByLevel;
import model.monster.MonsterSpeciesImpl;
import model.monster.MonsterStats;
import model.monster.MonsterType;
import model.npc.NpcMerchant;
import model.npc.NpcMerchantImpl;
import model.npc.NpcSimple;
import model.npc.NpcSimpleImpl;
import model.npc.NpcTrainer;
import model.npc.NpcTrainerImpl;

public class DataLoaderControllerImpl implements DataLoaderController {

	private static final String BASE_PATH = "res" + File.separator + "data" + File.separator;
	private static final String MOVES_PATH = BASE_PATH + "moves" + File.separator;
	private static final String NPC_BASE_PATH = BASE_PATH + File.separator + "npc" + File.separator;
	private static final String NPC_SIMPLE_PATH = NPC_BASE_PATH + "npcSimple" + File.separator;
	private static final String NPC_TRAINER_PATH = NPC_BASE_PATH + "npcTrainer" + File.separator;
	private static final String NPC_MERCHANT_PATH = NPC_BASE_PATH + "npcMerchant" + File.separator;
	private static final String MONSTER_SPECIES_PATH = BASE_PATH + File.separator + "monsterSpecies" + File.separator;
	private static final String GAME_ITEM_PATH = BASE_PATH + File.separator + "gameItem" + File.separator + "evolution"
			+ File.separator;
	private static final String MONSTER_PATH = BASE_PATH + "monster" + File.separator;
	private static final String GAME_MAP_DATA_PATH = BASE_PATH + "gameMapData" + File.separator;

	// TODO check if set this.x
	// TODO check ==/equals()
	// TODO do tests
	// TODO fix monsterSpecies
	// TODO restruct monsterbuilder
	// TODO box dei mostri 
	
	private GsonBuilder gsonBuilder;
	private Gson gson;
	private List<Moves> movesd;
	private List<Monster> monster;
	private List<MonsterSpecies> monsterSpecies;
	private List<NpcSimple> npcs;
	private List<GameItem> gameItems;
	private List<GameMapData> gameMapData;

	public DataLoaderControllerImpl() {
		this.gsonBuilder = new GsonBuilder();
		/*
		 * gsonBuilder.registerTypeAdapter(Moves.class, new TypeAdapterController());
		 * gsonBuilder.registerTypeAdapter(MovesData.class, new
		 * TypeAdapterController());
		 * gsonBuilder.registerTypeAdapter(MonsterSpecies.class, new
		 * TypeAdapterController()); gsonBuilder.registerTypeAdapter(Moves.class, new
		 * TypeAdapterController()); gsonBuilder.registerTypeAdapter(MonsterStats.class,
		 * new TypeAdapterController()); gsonBuilder.registerTypeAdapter(Monster.class,
		 * new TypeAdapterController());
		 * gsonBuilder.registerTypeAdapter(GameMapData.class, new
		 * TypeAdapterController()); gsonBuilder.registerTypeAdapter(GameItems.class,new
		 * TypeAdapterController());
		 */
		this.gson = gsonBuilder.create();
	}

	private void loadMoves() {
		File folder = new File(MOVES_PATH);
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();

			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				Moves m = gson.fromJson(stringRead, MovesImpl.class);
				this.movesd.add(m);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<Moves> getMoves() {
		if (this.movesd == null) {
			movesd = new ArrayList<>();
			this.loadMoves();
		}
		return this.movesd;
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
				NpcTrainerSupport n = gson.fromJson(stringRead, NpcTrainerSupport.class);
				NpcTrainer npc = new NpcTrainerImpl(n.getName(), n.getTypeOfNpc(), n.getSentences(), n.getPosition(),
						n.getIsVisible(), n.getIsEnabled(), n.getTranslatedMonsterList(monster), n.getIsVisible());
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
				NpcMerchantSupport n = gson.fromJson(stringRead, NpcMerchantSupport.class);
				NpcMerchant npc = new NpcMerchantImpl(n.getName(), n.getTypeOfNpc(), n.getSentences(), n.getPosition(),
						n.getIsVisible(), n.getisEnabled(), n.getTranslatedGameItem(gameItems));
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
		List<MonsterSpeciesSupport> mssList = new ArrayList<>();
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();

			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();

				MonsterSpeciesSupport mss = gson.fromJson(stringRead, MonsterSpeciesSupport.class);
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

	private void loadEvolvableMonsters(List<MonsterSpeciesSupport> mssList) {

		List<MonsterSpeciesSupport> removedMonsters = new ArrayList<>();
		for (MonsterSpeciesSupport ms : mssList) {
			Optional<MonsterSpecies> evolution = this.monsterSpecies.stream()
					.filter(m -> m.getName().equals(ms.getEvolution().get())).findAny();
			if (evolution.isPresent()) {
				MonsterSpecies m = null;

				switch (ms.getEvolutionType()) {
				case ITEM:
					m = new MonsterSpeciesBuilderImpl().name(ms.getName()).info(ms.getInfo())
							.monsterType(ms.getMonsterType()).health(ms.getHealth()).attack(ms.getAttack())
							.defense(ms.getDefense()).speed(ms.getSpeed()).evolution(evolution.get())
							.gameItem(ms.getEvolutionGameItem(gameItems).get()).allMoves(ms.getAllMoves(movesd))
							.build();
					break;
				case LEVEL:
					m = new MonsterSpeciesBuilderImpl().name(ms.getName()).info(ms.getInfo())
							.monsterType(ms.getMonsterType()).health(ms.getHealth()).attack(ms.getAttack())
							.defense(ms.getDefense()).speed(ms.getSpeed()).evolution(evolution.get())
							.allMoves(ms.getAllMoves(movesd)).build();
					break;
				default:
					throw new IllegalStateException();

				}

				this.monsterSpecies.add(m);
				removedMonsters.add(ms);
			}

		}
		mssList.removeAll(removedMonsters);

	}

	private void loadSimpleMonsters(List<MonsterSpeciesSupport> mssList) {
		List<MonsterSpeciesSupport> removedMonsters = new ArrayList<>();
		for (MonsterSpeciesSupport ms : mssList) {
			Optional<MonsterSpecies> evolution = this.monsterSpecies.stream()
					.filter(m -> m.getName().equals(ms.getEvolution().get())).findAny();
			if (ms.getEvolutionType().equals(EvolutionType.NONE)) {
				MonsterSpecies m = new MonsterSpeciesBuilderImpl().name(ms.getName()).info(ms.getInfo())
						.monsterType(ms.getMonsterType()).health(ms.getHealth()).attack(ms.getAttack())
						.defense(ms.getDefense()).speed(ms.getSpeed()).evolution(evolution.get())
						.allMoves(ms.getAllMoves(movesd)).build();
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
				GameItemSupport gis = gson.fromJson(stringRead, GameItemSupport.class);
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
				MonsterSupport m = gson.fromJson(stringRead, MonsterSupport.class);
				Monster monster = new MonsterBuilderImpl().health(m.getHealth()).attack(m.getAttack())
						.defense(m.getDefense()).speed(m.getSpeed()).exp(m.getExp()).level(m.getLevel()).isWild(false)
						.species(m.getTranslatedMonsterSpecies(monsterSpecies)).movesList(m.getTranslatedMoves(movesd))
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
				GameMapDataSupport m = gson.fromJson(stringRead, GameMapDataSupport.class);
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

}
