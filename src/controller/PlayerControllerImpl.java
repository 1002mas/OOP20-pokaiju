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
import model.battle.Moves;
import model.gameitem.GameItem;
import model.monster.Monster;
import model.npc.NpcMerchant;
import model.npc.NpcSimple;
import model.npc.TypeOfNpc;
import model.player.Player;

public class PlayerControllerImpl implements PlayerController {

	private Player player;
	private Direction currentDirection = Direction.DOWN;
	private Optional<MonsterBattle> battle = Optional.empty();
	private DataLoaderController dataController;
	private boolean hasTriggeredEvent;

	public PlayerControllerImpl(DataLoaderController dataController) {
		this.dataController = dataController;
	}

	// --PLAYER--

	@Override
	public void createNewPlayer(String name, String gender, int trainerNumber) {
		dataController.setPlayer(name, gender, trainerNumber);
		this.player = dataController.getPlayer();
	}

	@Override
	public String getPlayerName() {
		return player.getName();
	}

	@Override
	public int getTrainerNumber() {
		return player.getTrainerNumber();
	}

	@Override
	public String getPlayerGender() {
		return player.getGender().toString();
	}

	@Override
	public int getPlayerMoney() {
		return player.getMoney();
	}

	@Override
	public Optional<String> interact() {
		Pair<Integer, Integer> coord = generateCoordinates(this.currentDirection);
		boolean isNpcPresent = this.player.interactAt(coord);
		if (isNpcPresent) {
			this.battle = this.player.getPlayerBattle();
			NpcSimple npc = this.player.getLastInteractionWithNpc().get();
			Optional<String> result = npc.interactWith();
			this.hasTriggeredEvent = npc.getTriggeredEvent().isPresent();
			return result;
		}
		return Optional.empty();
	}

	@Override
	public boolean hasPlayerTriggeredEvent() {
		return this.hasTriggeredEvent || this.player.isTriggeredEvent();
	}
	// -- PLAYER MOVEMENT

	@Override
	public Pair<Integer, Integer> getPlayerPosition() {
		return this.player.getPosition();
	}

	@Override
	public boolean movePlayer(Direction direction) {
		this.currentDirection = direction;
		boolean hasPlayerMoved;
		switch (direction) {
		case UP: {
			hasPlayerMoved = this.player.moveUp();
			break;
		}
		case DOWN: {
			hasPlayerMoved = this.player.moveDown();
			break;
		}
		case LEFT: {
			hasPlayerMoved = this.player.moveLeft();
			break;
		}
		case RIGHT: {
			hasPlayerMoved = this.player.moveRight();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + direction);
		}
		this.battle = this.player.getPlayerBattle();
		return hasPlayerMoved;
	}

	@Override
	public boolean hasPlayerChangedMap() {
		return this.player.hasPlayerChangedMap();
	}

	private Pair<Integer, Integer> generateCoordinates(Direction direction) {

		Pair<Integer, Integer> newPosition = null;
		if (direction == Direction.DOWN) {
			newPosition = new Pair<>(player.getPosition().getFirst(), player.getPosition().getSecond() + 1);
		}
		if (direction == Direction.UP) {
			newPosition = new Pair<>(player.getPosition().getFirst(), player.getPosition().getSecond() - 1);
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
		return this.player.getMap().getCurrentMapId();
	}
	// -- MERCHANT --

	@Override
	public boolean hasMerchantInteractionOccurred() {
		Optional<NpcSimple> npc = this.player.getLastInteractionWithNpc();
		if (npc.isEmpty()) {
			return false;
		}
		return this.player.getLastInteractionWithNpc().get().getTypeOfNpc().equals(TypeOfNpc.MERCHANT);
	}

	@Override
	public List<String> getMerchantItems() {
		if (!hasMerchantInteractionOccurred()) {
			return new ArrayList<>();
		}
		NpcMerchant merchant = (NpcMerchant) this.player.getLastInteractionWithNpc().get();
		return merchant.getInventory().entrySet().stream().map(listedItem -> listedItem.getKey().getNameItem())
				.collect(Collectors.toList());
	}

	@Override
	public int getMerchantItemPrice(String itemName) {
		if (!hasMerchantInteractionOccurred()) {
			return -1;
		}
		NpcMerchant merchant = (NpcMerchant) this.player.getLastInteractionWithNpc().get();
		List<GameItem> itemList = new ArrayList<>();
		for (var map : merchant.getInventory().entrySet()) {
			itemList.add(map.getKey());
		}
		Optional<GameItem> item = getItem(itemName, itemList);
		if (item.isPresent()) {
			return merchant.getPrice(item.get());
		}
		return -1;
	}

	private List<Pair<GameItem, Integer>> getBuyingItemAsList(Map<String, Integer> buyItem) {

		NpcMerchant merchant = (NpcMerchant) this.player.getLastInteractionWithNpc().get();
		List<Pair<GameItem, Integer>> itemMap = new ArrayList<>();

		List<GameItem> list = new ArrayList<>();

		for (var mapMerchant : merchant.getInventory().entrySet()) {
			list.add(mapMerchant.getKey());
		}

		for (var mapPlayer : buyItem.entrySet()) {
			Optional<GameItem> item = getItem(mapPlayer.getKey(), list);
			if (item.isPresent()) {
				itemMap.add(new Pair<GameItem, Integer>(item.get(), mapPlayer.getValue()));
			}

		}
		return itemMap;
	}

	@Override
	public int getMerchantTotalPrice(Map<String, Integer> buyItem) {
		if (!hasMerchantInteractionOccurred()) {
			return -1;
		}
		NpcMerchant merchant = (NpcMerchant) this.player.getLastInteractionWithNpc().get();
		return merchant.getTotalPrice(getBuyingItemAsList(buyItem));
	}

	@Override
	public boolean canPlayerBuyFromMerchant(Map<String, Integer> buyItem) {
		return getMerchantTotalPrice(buyItem) <= this.player.getMoney();
	}

	@Override
	public boolean buyMerchantItems(Map<String, Integer> buyItem) {
		NpcMerchant merchant = (NpcMerchant) this.player.getLastInteractionWithNpc().get();
		if (canPlayerBuyFromMerchant(buyItem)) {
			return merchant.buyItem(getBuyingItemAsList(buyItem), player);
		}
		return false;
	}

	@Override
	public Map<String, Pair<Integer, Integer>> getAllNpcs() {
		Map<String, Pair<Integer, Integer>> res = new HashMap<>();
		for (NpcSimple npc : player.getMap().getAllNpcsInCurrentMap()) {
			if (npc.isVisible()) {
				res.put(npc.getName(), npc.getPosition());
			}
		}
		return res;
	}

	// --MONSTERS--

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
	public String getMonsterNameById(int monsterId) {
		return getMonster(monsterId).get().getName();
	}

	public List<Integer> getMonstersId() {
		List<Integer> playerMonster = new ArrayList<Integer>();
		for (Monster monster : player.getAllMonsters()) {
			playerMonster.add(monster.getId());
		}
		return playerMonster;
	}

	@Override
	public boolean isTeamFull() {
		return player.isTeamFull();
	}

	@Override
	public int getMonsterExp(int monsterId) {
		return getMonster(monsterId).get().getExp();
	}

	@Override
	public int getMonsterLevel(int monsterId) {
		return getMonster(monsterId).get().getLevel();
	}

	@Override
	public int getMonsterMaxHealth(int monsterId) {
		return getMonster(monsterId).get().getMaxHealth();
	}

	@Override
	public String getMonsterType(int monsterId) {
		return getMonster(monsterId).get().getSpecies().getType().toString();
	}

	private Optional<Monster> getMonster(int monsterId) {
		for (Monster monster : player.getAllMonsters()) {
			if (monster.getId() == monsterId) {
				return Optional.of(monster);
			}
		}
		for (Monster monster : player.getStorage().getCurrentBoxMonsters()) {
			if (monster.getId() == monsterId) {
				return Optional.of(monster);
			}
		}
		return Optional.empty();
	}

	// -- BOX --
	@Override
	public String getCurrentBoxName() {
		return this.player.getStorage().getCurrentBoxName();
	}

	@Override
	public void exchangeMonster(int teamMonsterId, int boxMonsterId) {
		Optional<Monster> tMonster = getMonster(teamMonsterId);
		if (tMonster.isPresent()) {

			this.player.getStorage().exchange(tMonster.get(), boxMonsterId);
		}

	}

	@Override
	public void depositMonster(int teamMonsterId) {
		Optional<Monster> tMonster = getMonster(teamMonsterId);
		if (tMonster.isPresent()) {
			this.player.getStorage().depositMonster(tMonster.get());
		}
	}

	@Override
	public void nextBox() {
		this.player.getStorage().nextBox();
		System.out.println(this.player.getStorage().getCurrentBoxName());
	}

	@Override
	public void previousBox() {
		this.player.getStorage().previousBox();
		System.out.println(this.player.getStorage().getCurrentBoxName());
	}

	@Override
	public List<Integer> getBoxMonsters() {
		return this.player.getStorage().getCurrentBoxMonsters().stream().map(m -> m.getId())
				.collect(Collectors.toList());
	}

	@Override
	public void withdrawMonster(int boxMonsterId) {
		this.player.getStorage().withdrawMonster(boxMonsterId);
	}

	@Override
	public int getMonsterHealth(int monsterId) {
		return getMonster(monsterId).get().getStats().getHealth();
	}

	@Override
	public int getMonsterAttack(int monsterId) {
		return getMonster(monsterId).get().getStats().getAttack();
	}

	@Override
	public int getMonsterDefense(int monsterId) {
		return getMonster(monsterId).get().getStats().getDefense();
	}

	@Override
	public int getMonsterSpeed(int monsterId) {
		return getMonster(monsterId).get().getStats().getSpeed();
	}

	// -- MOVES --
	@Override
	public List<String> getMovesNames(int monsterId) {
		Optional<Monster> m = getMonster(monsterId);
		List<String> moves = new ArrayList<>();
		if (m.isPresent()) {
			for (Moves mov : m.get().getAllMoves()) {
				moves.add(mov.getName());
			}
		}
		return moves;
	}

	@Override
	public int getMovePP(String moveName, int monsterID) {
		Optional<Monster> monster = getMonster(monsterID);
		if (monster.isPresent()) {
			for (Moves move : monster.get().getAllMoves()) {
				if (move.getName().equals(moveName)) {
					return monster.get().getCurrentPPByMove(move);
				}
			}
		}

		return -1;
	}

	// --ITEMS--
	private Optional<GameItem> getItem(String nameItem, List<GameItem> list) {
		return list.stream().filter(e -> e.getNameItem().equals(nameItem)).findFirst();
	}

	private List<GameItem> getItemFromMerchant(Map<GameItem, Integer> map) {
		List<GameItem> itemList = new ArrayList<>();
		for (var itemMap : map.entrySet()) {
			itemList.add(itemMap.getKey());
		}

		return itemList;

	}

	private List<GameItem> getItemsList(Map<GameItem, Integer> map) {
		List<GameItem> itemList = new ArrayList<>();
		for (var itemMap : this.player.getAllItems().entrySet()) {
			itemList.add(itemMap.getKey());
		}
		return itemList;
	}

	@Override
	public void useItem(String nameItem) {
		List<GameItem> itemList = getItemsList(this.player.getAllItems());
		Optional<GameItem> gameItem = getItem(nameItem, itemList);
		if (gameItem.isPresent()) {
			player.useItem(gameItem.get());
		}
	}

	@Override
	public void useItemOnMonster(String nameItem, int monsterId) {
		List<GameItem> itemList = getItemsList(this.player.getAllItems());
		Optional<GameItem> gameItem = getItem(nameItem, itemList);
		if (gameItem.isPresent()) {
			player.useItemOnMonster(gameItem.get(), getMonster(monsterId).get());
		}
	}

	@Override
	public void removeItem(String nameItem) {
		List<GameItem> itemList = getItemsList(this.player.getAllItems());
		Optional<GameItem> gameItem = getItem(nameItem, itemList);
		if (gameItem.isPresent()) {
			player.removeItem(gameItem.get());
		}

	}

	@Override
	public List<String> getPlayerItemsName() {
		return player.getAllItems().entrySet().stream().map(map -> map.getKey().getNameItem())
				.collect(Collectors.toList());
	}

	@Override
	public int getItemQuantity(String nameItem) {

		List<GameItem> itemList = getItemsList(this.player.getAllItems());
		Optional<GameItem> gameItem = getItem(nameItem, itemList);
		if (gameItem.isPresent()) {
			return player.getItemQuantity(gameItem.get());
		}
		return -1;
	}

	@Override
	public String getItemDescription(String nameItem) {
		List<GameItem> itemList;
		Optional<GameItem> gameItem;
		itemList = getItemsList(this.player.getAllItems());
		gameItem = getItem(nameItem, itemList);
		if (gameItem.isPresent()) {
			return gameItem.get().getDescription();
		}
		Optional<NpcSimple> npc = this.player.getLastInteractionWithNpc();
		if (npc.isPresent() && npc.get().getTypeOfNpc() == TypeOfNpc.MERCHANT) {
			NpcMerchant npcMerchant = (NpcMerchant) npc.get();
			itemList = getItemFromMerchant(npcMerchant.getInventory());
			gameItem = getItem(nameItem, itemList);
			if (gameItem.isPresent()) {
				return gameItem.get().getDescription();
			}

		}
		return null;

	}

	// ripetitività per player
	@Override
	public String getItemtype(String nameItem) {
		List<GameItem> itemList = getItemsList(this.player.getAllItems());
		Optional<GameItem> gameItem = getItem(nameItem, itemList);
		if (gameItem.isPresent()) {
			return gameItem.get().getType().toString();
		}
		return null;
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
		Monster monster = player.getAllMonsters().stream().filter(i -> i.getId() == monsterId).findAny().get();
		List<GameItem> itemList = getItemsList(this.player.getAllItems());
		Optional<GameItem> gameItem = getItem(nameItem, itemList);
		if (gameItem.isPresent()) {
			String monsterName = monster.getName();
			this.player.evolveMonster(monster, gameItem.get());
			return monster.getName().equals(monsterName) ? Optional.empty()
					: Optional.of(new Pair<>(monsterName, monster.getName()));
		}
		return Optional.empty();

	}

	@Override
	public boolean canUseItem(String nameItem) {
		List<GameItem> itemList = getItemsList(this.player.getAllItems());
		Optional<GameItem> gameItem = getItem(nameItem, itemList);
		if (gameItem.isPresent()) {
			return gameItem.get().getType().isConsumableInBag();
		}
		return false;
	}

	@Override
	public boolean canEvolveByItem(String nameItem, int monsterId) {
		List<GameItem> itemList = getItemsList(this.player.getAllItems());
		Optional<GameItem> gameItem = getItem(nameItem, itemList);
		if (gameItem.isPresent()) {
			Optional<Monster> monster = this.player.getAllMonsters().stream().filter(m -> m.getId() == monsterId)
					.findAny();
			return monster.isPresent() && monster.get().canEvolveByItem(gameItem.get());

		}
		return false;

	}

	@Override
	public int getBoxNumbers() {
		return this.player.getStorage().getMaxNumberOfBox();
	}

	@Override
	public boolean isPlayerLastMonsterLeft() {
		return (this.player.getAllMonsters().size() == 1);
	}

	@Override
	public int getMonstersForEachBox() {
		return this.player.getStorage().getMaxNumberOfBox();
	}

	@Override
	public boolean isItemPresent(String name) {
		for (var item : this.player.getAllItems().entrySet()) {
			if (item.getKey().getNameItem().equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Optional<String> getNpcName() {
		if (this.player.getLastInteractionWithNpc().isPresent()) {
			return Optional.of(this.player.getLastInteractionWithNpc().get().getName());
		}
		return Optional.empty();
	}

	@Override
	public void addItem(String nameItem) {
		Optional<NpcSimple> npc = this.player.getLastInteractionWithNpc();
		if (npc.isPresent() && npc.get().getTypeOfNpc() == TypeOfNpc.MERCHANT) {
			NpcMerchant npcMerchant = (NpcMerchant) npc.get();
			List<GameItem> itemList = getItemFromMerchant(npcMerchant.getInventory());
			Optional<GameItem> gameItem = getItem(nameItem, itemList);
			if (gameItem.isPresent()) {
				this.player.addItem(gameItem.get());
			}

		}
	
	}

	

}
