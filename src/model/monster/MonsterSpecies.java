package model.monster;

import java.util.Optional;

public interface MonsterSpecies {

    /**
     * This function returns monster's name
     * 
     * @return monster name
     */
    String getName();

    /**
     * This function returns all the info of the monster
     * 
     * @return info
     */
    String getInfo();

    /**
     * This function returns monster's type
     * 
     * @return monster type
     */
    MonsterType getType();
    
    /**
     * This function returns monster's stats
     * 
     * @return monster stats
     */
    MonsterStats getStats();

    /**
     * This function returns the evolution type
     * 
     * @return monster
     */
    EvolutionType getEvolutionType();

    /**
     * This function returns the evolution of the current monster
     * 
     * @return next evolution monster
     */
    Optional<MonsterSpecies> getEvolution();



}


