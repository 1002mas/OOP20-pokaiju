package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import controller.BattleController;
import controller.BattleControllerImpl;
import controller.ImagesLoader;
import gui.BattleFrame;
import model.Pair;
import model.battle.MonsterBattle;
import model.battle.MonsterBattleImpl;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.gameitem.GameItemTypes;
import model.gameitem.*;
import model.monster.*;
import model.npc.NpcTrainer;
import model.npc.NpcTrainerImpl;
import model.npc.TypeOfNpc;
import model.player.Gender;
import model.player.*;

public class TestBattleGUI {
    private static final int SECOND_EVOLUTION_LEVEL = 35;
    private static final int FIRST_EVOLUTION_LEVEL = 14;
    private static BattleController ctrl;
    private static MonsterBattle battle;
    private static PlayerImpl pg;
    private static NpcTrainer enemyTrainer;
    private static Monster pgMonster;
    private static Monster enemyMonster;
    private static List<Pair<Moves, Integer>> listOfMoves;
    private static BattleFrame GUI;
    static MonsterStats  stats = new MonsterStatsImpl(50, 10, 10, 10);
    
    public static void main(String[] args) {
	// TODO Auto-generated method stub
	Moves m1 = new MovesImpl("Braciere", 50, MonsterType.FIRE, 10);
	Moves m2 = new MovesImpl("Attacco", 10, MonsterType.FIRE, 10);
	Moves m3 = new MovesImpl("Volo", 50, MonsterType.FIRE, 10);
	Moves m4 = new MovesImpl("Fossa", 50, MonsterType.FIRE, 10);
	Moves m5 = new MovesImpl("Surf", 50, MonsterType.WATER, 10);
	Moves m6 = new MovesImpl("Idropompa", 50, MonsterType.WATER, 10);
	MonsterStats stats = new MonsterStatsImpl(50, 10, 10, 10);
	
	listOfMoves = List.of(new Pair<>(m1, 10), new Pair<>(m2, 10),new Pair<>(m3, 5),new Pair<>(m4, 5));
	List<Pair<Moves, Integer>> allMoves = List.of(new Pair<>(m5, 10), new Pair<>(m6, 25));
	List<Moves> listOfMoves2 = List.of(new MovesImpl("Lanciafiamme", 50, MonsterType.FIRE, 10),
		new MovesImpl("Pugno", 10, MonsterType.FIRE, 10), new MovesImpl("Volo", 50, MonsterType.FIRE, 10),
		new MovesImpl("Fossa", 50, MonsterType.FIRE, 10));
	
	
	MonsterSpeciesImpl secondEvolution = new MonsterSpeciesImpl("Pippo3", "Info3", MonsterType.FIRE, stats, allMoves);
	MonsterSpeciesByLevel firstEvolution = new MonsterSpeciesByLevel("Pippo2", "Info2", MonsterType.FIRE, stats, secondEvolution,
		SECOND_EVOLUTION_LEVEL, allMoves);
	MonsterSpeciesByLevel species = new MonsterSpeciesByLevel("kracez", "Info", MonsterType.FIRE, stats, firstEvolution,
		FIRST_EVOLUTION_LEVEL, allMoves);
	pgMonster = new MonsterBuilderImpl().health(500).attack(50).defense(10).speed(50).isWild(false).species(species)
		.movesList(listOfMoves).build();
	
	
	secondEvolution = new MonsterSpeciesImpl("Pippo3", "Info3", MonsterType.FIRE, null, null);

	firstEvolution = new MonsterSpeciesByLevel("Pippo2", "Info2", MonsterType.FIRE, null, secondEvolution,
		SECOND_EVOLUTION_LEVEL, null);

	species = new MonsterSpeciesByLevel("greyfish", "Info", MonsterType.FIRE, stats, firstEvolution,
		FIRST_EVOLUTION_LEVEL, allMoves);
	enemyMonster = new MonsterBuilderImpl().health(500).attack(20).defense(10).speed(20).exp(0).level(1)
		.isWild(true).species(species).movesList(listOfMoves).build();
	species = new MonsterSpeciesByLevel("Brun", "Info", MonsterType.FIRE, stats, firstEvolution,
		FIRST_EVOLUTION_LEVEL, allMoves);
	Monster enemySecondMonster = new MonsterBuilderImpl().health(500).attack(20).defense(10).speed(20).exp(0)
		.level(1).isWild(false).species(species).movesList(listOfMoves).build();
	species = new MonsterSpeciesByLevel("bibol", "Info", MonsterType.FIRE, stats, firstEvolution,
		FIRST_EVOLUTION_LEVEL, allMoves);
	Monster playerSecondMonster = new MonsterBuilderImpl().health(500).attack(20).defense(10).speed(20).exp(0)
		.level(1).isWild(true).species(species).movesList(listOfMoves).build();
	pg = new PlayerImpl("Luca", Gender.MAN, 0, null);
	ArrayList<Monster> pgList = new ArrayList<>(List.of(pgMonster, playerSecondMonster, pgMonster, enemyMonster));
	pg.setMonster(pgList);
	pg.addItem(new HealingItem("Cura", 5, ""));
	pg.addItem(new HealingItem("Life Jar", 10, ""));
	pg.addItem(new HealingItem("Sapone", 10, ""));
	pg.addItem(new GameItemImpl("Boooble", 5, ""));
	enemyTrainer = new NpcTrainerImpl("luca",TypeOfNpc.TRAINER,null,null,true,true,new ArrayList<>(List.of(enemyMonster, enemySecondMonster)),false);
	
	battle = new MonsterBattleImpl(pg, enemyMonster);
	ctrl = new BattleControllerImpl(battle);
	GUI = new BattleFrame(ctrl, new ImagesLoader(1,1,1,1));

    }

}
