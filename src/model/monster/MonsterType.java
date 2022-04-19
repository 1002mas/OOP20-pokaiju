package model.monster;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public enum MonsterType {
    /***
     * No Type.
     */
    NONE("none"),
    /***
     * Fire Type.
     */
    FIRE("fire"),
    /***
     * Grass type.
     */
    GRASS("grass"),
    /***
     * water type.
     */
    WATER("water");

    private final String name;
    private final Map<String, Double> damageMultiplier = new HashMap<>();

     MonsterType(final String name) {

        this.name = name;
        final String path = "data/weakness/" + name + ".dat";
        final InputStream fileStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(fileStream))) {
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

    public double resistanceTo(final MonsterType type) {
        return 1 / damageMultiplier.get(type.getName());
    }

    public double damageTo(final MonsterType type) {
        return damageMultiplier.get(type.getName());
    }
}
