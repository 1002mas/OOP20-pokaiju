package model.gameitem;

public enum GameItemTypes {
    HEAL("Heal"), EVOLUTIONTOOL("EvolutionTool"), MONSTERBALL("MonsterBall");

    final String name;

    GameItemTypes(String name) {
	this.name = name;
    }

    public String toString() {
	return this.name;
    }
}
