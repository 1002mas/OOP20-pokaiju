package controller;
import java.util.List;

import model.battle.MovesData;
import model.gameitem.GameItem;
import model.monster.MonsterSpecies;
import model.npc.NpcSimple;

public interface DataLoaderController {

	List<MovesData> getMoves();
	
	List<NpcSimple> getNpcs();
	
	List<MonsterSpecies> getMonsterSpecies();
	
	List<GameItem> getGameEvolution();
}
