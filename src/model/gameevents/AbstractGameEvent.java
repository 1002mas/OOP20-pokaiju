package model.gameevents;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.monster.Monster;

/**
 * It implements some generic game event functions.
 * 
 * @author sam
 *
 */
public abstract class AbstractGameEvent implements GameEvent {
    private final int id;
    private final boolean isToActiveImmediatly;
    private boolean isActive;
    private boolean isReactivable;
    private boolean hasBeenActivated;
    private List<GameEvent> eventsToActivate = new ArrayList<>();
    private List<GameEvent> eventsToDeactivate = new ArrayList<>();

    /**
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
     */
    public AbstractGameEvent(int id, boolean isActive, boolean isReactivable, boolean isToActiveImmediatly) {
	this.id = id;
	this.isToActiveImmediatly = isToActiveImmediatly;
	this.isActive = isActive;
	this.isReactivable = isReactivable;
	this.hasBeenActivated = isActive;
    }

    @Override
    public int getEventID() {
	return this.id;
    }

    @Override
    public void addDependentGameEvent(GameEvent e) {
	this.eventsToDeactivate.add(e);
    }

    @Override
    public void addSuccessiveGameEvent(GameEvent e) {
	this.eventsToActivate.add(e);
    }

    @Override
    public void setActivity(boolean active) {

	if (hasBeenActivated && !isReactivable) {
	    this.isActive = false;
	} else {
	    this.isActive = active;
	}
	if (active) {
	    hasBeenActivated = true;
	}
    }

    @Override
    public boolean isActive() {
	return this.isActive;
    }

    @Override
    public boolean isToActivateImmediatly() {
	return this.isToActiveImmediatly;
    }

    @Override
    public boolean isReactivable() {
	return this.isReactivable;
    }

    @Override
    public boolean isBattle() {
	return false;
    }

    @Override
    public List<Monster> getMonster() {
	return new ArrayList<>();
    }

    @Override
    public void activate() {
	if (isActive()) {
	    activateEvent();
	    this.eventsToDeactivate.forEach(e -> e.setActivity(false));

	    for (GameEvent e : eventsToActivate) {
		e.setActivity(true);
		if (e.isToActivateImmediatly()) {
		    e.activate();
		}
	    }

	    this.setActivity(false);

	}
    }

    /**
     * In this function you need to implement the event action.
     */
    protected abstract void activateEvent();

    @Override
    public int hashCode() {
	return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	AbstractGameEvent other = (AbstractGameEvent) obj;
	return id == other.id;
    }

    @Override
    public String toString() {
	return "AbstractGameEvent [id=" + id + ", isActive=" + isActive + "]";
    }

}