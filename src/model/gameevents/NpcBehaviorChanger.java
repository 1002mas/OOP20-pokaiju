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

    public NpcBehaviorChanger(int id, boolean isActive, boolean isDeactivable, List<GameEvent> events) {
	super(id, isActive, isDeactivable, true, events);
    }

    private void addNpc(NpcSimple npc, Optional<Pair<Integer, Integer>> newPosition, Optional<Integer> nextText,
	    Optional<Boolean> visibility) {
	this.npcs.add(npc);
	this.npcsPositions.add(newPosition);
	this.npcsText.add(nextText);
	this.npcsShow.add(visibility);
    }

    public void addNpcPositionChange(NpcSimple npc, Pair<Integer, Integer> newPosition) {
	addNpc(npc, Optional.of(newPosition), Optional.empty(), Optional.empty());
    }

    public void addNpcDialogChange(NpcSimple npc, int dialogID) {
	addNpc(npc, Optional.empty(), Optional.of(dialogID), Optional.empty());

    }

    /**
     * This function is used to logically delete a npc from the map
     * @param npc 
     * @param visibility true to make it visible, false to hide it
     */
    public void addNpcVisibilityChange(NpcSimple npc, boolean visibility) {
	addNpc(npc, Optional.empty(), Optional.empty(), Optional.of(visibility));
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
		npcs.get(i).setEnabled(npcsShow.get(i).get());
		npcs.get(i).setVisible(npcsShow.get(i).get());
	    }
	}
    }

}
