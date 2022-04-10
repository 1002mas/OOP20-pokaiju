package controller;

import java.util.Optional;

import model.gameitem.GameItemTypes;

public class GameItemSupport {

	private String nameItem;
	private int quantity;
	private String description;
	private GameItemTypes type;
	private Optional<Integer> healing;

	public GameItemSupport(String nameItem, int quantity, String description, GameItemTypes type,
			Optional<Integer> healing) {
		this.nameItem = nameItem;
		this.quantity = quantity;
		this.description = description;
		this.type = type;
		this.healing = healing;
	}
	
	public String getNameItem() {
		return this.nameItem;
	}

	public int getQuantity() {
		return this.quantity;
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
