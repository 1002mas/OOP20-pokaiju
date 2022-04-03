package model.gameitem;

import model.monster.*;

public class EvolutionItem extends GameItemImpl {

    public EvolutionItem(String nameItem, int number, String description, GameItemTypes type) {
	super(nameItem, number, description, type);
    }

    @Override
    public boolean use(Monster m) {
	if (m.getSpecies().getEvolutionType() == EvolutionType.ITEM) {
	    return m.evolveByItem(this);
	}
	return false;

    }

}
