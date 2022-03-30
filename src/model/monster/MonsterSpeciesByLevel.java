package model.monster;
import java.util.Optional;

public class MonsterSpeciesByLevel extends AbstractMonsterSpecies {

    private int evolutionLevel;

    public MonsterSpeciesByLevel(String name, String info, MonsterType type, MonsterStats stats, MonsterSpecies evolution, int evolutionLevel) {
	super(name, info, type, stats, Optional.of(evolution), EvolutionType.LEVEL);
	this.evolutionLevel = evolutionLevel;
    }

    public int getEvolutionLevel() {
	return this.evolutionLevel;
    }

}
