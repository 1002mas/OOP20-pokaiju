package model.gameitem;

import model.monster.*;

public class HealingItem extends GameItemImpl {
    private int healedHp = 100;// default Hp healed

    public HealingItem(String nameItem, int quantity, String description) {
	this(nameItem, quantity, description, 100);
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
	if (m.getHealth() == m.getMaxHealth()) {
	    return false;
	}
	m.setHealth(healedHp + m.getHealth());
	return true;
    }

}
