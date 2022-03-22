package model.player;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

import model.gameitem.GameItems;
import model.monster.Monster;

public class PlayerControllPart {
 /*   /**
     * load Player data from json file
     */
 /*   static void loadPlayerInfo(String filename) {
//	JSONParser parser = new JSONParser();
//	Player player = new PlayerImpl();

	try {
	    /*
	     * BufferedReader b = new BufferedReader(new FileReader(filename));
	     * System.out.println(b.readLine());
	     */
	  //  Object obj = parser.parse(new FileReader(filename));
	//    System.out.println(obj);
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

/*	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (ParseException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }*/

    /**
     * save Player data into json file
     * 
     * @param filename
     * @param p
     * @throws IOException
     */
    /*static void savePlayerInfo(String filename, Player p) throws IOException {
	JSONObject obj = new JSONObject();
	obj.put("name", p.getName());
	obj.put("Trainernumber", p.getTrainerNumber());
	obj.put("gender", p.getGender());
	obj.put("money", p.getMoney());
	obj.put("Position X Y", p.getPosition());
	JSONArray listMonster = new JSONArray();
	JSONArray listItem = new JSONArray();
	for (Monster m : p.allMonster()) {
	    JSONObject jo = new JSONObject();
	    jo.put(m.getName(), m);
	    listMonster.add(jo);

	}
	for (GameItems i : p.allItems()) {
	    JSONObject jo = new JSONObject();
	    jo.put(i.getNameItem(), i);
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
    }*/

}
