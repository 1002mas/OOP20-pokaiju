package model.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.Pair;
import model.battle.MonsterBattle;
import model.battle.MonsterBattleImpl;
import model.gameevents.GameEvent;
import model.gameitem.GameItem;
import model.map.GameMap;
import model.monster.Monster;
import model.npc.NpcSimple;
import model.npc.NpcTrainer;
import model.npc.TypeOfNpc;

public class PlayerImpl implements Player {
    private static final int START_MONEY = 1000;
    private static final int STEP = 1;
    private String name;
    private Gender gender;
    private int trainerNumber;
    private Pair<Integer, Integer> position;
    private List<Monster> team;
    private final Map<GameItem, Integer> gameItems;
    private int money;
    private final GameMap map;
    private boolean hasMapChanged;
    private Optional<NpcSimple> npc;
    private Optional<MonsterBattle> monsterBattle;
    private MonsterStorage storage = new MonsterStorageImpl(this);
    private boolean triggeredEvent;

    /**
     * 
     * @param name
     * @param gender
     * @param trainerNumber
     * @param startingPosition
     * @param map
     */
    public PlayerImpl(final String name, final Gender gender, final int trainerNumber,
            final Pair<Integer, Integer> startingPosition, final GameMap map) {
        this.name = name;
        this.gender = gender;
        this.trainerNumber = trainerNumber;
        this.position = startingPosition;
        this.team = new ArrayList<>();
        this.gameItems = new HashMap<>();
        this.money = START_MONEY;
        this.map = map;
        this.npc = Optional.empty();
        this.monsterBattle = Optional.empty();

    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public Pair<Integer, Integer> getPosition() {
        return this.position;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public List<Monster> getAllMonsters() {
        return new ArrayList<>(this.team);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.name + ", " + this.trainerNumber + ", " + this.gender + ", " + getAllMonsters().toString() + ", "
                + this.gameItems.toString() + ", " + this.position.getFirst() + ", " + this.position.getSecond();

    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public Map<GameItem, Integer> getAllItems() {
        return new HashMap<GameItem, Integer>(this.gameItems);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void addItem(final GameItem i) {
        if (!this.gameItems.containsKey(i)) {
            this.gameItems.put(i, 0);
        }
        this.gameItems.put(i, this.gameItems.get(i) + 1);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void removeItem(final GameItem i) {
        if (this.gameItems.containsKey(i)) {
            this.gameItems.put(i, this.gameItems.get(i) - 1);
            if (this.gameItems.get(i) < 1) {
                this.gameItems.remove(i);
            }
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void useItem(final GameItem i) {
        if (getAllItems().containsKey(i) && i.use(null)) {
            removeItem(i);
        }
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public int getTrainerNumber() {
        return trainerNumber;
    }

    public void setTrainerNumber(final int trainerNumber) {
        this.trainerNumber = trainerNumber;
    }

    public List<Monster> getMonster() {
        return team;
    }

    public void setMonster(final List<Monster> monster) {
        this.team = monster;
    }

    public List<GameItem> getItems() {
        return new ArrayList<>(this.gameItems.keySet());
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPosition(final Pair<Integer, Integer> position) {
        this.position = position;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean addMonster(final Monster m) {
        if (isTeamFull()) {
            this.storage.addMonster(m);
            return false;
        } else {
            return this.team.add(m);
        }

    }

    public int getMoney() {
        return money;
    }

    public void setMoney(final int money) {
        this.money = money < 0 ? 0 : money;
    }

    public String getName() {
        return name;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean isTeamFull() {
        return this.getAllMonsters().stream().count() >= 6 ? true : false;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean removeMonster(final Monster m) {
        if (this.getAllMonsters().contains(m)) {
            return this.team.remove(m);
        }
        return false;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void useItemOnMonster(final GameItem i, final Monster m) {
        if (getAllItems().containsKey(i) && i.use(m)) {
            removeItem(i);
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void addItem(final GameItem i, final int quantity) {
        if (quantity > 0) {
            this.gameItems.put(i, quantity);
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public int getItemQuantity(final GameItem i) {
        return this.gameItems.get(i);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public Optional<NpcSimple> getLastInteractionWithNpc() {
        return npc;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public Optional<MonsterBattle> getPlayerBattle() {
        return monsterBattle;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void evolveMonsters() {
        for (final Monster m : this.team) {
            if (m.canEvolveByLevel()) {
                m.evolve();
            }
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void evolveMonster(final Monster monster, final GameItem i) {
        if (monster.canEvolveByItem(i)) {
            useItemOnMonster(i, monster);
            monster.evolve();
        }
    }

    /**
     * move operation.
     * 
     * @param x coordinate x
     * @param y coordinate y
     * @return true if Player has moved
     */
    private boolean move(final int x, final int y) {
        this.hasMapChanged = false;
        this.monsterBattle = Optional.empty();
        final Pair<Integer, Integer> nextPosition = new Pair<>(position.getFirst() + x, position.getSecond() + y);
        final boolean canMove = map.canPassThrough(nextPosition);

        if (canMove) {
            this.position = nextPosition;
            final Optional<GameEvent> gameEvent = map.getEventAt(position);
            if (gameEvent.isPresent() && gameEvent.get().isBattle()) {
                this.monsterBattle = Optional.of(new MonsterBattleImpl(this, gameEvent.get().getMonster().get(0)));
                gameEvent.get().activate();
            }
            if (map.canChangeMap(nextPosition)) {
                this.hasMapChanged = true;
                map.changeMap(nextPosition);
                this.position = map.getPlayerMapPosition().get();
            }
        }
        final Optional<Monster> monster = map.getWildMonster(this.position);
        if (monster.isPresent()) {
            this.monsterBattle = Optional.of(new MonsterBattleImpl(this, monster.get()));
        }
        this.triggeredEvent = this.map.getEventAt(position).isPresent();
        return canMove;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean hasPlayerChangedMap() {
        return this.hasMapChanged;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean interactAt(final Pair<Integer, Integer> pos) {
        this.monsterBattle = Optional.empty();
        this.npc = map.getNpcAt(pos);
        if (npc.isPresent() && npc.get().getTypeOfNpc() == TypeOfNpc.TRAINER) {
            final NpcTrainer trainer = (NpcTrainer) npc.get();
            if (!trainer.isDefeated()) {
                this.monsterBattle = Optional.of(new MonsterBattleImpl(this, trainer));
            }
        }
        return npc.isPresent();
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean moveUp() {
        return move(0, -STEP);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean moveDown() {
        return move(0, STEP);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean moveLeft() {
        return move(-STEP, 0);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean moveRight() {
        return move(STEP, 0);
    }

    public GameMap getMap() {
        return map;
    }

    public void setStorage(final MonsterStorage storage) {
        this.storage = storage;
    }

    public MonsterStorage getStorage() {
        return storage;
    }

    public boolean isTriggeredEvent() {
        return triggeredEvent;
    }

}
