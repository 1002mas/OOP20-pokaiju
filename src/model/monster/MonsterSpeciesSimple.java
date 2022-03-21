package model.monster;

import java.util.Optional;

public class MonsterSpeciesSimple extends AbstractMonsterSpecies{

    public MonsterSpeciesSimple(String name, String info, MonsterType type) {
	super(name, info, type, Optional.empty(), EvolutionType.NONE);
    }

}
