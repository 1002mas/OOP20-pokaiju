package model.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.monster.Monster;

public class MonsterStorageImpl implements MonsterStorage {

    private static final int MAX_NUMBER_OF_BOX = 10;
    private static final int MAX_SIZE_OF_BOX = 10;
    private static final String INITIAL_BOX_NAME = "BOX";

    private List<MonsterBox> monsterBoxes;
    private int currentMonsterBoxIndex;
    private Player player;

    public MonsterStorageImpl(final Player player, final List<MonsterBox> boxes) {
        this.player = player;
        this.monsterBoxes = new ArrayList<>(boxes);
        this.currentMonsterBoxIndex = 0;
        if (this.monsterBoxes.size() > MAX_NUMBER_OF_BOX) {
            this.monsterBoxes = this.monsterBoxes.subList(0, MAX_NUMBER_OF_BOX);
        } else {
            generateBoxs(this.monsterBoxes.size());
        }
    }

    public MonsterStorageImpl(final Player player) {
        this(player, new ArrayList<>());

    }

    private void generateBoxs(final int n) {
        MonsterBox box;
        for (int i = n; i < MAX_NUMBER_OF_BOX; i++) {
            box = new MonsterBoxImpl(INITIAL_BOX_NAME + (i + 1), MAX_SIZE_OF_BOX);
            this.monsterBoxes.add(box);
        }
    }

    private void calculateNewIndex(final int n) {

        this.currentMonsterBoxIndex = ((this.currentMonsterBoxIndex + n) % MAX_SIZE_OF_BOX + MAX_SIZE_OF_BOX)
                % MAX_SIZE_OF_BOX;

    }

    private MonsterBox getFirstBoxFree() {
        for (int i = 0; i < MAX_NUMBER_OF_BOX; i++) {
            if (!this.monsterBoxes.get(i).isFull()) {
                return this.monsterBoxes.get(i);
            }

        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addMonster(final Monster monster) {
        if (this.getCurrentBox().addMonster(monster)) {
            return true;
        } else {
            final MonsterBox monsterBox = getFirstBoxFree();
            if (monsterBox != null) {
                return monsterBox.addMonster(monster);
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean depositMonster(final Monster monster) {
        if (this.player.getAllMonsters().size() > 1 && this.player.getAllMonsters().contains(monster)
                && getCurrentBox().addMonster(monster)) {

            this.player.removeMonster(monster);
            return true;

        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean withdrawMonster(final int monsterID) {
        if (isInBox(monsterID) && !this.player.isTeamFull()) {
            this.player.addMonster(getCurrentBox().getMonster(monsterID).get());
            getCurrentBox().removeMonster(monsterID);
            return true;

        }
        return false;
    }

    private boolean isInBox(final int monsterID) {
        for (final Monster monster : getCurrentBox().getAllMonsters()) {
            if (monster.getId() == monsterID) {
                return true;
            }
        }
        return false;
    }

    private MonsterBox getCurrentBox() {

        return this.monsterBoxes.get(currentMonsterBoxIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exchange(final Monster monster, final int monsterID) {
        if (isInBox(monsterID)) {
            final Optional<Monster> m = getCurrentBox().exchange(monster, monsterID);
            if (m.isPresent()) {
                this.player.addMonster(m.get());
                this.player.removeMonster(monster);
                return true;
            }
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextBox() {
        calculateNewIndex(1);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void previousBox() {
        calculateNewIndex(-1);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurrentBoxName() {
        return getCurrentBox().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Monster> getCurrentBoxMonsters() {
        return getCurrentBox().getAllMonsters();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxSizeOfBox() {
        return MAX_SIZE_OF_BOX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxNumberOfBox() {
        return MAX_NUMBER_OF_BOX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentBoxSize() {
        return this.getCurrentBoxMonsters().size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "MonsterStorageImpl [monsterBoxes=" + monsterBoxes + ", currentMonsterBoxIndex=" + currentMonsterBoxIndex
                + ", player=" + player + "]";
    }
}
