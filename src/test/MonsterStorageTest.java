package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import model.Pair;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesBuilderImpl;
import model.monster.MonsterType;
import model.player.Gender;
import model.player.MonsterBox;
import model.player.MonsterBoxImpl;
import model.player.MonsterStorage;
import model.player.MonsterStorageImpl;
import model.player.Player;
import model.player.PlayerImpl;

public class MonsterStorageTest {

	private MonsterStorage monsterStorage;
	private MonsterBox monsterBox;
	private Pair<Integer, Integer> position;
	private Monster monster1;
	private Monster monster2;
	private Monster monster3;
	private Monster monster4;
	private Monster monster5;
	private Monster monster6;
	private Monster monster7;
	private Player player;

	private void addMonsterList(MonsterStorage storage, int n, Monster monster) {
		for (int i = 0; i < n; i++) {
			storage.addMonster(monster);
		}
	}

	@org.junit.Before
	public void initFactory() {
		this.position = new Pair<>(1, 1);
		this.player = new PlayerImpl("player", Gender.WOMAN, 0, position, null);
		// monster
		Moves m1 = new MovesImpl("mossa1", 3, MonsterType.GRASS, 9);
		Moves m2 = new MovesImpl("mossa2", 3, MonsterType.FIRE, 3);
		List<Pair<Moves, Integer>> listOfMoves = List.of(new Pair<>(m1, 5), new Pair<>(m2, 6));
		List<Moves> listMoves = List.of(m1, m2);
		MonsterSpecies species = new MonsterSpeciesBuilderImpl().name("nome1").info("Info1")
				.monsterType(MonsterType.FIRE).health(7).attack(3).defense(3).speed(1).movesList(listMoves).build();
		this.monster1 = new MonsterBuilderImpl().health(10).attack(5).defense(9).speed(32).exp(8).level(3).isWild(false)
				.species(species).movesList(listOfMoves).build();
		MonsterSpecies species2 = new MonsterSpeciesBuilderImpl().name("nome2").info("Info2")
				.monsterType(MonsterType.FIRE).health(7).attack(3).defense(3).speed(1).movesList(listMoves).build();
		this.monster2 = new MonsterBuilderImpl().health(10).attack(5).defense(9).speed(32).exp(8).level(3).isWild(false)
				.species(species2).movesList(listOfMoves).build();
		this.monster3 = new MonsterBuilderImpl().health(10).attack(5).defense(9).speed(32).exp(8).level(3).isWild(false)
				.species(species2).movesList(listOfMoves).build();

		this.player.addMonster(this.monster2);
		this.monsterStorage = new MonsterStorageImpl(this.player);
		this.monsterBox = new MonsterBoxImpl("Box1", this.monsterStorage.getMaxSizeOfBox());

	}

	@org.junit.Test
	public void MonsterBox() {
		this.monsterBox.addMonster(this.monster1);
		assertEquals(this.monster1, this.monsterBox.getAllMonsters().get(0));
		assertEquals(Optional.of(monster1), this.monsterBox.getMonster(this.monster1.getId()));

		this.monsterBox.exchange(monster2, monster1.getId());
		assertEquals(1, this.monsterBox.getAllMonsters().size());
		assertEquals(Optional.of(this.monster2), this.monsterBox.getMonster(this.monster2.getId()));

		this.monsterBox.removeMonster(this.monster2.getId());
		assertFalse(this.monsterBox.getAllMonsters().contains(this.monster2));
		assertEquals(Optional.empty(), this.monsterBox.getMonster(this.monster2.getId()));
		assertEquals(0, this.monsterBox.getAllMonsters().size());

	}

	@org.junit.Test
	public void MonsterStorage() {
		// add monster
		assertEquals("BOX1", this.monsterStorage.getCurrentBoxName());
		assertTrue(this.monsterStorage.addMonster(this.monster1));
		assertTrue(this.monsterStorage.getCurrentBoxMonsters().contains(this.monster1));
		assertEquals("BOX1", this.monsterStorage.getCurrentBoxName());
		// previous box
		this.monsterStorage.previousBox();
		assertEquals("BOX10", this.monsterStorage.getCurrentBoxName());
		// next box
		this.monsterStorage.nextBox();
		assertEquals("BOX1", this.monsterStorage.getCurrentBoxName());

		addMonsterList(this.monsterStorage, this.monsterStorage.getMaxSizeOfBox(), this.monster1);
		this.monsterStorage.nextBox();

		assertTrue(this.monsterStorage.getCurrentBoxMonsters().contains(this.monster1));
		assertEquals(1, this.monsterStorage.getCurrentBoxMonsters().size());
		// exchange player monster2 with box monster1
		assertTrue(this.monsterStorage.exchange(this.monster2, this.monster1.getId()));
		assertTrue(this.monsterStorage.getCurrentBoxMonsters().contains(this.monster2));
		assertTrue(this.player.getAllMonsters().contains(this.monster1));

		player.addMonster(this.monster3); // player team: monster3, monster1
		assertTrue(this.monsterStorage.depositMonster(this.monster3));
		assertFalse(this.player.getAllMonsters().contains(this.monster3));
		assertTrue(this.monsterStorage.getCurrentBoxMonsters().contains(this.monster3));

		// player team: monster1
		// withDrawMonster

		assertTrue(this.monsterStorage.withdrawMonster(monster3.getId()));
		assertFalse(this.monsterStorage.withdrawMonster(monster3.getId()));

		// player team: monster1, monster3
		this.player.addMonster(this.monster4);
		this.player.addMonster(this.monster5);
		this.player.addMonster(this.monster6);
		this.player.addMonster(this.monster7);
		// player team full

		// withDrawMonster
		assertFalse(this.monsterStorage.withdrawMonster(this.monster2.getId()));
		
		

	}

	@org.junit.Test
	public void MonsterStorageWithList() {
		MonsterBox box = new MonsterBoxImpl("NEWBOX1", 4);
		List<MonsterBox> boxList = List.of(box);
		MonsterStorage monsterStorageWithList = new MonsterStorageImpl(player, boxList);
		assertEquals("NEWBOX1", monsterStorageWithList.getCurrentBoxName());
	}

}
