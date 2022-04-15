package model.npc;

import java.util.List;

import model.monster.Monster;
public interface NpcTrainer extends NpcSimple{
	/**
	 * 
	 * @return a list of monsters owned
	 */
	List<Monster> getMonstersOwned();
	
	/**
	 * 
	 * @return true if is defeated, false otherwise
	 */
	public boolean isDefeated();
	
	/**
	 * this function set if is defeated
	 * 
	 * @param isDefeated
	 */
	public void setDefeated(boolean isDefeated);	

}
