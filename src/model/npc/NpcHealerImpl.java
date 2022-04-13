package model.npc;

import java.util.List;

import model.Pair;

public class NpcHealerImpl  extends NpcSimpleImpl implements NpcHealer{

	public NpcHealerImpl(String name, List<String> sentences, Pair<Integer, Integer> position, boolean isVisible,
			boolean isEnabled) {
		super(name, sentences, position, isVisible, isEnabled);
		
	}

	

    
}
