package controller.json.saver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Pair;
import model.gameevents.GameEvent;
import model.monster.Monster;
import model.npc.NpcSimple;
import model.player.MonsterBox;
import model.player.MonsterBoxImpl;

public class GameDataSaver {
	private List<String> trainerDefeatedName;
	private int idBuilder;
	private List<Pair<String, List<Integer>>> boxData;
	private int idMap;
	private List<NpcDataSaver> npcDatatSaver;
	private List<Pair<Integer, Boolean>> events;
	private PlayerSaver playerSaver;

	public GameDataSaver(List<String> npcDefeatedName, int idBuilder, List<Pair<String, List<Integer>>> boxData,
			int idMap, List<NpcDataSaver> npcDatatSaver, List<Pair<Integer, Boolean>> events, PlayerSaver playerSaver) {
		this.trainerDefeatedName = npcDefeatedName;
		this.idBuilder = idBuilder;
		this.boxData = boxData;
		this.idMap = idMap;
		this.idMap = idMap;
		this.npcDatatSaver = npcDatatSaver;
		this.playerSaver = playerSaver;

	}

	public List<String> getNpcDefeatedList() {
		return this.trainerDefeatedName;
	}

	public int getMapId() {
		return this.idMap;
	}

	public int getIdBuilder() {
		return this.idBuilder;
	}

	public List<NpcDataSaver> getNpcDatatSaver() {
		return this.npcDatatSaver;
	}

	public void setTranslatedEvents(List<GameEvent> list) {
		for (Pair<Integer, Boolean> eventId : events) {
			for (GameEvent event : list) {
				if (event.getEventID() == eventId.getFirst()) {
					event.setActivity(eventId.getSecond());
				}
			}
		}
	}

	public PlayerSaver getPlayerSaver() {
		return this.playerSaver;
	}

	public List<MonsterBox> getTranslatedMonsterBox(List<Monster> list) {
		List<MonsterBox> monsterBoxs = new ArrayList<>();
		for (Pair<String, List<Integer>> boxData : this.boxData) {
			MonsterBox box = new MonsterBoxImpl(boxData.getFirst());
			monsterBoxs.add(box);
			for (int monsterId : boxData.getSecond()) {
				for (Monster monster : list) {
					if (monsterId == monster.getId()) {
						box.addMonster(monster);
					}
				}
			}

		}

		return monsterBoxs;
	}

	public List<Pair<Integer, Boolean>> getEventsList() {
		return this.events;
	}
}
