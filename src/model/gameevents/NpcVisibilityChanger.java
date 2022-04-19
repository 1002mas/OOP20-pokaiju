package model.gameevents;

import model.npc.NpcSimple;

/**
 * This class allows to create an event that changes a npc visibility.
 * 
 *
 */
public class NpcVisibilityChanger extends AbstractGameEvent {
    private final NpcSimple npc;
    private final boolean isVisible;

    /**
     * It creates an event that allows to hide or show a npc.
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
     * @param npc                  the npc you want to hide/show
     * @param isVisible            false if you want to hide it
     *
     */
    public NpcVisibilityChanger(final int id, final boolean isActive, final boolean isReactivable,
            final boolean isToActiveImmediatly, final NpcSimple npc, final boolean isVisible) {
        super(id, isActive, isReactivable, isToActiveImmediatly);
        this.npc = npc;
        this.isVisible = isVisible;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void activateEvent() {
        this.npc.setVisible(isVisible);
    }

}
