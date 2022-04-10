package model.monster;

import java.util.List;

import model.Pair;
import model.battle.Moves;
import model.gameitem.GameItems;

public interface MonsterSpeciesBuilder {

    /**
     * 
     * @param name
     * @return
     */
    MonsterSpeciesBuilder name(String name);

    /**
     * 
     * @param info
     * @return
     */
    MonsterSpeciesBuilder info(String info);
    
    /**
     * 
     * @param type
     * @return
     */
    MonsterSpeciesBuilder type(MonsterType type);    
    
    /**
     * 
     * @param evolution
     * @return
     */
    MonsterSpeciesBuilder evolution(MonsterSpecies evolution);
    
    /**
     * 
     * @param level
     * @return
     */
    MonsterSpeciesBuilder evolutionLevel(int level);
    
    /**
     * 
     * @param health
     * @return
     */
    MonsterSpeciesBuilder health(int health);
    
    /**
     * 
     * @param attack
     * @return
     */
    MonsterSpeciesBuilder attack(int attack);

    /**
     * 
     * @param defense
     * @return
     */
    MonsterSpeciesBuilder defense(int defense);

    /**
     * 
     * @param speed
     * @return
     */
    MonsterSpeciesBuilder speed(int speed);

    /**
     * 
     * @param allMoves
     * @return
     */
    MonsterSpeciesBuilder allMoves(List<Pair<Moves, Integer>> allMoves);
    
    MonsterSpeciesBuilder gameItem(GameItems gameItem);
    
    /**
     * 
     * @return
     */
    MonsterSpecies build();
    
}
