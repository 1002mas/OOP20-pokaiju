package model.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.monster.Monster;

public class MonsterBoxImpl implements MonsterBox {

	private static final int BOX_SIZE = 5;

	private String name;

	List<Monster> monsterList;

	public MonsterBoxImpl(String name) {
		this.name = name;
		this.monsterList = new ArrayList<>();
	}

	public MonsterBoxImpl(String name, List<Monster> monsters) {
		this(name);
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

	public boolean isFull() {
		return (this.monsterList.size() >= BOX_SIZE);
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

}
