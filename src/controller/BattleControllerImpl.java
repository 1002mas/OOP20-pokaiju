package controller;

import java.util.List;
import model.battle.MonsterBattle;
import model.battle.Moves;
import model.gameitem.GameItemTypes;
import model.gameitem.GameItems;
import model.monster.Monster;

public class BattleControllerImpl implements BattleController {

    private MonsterBattle monsterBattle;
    private boolean enemyCaptured = false;

    public BattleControllerImpl(MonsterBattle monsterBattle) {
	this.monsterBattle = monsterBattle;
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
    public void useItem(GameItems gameItem, int monsterIndex) {
	monsterBattle.getPlayer().useItem(gameItem, monsterBattle.getPlayer().allMonster().get(monsterIndex));
	if (gameItem.getType() == GameItemTypes.MONSTERBALL) {
	    this.enemyCaptured = monsterBattle.capture();
	}
    }

    @Override
    public List<GameItems> getAllPlayerItems() {
	return monsterBattle.getPlayer().allItems();
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
    public Monster getPlayerCurrentMonster() {
	return monsterBattle.getCurrentPlayerMonster();
    }

    @Override
    public String getPlayerCurrentMonsterName() {
	return monsterBattle.getCurrentPlayerMonster().getName();
    }

    @Override
    public int getPlayerCurrentMonsterHp() {
	return monsterBattle.getCurrentPlayerMonster().getHealth();
    }

    @Override
    public int getPlayerCurrentMonsterMaxHealth() {
	return monsterBattle.getCurrentPlayerMonster().getMaxHealth();
    }

    @Override
    public int getPlayerCurrentMonsterLevel() {
	return monsterBattle.getCurrentPlayerMonster().getLevel();
    }

    @Override
    public List<Monster> getPlayerTeam() {
	return monsterBattle.getPlayer().allMonster();
    }

    @Override
    public Monster getEnemyCurrentMonster() {
	return monsterBattle.getCurrentEnemyMonster();
    }

    @Override
    public String getEnemyCurrentMonsterName() {
	return monsterBattle.getCurrentEnemyMonster().getName();
    }

    @Override
    public int getEnemyCurrentMonsterHp() {
	return monsterBattle.getCurrentEnemyMonster().getHealth();
    }

    @Override
    public int getEnemyCurrentMonsterMaxHealth() {
	return monsterBattle.getCurrentEnemyMonster().getMaxHealth();
    }

    @Override
    public int getEnemyCurrentMonsterLevel() {
	return monsterBattle.getCurrentEnemyMonster().getLevel();
    }

    @Override
    public Moves getEnemyCurrentMove() {
	return monsterBattle.enemyAttack();
    }

    @Override
    public List<Monster> getEnemyTeam() {
	return monsterBattle.getNpcEnemy().get().getMonstersOwned();
    }

    @Override
    public boolean isEnemyCaught() {
	return this.enemyCaptured;
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
