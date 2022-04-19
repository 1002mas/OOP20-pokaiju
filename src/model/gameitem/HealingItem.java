package model.gameitem;

import model.monster.Monster;

public class HealingItem extends AbstractGameItem {
    private static final int DEFAULT_HEALING_POINTS = 50;
    private int healedHp;

    /**
     * 
     * @param nameItem
     * @param description
     */
    public HealingItem(final String nameItem, final String description) {
	this(nameItem, description, DEFAULT_HEALING_POINTS);
    }

    /**
     * 
     * @param nameItem
     * @param description
     * @param healedHp
     * 
     */
    public HealingItem(final String nameItem, final String description, final int healedHp) {
	super(nameItem, description, GameItemTypes.HEAL);
	if (healedHp < 0) {
	    throw new IllegalArgumentException("Healed Hp must be greater than zero");
	}
	this.healedHp = healedHp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean use(final Monster m) {
	if (m.getStats().getHealth() == m.getMaxHealth()) {
	    return false;
	}
	m.setHealth(healedHp + m.getStats().getHealth());
	return true;
    }

}
