package controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.battle.MonsterBattle;
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
    public int getPlayerCurrentMonsterId() {
	return monsterBattle.getCurrentPlayerMonster().getId();
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
    public List<Integer> getPlayerTeam() {
	return monsterBattle.getPlayer().allMonster().stream().map(i -> i.getId()).collect(Collectors.toList());
    }

    @Override
    public void changeMonster(int monsterIndex) {
	monsterBattle.playerChangeMonster(monsterIndex);
    }

    @Override
    public int getEnemyCurrentMonsterId() {
	return monsterBattle.getCurrentEnemyMonster().getId();
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
    public String getEnemyCurrentMove() {
	return monsterBattle.enemyAttack().getName();
    }

    @Override
    public List<Integer> getEnemyTeam() {
	return monsterBattle.getNpcEnemy().get().getMonstersOwned().stream().map(i -> i.getId())
		.collect(Collectors.toList());
    }

    @Override
    public String getMonsterName(int idMonster) {
	Optional<Monster> m = getMonsterById(idMonster);
	if (m.isEmpty()) {
	    throw new IllegalArgumentException();
	}
	return m.get().getName();
    }

    private Optional<Monster> getMonsterById(int idMonster) {
	return monsterBattle.getPlayer().allMonster().stream().filter(i -> i.getId() == idMonster).findAny();
    }

    @Override
    public boolean chooseMove(String moveName) {
	for (int i = 0; i < monsterBattle.getCurrentPlayerMonster().getAllMoves().size(); i++) {
	    if (monsterBattle.getCurrentPlayerMonster().getMoves(i).getName().equals(moveName)) {
		return monsterBattle.movesSelection(i);
	    }
	}
	return false;
    }

    @Override
    public int getCurrentPP(String moveName) {
	return monsterBattle.getCurrentPlayerMonster().getAllMoves().stream().filter(m -> m.getName().equals(moveName))
		.findAny().get().getCurrentPP();
    }

    @Override
    public boolean checkPP(String moveName) {
	return monsterBattle.getCurrentPlayerMonster().getAllMoves().stream().filter(m -> m.getName().equals(moveName))
		.findAny().get().checkPP();
    }

    @Override
    public List<String> getMoves() {
	return monsterBattle.getCurrentPlayerMonster().getAllMoves().stream().map(i -> i.getName())
		.collect(Collectors.toList());
    }

    @Override
    public void useItem(String gameItemName, int monsterId) {
	GameItems gameItem = monsterBattle.getPlayer().allItems().stream()
		.filter(i -> i.getNameItem().equals(gameItemName)).findAny().get();
	monsterBattle.getPlayer().useItem(gameItem, monsterBattle.getPlayer().allMonster().stream().filter(i -> i.getId() == monsterId).findAny().get());
	if (gameItem.getType() == GameItemTypes.MONSTERBALL) {
	    this.enemyCaptured = monsterBattle.capture();
	}
    }

    @Override
    public int getItemNumber(String gameItemName) {
	return monsterBattle.getPlayer().allItems().stream().filter(i -> i.getNameItem().equals(gameItemName)).findAny()
		.get().getNumber();
    }

    @Override
    public List<String> getAllPlayerItems() {
	return monsterBattle.getPlayer().allItems().stream()
		.filter(gameItem -> gameItem.getType() != GameItemTypes.EVOLUTIONTOOL)
		.map(gameItem -> gameItem.getNameItem()).collect(Collectors.toList());
    }
    
    @Override
    public boolean isCaptureItem(String gameItemName) {
	return monsterBattle.getPlayer().allItems().stream().filter(i -> i.getNameItem().equals(gameItemName) && i.getType() == GameItemTypes.MONSTERBALL).findAny().isPresent();
    }

    @Override
    public boolean isAlive(int monsterId) {
	return monsterBattle.getPlayer().allMonster().stream().filter(i -> i.getId() == monsterId).findAny().get()
		.isAlive();
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
