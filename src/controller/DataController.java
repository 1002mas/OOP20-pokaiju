package controller;

public interface DataController {

	 /**
     * saves player game data
     * 
     */
    void saveData();

    /**
     * loads player game data
     * 
     * @return false if no data found, true if data loaded.
     */
    boolean loadData();
    
    /**
     * 
     * @return true if data exist, flase otherwise
     */
    boolean dataExsist();
    
    /**
     * 
     * @return true if maps data are loaded, flase otherwise
     */
    boolean loadMap();
}
