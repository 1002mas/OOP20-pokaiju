package test;

import java.io.IOException;

<<<<<<< HEAD:src/test/Prova.java
import model.monster.Monster;
import model.monster.MonsterImpl;
=======
import model.GameItem.GameItemTypes;
import model.GameItem.GameItems;
import model.GameItem.HealingItem;
import model.monster.*;
>>>>>>> Player3.0:src/model/player/Prova.java
public class Prova {

    public static void main(String[] args) throws IOException {
	// TODO Auto-generated method stub
<<<<<<< HEAD:src/test/Prova.java
/*	Player p = new PlayerImpl("Luca",Gender.MAN, 6969669, 0, 0);
	Monster m = new MonsterImpl("pikachu", 100, "Electro", "Topo giallo");
	Monster m2 = new MonsterImpl("pikachu", 150, "Electro", "Topo giallo");
	//System.out.println(p);
	Item i1 = new Item("Pokeball", 1, 100,"cattura",ItemTypes.MONSTERBALL);
	Item i2 = new Item("Masterball", 10, 100 ,"cattura 100%",ItemTypes.MONSTERBALL);
=======
	Player p = new PlayerImpl("Luca",Gender.MAN, 6969669, new Pair<Integer, Integer>(10, 10));
	Monster m = new MonsterImpl("pikachu", 100, MonsterType.FIRE, "Topo giallo");
	Monster m2 = new MonsterImpl("pichu", 150, MonsterType.GRASS, "Topo verde");
	System.out.println(p);
	GameItems i1 = new HealingItem("Erba", 10,"cura",GameItemTypes.HEAL);
	GameItems i2 = new HealingItem("Ampolla", 10,"cura",GameItemTypes.HEAL,150);
>>>>>>> Player3.0:src/model/player/Prova.java
	p.addItem(i1);
	p.addItem(i2);
	p.addMonster(m);
	p.addMonster(m2);
	
	System.out.println(p.allItems());
	p.removeItem(i1);
<<<<<<< HEAD:src/test/Prova.java
	System.out.println(p.allItems());*/
	//System.out.println(p);
//	PlayerControllPart.savePlayerInfo("prova.json", p);
=======
	System.out.println(p.allItems());
	System.out.println(p);
	//PlayerControllPart.savePlayerInfo("prova.json", p);
>>>>>>> Player3.0:src/model/player/Prova.java
	//PlayerImpl.loadPlayerInfo("prova.json");
	/*Player leggi = new PlayerImpl();
	leggi=PlayerImpl.loadPlayerInfo("prova.json");*/
    }

}
