package model.gameevents;

import model.Pair;
import model.npc.NpcSimple;

/**
 * This class allows to create an event that changes a npc position in the same
 * map it is located.
 * 
 * @author sam
 *
 */
public class NpcPositionChanger extends AbstractGameEvent {
    private final NpcSimple npc;
    private final Pair<Integer, Integer> newPosition;

    /**
     * It creates an event that allows to change npc in the same map.
     * 
     * @param npc         the npc you want to move.
     * @param newPosition the position you want to move it to.
     * 
     *                    {@link AbstractGameEvent#AbstractGameEvent(int id, boolean isActive, boolean isDeactivable, boolean isToActiveImmediatly)
     *                    AbstractGameEvent}
     */
    public NpcPositionChanger(int id, boolean isActive, boolean isReactivable, boolean isToActiveImmediatly,
	    NpcSimple npc, Pair<Integer, Integer> newPosition) {
	super(id, isActive, isReactivable, isToActiveImmediatly);
	this.npc = npc;
	this.newPosition = newPosition;
    }

    @Override
    protected void activateEvent() {
	this.npc.changeNpcPosition(newPosition);
    }

}
