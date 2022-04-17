package model.npc;

import java.util.List;
import java.util.Optional;

import model.Pair;
import model.gameevents.GameEvent;

public interface NpcSimple {

	/**
	 * This function returns npc name
	 * 
	 * @return npc name
	 */
	String getName();

	/**
	 * This function return npc statement if it is a talking one
	 * 
	 * @return an Optional<String> containing a sentence if is a talking one, an
	 *         Optioanl.empty otherwise
	 */
	Optional<String> interactWith();

	/**
	 * After calling the function {@ #interactWith() interactWith} an event may have
	 * been triggered. This function returns the triggered event.
	 * 
	 * @return the event if any is triggered, Optional.empty otherwise
	 */
	Optional<GameEvent> getTriggeredEvent();

	/**
	 * 
	 * This function changes the interactWith string value if the npc has more than
	 * one sentence and it is active
	 * 
	 * @param textID the id of the sentence
	 */
	void setDialogueText(int textID);

	/**
	 * This function returns the type of npc
	 * 
	 * @return npc type (for more info look at @{TypeOfNpc})
	 */
	TypeOfNpc getTypeOfNpc();

	/**
	 * This function returns npc position
	 * 
	 * @return NpcPosition
	 */
	Pair<Integer, Integer> getPosition();

	/**
	 * This function changes the npc position in the map
	 * 
	 * @param newPosition new position
	 */
	void changeNpcPosition(Pair<Integer, Integer> newPosition);

	/**
	 * This function returns if the npc is visible
	 * 
	 * @return true if the npc is visible, false otherwise
	 */
	boolean isVisible();

	/**
	 * This function set whether the npc must be visible or not.
	 * 
	 * @param visible true it is visible, false it is not.
	 */
	void setVisible(boolean visible);

	/**
	 * This function returns if the npc is enabled to interact with player
	 * 
	 * @return true if player can interact with, false otherwise
	 */
	boolean isEnabled();

	/**
	 * This function set whether player can interact with the npc or not.
	 * 
	 * @param enabled
	 */
	void setEnabled(boolean enabled);

	/**
	 * This function adds an event that may trigger interacting with the player.
	 * 
	 * @param gameEvent the game event
	 */
	void addGameEvent(GameEvent gameEvent);

	/**
	 * This function returns a list of game events triggered by interacting with the
	 * npc
	 * 
	 * @return npc game events
	 */
	List<GameEvent> getGameEvents();

	/**
	 * This function returns index of the current sentence
	 * 
	 * @return index current sentence
	 */
	int getCurrentSetence();

}
