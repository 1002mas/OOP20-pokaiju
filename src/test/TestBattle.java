package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import model.Pair;
import model.battle.MonsterBattleImpl;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesBuilderImpl;
import model.monster.MonsterType;
import model.npc.NpcTrainer;
import model.npc.NpcTrainerImpl;
import model.player.Gender;
import model.player.Player;
import model.player.PlayerImpl;

public class TestBattle {

    private Monster playerMonster1;
    private Monster playerMonster2;
    private Monster wildMonster;
    private Monster enemyTrainerMonster;
    private NpcTrainer enemyTrainer;
    private Player player;

    @org.junit.Before
    public void init() {
	List<Pair<Moves, Integer>> firstListOfMoves;
	List<Pair<Moves, Integer>> secondListOfMoves;
	List<Moves> firstMonsterSpeciesMoves;
	List<Moves> secondMonsterSpeciesMoves;

	Moves m1 = new MovesImpl("Braciere", 50, MonsterType.FIRE, 10);
	Moves m2 = new MovesImpl("Attacco", 10, MonsterType.FIRE, 10);
	Moves m3 = new MovesImpl("Volo", 20, MonsterType.FIRE, 10);
	Moves m4 = new MovesImpl("Fossa", 10, MonsterType.FIRE, 10);

	firstListOfMoves = List.of(new Pair<>(m1, 10), new Pair<>(m2, 10));
	secondListOfMoves = List.of(new Pair<>(m3, 10), new Pair<>(m4, 10));
	firstMonsterSpeciesMoves = List.of(m1, m2);
	secondMonsterSpeciesMoves = List.of(m3, m4);

	MonsterSpecies species = new MonsterSpeciesBuilderImpl().name("bibol").info("Info")
		.monsterType(MonsterType.FIRE).health(50).attack(10).defense(10).speed(10)
		.movesList(firstMonsterSpeciesMoves).build();

	this.playerMonster1 = new MonsterBuilderImpl().health(50).attack(50).defense(50).speed(50).exp(0).level(1)
		.isWild(false).species(species).movesList(firstListOfMoves).build();

	species = new MonsterSpeciesBuilderImpl().name("greyfish").info("Info")
		.monsterType(MonsterType.WATER).health(50).attack(10).defense(10).speed(10)
		.movesList(firstMonsterSpeciesMoves).build();

	this.playerMonster2 = new MonsterBuilderImpl().health(50).attack(50).defense(50).speed(50).exp(0).level(1)
		.isWild(false).species(species).movesList(firstListOfMoves).build();

	species = new MonsterSpeciesBuilderImpl().name("kracez").info("Info").monsterType(MonsterType.FIRE).health(50)
		.attack(10).defense(10).speed(10).movesList(secondMonsterSpeciesMoves).build();

	this.wildMonster = new MonsterBuilderImpl().health(50).attack(50).defense(50).speed(50).exp(0).level(1)
		.isWild(true).species(species).movesList(secondListOfMoves).build();
	
	species = new MonsterSpeciesBuilderImpl().name("pirin").info("Info").monsterType(MonsterType.GRASS).health(50)
		.attack(10).defense(10).speed(10).movesList(secondMonsterSpeciesMoves).build();

	this.enemyTrainerMonster = new MonsterBuilderImpl().health(50).attack(50).defense(50).speed(50).exp(0).level(1)
		.isWild(true).species(species).movesList(secondListOfMoves).build();

	this.player = new PlayerImpl("Paolo", Gender.MAN, 0, new Pair<>(0, 0), null);
	this.player.addMonster(playerMonster1);
	this.player.addMonster(playerMonster2);
	this.enemyTrainer = new NpcTrainerImpl("Luca", List.of("test"), null, true, true,  List.of(enemyTrainerMonster), false);
    }

    @org.junit.Test
    public void simpleBattle() {

	MonsterBattleImpl battle = new MonsterBattleImpl(player, wildMonster);
	battle.movesSelection(0);
	assertFalse(wildMonster.isAlive());
    }

    @org.junit.Test
    public void capture() {
	boolean isCaptured = false;
	MonsterBattleImpl battle = new MonsterBattleImpl(player, wildMonster);
	while (!isCaptured) {
	    isCaptured = battle.capture();
	}
	assertEquals(wildMonster, player.getAllMonsters().get(2));
    }

    @org.junit.Test
    public void getOut() {
	boolean escaped = false;
	MonsterBattleImpl battle = new MonsterBattleImpl(player, wildMonster);
	while (!escaped) {
	    escaped = battle.escape();
	}
	assertTrue(escaped);
    }
    @org.junit.Test
    public void changeMonsters() {
	MonsterBattleImpl battle = new MonsterBattleImpl(player, wildMonster);
	battle.playerChangeMonster(playerMonster2.getId());
	assertEquals(battle.getCurrentPlayerMonster(),playerMonster2);
    }
    @org.junit.Test
    public void battleWithEnemyTrainer() {
	MonsterBattleImpl battle = new MonsterBattleImpl(player, enemyTrainer);
	battle.movesSelection(0);
	assertTrue(enemyTrainer.isDefeated());
    }

}
