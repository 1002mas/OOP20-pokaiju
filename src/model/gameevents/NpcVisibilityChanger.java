package model.gameevents;

import model.npc.NpcSimple;

/**
 * This class allows to create an event that changes a npc visibility.
 * 
 * @author sam
 *
 */
public class NpcVisibilityChanger extends AbstractGameEvent {
    private final NpcSimple npc;
    private final boolean isVisible;

    /**
     * It creates an event that allows to hide or show a npc.
     * 
     * @param npc       the npc you want to hide/show
     * @param isVisible false if you want to hide it
     * 
     *                  {@link AbstractGameEvent#AbstractGameEvent(int id, boolean isActive, boolean isDeactivable, boolean isToActiveImmediatly)
     *                  AbstractGameEvent}
     */
    public NpcVisibilityChanger(int id, boolean isActive, boolean isReactivable, boolean isToActiveImmediatly,
	    NpcSimple npc, boolean isVisible) {
	super(id, isActive, isReactivable, isToActiveImmediatly);
	this.npc = npc;
	this.isVisible = isVisible;
    }

    @Override
    protected void activateEvent() {
	this.npc.setVisible(isVisible);
    }

}
