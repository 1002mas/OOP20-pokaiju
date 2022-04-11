package model.gameevents;

import java.util.List;

import model.monster.Monster;
import model.player.Player;

public class MonsterGift extends AbstractGameEvent {
    private final List<Monster> monsters;
    private final Player player;

    public MonsterGift(int id, boolean isActive, boolean isDeactivable, boolean isToActiveImmediatly,
	    List<Monster> monsters, Player player) {
	super(id, isActive, isDeactivable, isToActiveImmediatly);
	this.monsters = monsters;
	this.player = player;
    }

    @Override
    public void activateEvent() {
	for (Monster m : monsters) {
	    player.addMonster(m);
	}
    }

    public List<Monster> getReceivedMonsters() {
	return this.monsters;
    }
}