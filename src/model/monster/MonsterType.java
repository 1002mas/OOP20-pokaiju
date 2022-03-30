package model.monster;

import java.util.HashMap;
import java.util.Map;

public enum MonsterType {
   NONE("none"), FIRE("fire"), GRASS("grass"), WATER("water");

    private final String name;
    private final Map<String, Double> damageMultiplier = new HashMap<>();

    private MonsterType(String name) {
	this.name = name;

	switch (name) { 
		case "fire": 
		    damageMultiplier.put("grass", 2.0);
		    damageMultiplier.put("fire", 1.0);
		    damageMultiplier.put("water", 0.5); 
		    break; 
		case "water":
		    damageMultiplier.put("fire", 2.0); 
		    damageMultiplier.put("water", 1.0);
		    damageMultiplier.put("grass", 0.5); 
		    break;
		case "grass": 
		    damageMultiplier.put("water", 2.0);
		    damageMultiplier.put("grass", 1.0);
		    damageMultiplier.put("fire", 0.5); 
		    break;
  
		default:
		    break; 
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
