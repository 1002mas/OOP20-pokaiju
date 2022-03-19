package model.monster;

import java.util.List;

import model.battle.Moves;

public interface MonsterBuilder {

    /**
     * Build monster's level
     * 
     * @param lvl
     * @return a MonsterBuilder
     */
    MonsterBuilder level(int lvl);

    /**
     * Build monster's stats
     * 
     * @param stats (health, attack, defense, speed)
     * @return a MonsterBuilder
     */
    MonsterBuilder stats(MonsterStatsImpl stats);

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
     * @param MonsterSpeciesImpl species
     * @return a MonsterBuilder
     */
    MonsterBuilder species(MonsterSpeciesImpl species);

    /**
     * Build the monster
     * 
     * @return new Monster
     */
    Monster build();

}