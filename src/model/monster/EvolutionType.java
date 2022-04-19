package model.monster;

/**
 * Type of evolution of the monster
 */
public enum EvolutionType {
    
    /**
     * Can not evolve.
     */
    NONE, 
    
    /**
     * Evolve by level.
     */
    LEVEL, 
    
    /**
     * Evolve by item.
     */
    ITEM;
}
