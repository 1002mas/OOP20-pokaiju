package model.item;
import java.util.Objects;

public class Item {
    private String nameItem;
    private int number;
    private int effect;
    private String description;
    private ItemTypes type;

    public Item(String name, int number, int effect, String description, ItemTypes type) {
	super();
	this.nameItem = name;
	this.number = number;
	this.effect = effect;
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
	return nameItem;
    }

    public void setName(String name) {
	this.nameItem = name;
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

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    @Override
    public int hashCode() {
	return Objects.hash(description, nameItem, number);
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
	return Objects.equals(description, other.description) && Objects.equals(nameItem, other.nameItem)
		&& number == other.number;
    }

    @Override
    public String toString() {
	return nameItem + ", number=" + number + ", description=" + description + ", type= "+ type ;
    }

}
