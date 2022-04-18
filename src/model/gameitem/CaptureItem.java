package model.gameitem;

import model.monster.Monster;

public class CaptureItem extends AbstractGameItem {
    public CaptureItem(String nameItem, String description) {
	super(nameItem, description, GameItemTypes.MONSTERBALL);
    }

    @Override
    public boolean use(Monster m) {
	return true;
    }

}