package model.battle;

import java.util.Optional;

import model.gameitem.GameItems;
import model.monster.Monster;
import model.npc.NpcTrainer;
import model.player.Player;

public interface MonsterBattle {
	
	Moves enemyAttack();

	boolean escape();

	boolean capture();

	boolean isOver();

	boolean playerChangeMonster(int index);

	boolean movesSelection(int moveIndex);

	boolean isCurrentMonsterAlive();

	boolean useItem(GameItems item);
	
	Monster getCurrentPlayerMonster();
	
	Monster getCurrentEnemyMonster();
	
	Player getPlayer();
	
	Optional<NpcTrainer> getNpcEnemy();
	
}
