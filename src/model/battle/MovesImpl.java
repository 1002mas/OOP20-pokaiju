package model.battle;

import java.util.Objects;

import model.monster.MonsterType;

public class MovesImpl implements Moves {

    private MovesData movesData;
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

    @Override
    public int getCurrentPP() {
	// TODO Auto-generated method stub
	return this.pp;
    }

    @Override
    public int hashCode() {
	return Objects.hash(movesData);
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
	return Objects.equals(movesData, other.movesData);
    }
    
}
