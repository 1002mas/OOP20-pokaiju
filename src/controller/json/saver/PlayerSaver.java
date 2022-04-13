package controller.json.saver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Pair;
import model.gameitem.GameItem;
import model.monster.Monster;
import model.player.Gender;

public class PlayerSaver {
	private String name;
	private Gender gender;
	private int trainerNumber;
	private Pair<Integer, Integer> position;
	private List<Integer> monsterId;
	private Map<String, Integer> gameItemsName;
	private int money;

	public PlayerSaver(String name, Gender gender, int trainerNumber, Pair<Integer, Integer> position,
			List<Integer> monsterId, Map<String, Integer> gameItemsName, int money) {

		this.name = name;
		this.gender = gender;
		this.trainerNumber = trainerNumber;
		this.position = position;
		this.monsterId = monsterId;
		this.gameItemsName = gameItemsName;
		this.money = money;
	}

	public String getName() {
		return this.name;
	}

	public Gender getGender() {
		return this.gender;
	}

	public int getTrainerNumber() {
		return this.trainerNumber;
	}

	public Pair<Integer, Integer> getPosition() {
		return this.position;
	}

	public List<Monster> getTranslatedMonster(List<Monster> list) {
		List<Monster> monsters = new ArrayList<>();
		for (int id : this.monsterId) {
			for (Monster monster : list) {
				if (monster.getId() == id) {
					monsters.add(monster);
					break;
				}
			}
		}
		return monsters;
	}

	public Map<GameItem, Integer> getTranslatedGameItems(List<GameItem> list) {
		Map<GameItem, Integer> gameItems = new HashMap<>();
		for (Entry<String, Integer> itemName : this.gameItemsName.entrySet()) {
			for (GameItem item: list) {
				if (itemName.getKey().equals(item.getNameItem())) {
					gameItems.put(item, itemName.getValue());
				}
			}
		}
		return gameItems;
	}

	public int getMoney() {
		return this.money;
	}

}