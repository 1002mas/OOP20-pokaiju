package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Pair;
import model.gameitem.GameItem;
import model.npc.TypeOfNpc;

public class NpcMerchantSupport {

	private String name;
	private TypeOfNpc typeOfNpc;
	private List<String> sentences;
	private Pair<Integer, Integer> position;
	private Map<String, Integer> inventary;
    private boolean isVisible;
    private boolean isEnabled;

	public NpcMerchantSupport(String name, TypeOfNpc typeOfNpc, List<String> sentences,
			Pair<Integer, Integer> position, boolean isVisible, boolean isEnabled, Map<String, Integer> inventary) {
		this.name = name;
		this.typeOfNpc = typeOfNpc;
		this.sentences = sentences;
		this.position = position;
		this.inventary = inventary;
		this.isVisible = isVisible;
		this.isEnabled = isEnabled;

	}

	public String getName() {
		return name;
	}

	public TypeOfNpc getTypeOfNpc() {
		return typeOfNpc;
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
	
	public boolean  getisEnabled() {
		return this.isEnabled;
	}

	public Map<GameItem, Integer> getTranslatedGameItem(List<GameItem> list) {

		Map<GameItem, Integer> map = new HashMap<>();
		for (Map.Entry<String, Integer> m : inventary.entrySet()) {
			for (GameItem gi : list) {
				if (gi.getNameItem().equals(m.getKey())) {
					map.put(gi, m.getValue());
					break;
				}
			}
		}
		return map;
	}
}
