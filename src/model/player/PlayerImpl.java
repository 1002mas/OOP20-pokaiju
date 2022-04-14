package model.player;

import java.util.ArrayList;
import java.util.Collections;
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
import model.monster.MonsterSpecies;
import model.npc.NpcSimple;
import model.npc.NpcTrainer;
import model.npc.TypeOfNpc;

public class PlayerImpl implements Player {
    private static final int STARTMONEY = 1000;
    private static final int STEP = 1;
    private String name;
    private Gender gender;
    private int trainerNumber;
    private Pair<Integer, Integer> position;
    private List<Monster> team;
    private Map<GameItem, Integer> gameItems;
    private int money;
    private List<Pair<MonsterSpecies, MonsterSpecies>> evolutionList;
    private GameMap map;
    private boolean hasMapChanged;
    private Optional<NpcSimple> npc;
    private Optional<MonsterBattle> monsterBattle;

    public PlayerImpl(String name, Gender gender, int trainerNumber, Pair<Integer, Integer> startingPosition,
	    GameMap map) {
	this.name = name;
	this.gender = gender;
	this.trainerNumber = trainerNumber;
	this.position = startingPosition;
	this.team = new ArrayList<Monster>();
	this.gameItems = new HashMap<GameItem, Integer>();
	this.money = STARTMONEY;
	this.map = map;
	this.npc = Optional.empty();
	this.monsterBattle = Optional.empty();

    }

    @Override
    public Pair<Integer, Integer> getPosition() {
	return this.position;
    }

    @Override
    public List<Monster> getAllMonsters() {
	List<Monster> list = new ArrayList<>(Collections.unmodifiableList(this.team));
	return list;
    }

    public String toString() {
	return this.name + ", " + this.trainerNumber + ", " + this.gender + ", " + getAllMonsters().toString() + ", "
		+ this.gameItems.toString() + ", " + this.position.getFirst() + ", " + this.position.getSecond();

    }

    @Override
    public Map<GameItem, Integer> getAllItems() {
	return new HashMap<GameItem, Integer>(this.gameItems);
    }

    @Override
    public void addItem(GameItem i) {
	if (!this.gameItems.containsKey(i)) {
	    this.gameItems.put(i, 0);
	}
	this.gameItems.put(i, this.gameItems.get(i) + 1);
    }

    @Override
    public void removeItem(GameItem i) {
	if (this.gameItems.containsKey(i)) {
	    this.gameItems.put(i, this.gameItems.get(i) - 1);
	    if (this.gameItems.get(i) < 1) {
		this.gameItems.remove(i);
	    }
	}
    }

    @Override
    public void useItem(GameItem i) {
	if (getAllItems().containsKey(i) && i.use(null)) {
	    removeItem(i);
	}
    }

    @Override
    public boolean buyItem(GameItem i, int price) {
	if (getMoney() - price >= 0) {
	    addItem(i);
	    setMoney(getMoney() - price);
	    return true;
	} else {
	    return false;
	}
    }

    public Gender getGender() {
	return gender;
    }

    public void setGender(Gender gender) {
	this.gender = gender;
    }

    public int getTrainerNumber() {
	return trainerNumber;
    }

    public void setTrainerNumber(int trainerNumber) {
	this.trainerNumber = trainerNumber;
    }

    public List<Monster> getMonster() {
	return team;
    }

    public void setMonster(ArrayList<Monster> monster) {
	this.team = monster;
    }

    public List<GameItem> getItems() {
	return new ArrayList<>(this.gameItems.keySet());
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setPosition(Pair<Integer, Integer> position) {
	this.position = position;
    }

    @Override
    public boolean addMonster(Monster m) {
	if (isTeamFull()) {
	    return false;
	} else {
	    return this.team.add(m);
	}

    }

    public int getMoney() {
	return money;
    }

    public void setMoney(int money) {
	this.money = money;
    }

    public String getName() {
	return name;
    }

    @Override
    public boolean isTeamFull() {
	return this.getAllMonsters().stream().count() >= 6 ? true : false;
    }

    @Override
    public boolean removeMonster(Monster m) {
	if (this.getAllMonsters().contains(m)) {
	    return this.team.remove(m);
	}
	return false;
    }

    @Override
    public void useItemOnMonster(GameItem i, Monster m) {
	if (getAllItems().containsKey(i) && i.use(m)) {
	    removeItem(i);
	}
    }

    @Override
    public void addItem(GameItem i, int quantity) {
	if (quantity > 0) {
	    this.gameItems.put(i, quantity);
	}
    }

    @Override
    public int getItemQuantity(GameItem i) {
	return this.gameItems.get(i);
    }

    @Override
    public Optional<NpcSimple> getLastInteractionWithNpc() {
	return npc;
    }

    @Override
    public Optional<MonsterBattle> getPlayerBattle() {
	return monsterBattle;
    }

    @Override
    public void evolveMonsters() {
	for (Monster m : this.team) {
	    if (m.canEvolveByLevel()) {
		addMonsterToEvolutionList(m);
		m.evolve();
	    }
	}
    }

    @Override
    public void evolveMonster(Monster monster, GameItem i) {
	if (monster.canEvolveByItem(i)) {
	    addMonsterToEvolutionList(monster);
	    monster.evolve();
	}
    }

    private void addMonsterToEvolutionList(Monster monster) {
	MonsterSpecies base = monster.getSpecies();
	evolutionList.add(new Pair<MonsterSpecies, MonsterSpecies>(base, base.getEvolution().get()));
    }

    public List<Pair<MonsterSpecies, MonsterSpecies>> getEvolutionList() {
	List<Pair<MonsterSpecies, MonsterSpecies>> temp = this.evolutionList;
	this.evolutionList = new ArrayList<>();
	return temp;
    }

    private boolean move(int x, int y) {
	this.hasMapChanged = false;
	this.monsterBattle = Optional.empty();
	Pair<Integer, Integer> nextPosition = new Pair<>(position.getFirst() + x, position.getSecond() + y);
	boolean canMove = map.canPassThrough(nextPosition);

	if (canMove) {
	    this.position = nextPosition;
	    Optional<GameEvent> gameEvent = map.getEventAt(position);
	    if (gameEvent.isPresent() && gameEvent.get().isBattle()) {
		this.monsterBattle = Optional.of(new MonsterBattleImpl(this, gameEvent.get().getMonster().get(0)));
	    }
	    if (map.canChangeMap(nextPosition)) {
		this.hasMapChanged = true;
		map.changeMap(nextPosition);
		this.position = map.getPlayerMapPosition().get();
	    }
	}
	Optional<Monster> monster = map.getWildMonster(this.position);
	if (monster.isPresent()) {
	    this.monsterBattle = Optional.of(new MonsterBattleImpl(this, monster.get()));
	}
	return canMove;
    }

    @Override
    public boolean hasPlayerChangedMap() {
	return this.hasMapChanged;
    }

    @Override
    public boolean interactAt(Pair<Integer, Integer> pos) {
	this.monsterBattle = Optional.empty();
	this.npc = map.getNpcAt(pos);
	if (npc.isPresent() && npc.get().getTypeOfNpc() == TypeOfNpc.TRAINER) {
	    NpcTrainer trainer = (NpcTrainer) npc.get();
	    if (!trainer.isDefeated()) {
		this.monsterBattle = Optional.of(new MonsterBattleImpl(this, trainer));
	    }
	}
	return npc.isPresent();
    }

    @Override
    public boolean moveUp() {
	return move(0, -STEP);
    }

    @Override
    public boolean moveDown() {
	return move(0, STEP);
    }

    @Override
    public boolean moveLeft() {
	return move(-STEP, 0);
    }

    @Override
    public boolean moveRight() {
	return move(STEP, 0);
    }

    public GameMap getMap() {
	return map;
    }

}
