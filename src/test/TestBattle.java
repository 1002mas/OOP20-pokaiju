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

    private Monster playerMonster2;
    private Monster wildMonster;
    private NpcTrainer enemyTrainer;
    private Player player;
    private static final int ATTACK = 50;
    private static final int HEALTH = 40;
    private static final int SPEED = 30;
    private static final int DEFENSE = 20;
    private static final int FIRST_LEVEL = 1;
    private static final int EXP_BASE = 0;
    private static final int PP = 10;
    @org.junit.Before
    public void init() {
        List<Pair<Moves, Integer>> firstListOfMoves;
        List<Pair<Moves, Integer>> secondListOfMoves;
        List<Moves> firstMonsterSpeciesMoves;
        List<Moves> secondMonsterSpeciesMoves;

        final Moves m1 = new MovesImpl("Braciere", ATTACK, MonsterType.FIRE, PP);
        final Moves m2 = new MovesImpl("Attacco", ATTACK, MonsterType.FIRE, PP);
        final Moves m3 = new MovesImpl("Volo", ATTACK, MonsterType.FIRE, PP);
        final Moves m4 = new MovesImpl("Fossa", ATTACK, MonsterType.FIRE, PP);

        firstListOfMoves = List.of(new Pair<>(m1, 10), new Pair<>(m2, 10));
        secondListOfMoves = List.of(new Pair<>(m3, 10), new Pair<>(m4, 10));
        firstMonsterSpeciesMoves = List.of(m1, m2);
        secondMonsterSpeciesMoves = List.of(m3, m4);

        MonsterSpecies species = new MonsterSpeciesBuilderImpl().name("bibol").info("Info1")
                .monsterType(MonsterType.FIRE).health(HEALTH).attack(PP).defense(DEFENSE).speed(PP)
                .movesList(firstMonsterSpeciesMoves).build();

        final Monster playerMonster1 = new MonsterBuilderImpl().health(HEALTH).attack(ATTACK * 2).defense(DEFENSE).speed(SPEED)
                .exp(EXP_BASE).level(FIRST_LEVEL).wild(false).species(species).movesList(firstListOfMoves).build();

        species = new MonsterSpeciesBuilderImpl().name("greyfish").info("Info").monsterType(MonsterType.WATER)
                .health(ATTACK).attack(10).defense(10).speed(10).movesList(firstMonsterSpeciesMoves).build();

        this.playerMonster2 = new MonsterBuilderImpl().health(HEALTH).attack(ATTACK).defense(DEFENSE).speed(SPEED).exp(EXP_BASE)
                .level(FIRST_LEVEL).wild(false).species(species).movesList(firstListOfMoves).build();

        species = new MonsterSpeciesBuilderImpl().name("kracez").info("Info").monsterType(MonsterType.FIRE)
                .health(ATTACK).attack(10).defense(10).speed(10).movesList(secondMonsterSpeciesMoves).build();

        this.wildMonster = new MonsterBuilderImpl().health(10).attack(ATTACK).defense(0).speed(SPEED).exp(EXP_BASE)
                .level(FIRST_LEVEL).wild(true).species(species).movesList(secondListOfMoves).build();

        species = new MonsterSpeciesBuilderImpl().name("pirin").info("Info").monsterType(MonsterType.GRASS)
                .health(ATTACK).attack(10).defense(10).speed(10).movesList(secondMonsterSpeciesMoves).build();

        final Monster enemyTrainerMonster = new MonsterBuilderImpl().health(HEALTH).attack(ATTACK).defense(DEFENSE)
                .speed(SPEED).exp(EXP_BASE).level(FIRST_LEVEL).wild(true).species(species).movesList(secondListOfMoves).build();

        this.player = new PlayerImpl("Paolo", Gender.MAN, 0, new Pair<>(0, 0), null);
        this.player.addMonster(playerMonster1);
        this.player.addMonster(playerMonster2);
        this.enemyTrainer = new NpcTrainerImpl("Luca", List.of("test"), null, true, true, List.of(enemyTrainerMonster),
                false);
    }

    @org.junit.Test
    public void simpleBattle() {

        final MonsterBattleImpl battle = new MonsterBattleImpl(player, wildMonster);
        battle.movesSelection(0);
        assertTrue(wildMonster.isAlive());
    }

    @org.junit.Test
    public void capture() {
        boolean isCaptured = false;
        final MonsterBattleImpl battle = new MonsterBattleImpl(player, wildMonster);
        while (!isCaptured) {
            isCaptured = battle.capture();
        }
        assertEquals(wildMonster, player.getAllMonsters().get(2));
    }

    @org.junit.Test
    public void escape() {
        boolean escaped = false;
        final MonsterBattleImpl battle = new MonsterBattleImpl(player, wildMonster);
        while (!escaped) {
            escaped = battle.escape();
        }
        assertTrue(escaped);
    }

    @org.junit.Test
    public void changeMonsters() {
        final MonsterBattleImpl battle = new MonsterBattleImpl(player, wildMonster);
        battle.playerChangeMonster(playerMonster2.getId());
        assertEquals(battle.getCurrentPlayerMonster(), playerMonster2);
    }

    @org.junit.Test
    public void battleWithEnemyTrainer() {
        final MonsterBattleImpl battle = new MonsterBattleImpl(player, enemyTrainer);
        battle.movesSelection(0);
        assertFalse(enemyTrainer.isDefeated());
    }

}
