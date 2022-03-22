package model.gameitem;

import model.monster.*;

public class SimpleItem extends AbstractGameItem {

    public SimpleItem(String nameItem, int number, String description, GameItemTypes type) {
	super(nameItem, number, description, type);
    }

    @Override
    public boolean use(Monster m) {
	return true;
    }

}
