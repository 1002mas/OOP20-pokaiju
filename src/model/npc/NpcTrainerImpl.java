package model.npc;

import java.util.List;

import model.Pair;
import model.monster.Monster;


public class NpcTrainerImpl extends NpcSimpleImpl implements NpcTrainer {
    private static final int DEFEATED_TEXTID = 1;
    private List<Monster> monstersOwned;
    private boolean isDefeated;

    public NpcTrainerImpl(String name, List<String> sentences,
	    Pair<Integer, Integer> position, boolean isVisible, boolean isEnabled, List<Monster> monstersOwned,
	    boolean isDefeated) {
	super(name, TypeOfNpc.TRAINER, sentences, position, isVisible, isEnabled);
	this.monstersOwned = monstersOwned;
	this.isDefeated = isDefeated;
    }

    @Override
    public List<Monster> getMonstersOwned() {
	return this.monstersOwned;
    }

    @Override
    public boolean isDefeated() {
	return this.isDefeated;
    }

    @Override
    public void setDefeated() {
	this.isDefeated = true;
	setDialogueText(DEFEATED_TEXTID);
    }

}
