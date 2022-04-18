package model.gameevents;

import java.util.List;

import model.monster.Monster;

/**
 * This class allows to create an event that creates a battle with a monster.
 * Once the monster has ended, it cannot be repeated.
 * 
 * @author sam
 *
 */
public class UniqueMonsterEvent extends AbstractGameEvent {
    private final Monster monster;

    /**
     * It creates an event that allows to create a battle with a monster. This event
     * is set to deactivate automatically after it happens.
     * 
     * @param monster the monster you want to battle with the player
     * 
     *                {@link AbstractGameEvent#AbstractGameEvent(int id, boolean isActive, boolean isDeactivable, boolean isToActiveImmediatly)
     *                AbstractGameEvent}
     */
    public UniqueMonsterEvent(int id, boolean isActive, boolean isToActiveImmediatly, Monster monster) {
	super(id, isActive, false, isToActiveImmediatly);
	this.monster = monster;
    }

    @Override
    public List<Monster> getMonster() {
	return List.of(monster);
    }

    @Override
    protected void activateEvent() {
    }

    @Override
    public boolean isBattle() {
	return true;
    }

}