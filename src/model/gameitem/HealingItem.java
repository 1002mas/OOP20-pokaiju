package model.gameitem;

import model.monster.*;

public class HealingItem extends AbstractGameItem {
    private static final int DEFAULTHEALINGPOINT = 50;
    private int healedHp;

    public HealingItem(String nameItem, String description) {
	this(nameItem, description, DEFAULTHEALINGPOINT);
    }

    public HealingItem(String nameItem, String description, int healedHp) throws IllegalArgumentException {
	super(nameItem, description, GameItemTypes.HEAL);
	if (healedHp < 0)
	    throw new IllegalArgumentException("healedHp must be greater than zero");
	this.healedHp = healedHp;
    }

    @Override
    public boolean use(Monster m) {
	if (m.getStats().getHealth() == m.getMaxHealth()) {
	    return false;
	}
	m.setHealth(healedHp + m.getStats().getHealth());
	return true;
    }

}
