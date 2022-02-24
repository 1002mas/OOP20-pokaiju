
public class Item {
    private String nameObject;
    private int number;
    private String description;

    public Item(String name, int number, String description) {
	super();
	this.nameObject = name;
	this.number = number;
	this.description = description;
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

}
