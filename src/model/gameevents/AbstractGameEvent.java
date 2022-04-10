package model.gameevents;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGameEvent implements GameEvent {
    private final int id;
    private final boolean isToActiveImmediatly;
    private boolean isActive;
    private boolean isDeactivable;
    private List<GameEvent> affectedEvents = new ArrayList<>();

    public AbstractGameEvent(int id, boolean isActive, boolean isDeactivable, boolean isToActiveImmediatly,
	    List<GameEvent> affectedEvents) {
	this.id = id;
	this.isToActiveImmediatly = isToActiveImmediatly;
	this.isActive = isActive;
	this.isDeactivable = isDeactivable;
	this.affectedEvents.addAll(affectedEvents);
    }

    @Override
    public int getEventID() {
	return this.id;
    }

    @Override
    public void setActivity(boolean active) {
	this.isActive = active;
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
    public boolean isPermanent() {
	return !this.isDeactivable;
    }

    @Override
    public void activate() {
	if (isActive()) {
	    activateEvent();
	    for (GameEvent e : affectedEvents) {
		e.setActivity(true);
		if (e.isToActivateImmediatly()) {
		    e.activate();
		}
	    }
	    if (!isPermanent()) {
		this.setActivity(false);
	    }
	}
    }

    protected abstract void activateEvent();
}