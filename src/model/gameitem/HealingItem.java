package model.gameitem;

import model.monster.*;

public class HealingItem extends GameItemImpl {
    private static final int DEFAULTHEALINGPOINT = 50;// default Hp healed
    private int healedHp;
    public HealingItem(String nameItem, int quantity, String description) {
	this(nameItem, quantity, description, DEFAULTHEALINGPOINT);
    }

    public HealingItem(String nameItem, int quantity, String description, int healedHp)
	    throws IllegalArgumentException {
	super(nameItem, quantity, description, GameItemTypes.HEAL);
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
