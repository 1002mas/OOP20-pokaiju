package model.battle;

import model.monster.MonsterType;

public interface Moves {

    String getName();

    int getBase();

    MonsterType getType();

    int getPP();
    
    int getDamage(MonsterType type);
    
    
}
