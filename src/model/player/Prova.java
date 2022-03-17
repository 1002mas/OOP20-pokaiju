package model.player;
import java.io.IOException;

import model.GameItem.GameItemTypes;
import model.GameItem.GameItems;
import model.GameItem.HealingItem;
import model.monster.*;
public class Prova {

    public static void main(String[] args) throws IOException {
	// TODO Auto-generated method stub
	Player p = new PlayerImpl("Luca",Gender.MAN, 6969669, new Pair<Integer, Integer>(10, 10));
	Monster m = new MonsterImpl("pikachu", 100, MonsterType.FIRE, "Topo giallo");
	Monster m2 = new MonsterImpl("pichu", 150, MonsterType.GRASS, "Topo verde");
	System.out.println(p);
	GameItems i1 = new HealingItem("Erba", 10,"cura",GameItemTypes.HEAL);
	GameItems i2 = new HealingItem("Ampolla", 10,"cura",GameItemTypes.HEAL,150);
	p.addItem(i1);
	p.addItem(i2);
	p.addMonster(m);
	p.addMonster(m2);
	
	System.out.println(p.allItems());
	p.removeItem(i1);
	System.out.println(p.allItems());
	System.out.println(p);
	//PlayerControllPart.savePlayerInfo("prova.json", p);
	//PlayerImpl.loadPlayerInfo("prova.json");
	/*Player leggi = new PlayerImpl();
	leggi=PlayerImpl.loadPlayerInfo("prova.json");*/
    }

}
