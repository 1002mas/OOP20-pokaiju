package model.battle;

import model.monster.MonsterType;

public class MovesImpl implements Moves {

    private MovesDataImpl movesData;
    private int pp;
    

    public MovesImpl(String name, int base, MonsterType type, int pp) {
	this.movesData = new MovesDataImpl(name, base, type, pp);
	this.pp = pp;
	

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
	return (int) (this.movesData.getBase() * this.movesData.getType().damageTo(enemytype));
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

    @Override
    public MovesData getData() {
	// TODO Auto-generated method stub
	return this.movesData;
    }

}
