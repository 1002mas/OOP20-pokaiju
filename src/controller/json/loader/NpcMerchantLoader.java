package controller.json.loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Pair;
import model.gameevents.GameEvent;
import model.gameitem.GameItem;
import model.npc.TypeOfNpc;

public class NpcMerchantLoader {

	private String name;
	private List<String> sentences;
	private Pair<Integer, Integer> position;
	private Map<String, Integer> inventary;
	private boolean isVisible;
	private boolean isEnabled;
	private List<Integer> events;

	public NpcMerchantLoader(String name, List<String> sentences, Pair<Integer, Integer> position,
			boolean isVisible, boolean isEnabled, Map<String, Integer> inventary,List<Integer> events) {
		this.name = name;
		this.sentences = sentences;
		this.position = position;
		this.inventary = inventary;
		this.isVisible = isVisible;
		this.isEnabled = isEnabled;
		this.events = events;

	}

	public String getName() {
		return name;
	}

	public List<String> getSentences() {
		return sentences;
	}

	public Pair<Integer, Integer> getPosition() {
		return position;
	}

	public boolean getIsVisible() {
		return this.isVisible;
	}

	public boolean getisEnabled() {
		return this.isEnabled;
	}
	
	public List<Integer> getEvents(){
		return this.events;
	}

	public Map<GameItem, Integer> getTranslatedGameItem(List<GameItem> list) {

		Map<GameItem, Integer> gameItems = new HashMap<>();
		for (Map.Entry<String, Integer> map : inventary.entrySet()) {
			for (GameItem item : list) {
				if (item.getNameItem().equals(map.getKey())) {
					gameItems.put(item, map.getValue());
					break;
				}
			}
		}
		return gameItems;
	}
}
