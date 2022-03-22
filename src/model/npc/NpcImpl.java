package model.npc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import model.monster.Monster;

public class NpcImpl implements Npc {

    private final String name;
    private boolean trainer;
    private boolean defeated;
    private final ArrayList<Monster> monstersOwned;

    public NpcImpl(String name, ArrayList<Monster> monsterOwned, boolean trainer) {

	this.name = name;
	this.trainer = trainer;
	this.monstersOwned = monsterOwned;
    }

    public NpcImpl(String name, boolean trainer) {

	this(name, null, trainer);
    }

    private int battle() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public String getName() {
	return this.name;
    }

    public ArrayList<String> setPhrases(String speechFileName) {
	ArrayList<String> fileLines = new ArrayList<String>();
	// da completare
	return fileLines;
    }

    @Override
    public void interactWith() {

	if (!isTrainer() || isDefeated()) {
	    talk();
	} else {
	    int isTheWinner = battle();
	    if (isTheWinner == 0) {
		setDefeated();
	    }
	}
    }

    @Override
    public boolean isTrainer() {

	return this.trainer;
    }

    public boolean isDefeated() {
	return this.defeated;
    }

    public void setDefeated() {
	this.defeated = true;
    }

    private String talk() {
	// da completare
	String result = "";
	return result;
    }

}