package model.GameItem;

import model.monster.*;

public class HealingItem extends AbstractGameItem {
    private int healedHp;

    public HealingItem(String nameItem, int number, String description, GameItemTypes type) {
	super(nameItem, number, description, type);
    }

    public HealingItem(String nameItem, int number, String description, GameItemTypes type, int healedHp) {
	super(nameItem, number, description, type);
	this.healedHp = healedHp;
    }

    @Override
    public boolean use(Monster m) {
	if (m.getHealth() == m.getMaxHealth()) {
	    return false;
	}
	if (healedHp + m.getHealth() >= m.getMaxHealth()) {
	    m.setHealth(m.getMaxHealth());
	} else {
	    m.setHealth(healedHp + m.getHealth());
	}
	return true;
    }

}
