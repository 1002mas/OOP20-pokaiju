package gui;

public enum Direction {
    UP("up", true), DOWN("down", true), RIGHT("right", false), LEFT("left", false);

    private final boolean isVertical;
    private final String name;

    private Direction(String name, boolean isVertical) {
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
