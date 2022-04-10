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
import model.battle.Moves;
import model.battle.MovesData;
import model.battle.MovesDataImpl;
import model.gameitem.EvolutionItem;
import model.gameitem.GameItemImpl;
import model.gameitem.GameItems;
import model.gameitem.HealingItem;
import model.monster.EvolutionType;
import model.monster.Monster;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesByItem;
import model.monster.MonsterSpeciesImpl;
import model.monster.MonsterStats;
import model.monster.MonsterType;
import model.npc.NpcSimple;
import model.npc.NpcSimpleImpl;

public class DataLoaderControllerImpl implements DataLoaderController {

	private static final String MOVES_PATH = "res" + File.separator + "Data" + File.separator + "Moves"
			+ File.separator;
	private static final String NPC_SIMPLE_PATH = "res" + File.separator + "Data" + File.separator + "Npc"
			+ File.separator + "NpcSimple" + File.separator;
	private static final String NPC_TRAINER_PATH = "res" + File.separator + "Data" + File.separator + "Npc"
			+ File.separator + "NpcTrainer" + File.separator;
	private static final String NPC_MERCHANT_PATH = "res" + File.separator + "Data" + File.separator + "Npc"
			+ File.separator + "NpcMerchant" + File.separator;
	private static final String MONSTER_SPECIES_PATH = "res" + File.separator + "Data" + File.separator
			+ "MonsterSpecies" + File.separator;
	private static final String EVOLUTION_GAME_ITEM_PATH = "res" + File.separator + "Data" + File.separator + "GameItem"
			+ File.separator + "Evolution" + File.separator;

	private GsonBuilder gsonBuilder;
	private Gson gson;

	private List<MovesData> movesd;
	private List<Monster> monster;// = new ArrayList<>();
	private List<MonsterSpecies> monsterSpecies;
	private List<NpcSimple> npc;
	private List<GameItems> gameItems;

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
				this.movesd.add(m);
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}

	}

	@Override
	public List<MovesData> getMoves() {
		if (this.movesd == null) {
			movesd = new ArrayList<>();
			loadMoves();
		}
		return this.movesd;
	}

	private void loadNpcSimple() {
		File folder = new File(NPC_SIMPLE_PATH);
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();

			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				NpcSimple npc = gson.fromJson(stringRead, NpcSimpleImpl.class);
				this.npc.add(npc);

			} catch (IOException e) {
				// e.printStackTrace();
			}

		}

	}

	@Override
	public List<NpcSimple> getNpcSimple() {
		if (this.npc == null) {
			npc = new ArrayList<>();
			loadNpcSimple();
			// ,, altri due npc
			// ..
		}
		return this.npc;
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
				// e.printStackTrace();
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

					break;
				case LEVEL:
					m = new MonsterSpeciesByItem(ms.getName(), ms.getInfo(), ms.getMonsterType(), ms.getMonsterStats(),
							evolution.get(), ms.getEvolutionGameItem().get(), null);
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
						ms.getMonsterStats(), ms.getAllMoves(this.movesd));

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

	private void loadEvolutionGameItem() {
		File folder = new File(this.EVOLUTION_GAME_ITEM_PATH);
		for (File file : folder.listFiles()) {
			String filePath = file.getPath();

			try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String stringRead = reader.readLine();
				GameItemSupport gis = gson.fromJson(stringRead, GameItemSupport.class);
				GameItems gameItem;
				switch(gis.getType()) {
				
				case EVOLUTIONTOOL:
					gameItem =  new EvolutionItem(gis.getNameItem(), gis.getQuantity(), gis.getDescription());
					break;
					
				case HEAL:
					gameItem =  new HealingItem(gis.getNameItem(), gis.getQuantity(), gis.getDescription(), gis.getHealing().get());
					break;
					
				case MONSTERBALL:
					gameItem =  new GameItemImpl(gis.getNameItem(), gis.getQuantity(), gis.getDescription());
					break;
				default:
					throw new IllegalStateException();
				}

				this.gameItems.add(gameItem);
			} catch (IOException e) {
				// e.printStackTrace();
			}
						}
	}

	private void loadGameItems() {
		
	}

	public List<GameItems> getGameEvolution() {
		if (this.gameItems == null) {
			gameItems = new ArrayList<>();
			loadGameItems();

		}
		return this.gameItems;
	}
}
