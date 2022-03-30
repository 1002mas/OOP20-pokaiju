package model.monster;

import java.util.Optional;

public class MonsterSpeciesSimple extends AbstractMonsterSpecies{

    public MonsterSpeciesSimple(String name, String info, MonsterType type, MonsterStats stats) {
	super(name, info, type, stats, Optional.empty(), EvolutionType.NONE);
    }

}
