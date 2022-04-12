package controller.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Pair;
import model.npc.NpcSimple;

public class NpcBehaviorChangerLoadSaver {

	private List<String> npcs = new ArrayList<>();

	private List<Optional<Pair<Integer, Integer>>> npcsPositions = new ArrayList<>();
	private List<Optional<Integer>> npcsText = new ArrayList<>();
	private List<Optional<Boolean>> npcsShow = new ArrayList<>();
	private int id;
	private boolean isToActiveImmediatly;
	private boolean isActive;
	private boolean isDeactivable;
	private List<Integer> eventsToActivate = new ArrayList<>();
	private List<Integer> eventsToDeactivate = new ArrayList<>();

	public NpcBehaviorChangerLoadSaver(int id, boolean isToActiveImmediatly, boolean isActive, boolean isDeactivable,
			List<Integer> eventsToActivate, List<Integer> eventsToDeactivate) {

		this.id = id;
		this.isToActiveImmediatly = isToActiveImmediatly;
		this.isActive = isActive;
		this.isDeactivable = isDeactivable;
		this.eventsToActivate = eventsToActivate;
		this.eventsToDeactivate = eventsToDeactivate;
	}

	public List<NpcSimple> getNpcs(List<NpcSimple> list) {
		List<NpcSimple> npcList = new ArrayList<>();
		for(String npcName : this.npcs) {
			for(NpcSimple npc : list) {
				if(npcName.equals(npc.getName())) {
					npcList.add(npc);
				}
			}
			
		}
		return npcList;
	}

	public List<Optional<Pair<Integer, Integer>>> getNpcsPositions() {
		return this.npcsPositions;
	}

	public List<Optional<Integer>> getNpcsText() {
		return this.npcsText;
	}

	public List<Optional<Boolean>> getNpcsShow() {
		return this.npcsShow;
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
}
