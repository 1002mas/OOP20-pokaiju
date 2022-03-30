package model.battle;

import model.monster.MonsterType;

public class MovesDataImpl implements MovesData {
    private String name;
    private int base;
    private MonsterType type;
    private int pp;

    public MovesDataImpl(String name, int base, MonsterType type, int pp) {

	this.name = name;
	this.base = base;
	this.type = type;
	this.pp = pp;
    }

    public String getName() {
	return name;
    }

    public int getBase() {
	return base;
    }

    public MonsterType getType() {
	return type;
    }

    public int getPP() {
	return pp;
    }


}
