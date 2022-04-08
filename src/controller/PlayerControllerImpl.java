package controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import gui.Direction;
import model.Pair;
import model.battle.Moves;
import model.gameitem.GameItemTypes;
import model.gameitem.GameItems;
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
    private List<GameItems> gameItems;
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
    public Optional<String> interact(Pair<Integer, Integer> coord) { // ----Problema battaglia-----
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
    }

    /*
     * @Override public void addNpcTrainer(NpcTrainer npc) { //-??- non aggiunto
     * quando un npc perde, funzione non in uso dataController.addNpcsDefeated(npc);
     * }
     */

    // --MONSTERS--

    @Override
    public List<String> getMonstersNames() { // --
	List<String> playerMonster = new ArrayList<String>();
	for (Monster monster : player.allMonster()) {
	    playerMonster.add(monster.getName());
	}
	return playerMonster;
    }

    private Monster getMonster(String name) { // --
	for (Monster monster : player.allMonster()) {
	    if (monster.getName().equals(name)) {
		return monster;
	    }
	}
	return null;
    }

    @Override
    public boolean addMonster(String m) { // --

	if (!player.isTeamFull()) {
	    for (Monster monster : gameMonster) {
		if (monster.getName().equals(m)) {
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
    public void removeMonster(String monster) { // --
	player.removeMonster(getMonster(monster));
    }

    @Override
    public int getMonsterExp(String monster) { // --
	return getMonster(monster).getExp();
    }

    @Override
    public int getMonsterLevel(String monster) { // --
	return getMonster(monster).getLevel();
    }

    @Override
    public boolean getMonsterIsWild(String monster) { // --
	return getMonster(monster).getWild();
    }

    @Override
    public int getMonsterMaxHealth(String monster) { // --
	return getMonster(monster).getMaxHealth();
    }

    @Override
    public String getMonsterType(String monster) { // --
	return getMonster(monster).getSpecies().getType().toString();
    }

    @Override
    public List<String> getMovesNames(String monster) { // --
	Monster m = getMonster(monster);
	List<String> moves = new ArrayList<>();
	for (Moves mov : m.getAllMoves()) {
	    moves.add(mov.getName());
	}

	return moves;
    }

    @Override
    public int getMonsterHealth(String monster) { // --
	return getMonster(monster).getStats().getHealth();
    }

    @Override
    public int getMonsterAttack(String monster) { // --
	return getMonster(monster).getStats().getAttack();
    }

    @Override
    public int getMonsterDefense(String monster) { // --
	return getMonster(monster).getStats().getDefense();
    }

    @Override
    public int getMonsterSpeed(String monster) { // --
	return getMonster(monster).getStats().getSpeed();
    }

    // --ITEMS--

    @Override
    public void useItem(String i, String m) { // --
	Monster monster;
	GameItems item;
	if ((monster = getMonster(m)) != null && (item = getItem(i)) != null) {
	    player.useItem(item, monster);
	}

    }

    private GameItems getItem(String name) { // --
	for (GameItems item : player.allItems()) {
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
	for (GameItems i : player.allItems()) {
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

	for (GameItems i : gameItems) {
	    if (i.getNameItem().equals(item)) {
		player.addItem(i);
	    }
	}

    }

    @Override
    public void save(Player player) { // --
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
    public boolean canEvolveByItem(String nameItem, int monsterId) {
	return player.allMonster().stream().filter(i -> i.getId() == monsterId).findAny().get().canEvolveByItem(
		player.allItems().stream().filter(i -> i.getNameItem().equals(nameItem)).findAny().get());
	//TODO use global list of items instead of bag
    }

    @Override
    public Optional<Pair<String, String>> evolveByItem(String nameItem, int monsterId) {
	if(canEvolveByItem(nameItem, monsterId)) {
	    Monster monster = player.allMonster().stream().filter(i -> i.getId() == monsterId).findAny().get();
	    String monsterName = monster.getName();
	    monster.evolve();
	    return Optional.of(new Pair<>(monsterName, monster.getName()));
	}
	return Optional.empty();
    }
}
