package model.gameitem;

import java.util.Objects;

import model.monster.Monster;

public class GameItemImpl implements GameItem {
    private String nameItem;
    private String description;
    private GameItemTypes type;

    public GameItemImpl(String nameItem, String description) {
	this(nameItem, description, GameItemTypes.MONSTERBALL);
    }

    protected GameItemImpl(String nameItem, String description, GameItemTypes type) {
	super();
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

    public boolean use(Monster m) {
	return true;
    }

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
	GameItemImpl other = (GameItemImpl) obj;
	return Objects.equals(nameItem, other.nameItem) && type == other.type;
    }

}