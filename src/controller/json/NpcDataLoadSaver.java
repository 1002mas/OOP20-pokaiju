package controller.json;

import java.util.ArrayList;
import java.util.List;

import model.gameevents.GameEvent;

public class NpcDataLoadSaver {

	private String name;
	private int idSentence;
	private List<Integer> EventId;
	private boolean isVisible;
	private boolean isEnable;

	public NpcDataLoadSaver(String name, int idSentence, List<Integer> EventId, boolean isVisible, boolean isEnable) {

		this.name = name;
		this.isEnable = isEnable;
		this.idSentence = idSentence;
		this.isVisible = isVisible;
		this.EventId = EventId;
	}

	
	public String getName() {
		return name;
	}

	public int getIdSentence() {
		return idSentence;
	}

	public List<GameEvent> getTranslatedEvent(List<GameEvent> list) {
		List<GameEvent> events = new ArrayList<>();
		for(int id : this.EventId) {
			for(GameEvent gameEvent : list) {
				if(gameEvent.getEventID() == id) {
					events.add(gameEvent);
				}
			}
		}
		return events;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public boolean isEnable() {
		return isEnable;
	}

	
}
