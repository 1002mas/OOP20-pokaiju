package model.npc;

import java.util.List;
import java.util.Map;
import model.Pair;
import model.gameitem.GameItem;
import model.player.Player;

public class NpcMerchantImpl extends NpcSimpleImpl implements NpcMerchant {

    private final Map<GameItem, Integer> inventory;

    public NpcMerchantImpl(final String name, final List<String> sentences, final Pair<Integer, Integer> position,
            final boolean isVisible, final boolean isEnabled, final Map<GameItem, Integer> inventory) {
        super(name, TypeOfNpc.MERCHANT, sentences, position, isVisible, isEnabled);
        this.inventory = inventory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<GameItem, Integer> getInventory() {
        return this.inventory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPrice(final GameItem item) {
        if (this.inventory.containsKey(item)) {
            return inventory.get(item);
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalPrice(final List<Pair<GameItem, Integer>> list) {
        int sum = 0;
        for (final Pair<GameItem, Integer> item : list) {
            sum = sum + (getPrice(item.getFirst()) * item.getSecond());
        }
        return sum;
    }

    private void addItems(final List<Pair<GameItem, Integer>> list, final Player player) {
        for (final Pair<GameItem, Integer> item : list) {
            for (int i = 0; i < item.getSecond(); i++) {
                player.addItem(item.getFirst());
            }

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean buyItem(final List<Pair<GameItem, Integer>> itemList, final Player player) {
        if ((player.getMoney() - getTotalPrice(itemList)) >= 0) {
            player.setMoney(player.getMoney() - getTotalPrice(itemList));
            addItems(itemList, player);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "NpcMerchantImpl [inventory=" + inventory + "]";
    }
}
