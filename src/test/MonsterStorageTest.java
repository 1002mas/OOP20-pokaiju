package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import model.Pair;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesBuilderImpl;
import model.monster.MonsterType;
import model.player.Gender;
import model.player.MonsterStorage;
import model.player.MonsterStorageImpl;
import model.player.Player;
import model.player.PlayerImpl;

public class MonsterStorageTest {

	private MonsterStorage monsterStorage;
	private Pair<Integer, Integer> position;
	private Monster monster1;
	private Monster monster2;
	private Player player;

	@org.junit.Before
	public void initFactory() {
		position = new Pair<>(1, 1);
		player = new PlayerImpl("player", Gender.WOMAN, 0, position, null);

		// monster
		Moves m1 = new MovesImpl("mossa1", 3, MonsterType.GRASS, 9);
		Moves m2 = new MovesImpl("mossa2", 3, MonsterType.FIRE, 3);
		List<Pair<Moves, Integer>> listOfMoves = List.of(new Pair<>(m1, 5), new Pair<>(m2, 6));
		List<Moves> listMoves = List.of(m1, m2);
		MonsterSpecies species = new MonsterSpeciesBuilderImpl().name("nome1").info("Info1")
				.monsterType(MonsterType.FIRE).health(7).attack(3).defense(3).speed(1).movesList(listMoves).build();
		monster1 = new MonsterBuilderImpl().health(10).attack(5).defense(9).speed(32).exp(8).level(3).isWild(false)
				.species(species).movesList(listOfMoves).build();
		MonsterSpecies species2 = new MonsterSpeciesBuilderImpl().name("nome2").info("Info2")
				.monsterType(MonsterType.FIRE).health(7).attack(3).defense(3).speed(1).movesList(listMoves).build();
		monster2 = new MonsterBuilderImpl().health(10).attack(5).defense(9).speed(32).exp(8).level(3).isWild(false)
				.species(species2).movesList(listOfMoves).build();
		this.player.addMonster(monster1);
		this.monsterStorage = new MonsterStorageImpl(player);

	}

	@org.junit.Test
	public void Test() {
		int listSizem;
		int listSizep;
		assertEquals("BOX1", monsterStorage.getCurrentBoxName());
		listSizem = monsterStorage.getCurrentBoxSize();
		monsterStorage.addMonster(monster1);
		assertEquals(listSizem + 1, monsterStorage.getCurrentBoxSize());

		listSizep = this.player.getAllMonsters().size();
		listSizem = this.monsterStorage.getCurrentBoxSize();
		monsterStorage.depositMonster(monster1);
		assertEquals(listSizem + 1, monsterStorage.getCurrentBoxSize());
		assertEquals(listSizep - 1, player.getAllMonsters().size());

		listSizep = this.player.getAllMonsters().size();
		listSizem = this.monsterStorage.getCurrentBoxSize();
		monsterStorage.withdrawMonster(monster1.getId());
		assertEquals(listSizem - 1, monsterStorage.getCurrentBoxSize());
		assertEquals(listSizep + 1, player.getAllMonsters().size());

		monsterStorage.addMonster(monster2);
		listSizep = this.player.getAllMonsters().size();
		listSizem = this.monsterStorage.getCurrentBoxSize();
		assertTrue(monsterStorage.exchange(monster1, monster2.getId()));
		assertEquals(listSizem, monsterStorage.getCurrentBoxSize());
		assertEquals(listSizep, player.getAllMonsters().size());

	}

}
