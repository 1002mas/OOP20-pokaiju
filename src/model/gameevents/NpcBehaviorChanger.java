package model.gameevents;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Pair;
import model.npc.NpcSimple;

public class NpcBehaviorChanger extends AbstractGameEvent {

    private List<NpcSimple> npcs = new ArrayList<>();
    private List<Optional<Pair<Integer, Integer>>> npcsPositions = new ArrayList<>();
    private List<Optional<Integer>> npcsText = new ArrayList<>();
    private List<Optional<Boolean>> npcsShow = new ArrayList<>();
    private List<Optional<Boolean>> npcsEnabled = new ArrayList<>();

    public NpcBehaviorChanger(int id, boolean isActive, boolean isDeactivable, boolean isToActiveImmediatly) {
	super(id, isActive, isDeactivable, isToActiveImmediatly);
    }

    private void addNpc(NpcSimple npc, Optional<Pair<Integer, Integer>> newPosition, Optional<Integer> nextText,
	    Optional<Boolean> visibility, Optional<Boolean> enabled) {
	this.npcs.add(npc);
	this.npcsPositions.add(newPosition);
	this.npcsText.add(nextText);
	this.npcsShow.add(visibility);
	this.npcsEnabled.add(enabled);
    }

    public void addNpcPositionChange(NpcSimple npc, Pair<Integer, Integer> newPosition) {
	addNpc(npc, Optional.of(newPosition), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public void addNpcDialogChange(NpcSimple npc, int dialogID) {
	addNpc(npc, Optional.empty(), Optional.of(dialogID), Optional.empty(), Optional.empty());
    }

    /**
     * This function is used to hide a npc from the map
     * 
     * @param npc
     * @param visibility true to make it visible, false to hide it
     */
    public void addNpcVisibilityChange(NpcSimple npc, boolean visibility) {
	addNpc(npc, Optional.empty(), Optional.empty(), Optional.of(visibility), Optional.empty());
    }

    /**
     * This function is used to deactivate a npc interaction
     * 
     * @param npc
     * @param enabled true to make interaction avaiable
     */
    public void addNpcInteractbilityChange(NpcSimple npc, boolean enabled) {
	addNpc(npc, Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(enabled));
    }

    @Override
    public void activateEvent() {
	for (int i = 0; i < npcs.size(); i++) {
	    // position change
	    if (npcsPositions.get(i).isPresent()) {
		npcs.get(i).changeNpcPosition(npcsPositions.get(i).get());
	    }
	    // dialog change
	    if (npcsText.get(i).isPresent()) {
		npcs.get(i).setDialogueText(npcsText.get(i).get());
	    }
	    // visibilty change
	    if (npcsShow.get(i).isPresent()) {
		npcs.get(i).setVisible(npcsShow.get(i).get());
	    }
	    // interaction active change
	    if (npcsEnabled.get(i).isPresent()) {
		npcs.get(i).setEnabled(npcsEnabled.get(i).get());
	    }
	}
    }

}
