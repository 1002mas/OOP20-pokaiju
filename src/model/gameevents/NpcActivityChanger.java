package model.gameevents;

import model.npc.NpcSimple;

/**
 * This class allows to create an event that enable or disable the interaction
 * with a npc.
 *
 */
public class NpcActivityChanger extends AbstractGameEvent {

    private final NpcSimple npc;
    private final boolean isEnabled;

    /**
     * It creates an event that allows to disable or enable a npc. Disabling the npc
     * makes the player unable to interact with the npc.
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
     * @param npc                  the npc you want to disable/enable
     * @param isEnabled            false if you want to disable it
     */
    public NpcActivityChanger(final int id, final boolean isActive, final boolean isReactivable,
            final boolean isToActiveImmediatly, final NpcSimple npc, final boolean isEnabled) {
        super(id, isActive, isReactivable, isToActiveImmediatly);
        this.npc = npc;
        this.isEnabled = isEnabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activateEvent() {
        this.npc.setEnabled(isEnabled);
    }

}
