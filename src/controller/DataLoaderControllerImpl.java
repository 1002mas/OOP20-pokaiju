package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Pair;
import model.battle.MovesData;
import model.battle.MovesDataImpl;
import model.gameitem.EvolutionItem;
import model.gameitem.GameItem;
import model.gameitem.GameItemImpl;
import model.gameitem.HealingItem;
import model.monster.EvolutionType;
import model.monster.Monster;
import model.monster.MonsterImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesByItem;
import model.monster.MonsterSpeciesImpl;
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

	private GsonBuilder gsonBuilder;
	private Gson gson;

	private List<MovesData> movesData;
	private List<Monster> monsters;
	private List<MonsterSpecies> monsterSpecies;
	private List<NpcSimple> npcs;
	private List<GameItem> gameItems;

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
				MovesData m = gson.fromJson(stringRead, MovesDataImpl.class);
				this.movesData.add(m);
			} catch (IOException e) {
				 e.printStackTrace();
			}
		}

	}

	@Override
	public List<MovesData> getMoves() {
		if (this.movesData == null) {
			movesData = new ArrayList<>();
			loadMoves();
		}
		return this.movesData;
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
				NpcTrainer npc = new NpcTrainerImpl(n.getName(), n.getTypeOfNpc(), n.getSentences(),
						n.getTranslatedMonsterList(monsters), n.getPosition());
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
				NpcMerchant npc = gson.fromJson(stringRead, NpcMerchantImpl.class);
				this.npcs.add(npc);
				// TODO translate
			} catch (IOException e) {
				 e.printStackTrace();
			}

		}

	}

	@Override
	public List<NpcSimple> getNpcs() {
		if (this.npcs == null) {
			npcs = new ArrayList<>();
			loadNpcs();

			// TODO npcMerchant and ncpTrainer
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

				// TODO use builder

				switch (ms.getEvolutionType()) {
				case ITEM:
					m = new MonsterSpeciesByItem(ms.getName(), ms.getInfo(), ms.getMonsterType(), ms.getMonsterStats(),
							evolution.get(), ms.getEvolutionGameItem().get(), null);
					// TODO use movesData ASAP is usable instead of null
					break;
				case LEVEL:
					m = new MonsterSpeciesByItem(ms.getName(), ms.getInfo(), ms.getMonsterType(), ms.getMonsterStats(),
							evolution.get(), ms.getEvolutionGameItem().get(), null);
					// TODO use movesData ASAP is usable instead of null
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

	private void loadSimpleMonsters(List<MonsterSpeciesSupport> mssList) { // --
		List<MonsterSpeciesSupport> removedMonsters = new ArrayList<>();
		for (MonsterSpeciesSupport ms : mssList) {
			if (ms.getEvolutionType().equals(EvolutionType.NONE)) {
				MonsterSpecies m = new MonsterSpeciesImpl(ms.getName(), ms.getInfo(), ms.getMonsterType(),
						ms.getMonsterStats(), ms.getAllMoves(this.movesData));

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

	public List<GameItem> getGameEvolution() {
		if (this.gameItems == null) {
			gameItems = new ArrayList<>();
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
				Monster monster = new MonsterImpl(m.getId(), m.getStats(), m.getExp(), m.getLevel(), false,
						m.getTranslatedMonsterSpecies(this.monsterSpecies), null);
				// TODO use movesData ASAP is usable instead of null
				// TODO use MonsterBuildier ?
				this.monsters.add(monster);
			} catch (IOException e) {
				 e.printStackTrace();
			}
		}
	}

	public List<Monster> getMonsters() {
		if (this.monsters == null) {
			monsters = new ArrayList<>();
			loadMonsters();

		}
		return this.monsters;
	}

}
