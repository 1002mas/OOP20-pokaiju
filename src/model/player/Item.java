package model.player;
import java.util.Objects;

public class Item {
    private String nameObject;
    private int number;
    private String description;
    private ItemTypes type;

    public Item(String name, int number, String description, ItemTypes type) {
	super();
	this.nameObject = name;
	this.number = number;
	this.description = description;
	this.type = type;
    }

    public ItemTypes getType() {
        return type;
    }

    public void setType(ItemTypes type) {
        this.type = type;
    }

    public String getName() {
	return nameObject;
    }

    public void setName(String name) {
	this.nameObject = name;
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

    @Override
    public int hashCode() {
	return Objects.hash(description, nameObject, number);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Item other = (Item) obj;
	return Objects.equals(description, other.description) && Objects.equals(nameObject, other.nameObject)
		&& number == other.number;
    }

    @Override
    public String toString() {
	return nameObject + ", number=" + number + ", description=" + description + ", type= "+ type ;
    }

}
