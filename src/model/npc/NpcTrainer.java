package model.npc;

import java.util.List;

import model.monster.Monster;
public interface NpcTrainer extends NpcSimple{
	/**
	 * 
	 * @return a List of monsters owned
	 */
	List<Monster> getMonstersOwned();
	
	/**
	 * 
	 * @return true if is defeated, false otherwise
	 */
	public boolean isDefeated();
	
	/**
	 *  set isDefeated filed true 
	 */
	public void setDefeated(boolean isDefeated);	

}
