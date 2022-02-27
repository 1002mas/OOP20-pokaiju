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
    private String gender;
    private int trainerNumber;
    private PositionPlayer position;
    private ArrayList<Monster> Monster;
    private ArrayList<Item> Items;

    public PlayerImpl(String name, String gender, int trainerNumber, int x, int y) {
	this.name = name;
	this.gender = gender;
	this.trainerNumber = trainerNumber;
	this.position = new PositionPlayer(x, y);
	this.Monster = new ArrayList<Monster>();
	this.Items = new ArrayList<Item>();

    }

    public PlayerImpl() {

    }

    @Override
    public PositionPlayer getPosition() {
	// TODO Auto-generated method stub
	return this.position;
    }

    @Override
    public ArrayList<Monster> allMonster() {
	ArrayList<Monster> list = new ArrayList<>(Collections.unmodifiableList(this.Monster));
	return list;
    }

    public String toString() {
	return this.name + ", " + this.trainerNumber + ", " + this.gender + ", " + allMonster().toString() + ", "
		+ this.Items.toString();

    }

    @Override
    public ArrayList<Item> allItems() {
	// TODO Auto-generated method stub
	return new ArrayList<>(this.Items);
    }

    @Override
    public void addItem(Item i) {
	// TODO Auto-generated method stub
	if (this.Items.contains(i)) {
	    int numberOfItem = this.Items.stream().filter(x -> x.getName().equals(i.getName())).findFirst().get()
		    .getNumber();
	    this.Items.stream().filter(x -> x.getName().equals(i.getName())).findFirst().get()
		    .setNumber(numberOfItem + i.getNumber());
	} else {
	    this.Items.add(i);
	}
    }

    @Override
    public void removeItem(Item i) {
	// TODO Auto-generated method stub
	if (this.Items.contains(i)) {
	    int numberOfItem = this.Items.stream().filter(x -> x.getName().equals(i.getName())).findFirst().get()
		    .getNumber();
	    if (--numberOfItem > 0) {
		this.Items.stream().filter(x -> x.getName().equals(i.getName())).findFirst().get()
			.setNumber(numberOfItem);
	    } else {
		this.Items.remove(i);
	    }
	}

    }

    public String getGender() {
	return gender;
    }

    public void setGender(String gender) {
	this.gender = gender;
    }

    public int getTrainerNumber() {
	return trainerNumber;
    }

    public void setTrainerNumber(int trainerNumber) {
	this.trainerNumber = trainerNumber;
    }

    public ArrayList<Monster> getMonster() {
	return Monster;
    }

    public void setMonster(ArrayList<Monster> monster) {
	this.Monster = monster;
    }

    public ArrayList<Item> getItems() {
	return this.Items;
    }

    public void setItems(ArrayList<Item> items) {
	this.Items = items;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setPosition(PositionPlayer position) {
	this.position = position;
    }

    /**
     * load Player data from json file
     */
    static void loadPlayerInfo(String filename) {
	JSONParser parser = new JSONParser();
//	Player player = new PlayerImpl();

	try {
	    /*
	     * BufferedReader b = new BufferedReader(new FileReader(filename));
	     * System.out.println(b.readLine());
	     */
	    Object obj = parser.parse(new FileReader(filename));
	    System.out.println(obj);
	    /*
	     * JSONObject jsonObj = (JSONObject) obj; System.out.println(jsonObj);
	     * PlayerImpl p = (PlayerImpl) jsonObj.get("Luca"); System.out.println(p);
	     */
	    /*
	     * String gender = (String) jsonObj.get("gender"); int trainerNumber = (int)
	     * jsonObj.get("trainerNumber"); PositionPlayer pos = (PositionPlayer)
	     * jsonObj.get("position"); ArrayList<String> Monster = ( ArrayList<String>)
	     * jsonObj.get("Monster"); HashMap<String, Item> items = (HashMap<String, Item>)
	     * jsonObj.get("Items"); p.setName(name); p.setGender(gender);
	     * p.setItems(items); p.setMonster(Monster); p.setPosition(pos);
	     * p.setTrainerNumber(trainerNumber);
	     */

	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (ParseException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    /**
     * save Player data into json file
     * 
     * @param filename
     * @param p
     * @throws IOException
     */
    static void savePlayerInfo(String filename, Player p) throws IOException {
	JSONObject obj = new JSONObject();
	obj.put("name", p.getName());
	obj.put("Trainernumber", p.getTrainerNumber());
	obj.put("gender", p.getGender());
	obj.put("PositionX", p.getPosition().getPosx());
	obj.put("PositionY", p.getPosition().getPosy());
	JSONArray listMonster = new JSONArray();
	JSONArray listItem = new JSONArray();
	for (Monster m : p.allMonster()) {
	    JSONObject jo = new JSONObject();
	    jo.put(m.getName(), m);
	    listMonster.add(jo);

	}
	for (Item i : p.allItems()) {
	    JSONObject jo = new JSONObject();
	    jo.put(i.getName(), i);
	    listItem.add(jo);
	}

	obj.put("Monsters", listMonster);
	obj.put("Items", listItem);

	try (FileWriter file = new FileWriter(filename)) {
	    file.write(obj.toString());
	    file.flush();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void addMonster(Monster m) {
	// TODO Auto-generated method stub
	    this.Monster.add(m);

    }

    @Override
    public String getName() {
	// TODO Auto-generated method stub
	return this.name;
    }


}
