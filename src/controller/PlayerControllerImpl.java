package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import model.Pair;
import model.battle.MonsterBattle;
import model.battle.Moves;
import model.gameitem.GameItem;
import model.monster.Monster;
import model.npc.NpcMerchant;
import model.npc.NpcSimple;
import model.npc.TypeOfNpc;
import model.player.Gender;
import model.player.Player;

public class PlayerControllerImpl implements PlayerController {

    private Player player;
    private Direction currentDirection = Direction.DOWN;
    private Optional<MonsterBattle> battle = Optional.empty();
    private final DataController dataController;
    private boolean hasTriggeredEvent;

    public PlayerControllerImpl(final DataController dataController) {
        this.dataController = dataController;
    }

    // --PLAYER--
    /**
     * {@inheritDoc}
     */
    @Override
    public void createNewPlayer(final String name, final String gender, final int trainerNumber) {
        Gender g = null;
        for (final Gender h : Gender.values()) {
            if (h.toString().equals(gender)) {
                g = h;
                break;
            }
        }
        dataController.setPlayer(name, g, trainerNumber);
        this.player = dataController.getPlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPlayerName() {
        return player.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTrainerNumber() {
        return player.getTrainerNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPlayerGender() {
        return player.getGender().toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlayerLastMonsterLeft() {
        return this.player.getAllMonsters().size() == 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerMoney() {
        return player.getMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getGender() {
        final List<String> genders = new ArrayList<>();
        for (final Gender type : Gender.values()) {
            genders.add(type.toString());
        }
        return genders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> interact() {
        final Pair<Integer, Integer> coord = generateCoordinates(this.currentDirection);
        final boolean isNpcPresent = this.player.interactAt(coord);
        if (isNpcPresent) {
            this.battle = this.player.getPlayerBattle();
            final NpcSimple npc = this.player.getLastInteractionWithNpc().get();
            final Optional<String> result = npc.interactWith();
            this.hasTriggeredEvent = npc.getTriggeredEvent().isPresent();
            return result;
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPlayerTriggeredEvent() {
        return this.hasTriggeredEvent || this.player.isTriggeredEvent();
    }

    // -- PLAYER MOVEMENT
    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<Integer, Integer> getPlayerPosition() {
        return this.player.getPosition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean movePlayer(final Direction direction) {
        this.currentDirection = direction;
        boolean hasPlayerMoved;
        switch (direction) {
        case UP:
            hasPlayerMoved = this.player.moveUp();
            break;

        case DOWN:
            hasPlayerMoved = this.player.moveDown();
            break;

        case LEFT:
            hasPlayerMoved = this.player.moveLeft();
            break;

        case RIGHT:
            hasPlayerMoved = this.player.moveRight();
            break;

        default:
            throw new IllegalArgumentException("Unexpected value: " + direction);
        }
        this.battle = this.player.getPlayerBattle();
        return hasPlayerMoved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPlayerChangedMap() {
        return this.player.hasPlayerChangedMap();
    }

    private Pair<Integer, Integer> generateCoordinates(final Direction direction) {

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

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentMapID() {
        return this.player.getMap().getCurrentMapId();
    }

    // -- MERCHANT --
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMerchantInteractionOccurred() {
        final Optional<NpcSimple> npc = this.player.getLastInteractionWithNpc();
        if (npc.isEmpty()) {
            return false;
        }
        return this.player.getLastInteractionWithNpc().get().getTypeOfNpc().equals(TypeOfNpc.MERCHANT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> getNpcName() {
        if (this.player.getLastInteractionWithNpc().isPresent()) {
            return Optional.of(this.player.getLastInteractionWithNpc().get().getName());
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getMerchantItems() {
        if (!hasMerchantInteractionOccurred()) {
            return new ArrayList<>();
        }
        final NpcMerchant merchant = (NpcMerchant) this.player.getLastInteractionWithNpc().get();
        return merchant.getInventory().entrySet().stream().map(listedItem -> listedItem.getKey().getNameItem())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMerchantItemPrice(final String itemName) {
        if (!hasMerchantInteractionOccurred()) {
            return -1;
        }

        final NpcMerchant merchant = (NpcMerchant) this.player.getLastInteractionWithNpc().get();
        final List<GameItem> itemList = new ArrayList<>();
        for (final var map : merchant.getInventory().entrySet()) {
            itemList.add(map.getKey());
        }
        final Optional<GameItem> item = getItem(itemName, itemList);
        if (item.isPresent()) {
            return merchant.getPrice(item.get());
        }
        return -1;
    }

    private List<Pair<GameItem, Integer>> getBuyingItemAsList(final Map<String, Integer> buyItem) {

        final NpcMerchant merchant = (NpcMerchant) this.player.getLastInteractionWithNpc().get();
        final List<Pair<GameItem, Integer>> itemMap = new ArrayList<>();

        final List<GameItem> list = new ArrayList<>();

        for (final var mapMerchant : merchant.getInventory().entrySet()) {
            list.add(mapMerchant.getKey());
        }

        for (final var mapPlayer : buyItem.entrySet()) {
            final Optional<GameItem> item = getItem(mapPlayer.getKey(), list);
            if (item.isPresent()) {
                itemMap.add(new Pair<GameItem, Integer>(item.get(), mapPlayer.getValue()));
            }

        }
        return itemMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMerchantTotalPrice(final Map<String, Integer> buyItem) {
        if (!hasMerchantInteractionOccurred()) {
            return -1;
        }
        final NpcMerchant merchant = (NpcMerchant) this.player.getLastInteractionWithNpc().get();
        return merchant.getTotalPrice(getBuyingItemAsList(buyItem));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canPlayerBuyFromMerchant(final Map<String, Integer> buyItem) {
        return getMerchantTotalPrice(buyItem) <= this.player.getMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean buyMerchantItems(final Map<String, Integer> buyItem) {
        final NpcMerchant merchant = (NpcMerchant) this.player.getLastInteractionWithNpc().get();
        if (canPlayerBuyFromMerchant(buyItem)) {
            return merchant.buyItem(getBuyingItemAsList(buyItem), player);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Pair<Integer, Integer>> getAllNpcs() {
        final Map<String, Pair<Integer, Integer>> res = new HashMap<>();
        for (final NpcSimple npc : player.getMap().getAllNpcsInCurrentMap()) {
            if (npc.isVisible()) {
                res.put(npc.getName(), npc.getPosition());
            }
        }
        return res;
    }

    // --MONSTERS--
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasBattleStarted() {
        return this.battle.isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BattleController> getBattleController() {
        if (this.battle.isPresent()) {
            final BattleController battleController = new BattleControllerImpl(this.battle.get());
            this.battle = Optional.empty();
            return Optional.of(battleController);
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMonsterNameById(final int monsterId) {
        return getMonster(monsterId).get().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getMonstersId() {
        final List<Integer> playerMonster = new ArrayList<>();
        for (final Monster monster : player.getAllMonsters()) {
            playerMonster.add(monster.getId());
        }
        return playerMonster;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTeamFull() {
        return player.isTeamFull();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMonsterExp(final int monsterId) {
        return getMonster(monsterId).get().getExp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMonsterLevel(final int monsterId) {
        return getMonster(monsterId).get().getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMonsterMaxHealth(final int monsterId) {
        return getMonster(monsterId).get().getMaxHealth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMonsterType(final int monsterId) {
        return getMonster(monsterId).get().getSpecies().getType().toString();
    }

    private Optional<Monster> getMonster(final int monsterId) {
        for (final Monster monster : player.getAllMonsters()) {
            if (monster.getId() == monsterId) {
                return Optional.of(monster);
            }
        }
        for (final Monster monster : player.getStorage().getCurrentBoxMonsters()) {
            if (monster.getId() == monsterId) {
                return Optional.of(monster);
            }
        }
        return Optional.empty();
    }

    // -- BOX --
    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurrentBoxName() {
        return this.player.getStorage().getCurrentBoxName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exchangeMonster(final int teamMonsterId, final int boxMonsterId) {
        final Optional<Monster> tMonster = getMonster(teamMonsterId);
        if (tMonster.isPresent()) {

            this.player.getStorage().exchange(tMonster.get(), boxMonsterId);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBoxNumbers() {
        return this.player.getStorage().getMaxNumberOfBox();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void depositMonster(final int teamMonsterId) {
        final Optional<Monster> tMonster = getMonster(teamMonsterId);
        if (tMonster.isPresent()) {
            this.player.getStorage().depositMonster(tMonster.get());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMonstersForEachBox() {
        return this.player.getStorage().getMaxNumberOfBox();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextBox() {
        this.player.getStorage().nextBox();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void previousBox() {
        this.player.getStorage().previousBox();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getBoxMonsters() {
        return this.player.getStorage().getCurrentBoxMonsters().stream().map(m -> m.getId())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void withdrawMonster(final int boxMonsterId) {
        this.player.getStorage().withdrawMonster(boxMonsterId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMonsterHealth(final int monsterId) {
        return getMonster(monsterId).get().getStats().getHealth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMonsterAttack(final int monsterId) {
        return getMonster(monsterId).get().getStats().getAttack();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMonsterDefense(final int monsterId) {
        return getMonster(monsterId).get().getStats().getDefense();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMonsterSpeed(final int monsterId) {
        return getMonster(monsterId).get().getStats().getSpeed();
    }

    // -- MOVES --
    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getMovesNames(final int monsterId) {
        final Optional<Monster> m = getMonster(monsterId);
        final List<String> moves = new ArrayList<>();
        if (m.isPresent()) {
            for (final Moves mov : m.get().getAllMoves()) {
                moves.add(mov.getName());
            }
        }
        return moves;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMovePP(final String moveName, final int monsterID) {
        final Optional<Monster> monster = getMonster(monsterID);
        if (monster.isPresent()) {
            for (final Moves move : monster.get().getAllMoves()) {
                if (move.getName().equals(moveName)) {
                    return monster.get().getCurrentPPByMove(move);
                }
            }
        }

        return -1;
    }

    // --ITEMS--

    private Optional<GameItem> getItem(final String name, final List<GameItem> list) {
        return list.stream().filter(e -> e.getNameItem().equals(name)).findFirst();
    }

    private List<GameItem> getItemFromMerchant(final Map<GameItem, Integer> map) {
        final List<GameItem> itemList = new ArrayList<>();
        for (final var itemMap : map.entrySet()) {
            itemList.add(itemMap.getKey());
        }

        return itemList;

    }

    private List<GameItem> getItemsList(final Map<GameItem, Integer> map) {
        final List<GameItem> itemList = new ArrayList<>();
        for (final var itemMap : map.entrySet()) {
            itemList.add(itemMap.getKey());
        }
        return itemList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useItem(final String i) {
        final List<GameItem> itemList = getItemsList(this.player.getAllItems());
        final Optional<GameItem> gameItem = getItem(i, itemList);
        if (gameItem.isPresent()) {
            player.useItem(gameItem.get());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useItemOnMonster(final String i, final int monsterId) {
        final List<GameItem> itemList = getItemsList(this.player.getAllItems());
        final Optional<GameItem> gameItem = getItem(i, itemList);
        if (gameItem.isPresent()) {
            player.useItemOnMonster(gameItem.get(), getMonster(monsterId).get());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeItem(final String i) {
        final List<GameItem> itemList = getItemsList(this.player.getAllItems());
        final Optional<GameItem> gameItem = getItem(i, itemList);
        if (gameItem.isPresent()) {
            player.removeItem(gameItem.get());
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getPlayerItemsName() {
        return player.getAllItems().entrySet().stream().map(map -> map.getKey().getNameItem())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemQuantity(final String item) {

        final List<GameItem> itemList = getItemsList(this.player.getAllItems());
        final Optional<GameItem> gameItem = getItem(item, itemList);
        if (gameItem.isPresent()) {
            return player.getItemQuantity(gameItem.get());
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getItemDescription(final String item) {
        List<GameItem> itemList = getItemsList(this.player.getAllItems());
        Optional<GameItem> gameItem = getItem(item, itemList);
        if (gameItem.isPresent()) {
            return gameItem.get().getDescription();
        }
        final Optional<NpcSimple> npc = this.player.getLastInteractionWithNpc();
        if (npc.isPresent() && npc.get().getTypeOfNpc() == TypeOfNpc.MERCHANT) {
            final NpcMerchant npcMerchant = (NpcMerchant) npc.get();
            itemList = getItemFromMerchant(npcMerchant.getInventory());
            gameItem = getItem(item, itemList);
            if (gameItem.isPresent()) {
                return gameItem.get().getDescription();
            }

        }
        return null;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getItemtype(final String item) {
        final List<GameItem> itemList = getItemsList(this.player.getAllItems());
        final Optional<GameItem> gameItem = getItem(item, itemList);
        if (gameItem.isPresent()) {
            return gameItem.get().getType().toString();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Pair<String, String>> evolveByItem(final String nameItem, final int monsterId) {
        final Monster monster = player.getAllMonsters().stream().filter(i -> i.getId() == monsterId).findAny().get();

        final List<GameItem> itemList = getItemsList(this.player.getAllItems());
        final Optional<GameItem> gameItem = getItem(nameItem, itemList);
        if (gameItem.isPresent()) {
            final String monsterName = monster.getName();
            this.player.evolveMonster(monster, gameItem.get());
            return monster.getName().equals(monsterName) ? Optional.empty()
                    : Optional.of(new Pair<>(monsterName, monster.getName()));
        }
        return Optional.empty();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(final String item) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canUseItem(final String item) {
        final List<GameItem> itemList = getItemsList(this.player.getAllItems());
        final Optional<GameItem> gameItem = getItem(item, itemList);
        if (gameItem.isPresent()) {
            return gameItem.get().getType().isConsumableInBag();
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canEvolveByItem(final String nameItem, final int monsterId) {
        final List<GameItem> itemList = getItemsList(this.player.getAllItems());
        final Optional<GameItem> gameItem = getItem(nameItem, itemList);
        if (gameItem.isPresent()) {
            final Optional<Monster> monster = this.player.getAllMonsters().stream().filter(m -> m.getId() == monsterId)
                    .findAny();
            return monster.isPresent() && monster.get().canEvolveByItem(gameItem.get());

        }
        return false;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isItemPresent(final String name) {
        for (final var item : this.player.getAllItems().entrySet()) {
            if (item.getKey().getNameItem().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaximumBlocksInRow() {
        return dataController.getMaximumBlockInRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaximumBlocksInColumn() {
        return dataController.getMaximumBlockInColumn();
    }

}
