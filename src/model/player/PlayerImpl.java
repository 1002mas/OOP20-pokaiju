package model.player;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import model.monster.*;

public class PlayerImpl implements Player {
    private String name;
    private Gender gender;
    private int trainerNumber;
    private Pair<Integer, Integer> position;
    private ArrayList<Monster> monster;
    private ArrayList<Item> gameItems;
    private int money;

    public PlayerImpl(String name, Gender gender, int trainerNumber, Pair<Integer, Integer> startingPosition) {
	this.name = name;
	this.gender = gender;
	this.trainerNumber = trainerNumber;
	this.position = startingPosition;
	this.monster = new ArrayList<Monster>();
	this.gameItems = new ArrayList<Item>();
	this.money = 500;

    }

    @Override
    public Pair<Integer, Integer> getPosition() {
	// TODO Auto-generated method stub
	return this.position;
    }

    @Override
    public ArrayList<Monster> allMonster() {
	ArrayList<Monster> list = new ArrayList<>(Collections.unmodifiableList(this.monster));
	return list;
    }

    public String toString() {
	return this.name + ", " + this.trainerNumber + ", " + this.gender + ", " + allMonster().toString() + ", "
		+ this.gameItems.toString();

    }

    @Override
    public ArrayList<Item> allItems() {
	// TODO Auto-generated method stub
	return new ArrayList<>(this.gameItems);
    }

    @Override
    public void addItem(Item i) {
	// TODO Auto-generated method stub
	if (this.gameItems.contains(i)) {
	    int numberOfItem = this.gameItems.stream().filter(x -> x.getName().equals(i.getName())).findFirst().get()
		    .getNumber();
	    this.gameItems.stream().filter(x -> x.getName().equals(i.getName())).findFirst().get()
		    .setNumber(numberOfItem + i.getNumber());
	} else {
	    this.gameItems.add(i);
	}
    }

    @Override
    public void removeItem(Item i) {
	// TODO Auto-generated method stub
	if (this.gameItems.contains(i)) {
	    int numberOfItem = this.gameItems.stream().filter(x -> x.getName().equals(i.getName())).findFirst().get()
		    .getNumber();
	    if (--numberOfItem > 0) {
		this.gameItems.stream().filter(x -> x.getName().equals(i.getName())).findFirst().get()
			.setNumber(numberOfItem);
	    } else {
		this.gameItems.remove(i);
	    }
	}

    }

    @Override
    public void useItem(Item i, Monster m) {
	// TODO Auto-generated method stub
	if (allItems().contains(i)) {
	    switch (i.getType()) {
	    case HEAL:
		if (m.getHealth() + i.getEffect() > m.getHealth()) {
		    m.setHealth(m.getHealth());
		} else {
		    m.setHealth(m.getHealth() + i.getEffect());
		}
		removeItem(i);
	    case EVOLUTIONTOOL:
	    case MONSTERBALL:
	    }
	}

    }

    @Override
    public boolean buyItem(Item i, int price) {
	// TODO Auto-generated method stub
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

    public ArrayList<Monster> getMonster() {
	return monster;
    }

    public void setMonster(ArrayList<Monster> monster) {
	this.monster = monster;
    }

    public ArrayList<Item> getItems() {
	return this.gameItems;
    }

    public void setItems(ArrayList<Item> items) {
	this.gameItems = items;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setPosition(Pair<Integer, Integer> position) {
	this.position = position;
    }

    @Override
    public void addMonster(Monster m) {
	this.monster.add(m);
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

}
