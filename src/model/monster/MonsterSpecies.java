package model.monster;
import java.util.Optional;
import model.GameItem.*;

public interface MonsterSpecies{
	
	/**
	 * This function returns monster's name
	 * @return monster name
	 */
	String getName();
	
	/**
	 * This function returns all the info of the monster
	 * @return info
	 */
	String getInfo();
	
	/**
	 * This function returns monster's type
	 * @return monster type
	 */
	MonsterType getType();
	
	/**
	 * This function returns the level needed for the evolution
	 * @return monster evolution level
	 */
	int getEvolutionLevel();
	
	/**
	 * This function returns the evolution type
	 * @return monster 
	 */
	EvolutionType getEvolutionType();
	
	/**
	 * This function returns the evolution of the current monster
	 * @return next evolution monster
	 */
	Optional<MonsterSpeciesImpl> getEvolution();
	
	/**
	 * This function returns the item needed for the evolution
	 * @return item needed for evolution
	 */
	GameItems getItem();
	
}
