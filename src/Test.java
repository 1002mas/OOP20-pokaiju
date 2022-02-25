import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			AttackImpl mossa1 = new AttackImpl("Sborrata", 25, "Water",10);
			AttackImpl mossa2 = new AttackImpl("Calibro 22", 35, "Fire",10);
			AttackImpl mossa3 = new AttackImpl("Fucile a pompa", 50, "Fire",5);
			AttackImpl mossa4 = new AttackImpl("Torbone", 60, "Grass",5);
			AttackImpl[] moveset = new AttackImpl[4];
			moveset[0] = mossa1;
			moveset[1] = mossa2;
			moveset[2] = mossa3;
			moveset[3] = mossa4;
			Monster uno = new MonsterImpl("Pippo", 100, 10, moveset, "Fire",false);
			Monster due = new MonsterImpl("Paperino", 100, 10, moveset, "Water",true);
			
			MonsterBattleImpl battle = new MonsterBattleImpl(new ArrayList<Monster>(List.of(uno)),new ArrayList<Monster>(List.of(due)));
			battle.click(4);
			battle.click(0);
			battle.click(0);
			battle.click(2);
			battle.click(0);
	}

}
