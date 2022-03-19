package model.battle;

import java.util.*;

import model.monster.MonsterType;

public class MovesImpl implements Moves {

    private MovesDataImpl movesData;
    private int pp;
    Map<MonsterType, Map<MonsterType, Double>> buff = new HashMap<>();

    public MovesImpl(String name, int base, MonsterType type, int pp) {
	this.movesData = new MovesDataImpl(name, base, type, pp);
	this.pp = pp;
	Map<MonsterType, Double> temp = new HashMap<>();
	temp.put(MonsterType.WATER, 0.50);
	temp.put(MonsterType.FIRE, 0.75);
	temp.put(MonsterType.GRASS, 1.50);
	buff.put(MonsterType.FIRE, temp);
	temp = new HashMap<>();
	temp.put(MonsterType.WATER, 0.75);
	temp.put(MonsterType.FIRE, 1.25);
	temp.put(MonsterType.GRASS, 0.50);
	buff.put(MonsterType.WATER, temp);
	temp = new HashMap<>();
	temp.put(MonsterType.WATER, 1.25);
	temp.put(MonsterType.FIRE, 0.50);
	temp.put(MonsterType.GRASS, 0.75);
	buff.put(MonsterType.GRASS, temp);

    }

    @Override
    public boolean checkPP() {
	// TODO Auto-generated method stub
	if (this.pp <= 0) {
	    return false;
	}
	return true;
    }

    @Override
    public String getName() {
	// TODO Auto-generated method stub
	return this.movesData.getName();
    }

    @Override
    public int getDamage(MonsterType enemytype) {
	// TODO Auto-generated method stub
	return (int) (this.movesData.getBase() * buff.get(this.movesData.getType()).get(enemytype));
    }

    @Override
    public void decPP() {
	// TODO Auto-generated method stub
	this.pp--;
    }

    @Override
    public String toString() {
	return "MovesImpl [name=" + this.movesData.getName() + ", base=" + this.movesData.getBase() + ", type="
		+ this.movesData.getType() + "]";
    }

}
