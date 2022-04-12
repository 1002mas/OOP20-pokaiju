package controller.json;

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

public class GeneralDataLoadSaver {
	private List<String> npcDefeatedName;
	private int idBuilder;
	private List<Pair<String, List<Integer>>> boxData;
	private int idMap;
	
	public GeneralDataLoadSaver(List<String> npcDefeatedName,int idBuilder, List<Pair<String, List<Integer>>> boxData, int idMap) {
		this.npcDefeatedName = npcDefeatedName;
		this.idBuilder = idBuilder;
		this.boxData = boxData;
		this.idMap = idMap;
	}

	public List<String> getNpcDefeatedList() {
		return this.npcDefeatedName;
	}

	/*
	public void setTranslatedNpcSimpleData(List<GameEvent> listEvent, List<NpcSimple> listNpc) {	//TODO ??

		List<GameEvent> events;
		for (Entry<Pair<String, Integer>, List<Integer>> npcData : npcSimpleData.entrySet()) {
			events = new ArrayList<>();
			Pair<String, Integer> npcKey = npcData.getKey();
			List<Integer> npcValue = npcData.getValue();
			for (int gameEventId : npcValue) {
				for (GameEvent gameEvent : listEvent) {
					if (gameEventId == gameEvent.getEventID()) {
						events.add(gameEvent);
					}
				}
			}
			for (NpcSimple npc : listNpc) {
				if (npc.getName().equals(npcKey.getFirst())) {
					npc.setDialogueText(npcKey.getSecond());
					for (GameEvent e : events) {
						npc.addGameEvent(e);
					}
				}
			}
		}

	}
*/
	public int getMapId() {
		return this.idMap;
	}
	
	public int getIdBuilder() {
		return this.idBuilder;
	}
	
	public List<MonsterBox> getMonsterBox(List<Monster> list){
		List<MonsterBox> monsterBoxs = new ArrayList<>();
		for(Pair<String, List<Integer>> boxData : this.boxData) {
			MonsterBox box = new MonsterBoxImpl(boxData.getFirst());
			monsterBoxs.add(box);
			for(int monsterId : boxData.getSecond()) {
				for(Monster monster: list) {
					if(monsterId == monster.getId()) {
						box.addMonster(monster);
					}
				}
			}
			
		}
		
		return monsterBoxs;
	}
	
}
