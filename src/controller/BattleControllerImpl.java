package controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.battle.MonsterBattle;
import model.gameitem.GameItem;
import model.gameitem.GameItemTypes;
import model.monster.Monster;
import model.monster.MonsterType;

public class BattleControllerImpl implements BattleController {

    private final MonsterBattle monsterBattle;
    private static boolean enemyCaptured;

    /**
     * Constructor for BattleController.
     * 
     * @param monsterBattle
     */
    public BattleControllerImpl(final MonsterBattle monsterBattle) {
        this.monsterBattle = monsterBattle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerCurrentMonsterId() {
        return monsterBattle.getCurrentPlayerMonster().getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPlayerCurrentMonsterName() {
        return monsterBattle.getCurrentPlayerMonster().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerCurrentMonsterHp() {
        return monsterBattle.getCurrentPlayerMonster().getStats().getHealth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerCurrentMonsterMaxHealth() {
        return monsterBattle.getCurrentPlayerMonster().getMaxHealth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerCurrentMonsterLevel() {
        return monsterBattle.getCurrentPlayerMonster().getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getPlayerTeam() {
        return monsterBattle.getPlayer().getAllMonsters().stream().map(i -> i.getId()).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeMonster(final int monsterIndex) {
        monsterBattle.playerChangeMonster(monsterIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEnemyCurrentMonsterId() {
        return monsterBattle.getCurrentEnemyMonster().getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEnemyCurrentMonsterName() {
        return monsterBattle.getCurrentEnemyMonster().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEnemyCurrentMonsterHp() {
        return monsterBattle.getCurrentEnemyMonster().getStats().getHealth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEnemyCurrentMonsterMaxHealth() {
        return monsterBattle.getCurrentEnemyMonster().getMaxHealth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEnemyCurrentMonsterLevel() {
        return monsterBattle.getCurrentEnemyMonster().getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterType getEnemyCurrentMonsterType() {
        return monsterBattle.getCurrentEnemyMonster().getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEnemyCurrentMove() {
        return monsterBattle.enemyAttack().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getEnemyTeam() {
        return monsterBattle.getNpcEnemy().get().getMonstersOwned().stream().map(i -> i.getId())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMonsterName(final int idMonster) {
        final Optional<Monster> m = getMonsterById(idMonster);
        if (m.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return m.get().getName();
    }

    private Optional<Monster> getMonsterById(final int idMonster) {
        return monsterBattle.getPlayer().getAllMonsters().stream().filter(i -> i.getId() == idMonster).findAny();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean chooseMove(final String moveName) {
        for (int i = 0; i < monsterBattle.getCurrentPlayerMonster().getAllMoves().size(); i++) {
            if (monsterBattle.getCurrentPlayerMonster().getMoves(i).getName().equals(moveName)) {
                return monsterBattle.movesSelection(i);
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean attackWithExtraMove() {
        return this.monsterBattle.attackWithExtraMove();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkPP(final String moveName) {
        return monsterBattle.getCurrentPlayerMonster().isOutOfPP(monsterBattle.getCurrentPlayerMonster().getAllMoves()
                .stream().filter(m -> m.getName().equals(moveName)).findAny().get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOverOfPP() {
        return this.monsterBattle.isOverOfPP();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getMoves() {
        return monsterBattle.getCurrentPlayerMonster().getAllMoves().stream().map(i -> i.getName())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useItem(final String gameItemName, final int monsterId) {
        final GameItem gameItem = monsterBattle.getPlayer().getAllItems().keySet().stream()
                .filter(i -> i.getNameItem().equals(gameItemName)).findAny().get();
        if (gameItem.getType() == GameItemTypes.MONSTERBALL) {
            monsterBattle.getPlayer().useItem(gameItem);
            BattleControllerImpl.enemyCaptured = monsterBattle.capture();
        } else {
            monsterBattle.getPlayer().useItemOnMonster(gameItem, monsterBattle.getPlayer().getAllMonsters().stream()
                    .filter(i -> i.getId() == monsterId).findAny().get());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemNumber(final String gameItemName) {
        return this.monsterBattle.getPlayer().getItemQuantity(monsterBattle.getPlayer().getAllItems().keySet().stream()
                .filter(i -> i.getNameItem().equals(gameItemName)).findAny().get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllPlayerItems() {
        return monsterBattle.getPlayer().getAllItems().keySet().stream()
                .filter(gameItem -> gameItem.getType() != GameItemTypes.EVOLUTIONTOOL)
                .map(gameItem -> gameItem.getNameItem()).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCaptureItem(final String gameItemName) {
        return monsterBattle.getPlayer().getAllItems().keySet().stream()
                .filter(i -> i.getNameItem().equals(gameItemName) && i.getType() == GameItemTypes.MONSTERBALL).findAny()
                .isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive(final int monsterId) {
        return monsterBattle.getPlayer().getAllMonsters().stream().filter(i -> i.getId() == monsterId).findAny().get()
                .isAlive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnemyCaught() {
        return BattleControllerImpl.enemyCaptured;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean flee() {
        return monsterBattle.escape();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        return monsterBattle.isOver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPlayerLost() {
        return monsterBattle.hasPlayerLost();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endingBattle() {
        monsterBattle.endingBattle();
    }
}
