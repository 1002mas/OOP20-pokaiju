package test;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import controller.BattleController;
import controller.BattleControllerImpl;
import gui.ImagesLoader;
import gui.panels.BattlePanel;
import model.Pair;
import model.battle.MonsterBattle;
import model.battle.MonsterBattleImpl;
import model.battle.Moves;
import model.battle.MovesImpl;

import model.gameitem.*;
import model.monster.*;
import model.npc.NpcTrainerImpl;

import model.player.Gender;
import model.player.*;

public class TestBattleGUI {
    private static final int SECOND_EVOLUTION_LEVEL = 35;
    private static final int FIRST_EVOLUTION_LEVEL = 14;
    private static BattleController ctrl;
    private static MonsterBattle battle;
    private static PlayerImpl pg;
    private static Monster pgMonster;
    private static Monster enemyMonster;
    private static List<Pair<Moves, Integer>> listOfMoves;
    private static BattlePanel GUI;
    static MonsterStats  stats = new MonsterStatsImpl(50, 10, 10, 10);
    
    
    public static void main(String[] args) {
	// TODO Auto-generated method stub
	JFrame frame = new JFrame();
	frame.setBounds(0, 0, 1024, 1024);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Moves m1 = new MovesImpl("Braciere", 50, MonsterType.FIRE, 10);
	Moves m2 = new MovesImpl("Attacco", 10, MonsterType.NONE, 10);
	Moves m3 = new MovesImpl("Volo", 50, MonsterType.GRASS, 10);
	Moves m4 = new MovesImpl("Fossa", 50, MonsterType.FIRE, 10);
	Moves m5 = new MovesImpl("Surf", 50, MonsterType.WATER, 10);
	Moves m6 = new MovesImpl("Idropompa", 50, MonsterType.WATER, 10);
	MonsterStats stats = new MonsterStatsImpl(50, 10, 10, 10);
	
	listOfMoves = List.of(new Pair<>(m1, 10), new Pair<>(m2, 10),new Pair<>(m3, 10),new Pair<>(m4, 10));
	List<Pair<Moves, Integer>> allMoves = List.of(new Pair<>(m5, 10), new Pair<>(m6, 25));
	List<Moves> listOfMoves2 = List.of(new MovesImpl("Lanciafiamme", 50, MonsterType.FIRE, 10),
		new MovesImpl("Pugno", 10, MonsterType.FIRE, 10), new MovesImpl("Volo", 50, MonsterType.FIRE, 10),
		new MovesImpl("Fossa", 50, MonsterType.FIRE, 10),new MovesImpl("Calcio", 50, MonsterType.FIRE, 10));
	
	
	MonsterSpeciesSimple secondEvolution = new MonsterSpeciesSimple("Pippo3", "Info3", MonsterType.FIRE, stats, allMoves);
	MonsterSpeciesByLevel firstEvolution = new MonsterSpeciesByLevel("Pippo2", "Info2", MonsterType.FIRE, stats, secondEvolution,
		SECOND_EVOLUTION_LEVEL, allMoves);
	MonsterSpeciesByLevel species = new MonsterSpeciesByLevel("kracez", "Info", MonsterType.FIRE, stats, firstEvolution,
		FIRST_EVOLUTION_LEVEL, allMoves);
	pgMonster = new MonsterBuilderImpl().health(500).attack(50).defense(10).speed(50).isWild(false).species(species)
		.movesList(listOfMoves).build();
	
	
	secondEvolution = new MonsterSpeciesSimple("Pippo3", "Info3", MonsterType.FIRE, null, null);

	firstEvolution = new MonsterSpeciesByLevel("Pippo2", "Info2", MonsterType.FIRE, null, secondEvolution,
		SECOND_EVOLUTION_LEVEL, null);

	species = new MonsterSpeciesByLevel("greyfish", "Info", MonsterType.WATER, stats, firstEvolution,
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
	ArrayList<Monster> pgList = new ArrayList<>(List.of(pgMonster, playerSecondMonster,enemyMonster));
	pg.setMonster(pgList);
	pg.addItem(new HealingItem("Cura", 5, ""));
	pg.addItem(new HealingItem("Life Jar", 10, ""));
	pg.addItem(new HealingItem("Sapone", 10, ""));
	pg.addItem(new HealingItem("Benda", 10, ""));
	pg.addItem(new GameItemImpl("Ball", 5, ""));
	pg.addItem(new GameItemImpl("Boooble", 5, ""));
	NpcTrainerImpl enemyTrainer = new NpcTrainerImpl("Luca", null, null, true, true, pgList, false);
	
	battle = new MonsterBattleImpl(pg, enemyMonster);
	ctrl = new BattleControllerImpl(battle);
	GUI = new BattlePanel (new ImagesLoader(1024,1024,1,1), null);
	frame.add(GUI);
	GUI.setBattleController(ctrl);
	GUI.start();
	frame.setVisible(true);

    }

}
