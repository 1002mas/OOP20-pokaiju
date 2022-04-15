package model.gameevents;

import java.util.List;

import model.monster.Monster;

public class UniqueMonsterEvent extends AbstractGameEvent {
    private final Monster monster;

    public UniqueMonsterEvent(int id, boolean isActive, boolean isToActiveImmediatly, Monster monster) {
	super(id, isActive, true, isToActiveImmediatly);
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