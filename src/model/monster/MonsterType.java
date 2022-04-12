package model.monster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public enum MonsterType {
    NONE("none"), FIRE("fire"), GRASS("grass"), WATER("water");

    private final String name;
    private final Map<String, Double> damageMultiplier = new HashMap<>();

    private MonsterType(String name) {

	this.name = name;
	String path = "res" + File.separator + "data" + File.separator + "weakness" + File.separator + name + ".dat";
	try (BufferedReader in = new BufferedReader(new FileReader(path))) {
	    String line;

	    String[] splittedLine;
	    while ((line = in.readLine()) != null) {

		splittedLine = line.split(" ");
		damageMultiplier.put(splittedLine[0], Double.parseDouble(splittedLine[1]));

	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e1) {
	    e1.printStackTrace();
	}

    }

    public String getName() {
	return this.name;
    }

    public double resistanceTo(MonsterType type) {
	return 1 / damageMultiplier.get(type.getName());
    }

    public double damageTo(MonsterType type) {
	System.out.println(damageMultiplier.get(type.getName()));
	return damageMultiplier.get(type.getName());
    }
}
