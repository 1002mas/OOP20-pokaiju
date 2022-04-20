package model.player;

/**
 * Gender of Player.
 * 
 */
public enum Gender {
    /**
     * male gender.
     */
    MAN("male"),
    /**
     * female gender.
     */
    WOMAN("female");

    private final String name;

    Gender(final String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
