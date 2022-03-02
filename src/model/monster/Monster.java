package model.monster;

import model.battle.Attack;

public interface Monster {
	
	/**
	 * This function returns the name of the monster 
	 * @return monster's name
	 */
	String getName();
	
	/**
	 * This function returns the health of the monster
	 * @return monster's health
	 */
	int getHealth();
	
	/**
	 * This function set the health of the monster
	 * @param health
	 */
	void setHealth(int health);
	
	/**
	 * This function returns all the info of the monster
	 * @return monster's info
	 */
	String getInfo();
	
	/**
	 * This function returns all the info of the monster
	 * @return monster's level
	 */
	int getLevel();
	
	/**
	 * This function set the monster's level
	 * @param level
	 */
	void setLevel(int level);
	
	/**
	 * This function increase the monster's experience points
	 * @param experience
	 */
	void incExp(int experience);
	
	/**
	 * This function returns the monster's experience points
	 * @return monster's experience
	 */
	int getExp();
	
	/**
	 * This function set the monster's experience points
	 * @param experience
	 */
	void setExp(int exp);
	
	/**
	 * This function returns the experience point cap
	 * @return experience cap
	 */
	int getExpCap();
	
	public String toString();
	
	boolean getWild();
	
	boolean isAlive();
	
	Attack getAttack(int index);
	
	String getType();
	
	//******DA RIMUOVERE - ONLY DEBUG *********************
	
	public void setFirstEvolution(String secondName);
	
	public void setSecondEvolution(String thirdName);
	
	public void setSecondInfo(String secondInfo);
	
	public void setThirdInfo(String thirdInfo);
	
	public String getSecondInfo();
	
	public String getThirdInfo();
	
	public String getSecondName();
	
	public String getThirdName();
	

	//*****************************************************
	
}
