package controller.json;

import java.util.List;

import model.monster.Monster;

public class UniqueMonsterEventLoadSaver {

	private int id;
	private boolean isToActiveImmediatly;
	private boolean isActive;
	private boolean isDeactivable;
	private List<Integer> eventsToActivate;
	private List<Integer> eventsToDeactivate;
	private Integer monster;

	public UniqueMonsterEventLoadSaver(int id, boolean isToActiveImmediatly, boolean isActive, boolean isDeactivable,
			List<Integer> eventsToActivate, List<Integer> eventsToDeactivate, Integer monster) {

		this.id = id;
		this.eventsToActivate = eventsToActivate;
		this.eventsToDeactivate = eventsToDeactivate;
		this.isActive = isActive;
		this.isDeactivable = isDeactivable;
		this.isToActiveImmediatly = isToActiveImmediatly;
		this.monster = monster;

	}

	public int getId() {
		return this.id;
	}

	public boolean getIsToActiveImmediatly() {
		return this.isToActiveImmediatly;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public boolean getIsDeactivable() {
		return this.isDeactivable;
	}

	public List<Integer> getEventsToActivate() {
		return this.eventsToActivate;
	}

	public List<Integer> getEventsToDeactivate() {
		return this.eventsToDeactivate;
	}

	public Monster getTranslatedMonster(List<Monster> list) {

		for (Monster monsterInList : list) {
			if (monsterInList.getId() == this.monster) {
				return monsterInList;
			}
		}
		return null;
	}
}
