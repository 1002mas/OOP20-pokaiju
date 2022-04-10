package model.npc;

import java.util.List;
import java.util.Optional;

import model.Pair;
import model.gameevents.GameEvent;

public interface NpcSimple {

    /**
     * 
     * @return npc Name
     */
    String getName();

    /**
     * 
     * @return npc statement if it is a talking one
     */
    Optional<String> interactWith();

    /**
     * After calling the function {@ #interactWith() interactWith} an event may have been triggered.
     * This function returns the triggered event. 
     * 
     * @return the event if any is triggered, Optional.empty otherwise
     */
    Optional<GameEvent> getTriggeredEvent();

    /**
     * 
     * change the interactWith string value if the npc has more than one sentence
     * and it is active
     * 
     * @param textID the id of the sentence
     */
    void setDialogueText(int textID);

    /**
     * 
     * @return npc type (for more info look at @{TypeOfNpc})
     */
    TypeOfNpc getTypeOfNpc();

    /**
     * 
     * @return NpcPosition
     */
    Pair<Integer, Integer> getPosition();

    /**
     * change the npc position in the map
     * 
     * @param newPosition new position in the map
     */
    void changeNpcPosition(Pair<Integer, Integer> newPosition);

    /**
     * 
     * @return true if the npc is visible, false if it should not been showed
     */
    boolean isVisible();

    /**
     * Set whether the npc must be visible or not.
     * 
     * @param visible true it is visible, false it is not.
     */
    void setVisible(boolean visible);

    /**
     * @return true if you can interact with the npc, false if you cannot
     */
    boolean isEnabled();

    /**
     * Set whether you can interact with the npc or not.
     * 
     * @param enabled
     */
    void setEnabled(boolean enabled);

    /**
     * Add an event that may trigger interacting with the player.
     * 
     * @param gameEvent the game event
     */
    void addGameEvent(GameEvent gameEvent);

    /**
     * 
     * @return a list of game events triggered by interacting with the npc
     */
    List<GameEvent> getGameEvents();

}
