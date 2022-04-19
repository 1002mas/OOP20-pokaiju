package model.npc;

import java.util.List;
import java.util.Optional;

import model.Pair;
import model.monster.Monster;
import model.player.Player;

public class NpcHealerImpl extends NpcSimpleImpl {

    private final Player player;

    public NpcHealerImpl(final String name, final List<String> sentences, final Pair<Integer, Integer> position,
            final Player player, final boolean isVisible, final boolean isEnabled) {
        super(name, TypeOfNpc.HEALER, sentences, position, isVisible, isEnabled);
        this.player = player;
    }

    private void heal() {
        for (final Monster monster : this.player.getAllMonsters()) {
            monster.setHealth(monster.getMaxHealth());
            monster.restoreAllMovesPP();
        }

    }

    @Override
    public Optional<String> interactWith() {
        final Optional<String> result = super.interactWith();
        if (isEnabled()) {
            heal();
        }
        return result;

    }

}
