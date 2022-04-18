package model.gameitem;

import java.util.Objects;

import model.monster.Monster;

public abstract class AbstractGameItem implements GameItem {
    private String nameItem;
    private String description;
    private GameItemTypes type;

    public AbstractGameItem(String nameItem, String description, GameItemTypes type) {
	this.nameItem = nameItem;
	this.description = description;
	this.type = type;
    }

    public String getNameItem() {
	return nameItem;
    }

    public String getDescription() {
	return description;
    }

    public GameItemTypes getType() {
	return type;
    }

    public abstract boolean use(Monster m);

    @Override
    public String toString() {
	return "nameItem=" + nameItem + ", description=" + description + ", type=" + type;
    }

    @Override
    public int hashCode() {
	return Objects.hash(nameItem, type);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	AbstractGameItem other = (AbstractGameItem) obj;
	return Objects.equals(nameItem, other.nameItem) && type == other.type;
    }

}
