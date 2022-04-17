package model.gameevents;

import java.util.List;

import model.monster.Monster;

/**
 * It represents a generic game event.
 * 
 * @author sam
 *
 */
public interface GameEvent {
    /**
     * 
     * @return event ID
     */
    int getEventID();

    /**
     * Add an event that has to deactivate after this one is triggered.
     * 
     * @param e the event
     */
    void addDependentGameEvent(GameEvent e);

    /**
     * Add an event that has to be set active after this one is triggered.
     * 
     * @param e the event
     */
    void addSuccessiveGameEvent(GameEvent e);

    /**
     * 
     * @param active if true make the event active, if false this event cannot be
     *               activated
     */
    void setActivity(boolean active);

    /**
     * 
     * @return true if the event is active, false otherwise
     */
    boolean isActive();

    /**
     * 
     * @return true if the event is to active as soon as it turns active, false
     *         otherwise
     */
    boolean isToActivateImmediatly();

    /**
     * 
     * @return false can be activated only one time.
     */
    boolean isReactivable();

    /**
     * @return true if a battle is required
     */
    boolean isBattle();

    /**
     * if the isBattle is true these function return a list of enemies. If it is
     * false the monsters are directly added to the player collection
     * 
     * @return monsters list
     */
    List<Monster> getMonster();

    /**
     * This function make the event happens. If the event is not active nothing will
     * happen. If the event is not permanent, it will be automatically deactivated
     * after calling this function.
     */
    void activate();
}
