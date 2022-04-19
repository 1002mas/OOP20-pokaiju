package model.gameevents;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.monster.Monster;

/**
 * It implements some generic game event functions.
 * 
 *
 */
public abstract class AbstractGameEvent implements GameEvent {
    private final int id;
    private final boolean isToActiveImmediatly;
    private final boolean isReactivable;
    private final List<GameEvent> eventsToActivate = new ArrayList<>();
    private final List<GameEvent> eventsToDeactivate = new ArrayList<>();
    private boolean isActive;
    private boolean hasBeenActivated;

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
    public AbstractGameEvent(final int id, final boolean isActive, final boolean isReactivable,
	    final boolean isToActiveImmediatly) {
	this.id = id;
	this.isToActiveImmediatly = isToActiveImmediatly;
	this.isActive = isActive;
	this.isReactivable = isReactivable;
	this.hasBeenActivated = isActive;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEventID() {
	return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDependentGameEvent(final GameEvent e) {
	this.eventsToDeactivate.add(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSuccessiveGameEvent(final GameEvent e) {
	this.eventsToActivate.add(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActivity(final boolean active) {

	if (hasBeenActivated && !isReactivable) {
	    this.isActive = false;
	} else {
	    this.isActive = active;
	}
	if (active) {
	    hasBeenActivated = true;
	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
	return this.isActive;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isToActivateImmediatly() {
	return this.isToActiveImmediatly;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isReactivable() {
	return this.isReactivable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBattle() {
	return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Monster> getMonster() {
	return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activate() {
	if (isActive()) {
	    activateEvent();
	    this.eventsToDeactivate.forEach(e -> e.setActivity(false));

	    for (final GameEvent e : eventsToActivate) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
	return Objects.hash(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final AbstractGameEvent other = (AbstractGameEvent) obj;
	return id == other.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
	return "AbstractGameEvent [id=" + id + ", isActive=" + isActive + "]";
    }

}
