package controller.json.loader;

import java.util.List;

import model.monster.Monster;

public class UniqueMonsterEventLoader {

	private int id;
	private boolean isToActiveImmediatly;
	private boolean isActive;
	private List<Integer> eventsToActivate;
	private List<Integer> eventsToDeactivate;
	private Integer monster;

	public UniqueMonsterEventLoader(int id, boolean isToActiveImmediatly, boolean isActive,
			List<Integer> eventsToActivate, List<Integer> eventsToDeactivate, Integer monster) {

		this.id = id;
		this.eventsToActivate = eventsToActivate;
		this.eventsToDeactivate = eventsToDeactivate;
		this.isActive = isActive;
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
