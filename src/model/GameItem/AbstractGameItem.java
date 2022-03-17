package model.GameItem;

import java.util.Objects;

import model.monster.Monster;

public abstract class AbstractGameItem implements GameItems {
    private String nameItem;
    private int number;
    private String description;
    private GameItemTypes type;

    public AbstractGameItem(String nameItem, int number, String description, GameItemTypes type) {
	super();
	this.nameItem = nameItem;
	this.number = number;
	this.description = description;
	this.type = type;
    }

    public String getNameItem() {
	return nameItem;
    }

    public void setNameItem(String nameItem) {
	this.nameItem = nameItem;
    }

    public int getNumber() {
	return number;
    }

    public void setNumber(int number) {
	this.number = number;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public GameItemTypes getType() {
	return type;
    }

    public void setType(GameItemTypes type) {
	this.type = type;
    }

    @Override
    public int hashCode() {
	return Objects.hash(description, nameItem, number, type);
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
	return Objects.equals(description, other.description) && Objects.equals(nameItem, other.nameItem)
		&& number == other.number && type == other.type;
    }

    public abstract boolean use(Monster m);

    @Override
    public String toString() {
	return "AbstractGameItem [nameItem=" + nameItem + ", number=" + number + ", description=" + description
		+ ", type=" + type + "]";
    }
    
}