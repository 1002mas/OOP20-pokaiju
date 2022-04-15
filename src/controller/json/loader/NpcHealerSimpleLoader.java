package controller.json.loader;

import java.util.List;

import model.Pair;
import model.npc.TypeOfNpc;

public class NpcHealerSimpleLoader {
	private String name;
	private List<String> sentences;
	private Pair<Integer, Integer> position;
	private boolean isVisible;
	private boolean isEnabled;
	private List<Integer> events;

	public NpcHealerSimpleLoader(String name, List<String> sentences, Pair<Integer, Integer> position,
			boolean isVisible, boolean isEnabled, List<Integer> events) {
		this.name = name;
		this.sentences = sentences;
		this.position = position;
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

	public List<Integer> getEvents() {
		return this.events;
	}

}
