package model.battle;

import java.util.Optional;

import model.gameitem.GameItem;
import model.monster.Monster;
import model.npc.NpcTrainer;
import model.player.Player;

public interface MonsterBattle {
	/***
	 * 
	 * this function return a move that the enemy can do
	 * 
	 * @return an enemy's move
	 */
	Moves enemyAttack();
	
	/***
	 * 
	 * this function tries to finish the battle
	 * 
	 * @return true if the player escaped, false otherwise
	 */
	boolean escape();
	/***
	 * 
	 * this function tries to capture the enemy
	 * 
	 * @return true if the enemy has been captured, false otherwhise
	 */
	boolean capture();
	/***
	 * 
	 * this function return if the battle is over
	 * 
	 * @return true if the battle is over, false otherwhise
	 */
	boolean isOver();
	/***
	 * 
	 * this function changes the monster that is fighting with another available
	 * 
	 * @param index the position in the list of the player's monster
	 * 
	 * @return true if the change has been made, false otherwhise
	 */
	boolean playerChangeMonster(int index);
	/***
	 * 
	 * this function tries to use a move of the monster that is fighting
	 * 
	 * @param moveIndex the position in the list of the monster's moves
	 * 
	 * @return true if the move can be made, false otherwhise
	 */
	boolean movesSelection(int moveIndex);
	/***
	 * 
	 * this function return if the monster that is fighting is alive
	 * 
	 * @return true if the monster is alive, false otherwhise
	 */
	boolean isCurrentMonsterAlive();
	/***
	 * 
	 * this function tries to use an item on a player's monster
	 * 
	 * @param item the item that the player wants to use
	 * 
	 * @return true if the item has been used, false otherwhise
	 */
	boolean useItem(GameItem item);
	/***
	 * 
	 * the function return the player's monster that is fighting
	 * 
	 * @return the player's monster that is fighting
	 */
	Monster getCurrentPlayerMonster();
	/***
	 * 
	 * the function return the enemy's monster that is fighting
	 * 
	 * @return the enemy's monster that is fighting
	 */
	Monster getCurrentEnemyMonster();
	/***
	 * 
	 * the function return the player who is playing
	 * 
	 * @return the player who is playing
	 */
	Player getPlayer();
	/***
	 * 
	 * this function return the enemy trainer (Npc)
	 * 
	 * @return the enemy trainer
	 */
	Optional<NpcTrainer> getNpcEnemy();
	/***
	 * 
	 * the function return the player's monster finished the PP in all moves
	 * 
	 * @return true the PPs are over, false otherwhise
	 */
	boolean isOverOfPP();
	
}
