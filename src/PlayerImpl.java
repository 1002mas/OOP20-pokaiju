import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class PlayerImpl implements Player {
    private String name;
    private String gender;
    private int trainerNumber;
    private PositionPlayer position;
    private ArrayList<String> Monster;
    private HashMap<String, Item> Items;

    public PlayerImpl(String name, String gender, int trainerNumber, int x, int y) {
	this.name = name;
	this.gender = gender;
	this.trainerNumber = trainerNumber;
	this.position = new PositionPlayer(x, y);
	this.Monster = new ArrayList<String>();
	this.Items = new HashMap<String, Item>();

    }

    public PlayerImpl() {

    }

    @Override
    public PositionPlayer getPosition() {
	// TODO Auto-generated method stub
	return this.position;
    }

    @Override
    public ArrayList<String> allMonster() {
	ArrayList<String> list = new ArrayList<>(Collections.unmodifiableList(this.Monster));
	return list;
    }

    public String toString() {
	return this.name + ", " + this.trainerNumber + ", " + this.gender + ", " + allMonster().toString() + ", "
		+ this.Items.toString();

    }

    @Override
    public ArrayList<Item> allItems() {
	// TODO Auto-generated method stub
	return new ArrayList<>(this.Items.values());
    }

    @Override
    public void addItem(Item i) {
	// TODO Auto-generated method stub
	if (this.Items.containsKey(i.getName())) {
	    this.Items.get(i.getName()).setNumber(i.getNumber() + this.Items.get(i.getName()).getNumber());
	} else {
	    this.Items.put(i.getName(), i);
	}
    }

    @Override
    public void removeItem(Item i) {
	// TODO Auto-generated method stub
	if (this.Items.containsKey(i.getName())) {
	    int number = this.Items.get(i.getName()).getNumber();
	    if (number-- > 0) {
		this.Items.get(i.getName()).setNumber(number--);
	    } else {
		this.Items.remove(i.getName());
	    }
	}

    }

    /**
     * load Player data from json file
     */
    static void loadPlayerInfo(String filename) {
	JSONParser parser = new JSONParser();
	/* Player player = new PlayerImpl(); */

	try {

	    Object obj = parser.parse(new FileReader(filename));
	    JSONObject jsonObj = (JSONObject) obj;
	    /* String p = (String) jsonObj.get("name"); */
	    System.out.println(jsonObj.toString());
	    /* player=p; */
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
	/* return player; */
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
	obj.put(p.getName(), p);
	try (FileWriter file = new FileWriter(filename)) {
	    file.write(obj.toString());
	    file.flush();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void addMonster() {
	// TODO Auto-generated method stub

    }

    public String getName() {
	// TODO Auto-generated method stub
	return this.name;
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

    public ArrayList<String> getMonster() {
	return Monster;
    }

    public void setMonster(ArrayList<String> monster) {
	Monster = monster;
    }

    public HashMap<String, Item> getItems() {
	return Items;
    }

    public void setItems(HashMap<String, Item> items) {
	Items = items;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setPosition(PositionPlayer position) {
	this.position = position;
    }

}
