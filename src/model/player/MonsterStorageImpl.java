package model.player;

import java.util.ArrayList;
import java.util.List;

import model.monster.Monster;

public class MonsterStorageImpl implements MonsterStorage {

	private static final int MAX_NUMBER_OF_BOX = 10;
	private static final String INITIAL_BOX_NAME = "BOX";

	private List<MonsterBox> monsterBoxes;
	private int currentMonsterBoxIndex;
	private Player player;

	public MonsterStorageImpl(Player player, List<MonsterBox> boxes) {
		this.player = player;
		this.monsterBoxes = new ArrayList<>(boxes);
		this.currentMonsterBoxIndex = 1;
		if (monsterBoxes.size() > MAX_NUMBER_OF_BOX) {
			this.monsterBoxes = monsterBoxes.subList(0, MAX_NUMBER_OF_BOX);
		} else {
			generateBoxs(MAX_NUMBER_OF_BOX - monsterBoxes.size());
		}
	}

	private void generateBoxs(int n) {
		MonsterBox box;
		for (int i = n; i < MAX_NUMBER_OF_BOX; i++) {
			box = new MonsterBoxImpl(INITIAL_BOX_NAME + i);
			this.monsterBoxes.add(box);
		}
	}

	@Override
	public void addMonster(Monster monster) {
		if (!getCurrentBox().isFull()) {
			getCurrentBox().addMonster(monster);
		}
	}

	@Override
	public boolean depositMonster(Monster monster) {
		if (this.player.removeMonster(monster)) {
			getCurrentBox().addMonster(monster);
			return true;
		}
		return false;
	}

	@Override
	public boolean withdrawMonster(int monsterID) {
		if (isInBox(monsterID)) {
			getCurrentBox().removeMonster(monsterID);
			if (!this.player.isTeamFull()) {
				this.player.addMonster(getCurrentBox().getMonster(monsterID).get());
				return true;
			}
		}
		return false;
	}

	private boolean isInBox(int monsterID) {
		for (Monster monster : getCurrentBox().getAllMonsters()) {
			if (monster.getId() == monsterID) {
				return true;
			}
		}
		return false;
	}

	private MonsterBox getCurrentBox() {
		return this.monsterBoxes.get(currentMonsterBoxIndex);
	}

	@Override
	public boolean exchange(Monster monster, int monsterID) {

		if (this.player.getAllMonsters().contains(monster) && isInBox(monsterID)) {
			getCurrentBox().exchange(monster, monsterID);
		}

		return false;
	}

	@Override
	public void nextBox() {
		if (this.currentMonsterBoxIndex < MAX_NUMBER_OF_BOX) {
			this.currentMonsterBoxIndex++;
		}

	}

	@Override
	public void previousBox() {
		if (this.currentMonsterBoxIndex > 1) {
			this.currentMonsterBoxIndex--;
		}
	}

	@Override
	public String getCurrentBoxName() {
		return getCurrentBox().getName();
	}

	@Override
	public List<Monster> getCurrentBoxMonsters() {
		return getCurrentBox().getAllMonsters();
	}
}
