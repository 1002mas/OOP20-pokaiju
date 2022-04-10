package model.battle;

import java.util.Objects;

import model.monster.MonsterType;

public class MovesImpl implements Moves {
    private String name;
    private int base;
    private MonsterType type;
    private int maxPP;
    

    public MovesImpl(String name, int base, MonsterType type, int pp) {

	this.name = name;
	this.base = base;
	this.type = type;
	this.maxPP = pp;
	
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
	return maxPP;
    }

    @Override
    public int hashCode() {
	return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	MovesImpl other = (MovesImpl) obj;
	return Objects.equals(name, other.name);
    }

    @Override
    public int getDamage(MonsterType type) {
	// TODO Auto-generated method stub
	return (int) this.type.damageTo(type);
    }
    
    

}
