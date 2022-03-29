package controller;

import java.util.List;
import model.battle.MonsterBattle;
import model.battle.Moves;
import model.gameitem.GameItems;
import model.monster.Monster;
import model.npc.NpcTrainer;
import model.player.Player;

public class BattleControllerImpl implements BattleController {

    private Player player;
    private MonsterBattle monsterBattle;
    private NpcTrainer npcTrainer;

    public BattleControllerImpl(Player player, MonsterBattle monsterBattle, NpcTrainer npcTrainer) {
	this.player = player;
	this.monsterBattle = monsterBattle;
	this.npcTrainer = npcTrainer;
    }

    @Override
    public boolean chooseMove(int moveIndex) {
	if (monsterBattle.getCurrentPlayerMonster().getMoves(moveIndex).checkPP()) {
	    monsterBattle.movesSelection(moveIndex);
	    return true;
	}
	return false;
    }

    @Override
    public void useItem(GameItems gameItem) {
	player.useItem(gameItem, monsterBattle.getCurrentPlayerMonster());
    }

    @Override
    public List<GameItems> getAllPlayerItems() {
	return player.allItems();
    }

    @Override
    public void changeMonster(int monsterIndex) {
	monsterBattle.playerChangeMonster(monsterIndex);
    }

    @Override
    public List<Moves> getMoves() {
	return monsterBattle.getCurrentPlayerMonster().getAllMoves();
    }

    @Override
    public Monster getCurrentPlayerMonster() {
	return monsterBattle.getCurrentPlayerMonster();
    }

    @Override
    public String getCurrentPlayerMonsterName() {
	return monsterBattle.getCurrentPlayerMonster().getName();
    }

    @Override
    public int getCurrentPlayerMonsterHp() {
	return monsterBattle.getCurrentPlayerMonster().getHealth();
    }

    @Override
    public int getCurrentPlayerMonsterLevel() {
	return monsterBattle.getCurrentPlayerMonster().getLevel();
    }

    @Override
    public List<Monster> getPlayerTeam() {
	return player.allMonster();
    }

    @Override
    public Monster getCurrentEnemyMonster() {
	return monsterBattle.getCurrentEnemyMonster();
    }

    @Override
    public String getCurrentEnemyMonsterName() {
	return monsterBattle.getCurrentPlayerMonster().getName();
    }

    @Override
    public int getCurrentEnemyMonsterHp() {
	return monsterBattle.getCurrentEnemyMonster().getHealth();
    }

    @Override
    public int getCurrentEnemyMonsterLevel() {
	return monsterBattle.getCurrentPlayerMonster().getLevel();
    }

    @Override
    public Moves getCurrentEnemyMove() {
	return monsterBattle.enemyAttack();
    }

    @Override
    public List<Monster> getEnemyTeam() {
	return npcTrainer.getMonstersOwned();
    }

    @Override
    public boolean capture() {
	return monsterBattle.capture();
    }

    @Override
    public boolean flee() {
	return monsterBattle.escape();
    }

    @Override
    public boolean isOver() {
	return monsterBattle.isOver();
    }

}
