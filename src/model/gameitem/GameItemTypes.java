package model.gameitem;
/**
 * Type of gameitem
 * 
 * @author Guo jiahao
 */
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
