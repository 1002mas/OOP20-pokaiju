package model.gameitem;

import model.monster.Monster;

public class CaptureItem extends AbstractGameItem {
    /**
     * 
     * @param nameItem
     * @param description
     */
    public CaptureItem(final String nameItem, final String description) {
	super(nameItem, description, GameItemTypes.MONSTERBALL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean use(final Monster m) {
	return true;
    }

}