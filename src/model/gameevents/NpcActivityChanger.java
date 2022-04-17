package model.gameevents;

import model.npc.NpcSimple;

/**
 * This class allows to create an event that enable or disable the interaction
 * with a npc.
 * 
 * @author sam
 *
 */
public class NpcActivityChanger extends AbstractGameEvent {

    private final NpcSimple npc;
    private final boolean isEnabled;

    /**
     * It creates an event that allows to disable or enable a npc. Disabling the npc
     * makes the player unable to interact with the npc.
     * 
     * @param npc       the npc you want to disable/enable
     * @param isEnabled false if you want to disable it
     * 
     *                  {@link AbstractGameEvent#AbstractGameEvent(int id, boolean isActive, boolean isDeactivable, boolean isToActiveImmediatly)
     *                  AbstractGameEvent}
     */
    public NpcActivityChanger(int id, boolean isActive, boolean isReactivable, boolean isToActiveImmediatly,
	    NpcSimple npc, boolean isEnabled) {
	super(id, isActive, isReactivable, isToActiveImmediatly);
	this.npc = npc;
	this.isEnabled = isEnabled;
    }

    @Override
    public void activateEvent() {
	this.npc.setEnabled(isEnabled);
    }

}
