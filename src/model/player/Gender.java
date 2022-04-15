package model.player;
/**
 * Gender of Player
 * 
 * @author Guo jiahao
 */
public enum Gender {
    MAN("male"), WOMAN("female");

    final String name;

    private Gender(String name) {
	this.name = name;
    }

    public String toString() {
	return this.name;
    }
}
