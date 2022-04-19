package model.npc;

import java.util.List;

import model.Pair;
import model.monster.Monster;

public class NpcTrainerImpl extends NpcSimpleImpl implements NpcTrainer {
    private static final int DEFEATED_TEXTID = 1;
    private final List<Monster> monstersOwned;
    private boolean isDefeated;

    public NpcTrainerImpl(final String name, final List<String> sentences, final Pair<Integer, Integer> position,
            final boolean isVisible, final boolean isEnabled, final List<Monster> monstersOwned,
            final boolean isDefeated) {
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
    public void setDefeated(final boolean isDefeated) {
        this.isDefeated = isDefeated;
        if (isDefeated()) {
            setDialogueText(DEFEATED_TEXTID);
        } else {
            setDialogueText(0);
        }

    }

    @Override
    public String toString() {
        return "NpcTrainerImpl [monstersOwned=" + monstersOwned + ", isDefeated=" + isDefeated + "]";
    }

}
