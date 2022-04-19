package model.monster;

import java.util.List;
import java.util.Optional;

import model.battle.Moves;

public interface MonsterSpecies {

    /**
     * This function returns monster's name.
     * 
     * @return monster name
     */
    String getName();

    /**
     * This function returns all the info of the monster.
     * 
     * @return info
     */
    String getInfo();

    /**
     * This function returns monster's type.
     * 
     * @return monster type
     */
    MonsterType getType();
    
    /**
     * This function returns monster's statistics.
     * 
     * @return monster statistics
     */
    MonsterStats getBaseStats();

    /**
     * This function returns the evolution type.
     * 
     * @return monster
     */
    EvolutionType getEvolutionType();

    /**
     * This function returns the evolution of the current monster.
     * 
     * @return next evolution monster
     */
    Optional<MonsterSpecies> getEvolution();
    
    /**
     * This function returns a list of all learnable moves.
     * 
     * @param level
     * @return a list of all learnable moves
     */
    List<Moves> getAllLearnableMoves();
}


