package controller.json;

import java.util.ArrayList;
import java.util.List;

import model.monster.Monster;

public class MonsterGiftLoadSaver {
	private int id;

	private boolean isToActiveImmediatly;
	private boolean isActive;
	private boolean isDeactivable;
	private List<Integer> eventsToActivate;
	private List<Integer> eventsToDeactivate;
	private final List<Integer> monsters;

	public MonsterGiftLoadSaver(int id, boolean isToActiveImmediatly, boolean isActive, boolean isDeactivable,
			List<Integer> eventsToActivate, List<Integer> eventsToDeactivate, List<Integer> monsters) {
		this.id = id;
		this.isToActiveImmediatly = isToActiveImmediatly;
		this.isActive = isActive;
		this.isDeactivable = isDeactivable;
		this.eventsToActivate = eventsToActivate;
		this.eventsToDeactivate = eventsToDeactivate;
		this.monsters = monsters;
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

	public List<Monster> getTranslatedMonsters(List<Monster> list) {
		List<Monster> monsters = new ArrayList<>();
		for (int id : this.monsters) {
			for (Monster monsterInList : list) {
				if (id == monsterInList.getId()) {
					monsters.add(monsterInList);
				}
			}
		}
		return monsters;
	}

}
