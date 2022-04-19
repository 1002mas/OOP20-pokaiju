package model.gameevents;

import model.Pair;
import model.npc.NpcSimple;

/**
 * This class allows to create an event that changes a npc position in the same
 * map it is located.
 *
 */
public class NpcPositionChanger extends AbstractGameEvent {
    private final NpcSimple npc;
    private final Pair<Integer, Integer> newPosition;

    /**
     * It creates an event that allows to change npc in the same map.
     * 
     * @param id                   it is used to identify the event
     * @param isActive             if it is active when the player interacts with a
     *                             trigger, this event will use {@link #activate()
     *                             activate}
     * @param isReactivable        if the event has to be has to deactivate and
     *                             never be reactivated after calling the function
     *                             {@link #activate() activate}
     * @param isToActiveImmediatly if the event has to be activated right after
     *                             another event
     * @param npc                  the npc you want to move.
     * @param newPosition          the position you want to move it to.
     */
    public NpcPositionChanger(final int id, final boolean isActive, final boolean isReactivable,
            final boolean isToActiveImmediatly, final NpcSimple npc, final Pair<Integer, Integer> newPosition) {
        super(id, isActive, isReactivable, isToActiveImmediatly);
        this.npc = npc;
        this.newPosition = newPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void activateEvent() {
        this.npc.changeNpcPosition(newPosition);
    }

}
