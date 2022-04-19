package model.gameevents;

import model.npc.NpcSimple;

/**
 * This class allows to create an event that changes a npc dialogue text.
 * 
 *
 */
public class NpcTextChanger extends AbstractGameEvent {
    private final NpcSimple npc;
    private final int textID;

    /**
     * It creates an event that allows to change a npc sentence.
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
     * @param npc                  the npc that changes sentence
     * @param textID               sentence ID. Be careful, this function does not
     *                             check that the id is present. Giving a wrong ID
     *                             will result in an error.
     */
    public NpcTextChanger(final int id, final boolean isActive, final boolean isReactivable,
            final boolean isToActiveImmediatly, final NpcSimple npc, final int textID) {
        super(id, isActive, isReactivable, isToActiveImmediatly);
        this.npc = npc;
        this.textID = textID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void activateEvent() {
        this.npc.setDialogueText(textID);
    }

}
