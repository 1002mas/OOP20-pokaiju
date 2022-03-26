package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.battle.MonsterBattle;
import model.battle.Moves;
import model.gameitem.GameItems;
import model.monster.Monster;
import model.npc.NpcTrainer;
import model.player.Player;

public class BattleControllerImpl implements BattleController {

    private Player player;
    private MonsterBattle monsterBattle;
    private Monster monster;
    private NpcTrainer npcTrainer;

    public BattleControllerImpl(Player player, MonsterBattle monsterBattle, NpcTrainer npcTrainer, Monster monster) {
	this.player = player;
	this.monsterBattle = monsterBattle;
	this.npcTrainer = npcTrainer;
	this.monster = monster;
    }

    @Override
    public boolean chooseMove(int moveIndex) {
	if (monster.getMoves(moveIndex).checkPP()) {
	    monsterBattle.movesSelection(moveIndex);
	    return true;
	}
	return false;
    }

    @Override
    public boolean flee() {
	return monsterBattle.escape();
    }

    @Override
    public void useItem(GameItems gameItem) {
	player.useItem(gameItem, this.monster);
    }

    @Override
    public void changeMonster(int monsterIndex) {
	monsterBattle.playerChangeMonster(monsterIndex);
    }

    @Override
    public List<Moves> getMoves() {
	List<Moves> moves = new ArrayList<>();
	for (int i = 0; i < monster.getNumberOfMoves(); i++) {
	    moves.add(this.monster.getMoves(i));
	}
	return moves;
    }

    @Override
    public List<Monster> getPlayerTeam() {
	return player.allMonster();
    }

    @Override
    public List<Monster> getEnemyTeam() {
	return npcTrainer.getMonstersOwned();
    }

}
