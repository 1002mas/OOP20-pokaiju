package model.gameevents;

import java.util.ArrayList;
import java.util.List;

import model.Pair;
import model.npc.NpcSimple;

public class NpcBehaviorChanger extends AbstractGameEvent {

    private List<NpcSimple> npcs = new ArrayList<>();
    private List<Pair<Integer, Integer>> npcsPositions = new ArrayList<>();
    private List<Integer> npcsText = new ArrayList<>();
    private List<Boolean> npcsShow = new ArrayList<>();
    private List<Boolean> npcsEnabled = new ArrayList<>();

    public NpcBehaviorChanger(int id, boolean isActive, boolean isDeactivable, boolean isToActiveImmediatly) {
	super(id, isActive, isDeactivable, isToActiveImmediatly);
    }

    /**
     * add npc behavior event.
     * 
     * @param npc         npc to change behavior
     * @param newPosition new npc position
     * @param nextText    npc sentence id
     * @param visibility  whether or not the npc is hidden
     * @param enabled     if the player can interact with the npc
     */
    public void addNpc(NpcSimple npc, Pair<Integer, Integer> newPosition, int nextText, boolean visibility,
	    boolean enabled) {
	this.npcs.add(npc);
	this.npcsPositions.add(newPosition);
	this.npcsText.add(nextText);
	this.npcsShow.add(visibility);
	this.npcsEnabled.add(enabled);
    }

    @Override
    public void activateEvent() {
	for (int i = 0; i < npcs.size(); i++) {
	    npcs.get(i).changeNpcPosition(npcsPositions.get(i));
	    npcs.get(i).setDialogueText(npcsText.get(i));
	    npcs.get(i).setEnabled(npcsShow.get(i));
	    npcs.get(i).setVisible(npcsEnabled.get(i));
	}
    }

}
