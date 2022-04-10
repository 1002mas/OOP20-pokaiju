package model.monster;

import java.util.List;

import model.Pair;
import model.battle.Moves;

public interface MonsterBuilder {

    /**
     * Build monster's level
     * 
     * @param lvl
     * @throws IllegalArgumentException if level is under min level or over max level
     * @return a MonsterBuilder
     */
    MonsterBuilder level(int lvl);

    /**
     * 
     * @param health
     * @return
     */
    MonsterBuilder health(int health);
    
    /**
     * 
     * @param atk
     * @return
     */
    MonsterBuilder attack(int atk);    
   
    /**
     * 
     * @param dfs
     * @return
     */
    MonsterBuilder defense(int dfs);
    
    /**
     * 
     * @param spd
     * @return
     */
    MonsterBuilder speed(int spd);
    
    /**
     * Build monster's experience
     * 
     * @param exp
     * @throws IllegalArgumentException if exp is under zero or over exp cap
     * @return a MonsterBuilder
     */
    MonsterBuilder exp(int exp);

    /**
     * Build monster isWild parameter
     * 
     * @param isWild
     * @return a MonsterBuilder
     */
    MonsterBuilder isWild(boolean isWild);

    /**
     * Build monster's moves
     * 
     * @param a list of moves
     * @return a MonsterBuilder
     */
    MonsterBuilder movesList(List<Pair<Moves, Integer>> movesList);

    /**
     * Build monster's species
     * 
     * @param MonsterSpecies species
     * @return a MonsterBuilder
     */
    MonsterBuilder species(MonsterSpecies species);

    /**
     * @throws IllegalStateException when obligatory parameters are missing
     * @return monster builded
     */
    Monster build();
}