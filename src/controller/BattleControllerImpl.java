package controller;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.battle.MonsterBattle;
import model.gameitem.GameItem;
import model.gameitem.GameItemTypes;
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
	return monsterBattle.getCurrentPlayerMonster().getStats().getHealth();
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
	return monsterBattle.getPlayer().getAllMonsters().stream().map(i -> i.getId()).collect(Collectors.toList());
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
	return monsterBattle.getCurrentEnemyMonster().getStats().getHealth();
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
	return monsterBattle.getPlayer().getAllMonsters().stream().filter(i -> i.getId() == idMonster).findAny();
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
	return monsterBattle.getCurrentPlayerMonster().getCurrentPPByMove(monsterBattle.getCurrentPlayerMonster()
		.getAllMoves().stream().filter(m -> m.getName().equals(moveName)).findAny().get());
    }

    @Override
    public boolean checkPP(String moveName) {
	return monsterBattle.getCurrentPlayerMonster().isOutOfPP(monsterBattle.getCurrentPlayerMonster()
		.getAllMoves().stream().filter(m -> m.getName().equals(moveName)).findAny().get());
    }

    @Override
    public List<String> getMoves() {
	return monsterBattle.getCurrentPlayerMonster().getAllMoves().stream().map(i -> i.getName())
		.collect(Collectors.toList());
    }

    @Override
    public void useItem(String gameItemName, int monsterId) {
	GameItem gameItem = monsterBattle.getPlayer().getAllItems().keySet().stream()
		.filter(i -> i.getNameItem().equals(gameItemName)).findAny().get();
	if (gameItem.getType() == GameItemTypes.MONSTERBALL) {
	    monsterBattle.getPlayer().useItem(gameItem);
	    this.enemyCaptured = monsterBattle.capture();
	} else {
	    monsterBattle.getPlayer().useItemOnMonster(gameItem, monsterBattle.getPlayer().getAllMonsters().stream()
		    .filter(i -> i.getId() == monsterId).findAny().get());
	}
    }

    @Override
    public int getItemNumber(String gameItemName) {
	return this.monsterBattle.getPlayer().getItemQuantity(monsterBattle.getPlayer().getAllItems().keySet().stream().filter(i -> i.getNameItem().equals(gameItemName)).findAny()
		.get());
    }

    @Override
    public List<String> getAllPlayerItems() {
	return monsterBattle.getPlayer().getAllItems().keySet().stream()
		.filter(gameItem -> gameItem.getType() != GameItemTypes.EVOLUTIONTOOL)
		.map(gameItem -> gameItem.getNameItem()).collect(Collectors.toList());
    }

    @Override
    public boolean isCaptureItem(String gameItemName) {
	return monsterBattle.getPlayer().getAllItems().keySet().stream()
		.filter(i -> i.getNameItem().equals(gameItemName) && i.getType() == GameItemTypes.MONSTERBALL).findAny()
		.isPresent();
    }

    public boolean canOneMonsterEvolve() {
	return getIdOfEvolvingMonster().isPresent();
    }

    public Optional<Integer> getIdOfEvolvingMonster() {
	Iterator<Monster> i = monsterBattle.getPlayer().getAllMonsters().iterator();
	while (i.hasNext()) {
	    Monster m = i.next();
	    if (m.canEvolveByLevel()) {
		return Optional.of(m.getId());
	    }
	}
	return Optional.empty();
    }

    @Override
    public boolean isAlive(int monsterId) {
	return monsterBattle.getPlayer().getAllMonsters().stream().filter(i -> i.getId() == monsterId).findAny().get()
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
