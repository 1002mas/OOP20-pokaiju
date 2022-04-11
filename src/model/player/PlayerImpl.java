package model.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Pair;
import model.gameitem.GameItem;
import model.monster.Monster;

public class PlayerImpl implements Player {
    private static final int STARTMONEY=1000;
    private String name;
    private Gender gender;
    private int trainerNumber;
    private Pair<Integer, Integer> position;
    private List<Monster> monster;
    private List<GameItem> gameItems;
    private int money;

    public PlayerImpl(String name, Gender gender, int trainerNumber, Pair<Integer, Integer> startingPosition) {
	this.name = name;
	this.gender = gender;
	this.trainerNumber = trainerNumber;
	this.position = startingPosition;
	this.monster = new ArrayList<Monster>();
	this.gameItems = new ArrayList<GameItem>();
	this.money = STARTMONEY;

    }

    @Override
    public Pair<Integer, Integer> getPosition() {
	return this.position;
    }

    @Override
    public List<Monster> getAllMonsters() {
	List<Monster> list = new ArrayList<>(Collections.unmodifiableList(this.monster));
	return list;
    }

    public String toString() {
	return this.name + ", " + this.trainerNumber + ", " + this.gender + ", " + getAllMonsters().toString() + ", "
		+ this.gameItems.toString() + ", " + this.position.getFirst() + ", " + this.position.getSecond();

    }

    @Override
    public List<GameItem> getAllItems() {
	return new ArrayList<>(this.gameItems);
    }

    @Override
    public void addItem(GameItem i) {
	if (this.gameItems.contains(i)) {
	    int numberOfItem = this.gameItems.stream().filter(x -> x.getNameItem().equals(i.getNameItem())).findFirst()
		    .get().getNumber();
	    this.gameItems.stream().filter(x -> x.getNameItem().equals(i.getNameItem())).findFirst().get()
		    .setNumber(numberOfItem + i.getNumber());
	} else {
	    this.gameItems.add(i);
	}
    }

    @Override
    public void removeItem(GameItem i) {
	if (this.gameItems.contains(i)) {
	    int numberOfItem = this.gameItems.stream().filter(x -> x.getNameItem().equals(i.getNameItem())).findFirst()
		    .get().getNumber();
	    numberOfItem--;
	    if (numberOfItem > 0) {
		this.gameItems.stream().filter(x -> x.getNameItem().equals(i.getNameItem())).findFirst().get()
			.setNumber(numberOfItem);
	    } else {
		this.gameItems.remove(i);
	    }
	}
    }

    @Override
    public void useItem(GameItem i) {
	if (getAllItems().contains(i) && i.use(null)) {
	    removeItem(i);
	}
    }

    @Override
    public boolean buyItem(GameItem i, int price) {
	if (getMoney() - price >= 0) {
	    addItem(i);
	    setMoney(getMoney() - price);
	    return true;
	} else {
	    return false;
	}
    }

    public Gender getGender() {
	return gender;
    }

    public void setGender(Gender gender) {
	this.gender = gender;
    }

    public int getTrainerNumber() {
	return trainerNumber;
    }

    public void setTrainerNumber(int trainerNumber) {
	this.trainerNumber = trainerNumber;
    }

    public List<Monster> getMonster() {
	return monster;
    }

    public void setMonster(ArrayList<Monster> monster) {
	this.monster = monster;
    }

    public List<GameItem> getItems() {
	return this.gameItems;
    }

    public void setItems(ArrayList<GameItem> items) {
	this.gameItems = items;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setPosition(Pair<Integer, Integer> position) {
	this.position = position;
    }

    @Override
    public boolean addMonster(Monster m) {
	if (isTeamFull()) {
	    return false;
	} else {
	    return this.monster.add(m);
	}

    }

    public int getMoney() {
	return money;
    }

    public void setMoney(int money) {
	this.money = money;
    }

    public String getName() {
	return name;
    }

    @Override
    public boolean isTeamFull() {
	return this.getAllMonsters().stream().count() >= 6 ? true : false;
    }

    @Override
    public boolean removeMonster(Monster m) {
	if (this.getAllMonsters().contains(m)) {
	    return this.monster.remove(m);
	}
	return false;
    }

    @Override
    public void useItemOnMonster(GameItem i, Monster m) {
	if (getAllItems().contains(i) && i.use(m)) {
	    removeItem(i);
	}
    }

}
