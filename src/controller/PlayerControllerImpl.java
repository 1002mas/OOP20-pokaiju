package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import controller.json.DataLoaderController;
import model.Pair;
import model.battle.MonsterBattle;
import model.battle.MonsterBattleImpl;
import model.battle.Moves;
import model.gameitem.GameItem;
import model.gameitem.GameItemTypes;
import model.map.GameMap;
import model.monster.Monster;
import model.npc.NpcMerchant;
import model.npc.NpcSimple;
import model.npc.NpcTrainer;
import model.npc.TypeOfNpc;
import model.player.Gender;
import model.player.Player;
import model.player.PlayerImpl;

public class PlayerControllerImpl implements PlayerController {

    private Player player;
    private boolean hasPlayerMoved;
    private GameMap map;// TODO initialize this field when you load saves
    private Direction currentDirection = Direction.DOWN;
    private Optional<MonsterBattle> battle = Optional.empty();
    private Optional<NpcMerchant> merchantInteraction = Optional.empty();
    private DataLoaderController dataController;

    public PlayerControllerImpl(DataLoaderController dataController) {

	this.dataController = dataController;
    }

    // --PLAYER--
    @Override
    public Optional<String> interact() { // ----Problema battaglia-----
	Pair<Integer, Integer> coord = generateCoordinates(this.currentDirection);
	Optional<NpcSimple> npc = dataController.getGameMap().getNpcAt(coord);
	if (npc.isPresent() && merchantInteraction.isEmpty()) {
	    Optional<String> result = npc.get().interactWith();
	    if (npc.get().getTypeOfNpc() == TypeOfNpc.TRAINER) {
		NpcTrainer trainer = (NpcTrainer) npc.get();
		if (!trainer.isDefeated()) {
		    this.battle = Optional.of(new MonsterBattleImpl(player, trainer));
		}
	    }
	    if (npc.get().getTypeOfNpc() == TypeOfNpc.MERCHANT) {
		merchantInteraction = Optional.of((NpcMerchant) (npc.get()));
	    }
	    return result;
	}
	return Optional.empty();
    }

    @Override
    public boolean hasMerchantInteractionOccurred() {
	return merchantInteraction.isPresent();
    }

    @Override
    public Optional<String> getMerchantName() {
	return merchantInteraction.isPresent() ? Optional.of(merchantInteraction.get().getName()) : Optional.empty();
    }

    @Override
    public List<String> getMerchantItems() {
	if (!hasMerchantInteractionOccurred()) {
	    return new ArrayList<>();
	}
	return this.merchantInteraction.get().getInventory().entrySet().stream()
		.map(listedItem -> listedItem.getKey().getNameItem()).collect(Collectors.toList());
    }

    @Override
    public int getMerchantItemPrice(String itemName) {
	if (!hasMerchantInteractionOccurred()) {
	    return -1;
	}
	return this.merchantInteraction.get().getPrice(dataController.getItem((itemName)));
    }

    private List<Pair<GameItem, Integer>> getBuyingItemAsList(Map<String, Integer> buyItem) {
	return buyItem.entrySet().stream()
		.map(listedItem -> new Pair<>(dataController.getItem(listedItem.getKey()), listedItem.getValue()))
		.collect(Collectors.toList());
    }

    @Override
    public int getMerchantTotalPrice(Map<String, Integer> buyItem) {
	return this.merchantInteraction.get().getTotalPrice(getBuyingItemAsList(buyItem));
    }

    @Override
    public boolean canPlayerBuyFromMerchant(Map<String, Integer> buyItem) {
	return getMerchantTotalPrice(buyItem) <= this.player.getMoney();
    }

    @Override
    public boolean buyMerchantItems(Map<String, Integer> buyItem) {
	if (canPlayerBuyFromMerchant(buyItem)) {
	    return this.merchantInteraction.get().buyItem(getBuyingItemAsList(buyItem), player);
	}
	return false;
    }

    @Override
    public void endInteractionWithMerchant() {
	this.merchantInteraction = Optional.empty();

    }

    @Override
    public Pair<Integer, Integer> getPlayerPosition() { // --
	return this.player.getPosition();
    }

    // TODO improve this function
    @Override
    public Pair<Integer, Integer> movePlayer(Direction direction) { // --
	this.currentDirection = direction;
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
	    Optional<Monster> wildMonster = map.getWildMonster(newPosition);
	    if (wildMonster.isPresent()) {
		this.battle = Optional.of(new MonsterBattleImpl(player, wildMonster.get()));
	    }
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
    public Map<String, Pair<Integer, Integer>> getAllNpcs() {
	Map<String, Pair<Integer, Integer>> res = new HashMap<>();
	for (NpcSimple npc : map.getAllNpcsInCurrentMap()) {
	    res.put(npc.getName(), npc.getPosition());
	}
	return res;
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
    public int getCurrentMapID() {
	return this.map.getCurrentMapId();
    }

    @Override
    public boolean hasBattleStarted() {
	return this.battle.isPresent();
    }

    @Override
    public Optional<BattleController> getBattleController() {
	if (this.battle.isPresent()) {
	    BattleController battleController = new BattleControllerImpl(this.battle.get());
	    this.battle = Optional.empty();
	    return Optional.of(battleController);
	}
	return Optional.empty();
    }

    @Override
    public void createNewPlayer(String name, Gender gender, int trainerNumber) { // --
	this.player = new PlayerImpl(name, gender, trainerNumber, new Pair<Integer, Integer>(0, 0));
	// this.map = new GameMapImpl(this.dataController.);//TODO get Map Data
	this.hasPlayerMoved = false;
	dataController.deleteData();
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
    public String getPlayerGender() { // --
	return player.getGender().name();
    }

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
	/*
	 * //TODO if (!player.isTeamFull()) { for (Monster monster : gameMonster) { if
	 * (monster.getId() == monsterId) { player.addMonster(monster); } return true; }
	 * }
	 */
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
	GameItem gameItem = dataController.getItem(i);
	if (player.getAllItems().containsKey(gameItem)) {
	    player.useItem(gameItem);
	}
    }

    @Override
    public void removeItem(String i) { // --
	player.removeItem(dataController.getItem(i));
    }

    @Override
    public List<String> getPlayerItemsName() { // --
	return player.getAllItems().entrySet().stream().map(map -> map.getKey().getNameItem())
		.collect(Collectors.toList());
    }

    @Override
    public int getPlayerMoney() { // --
	return player.getMoney();
    }

    @Override
    public int getItemQuantity(String item) { // --
	return player.getItemQuantity(dataController.getItem(item));
    }

    @Override
    public String getItemDescription(String item) { // --
	return dataController.getItem(item).getDescription();
    }

    @Override
    public String getItemtype(String item) { // --
	return dataController.getItem(item).getType().toString();
    }

    @Override
    public void save() { // --
	// TODO monsterBuilder id and monster storage
	dataController.saveData(0, this.map.getCurrentMapId(), null, player);
    }

    @Override
    public boolean load() { // --
	return dataController.loadData(player);
    }

    @Override
    public boolean dataExist() { // --
	return dataController.gameSaveExist();
    }

    @Override
    public int getMaximumBlocksInRow() {
	return dataController.getMaximumBlockInRow();
    }

    @Override
    public int getMaximumBlocksInColumn() {
	return dataController.getMaximumBlockInColumn();
    }

    @Override
    public Optional<Pair<String, String>> evolveByItem(String nameItem, int monsterId) {
	if (canEvolveByItem(nameItem, monsterId)) {
	    Monster monster = player.getAllMonsters().stream().filter(i -> i.getId() == monsterId).findAny().get();
	    String monsterName = monster.getName();
	    monster.evolve();
	    return Optional.of(new Pair<>(monsterName, monster.getName()));
	}
	return Optional.empty();
    }

    @Override
    public Optional<Pair<String, String>> evolveByLevel(int monsterId) {
	Optional<Monster> monster = player.getAllMonsters().stream().filter(i -> i.getId() == monsterId).findAny();
	if (monster.isPresent() && monster.get().canEvolveByLevel()) {
	    String monsterName = monster.get().getName();
	    monster.get().evolve();
	    return Optional.of(new Pair<>(monsterName, monster.get().getName()));
	}
	return Optional.empty();
    }

    @Override
    public void addItem(String item) {
	player.addItem(dataController.getItem(item));
    }

    // TODO check correct
    @Override
    public boolean canUseItem(String item) {
	return dataController.getItem(item).getType().equals(GameItemTypes.HEAL);
    }

    @Override
    public boolean canEvolveByItem(String nameItem, int monsterId) {
	Optional<Monster> monster = this.player.getAllMonsters().stream().filter(m -> m.getId() == monsterId).findAny();
	if (monster.isEmpty()) {
	    return false;
	}
	return monster.get().canEvolveByItem(dataController.getItem(nameItem));
    }

}
