package controller;

public enum Direction {
    /**
     * direction UP.
     */
    UP("up", true),
    /**
     * direction DOWN.
     */
    DOWN("down", true),
    /**
     * direction RIGHT.
     */
    RIGHT("right", false),
    /**
     * direction LEFT.
     */
    LEFT("left", false);

    private final boolean isVertical;
    private final String name;

    Direction(final String name, final boolean isVertical) {
        this.isVertical = isVertical;
        this.name = name;
    }

    public boolean isVertical() {
        return this.isVertical;
    }

    public boolean isHorizontal() {
        return !this.isVertical;
    }

    public String toString() {
        return this.name;
    }
}
