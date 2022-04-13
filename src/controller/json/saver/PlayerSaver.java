package controller.json.saver;

import java.util.ArrayList;
import java.util.List;

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
	private List<String> gameItemsName;
	private int money;

	public PlayerSaver(String name, Gender gender, int trainerNumber, Pair<Integer, Integer> position,
			List<Integer> monsterId, List<String> gameItemsName, int money) {

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

	public List<GameItem> getTranslatedGameItems(List<GameItem> list) {
		List<GameItem> gameItems = new ArrayList<>();
		for (String itemName : this.gameItemsName) {
			for (GameItem item : list) {
				if (itemName.equals(item.getNameItem())) {
					gameItems.add(item);
				}
			}
		}
		return gameItems;
	}

	public int getMoney() {
		return this.money;
	}

}