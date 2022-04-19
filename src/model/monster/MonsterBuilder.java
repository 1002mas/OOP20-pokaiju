package model.monster;

import java.util.List;

import model.Pair;
import model.battle.Moves;

public interface MonsterBuilder {

    /**
     * Build monster level.
     * 
     * @param level
     * @throws IllegalArgumentException if level is under minimum level or over max level
     * @return MonsterBuilder
     */
    MonsterBuilder level(int level);

    /**
     * Build monster health.
     * 
     * @param health
     * @return a MonsterBuilder
     */
    MonsterBuilder health(int health);

    /**
     * Build monster attack.
     * 
     * @param attack
     * @return a MonsterBuilder
     */
    MonsterBuilder attack(int attack);

    /**
     * Build monster defense.
     * 
     * @param defense
     * @return a MonsterBuilder
     */
    MonsterBuilder defense(int defense);

    /**
     * Build monster speed.
     * 
     * @param speed
     * @return a MonsterBuilder
     */
    MonsterBuilder speed(int speed);

    /**
     * Build monster's experience.
     * 
     * @param exp
     * @throws IllegalArgumentException if experience is under zero or over
     *                                  experience cap
     * @return a MonsterBuilder
     */
    MonsterBuilder exp(int exp);

    /**
     * Build monster isWild parameter.
     * 
     * @param isWild
     * @return a MonsterBuilder
     */
    MonsterBuilder wild(boolean isWild);

    /**
     * Build monster's moves.
     * 
     * @param movesList
     * @return a MonsterBuilder
     */
    MonsterBuilder movesList(List<Pair<Moves, Integer>> movesList);

    /**
     * Build monster's species.
     * 
     * @param species
     * @return a MonsterBuilder
     */
    MonsterBuilder species(MonsterSpecies species);

    /**
     * Build monster.
     * 
     * @throws IllegalStateException when obligatory parameters are missing
     * @return monster builded
     */
    Monster build();
}
