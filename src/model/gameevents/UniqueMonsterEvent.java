package model.gameevents;

import java.util.List;

import model.monster.Monster;

/**
 * This class allows to create an event that creates a battle with a monster.
 * Once the monster has ended, it cannot be repeated.
 * 
 *
 */
public class UniqueMonsterEvent extends AbstractGameEvent {
    private final Monster monster;

    /**
     * It creates an event that allows to create a battle with a monster. This event
     * is set to deactivate automatically after it happens.
     * 
     * @param id                   it is used to identify the event
     * @param isActive             if it is active when the player interacts with a
     *                             trigger, this event will use {@link #activate()
     *                             activate}
     * @param isToActiveImmediatly if the event has to be activated right after
     *                             another event
     * @param monster              the monster you want to battle with the player
     */
    public UniqueMonsterEvent(final int id, final boolean isActive, final boolean isToActiveImmediatly,
            final Monster monster) {
        super(id, isActive, false, isToActiveImmediatly);
        this.monster = monster;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Monster> getMonster() {
        return List.of(monster);
    }

    @Override
    protected void activateEvent() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBattle() {
        return true;
    }
}
