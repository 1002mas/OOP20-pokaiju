package model.gameevents;

import java.util.Collections;
import java.util.List;

import model.monster.Monster;
import model.player.Player;

/**
 * This class allows to create an event gives one or more monster to the player.
 * 
 * @author sam
 *
 */
public class MonsterGift extends AbstractGameEvent {
    private final List<Monster> monsters;
    private final Player player;

    /**
     * It creates an event that allows to give the player some monsters.
     * 
     * @param monsters the list of monsters the player will receive.
     * @param player   the player receiving the monsters.
     * 
     *                 {@link AbstractGameEvent#AbstractGameEvent(int id, boolean isActive, boolean isDeactivable, boolean isToActiveImmediatly)
     *                 AbstractGameEvent}
     */
    public MonsterGift(int id, boolean isActive, boolean isReactivable, boolean isToActiveImmediatly,
	    List<Monster> monsters, Player player) {
	super(id, isActive, isReactivable, isToActiveImmediatly);
	this.monsters = monsters;
	this.player = player;
    }

    @Override
    public void activateEvent() {
	for (Monster m : monsters) {
	    player.addMonster(m);
	}
    }

    @Override
    public List<Monster> getMonster() {
	return Collections.unmodifiableList(this.monsters);
    }
}