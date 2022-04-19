package model.monster;

import java.util.List;

import model.battle.Moves;
import model.gameitem.GameItem;

public interface MonsterSpeciesBuilder {

    /**
     * Build species level.
     * 
     * @param name
     * @return a MonsterSpeciesBuilder
     */
    MonsterSpeciesBuilder name(String name);

    /**
     * Build species info.
     * 
     * @param info
     * @return a MonsterSpeciesBuilder
     */
    MonsterSpeciesBuilder info(String info);
    
    /**
     * Build species type.
     * 
     * @param type
     * @return a MonsterSpeciesBuilder
     */
    MonsterSpeciesBuilder monsterType(MonsterType type);    
    
    /**
     * Build species evolution.
     * 
     * @param evolution
     * @return a MonsterSpeciesBuilder
     */
    MonsterSpeciesBuilder evolution(MonsterSpecies evolution);
    
    /**
     * Build species evolutionLevel for evolving by level.
     * 
     * @param level
     * @return a MonsterSpeciesBuilder
     */
    MonsterSpeciesBuilder evolutionLevel(int level);
    
    /**
     * Build species health.
     * 
     * @param health
     * @return a MonsterSpeciesBuilder
     */
    MonsterSpeciesBuilder health(int health);
    
    /**
     * Build species attack.
     * 
     * @param attack
     * @return a MonsterSpeciesBuilder
     */
    MonsterSpeciesBuilder attack(int attack);

    /**
     * Build species defense.
     * 
     * @param defense
     * @return a MonsterSpeciesBuilder
     */
    MonsterSpeciesBuilder defense(int defense);

    /**
     * Build species speed.
     * 
     * @param speed
     * @return a MonsterSpeciesBuilder
     */
    MonsterSpeciesBuilder speed(int speed);
    
    /**
     * Build species item for evolving by level.
     * 
     * @param gameItem
     * @return a MonsterSpeciesBuilder
     */
    MonsterSpeciesBuilder gameItem(GameItem gameItem);
    
    /**
     * Build species moves.
     * 
     * @param allMoves
     * @return a  MonsterSpeciesBuilder
     */
    MonsterSpeciesBuilder movesList(List<Moves> movesList);
    
    /**
     * Build MonsterSpecies.
     * 
     * @throws IllegalStateException if the obligatory fields are missing
     * @return a MonsterSpecies builded
     */
    MonsterSpecies build();
    
}
