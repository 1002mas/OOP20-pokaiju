import java.io.IOException;

public class Prova {

    public static void main(String[] args) throws IOException {
	// TODO Auto-generated method stub
	Player p = new PlayerImpl("Luca","Unisex", 6969669, 0, 0);
	//System.out.println(p);
	Item i1 = new Item("Pokeball", 1, "cattura",ItemTypes.MONSTERBALL);
	Item i2 = new Item("Masterball", 10, "cattura 100%",ItemTypes.MONSTERBALL);
	p.addItem(i1);
	p.addItem(i2);
	System.out.println(p);
	//System.out.println(p);
	PlayerImpl.savePlayerInfo("prova.json", p);
	/*PlayerImpl.loadPlayerInfo("prova.json");*/
	/*Player leggi = new PlayerImpl();
	leggi=PlayerImpl.loadPlayerInfo("prova.json");*/
    }

}
