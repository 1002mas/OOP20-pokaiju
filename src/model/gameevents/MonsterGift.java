package model.gameevents;

import java.util.Collections;
import java.util.List;

import model.monster.Monster;
import model.player.Player;

/**
 * This class allows to create an event gives one or more monster to the player.
 * 
 *
 */
public class MonsterGift extends AbstractGameEvent {
    private final List<Monster> monsters;
    private final Player player;

    /**
     * It creates an event that allows to give the player some monsters.
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
     * @param monsters             the list of monsters the player will receive.
     * @param player               the player receiving the monsters.
     */
    public MonsterGift(final int id, final boolean isActive, final boolean isReactivable,
	    final boolean isToActiveImmediatly, final List<Monster> monsters, final Player player) {
	super(id, isActive, isReactivable, isToActiveImmediatly);
	this.monsters = monsters;
	this.player = player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activateEvent() {
	for (final Monster m : monsters) {
	    player.addMonster(m);
	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Monster> getMonster() {
	return Collections.unmodifiableList(this.monsters);
    }
}
