package model.npc;

import java.util.List;

import model.monster.Monster;

public interface NpcTrainer extends NpcSimple {
	/**
	 * This function returns trainer monsters owned
	 * 
	 * @return a list of monsters
	 */
	List<Monster> getMonstersOwned();

	/**
	 * This function returns if a trainer is defeated
	 * 
	 * @return true if is defeated, false otherwise
	 */
	 boolean isDefeated();

	/**
	 * This function set if trainer is defeated
	 * 
	 * @param isDefeated
	 */
	 void setDefeated(boolean isDefeated);

}
