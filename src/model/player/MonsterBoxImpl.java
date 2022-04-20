package model.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.monster.Monster;

public class MonsterBoxImpl implements MonsterBox {

    private final String name;
    private int boxSize;
    private List<Monster> monsterList;

    /**
     * Constructor for MonsterBoxImpl.
     * 
     * @param name
     * @param boxSize
     */
    public MonsterBoxImpl(final String name, final int boxSize) {
        this.name = name;
        this.boxSize = boxSize;
        this.monsterList = new ArrayList<>();
    }

    /**
     * Constructor for MonsterBoxImpl.
     * 
     * @param name
     * @param monsters
     * @param boxSize
     */
    public MonsterBoxImpl(final String name, final List<Monster> monsters, final int boxSize) {
        this(name, boxSize);
        for (final Monster monster : monsters) {
            addMonster(monster);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Monster> getAllMonsters() {
        final List<Monster> monsterList = new ArrayList<>();
        for (final Monster monster : this.monsterList) {
            monsterList.add(monster);
        }
        return monsterList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean addMonster(final Monster monster) {
        if (!isFull()) {
            return this.monsterList.add(monster);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Monster> getMonster(final int monsterID) {

        for (final Monster monster : this.monsterList) {
            if (monster.getId() == monsterID) {
                return Optional.of(monster);
            }
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isFull() {
        return this.monsterList.size() >= this.boxSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Monster> exchange(final Monster toBox, final int monsterID) {

        final Optional<Monster> monsterInBox = getMonster(monsterID);
        if (monsterInBox.isPresent()) {
            this.monsterList.remove(monsterInBox.get());
            addMonster(toBox);
        }
        return monsterInBox;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeMonster(final int monsterID) {
        final Optional<Monster> monsterInBox = getMonster(monsterID);
        if (monsterInBox.isPresent()) {
            this.monsterList.remove(monsterInBox.get());
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "MonsterBoxImpl [name=" + name + ", boxSize=" + boxSize + ", monsterList=" + monsterList + "]";
    }

}
