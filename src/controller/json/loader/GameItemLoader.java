package controller.json.loader;

import java.util.Optional;

import model.gameitem.GameItemTypes;

public class GameItemLoader {

	private String nameItem;
	private String description; 
	private GameItemTypes type;
	private Optional<Integer> healing;

	public GameItemLoader(String nameItem, String description, GameItemTypes type,
			Optional<Integer> healing) {
		this.nameItem = nameItem;
		this.description = description;
		this.type = type;
		this.healing = healing;
	}
	
	public String getNameItem() {
		return this.nameItem;
	}

	public String getDescription() {
		return this.description;
	}

	public GameItemTypes getType() {
		return this.type;
	}

	public Optional<Integer> getHealing() {
		return this.healing;
	}
	
}
