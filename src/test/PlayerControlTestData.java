package test;
import model.Pair;
import model.player.Gender;
import model.player.MonsterStorage;
import model.player.MonsterStorageImpl;
import model.player.Player;
import model.player.PlayerImpl;

public class PlayerControlTestData {

	public static void main(String[] args) {
		Player player = new PlayerImpl("player", Gender.WOMAN, 0, new Pair<Integer,Integer>(1,1), null);
		MonsterStorage m = new MonsterStorageImpl(player);
		//System.out.println(m);
		
	}

}
