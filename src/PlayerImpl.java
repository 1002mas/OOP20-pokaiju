import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class PlayerImpl implements Player {
    private String name;
    private String gender;
    private int trainerNumber;
    private PositionPlayer position;
    private ArrayList<String> Monster;
    private ArrayList<Item> Items;

    public PlayerImpl(String name, String gender, int trainerNumber, int x, int y) {
	this.name = name;
	this.gender = gender;
	this.trainerNumber = trainerNumber;
	this.position = new PositionPlayer(x, y);
	this.Monster = new ArrayList<String>();
	this.Items = new ArrayList<Item>();

    }

    @Override
    public PositionPlayer getPosition() {
	// TODO Auto-generated method stub
	return this.position;
    }

    public ArrayList<String> allMonster() {
	ArrayList<String> list = (ArrayList<String>) Collections.unmodifiableList(this.Monster);
	return list;
    }

    public String toString() {
	return this.name + "," + this.trainerNumber + this.gender + "," + allMonster().toString();

    }

    @Override
    public Player getData(String filename) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ArrayList<Item> allItems() {
	// TODO Auto-generated method stub
	return this.Items;
    }

}
