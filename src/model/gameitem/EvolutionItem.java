package model.gameitem;

import model.monster.*;

public class EvolutionItem extends AbstractGameItem {
    private MonsterType monstertype;

    public EvolutionItem(String nameItem, int number, String description, GameItemTypes type) {
	super(nameItem, number, description, type);
    }

    public EvolutionItem(String nameItem, int number, String description, GameItemTypes type, MonsterType monstertype) {
	super(nameItem, number, description, type);
	this.monstertype = monstertype;
    }

    @Override
    public boolean use(Monster m) {
	if(this.monstertype.equals(m.getType())) {
	    return true;
	}
	return false;

    }

}
