package model.monster;

import java.util.List;
import model.battle.Moves;

public interface MonsterBuilder {

    /**
     * 
     * @return
     */
    MonsterBuilder monsterId(int id);

    /**
     * Build monster's level
     * 
     * @param lvl
     * @return a MonsterBuilder
     */
    MonsterBuilder level(int lvl);

    /**
     * 
     * @param health
     * @return
     */
    public MonsterBuilder health(int health);
    
    /**
     * 
     * @param atk
     * @return
     */
    public MonsterBuilder attack(int atk);    
   
    /**
     * 
     * @param dfs
     * @return
     */
    public MonsterBuilder defense(int dfs);
    
    /**
     * 
     * @param spd
     * @return
     */
    public MonsterBuilder speed(int spd);
    
    /**
     * Build monster's experience
     * 
     * @param exp
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
    MonsterBuilder movesList(List<Moves> movesList);

    /**
     * Build monster's species
     * 
     * @param MonsterSpecies species
     * @return a MonsterBuilder
     */
    MonsterBuilder species(MonsterSpecies species);

    /**
     * Build the monster
     * 
     * @return new Monster
     */
    Monster build();
}