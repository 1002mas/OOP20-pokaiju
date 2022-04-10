package model.monster;

import java.util.List;

import model.Pair;
import model.battle.Moves;

public interface MonsterBuilder {

    /**
     * Build monster level
     * 
     * @param monster level
     * @throws IllegalArgumentException if level is under min level or over max level
     * @return a MonsterBuilder
     */
    MonsterBuilder level(int level);

    /**
     * Build monster health
     * 
     * @param monster health
     * @return a MonsterBuilder
     */
    MonsterBuilder health(int health);
    
    /**
     * Build monster attack
     * 
     * @param monster attack
     * @return a MonsterBuilder
     */
    MonsterBuilder attack(int attack);    
   
    /**
     * Build monster defense
     * 
     * @param monster defense
     * @return a MonsterBuilder
     */
    MonsterBuilder defense(int defense);
    
    /**
     * Build monster speed
     * 
     * @param monster speed
     * @return a MonsterBuilder
     */
    MonsterBuilder speed(int speed);
    
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
     * Build monster
     * 
     * @throws IllegalStateException when obligatory parameters are missing
     * @return monster builded
     */
    Monster build();
}