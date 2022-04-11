package controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import gui.Direction;
import model.Pair;
import model.battle.Moves;
import model.gameitem.GameItemTypes;
import model.gameitem.GameItem;
import model.monster.EvolutionType;
import model.monster.Monster;
import model.monster.MonsterSpeciesByItem;
import model.player.Gender;
import model.player.Player;
import model.player.PlayerImpl;

public class PlayerControllerImpl implements PlayerController {

	private Player player;
	private boolean hasPlayerMoved;
	private List<Monster> gameMonster;
	private List<GameItem> gameItems;
	private DataController dataController;

	public PlayerControllerImpl(DataController dataController) {

		this.dataController = dataController;
		loadGameData();
	}

	private void loadGameData() { // --
		gameItems = dataController.loadItems();
		gameMonster = dataController.loadMonsters();

	}

	// --PLAYER--
	@Override
	public Optional<String> interact(Direction direction) { // ----Problema battaglia-----
		Pair<Integer, Integer> coord = generateCoordinates(direction);
		if (dataController.getGameMap().getNpcAt(coord).isPresent()) {
			Optional<String> result = dataController.getGameMap().getNpcAt(coord).get().interactWith();
			return result;
		}
		return Optional.empty();
	}

	@Override
	public Pair<Integer, Integer> getPlayerPosition() { // --
		return this.player.getPosition();
	}

	@Override
	public Pair<Integer, Integer> movePlayer(Direction direction) { // --
		if (canChangeMap()) {
			dataController.setNpcDefeatedFromMap();
			dataController.getGameMap().changeMap(getPlayerPosition());
			dataController.setNpcDefeatedInMap();
			setPlayerPosition(dataController.getGameMap().getPlayerMapPosition().get());
			setHasPlayerMoved(true);
		} else if (canPassThrough(direction)) {
			Pair<Integer, Integer> newPosition = generateCoordinates(direction);
			player.setPosition(newPosition);
			setHasPlayerMoved(true);
		}
		return getPlayerPosition();
	}

	private void setPlayerPosition(Pair<Integer, Integer> position) { // --
		this.player.setPosition(position);
	}

	@Override
	public boolean hasPlayerMoved() { // --
		return hasPlayerMoved;
	}

	private void setHasPlayerMoved(boolean value) { // --
		hasPlayerMoved = value;
	}

	@Override
	public boolean canPassThrough(Direction direction) { // --
		Pair<Integer, Integer> newPosition = generateCoordinates(direction);
		return dataController.getGameMap().canPassThrough(newPosition);
	}

	@Override
	public boolean canChangeMap() { // --
		return dataController.getGameMap().canChangeMap(getPlayerPosition());
	}

	@Override
	public Player getPlayer() { // --
		return player;
	}

	private Pair<Integer, Integer> generateCoordinates(Direction direction) { // --

		Pair<Integer, Integer> newPosition = null;
		if (direction == Direction.DOWN) {
			newPosition = new Pair<>(player.getPosition().getFirst(), player.getPosition().getSecond() - 1);
		}
		if (direction == Direction.UP) {
			newPosition = new Pair<>(player.getPosition().getFirst(), player.getPosition().getSecond() + 1);
		}
		if (direction == Direction.LEFT) {
			newPosition = new Pair<>(player.getPosition().getFirst() - 1, player.getPosition().getSecond());
		}
		if (direction == Direction.RIGHT) {
			newPosition = new Pair<>(player.getPosition().getFirst() + 1, player.getPosition().getSecond());
		}
		return newPosition;
	}

	@Override
	public String getPlayerName() { // --
		return player.getName();
	}

	@Override
	public int getTrainerNumber() { // --
		return player.getTrainerNumber();
	}

	@Override
	public String getGender() { // --
		return player.getGender().name();
	}

	
	@Override
	public void createNewPlayer(String name, Gender gender, int trainerNumber) { // --
		this.player = new PlayerImpl(name, gender, trainerNumber, new Pair<Integer, Integer>(0, 0));
		this.hasPlayerMoved = false;
		dataController.deleteNpcData();
	}

	/*
	 * @Override public void addNpcTrainer(NpcTrainer npc) { //-??- non aggiunto
	 * quando un npc perde, funzione non in uso dataController.addNpcsDefeated(npc);
	 * }
	 */

	// --MONSTERS--

	@Override
	public String getMonsterNameById(int monsterId) { // --
		return getMonster(monsterId).getName();
	}
	
	public List<Integer> getMonstersId() {
		List<Integer> playerMonster = new ArrayList<Integer>();
		for (Monster monster : player.getAllMonsters()) {
			playerMonster.add(monster.getId());
		}
		return playerMonster;
	}
	

	private Monster getMonster(int monsterId) { // --
		for (Monster monster : player.getAllMonsters()) {
			if (monster.getId() == monsterId) {
				return monster;
			}
		}
		return null;
	}

	@Override
	public boolean addMonster(int monsterId) { // --

		if (!player.isTeamFull()) {
			for (Monster monster : gameMonster) {
				if (monster.getId() == monsterId) {
					player.addMonster(monster);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isTeamFull() { // --
		return player.isTeamFull();
	}

	@Override
	public void removeMonster(int monsterId) { // --
		player.removeMonster(getMonster(monsterId));
	}

	@Override
	public int getMonsterExp(int monsterId) { // --
		return getMonster(monsterId).getExp();
	}

	@Override
	public int getMonsterLevel(int monsterId) { // --
		return getMonster(monsterId).getLevel();
	}

	@Override
	public boolean getMonsterIsWild(int monsterId) { // --
		return getMonster(monsterId).getWild();
	}

	@Override
	public int getMonsterMaxHealth(int monsterId) { // --
		return getMonster(monsterId).getMaxHealth();
	}

	@Override
	public String getMonsterType(int monsterId) { // --
		return getMonster(monsterId).getSpecies().getType().toString();
	}

	@Override
	public List<String> getMovesNames(int monsterId) { // --
		Monster m = getMonster(monsterId);
		List<String> moves = new ArrayList<>();
		for (Moves mov : m.getAllMoves()) {
			moves.add(mov.getName());
		}

		return moves;
	}

	@Override
	public int getMonsterHealth(int monsterId) { // --
		return getMonster(monsterId).getStats().getHealth();
	}

	@Override
	public int getMonsterAttack(int monsterId) { // --
		return getMonster(monsterId).getStats().getAttack();
	}

	@Override
	public int getMonsterDefense(int monsterId) { // --
		return getMonster(monsterId).getStats().getDefense();
	}

	@Override
	public int getMonsterSpeed(int monsterId) { // --
		return getMonster(monsterId).getStats().getSpeed();
	}

	// --ITEMS--

	@Override
	public void useItem(String i) { 
		GameItem item = getItem(i) ;
		if (item != null) {
			player.useItem(item);
		}
	}
	/*
	public void useItem(String i, int monsterId) { // --
		Monster monster;
		GameItems item;
		if ((monster = getMonster(monsterId)) != null && (item = getItem(i)) != null) {
			player.useItem(item);
		}

	}
	*/

	private GameItem getItem(String name) { // --
		for (GameItem item : player.getAllItems()) {
			if (item.getNameItem().equals(name)) {
				return item;
			}
		}
		return null;
	}

	@Override
	public void removeItem(String i) { // --
		player.removeItem(getItem(i));
	}

	@Override
	public boolean buyItem(String i, int price) { // --
		return player.buyItem(getItem(i), price);
	}

	@Override
	public List<String> getPlayerItemsName() { // --
		List<String> items = new ArrayList<>();
		for (GameItem i : player.getAllItems()) {
			items.add(i.getNameItem());
		}
		return items;
	}

	@Override
	public int getMoney() { // --
		return player.getMoney();
	}

	@Override
	public void setMoney(int money) { // --
		player.setMoney(money);
	}

	@Override
	public int getItemQuantity(String item) { // --
		return getItem(item).getNumber();
	}

	@Override
	public String getItemDescription(String item) { // --
		return getItem(item).getDescription();
	}

	@Override
	public String getItemtype(String item) { // --
		return getItem(item).getType().toString();
	}

	@Override
	public void addItem(String item) { // --

		for (GameItem i : gameItems) {
			if (i.getNameItem().equals(item)) {
				player.addItem(i);
			}
		}

	}

	@Override
	public void save() { // --
		dataController.saveData(player);
	}

	@Override
	public boolean load() { // --
		return dataController.loadData(player);
	}

	@Override
	public boolean dataExist() { // --
		return dataController.dataExsist();
	}

	@Override
	public boolean usableItem(String item) { // --
		return getItem(item).getType().equals(GameItemTypes.MONSTERBALL);
	}

	@Override
	public int getMaximumBlocksInRow() {
		return dataController.getMaximumBlocksInRow();
	}

	@Override
	public int getMaximumBlocksInColumn() {
		return dataController.getMaximumBlocksInColumn();
	}
	
	 @Override
	public boolean canEvolveByItem(String nameItem, int monsterId) {
	return player.getAllMonsters().stream().filter(i -> i.getId() == monsterId).findAny().get().canEvolveByItem(
			player.getAllItems().stream().filter(i -> i.getNameItem().equals(nameItem)).findAny().get());
	    }

	    @Override
	    public Optional<Pair<String, String>> evolveByItem(String nameItem, int monsterId) {
		if(canEvolveByItem(nameItem, monsterId)) {
		    Monster monster = player.getAllMonsters().stream().filter(i -> i.getId() == monsterId).findAny().get();
		    String monsterName = monster.getName();
		    monster.evolve();
		    return Optional.of(new Pair<>(monsterName, monster.getName()));
		}
		return Optional.empty();
	    
		
	    }
}
