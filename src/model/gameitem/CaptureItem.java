package model.gameitem;

import model.monster.*;

public class CaptureItem extends AbstractGameItem {

    public CaptureItem(String nameItem, int number, String description, GameItemTypes type) {
	super(nameItem, number, description, type);
    }

    @Override
    public boolean use(Monster m) {
	return true;
    }

}
