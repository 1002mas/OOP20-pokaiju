package model.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.monster.Monster;

public class MonsterBoxImpl implements MonsterBox {

	private String name;
	private int boxSize;
	private List<Monster> monsterList;

	public MonsterBoxImpl(String name, int boxSize) {
		this.name = name;
		this.boxSize = boxSize;
		this.monsterList = new ArrayList<>();
	}

	public MonsterBoxImpl(String name, List<Monster> monsters, int boxSize) {
		this(name, boxSize);
		for (Monster monster : monsters) {
			addMonster(monster);
		}
	}

	@Override
	public List<Monster> getAllMonsters() {
		List<Monster> monsterList = new ArrayList<>();
		for (Monster monster : this.monsterList) {
			monsterList.add(monster);
		}
		return monsterList;
	}

	@Override
	public boolean addMonster(Monster monster) {
		if (!isFull()) {
			return this.monsterList.add(monster);
		}
		return false;
	}

	@Override
	public Optional<Monster> getMonster(int monsterID) {

		for (Monster monster : this.monsterList) {
			if (monster.getId() == monsterID) {
				return Optional.of(monster);
			}
		}
		return Optional.empty();
	}

	@Override
	public boolean isFull() {
		return (this.monsterList.size() >= this.boxSize);
	}

	@Override
	public Optional<Monster> exchange(Monster toBox, int monsterID) {

		Optional<Monster> monsterInBox = getMonster(monsterID);
		if (monsterInBox.isPresent()) {
			this.monsterList.remove(monsterInBox.get());
			addMonster(toBox);
		}
		return monsterInBox;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void removeMonster(int monsterID) {
		Optional<Monster> monsterInBox = getMonster(monsterID);
		if (monsterInBox.isPresent()) {
			this.monsterList.remove(monsterInBox.get());
		}

	}

	@Override
	public String toString() {
		return "MonsterBoxImpl [name=" + name + ", boxSize=" + boxSize + ", monsterList=" + monsterList + "]";
	}

}
