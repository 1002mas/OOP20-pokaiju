package controller;

import java.util.List;

import model.battle.Moves;
import model.gameitem.GameItems;
import model.monster.Monster;

public class BattleControllerImpl implements BattleController{

    @Override
    public boolean chooseMove(int moveIndex) {
	// TODO
	return false;
    }

    @Override
    public boolean flee() {
	// TODO
	return false;
    }

    @Override
    public void useItem(GameItems gameItem) {
	// TODO 
    }

    @Override
    public void changeMonster() {
	// TODO
    }

    @Override
    public List<Moves> getMoves() {
	// TODO 
	return null;
    }

    @Override
    public List<Monster> getPlayerTeam() {
	// TODO 
	return null;
    }

}
