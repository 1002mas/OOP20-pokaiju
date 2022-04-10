package model.npc;

import java.util.List;

import model.monster.Monster;
public interface NpcTrainer extends NpcSimple{
	List<Monster> getMonstersOwned();
	
	public boolean isDefeated();
	
	public void setDefeated();	

}
