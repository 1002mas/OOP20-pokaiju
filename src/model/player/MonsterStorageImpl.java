package model.player;

import java.util.ArrayList;
import java.util.List;

import model.Pair;
import model.monster.Monster;

public class MonsterStorageImpl {

	private static final int MAX_NUMBER_OF_BOX = 10;
	private static final String INITIAL_BOX_NAME = "BOX";

	private List<MonsterBox> monsterBoxs;
	private Player player;
	
	public MonsterStorageImpl(Player player) {
		this.player = player;
		generateBoxs();
	}

	private void generateBoxs() {
		MonsterBox box;
		for (int i = 0; i < MAX_NUMBER_OF_BOX; i++) {
			box = new MonsterBoxImpl(INITIAL_BOX_NAME + i);
			this.monsterBoxs.add(box);
		}
	}
	
	public List<Pair<String, List<Monster>>> getMonsterList() {
		List<Pair<String, List<Monster>>> boxList = new ArrayList<>();
		for (MonsterBox mb : this.monsterBoxs) {
			boxList .add(new Pair<>(mb.getName(),mb.getAllMonsters()));
		}
		return boxList ;
	}

	private MonsterBox findMonster(Monster monster) {
		for (MonsterBox m : monsterBoxs) {
			if (m.getAllMonsters().contains(monster)) {
				return m;
			}
		}
		return null;
	}

	public void addMonster(Monster monster) {
		for (MonsterBox m : monsterBoxs) {
			if (!m.isFull()) {
				m.addMonster(monster);
				break;
			}
		}
		this.player.removeMonster(monster);
	}

	public void takeMonster(Monster monster) {
		MonsterBox m = findMonster(monster);
		if (m != null) {
			m.takeMonster(monster);
		}
		this.player.addMonster(monster);
	}

	public void exchange(Monster monsterToBox, Monster monsterFromBox) {
		MonsterBox m = findMonster(monsterFromBox);
		if (m != null) {
			m.exchange(monsterToBox, monsterFromBox);
			this.player.removeMonster(monsterToBox);
			this.player.addMonster(monsterFromBox);
		}
	}
}
