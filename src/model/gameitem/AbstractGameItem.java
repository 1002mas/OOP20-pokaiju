package model.gameitem;

import java.util.Objects;

import model.monster.Monster;

public abstract class AbstractGameItem implements GameItem {
    private final String nameItem;
    private final String description;
    private final GameItemTypes type;

    /**
     * 
     * @param nameItem
     * @param description
     * @param type
     */
    public AbstractGameItem(final String nameItem, final String description, final GameItemTypes type) {
	this.nameItem = nameItem;
	this.description = description;
	this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    public String getNameItem() {
	return nameItem;
    }

    /**
     * {@inheritDoc}
     */
    public String getDescription() {
	return description;
    }

    /**
     * {@inheritDoc}
     */
    public GameItemTypes getType() {
	return type;
    }

    /**
     * {@inheritDoc}
     */
    public abstract boolean use(Monster m);

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
	return "nameItem=" + nameItem + ", description=" + description + ", type=" + type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
	return Objects.hash(nameItem, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final AbstractGameItem other = (AbstractGameItem) obj;
	return Objects.equals(nameItem, other.nameItem) && type == other.type;
    }

}
