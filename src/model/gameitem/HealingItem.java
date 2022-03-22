package model.gameitem;

import model.monster.*;

public class HealingItem extends AbstractGameItem {
    private int healedHp = 100;// default Hp healed

    public HealingItem(String nameItem, int quantity, String description, GameItemTypes type) {
	this(nameItem, quantity, description, type, 100);
    }

    public HealingItem(String nameItem, int quantity, String description, GameItemTypes type, int healedHp) throws IllegalArgumentException{
	super(nameItem, quantity, description, type);
	if(healedHp<0) throw new IllegalArgumentException("healedHp must be greater than zero");
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
