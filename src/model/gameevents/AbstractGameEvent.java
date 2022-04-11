package model.gameevents;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGameEvent implements GameEvent {
    private final int id;
    private final boolean isToActiveImmediatly;
    private boolean isActive;
    private boolean isDeactivable;
    private List<GameEvent> eventsToActivate = new ArrayList<>();
    private List<GameEvent> eventsToDeactivate = new ArrayList<>();

    public AbstractGameEvent(int id, boolean isActive, boolean isDeactivable, boolean isToActiveImmediatly) {
	this.id = id;
	this.isToActiveImmediatly = isToActiveImmediatly;
	this.isActive = isActive;
	this.isDeactivable = isDeactivable;
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
	    this.eventsToDeactivate.forEach(e -> e.setActivity(false));
	    
	    for (GameEvent e : eventsToActivate) {
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