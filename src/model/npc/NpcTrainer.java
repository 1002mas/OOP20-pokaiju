package model.npc;
import java.util.ArrayList;

import model.monster.Monster;

public interface NpcTrainer extends NpcSimple{
	ArrayList<Monster> getMonstersOwned();
	public boolean isDefeated();
	public void setDefeated();	
}
