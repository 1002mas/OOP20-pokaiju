package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import controller.BattleController;
import controller.BattleControllerImpl;
import gui.BattleFrame;
import model.battle.MonsterBattle;
import model.battle.MonsterBattleImpl;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.gameitem.GameItemTypes;
import model.gameitem.HealingItem;
import model.gameitem.SimpleItem;
import model.monster.EvolutionType;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesByLevel;
import model.monster.MonsterSpeciesSimple;
import model.monster.MonsterStatsImpl;
import model.monster.MonsterType;
import model.npc.NpcTrainer;
import model.npc.NpcTrainerImpl;
import model.npc.TypeOfNpc;
import model.player.Gender;
import model.player.Player;
import model.player.PlayerImpl;

public class TestBattleGUI {
    private static final int SECOND_EVOLUTION_LEVEL = 35;
    private static final int FIRST_EVOLUTION_LEVEL = 14;
    private static BattleController ctrl;
    private static MonsterBattle battle;
    private static PlayerImpl pg;
    private static NpcTrainer enemyTrainer;
    private  static Monster pgMonster;
    private  static Monster enemyMonster;
    private  static List<Moves> listOfMoves;
    private static BattleFrame GUI;
    public static void main(String[] args) {
	// TODO Auto-generated method stub
	listOfMoves = List.of(new MovesImpl("Braciere", 50, MonsterType.FIRE, 10),
		new MovesImpl("Attacco", 10, MonsterType.FIRE, 10), new MovesImpl("Volo", 50, MonsterType.FIRE, 10),
		new MovesImpl("Fossa", 50, MonsterType.FIRE, 10));
	MonsterSpeciesSimple secondEvolution = new MonsterSpeciesSimple("Pippo3", "Info3", MonsterType.FIRE);
	MonsterSpecies firstEvolution;
	firstEvolution = new MonsterSpeciesByLevel("Pippo2", "Info2", MonsterType.FIRE, secondEvolution, SECOND_EVOLUTION_LEVEL);
	
	MonsterSpeciesByLevel species = new MonsterSpeciesByLevel("Pippo", "Info", MonsterType.FIRE, firstEvolution,
		FIRST_EVOLUTION_LEVEL);
	pgMonster = new MonsterBuilderImpl().stats(new MonsterStatsImpl(500, 20, 20, 20)).exp(0).level(1).isWild(false).species(species).movesList(listOfMoves).build();
	listOfMoves = List.of(new MovesImpl("Braciere", 50, MonsterType.FIRE, 10),
		new MovesImpl("Attacco", 10, MonsterType.FIRE, 10), new MovesImpl("Volo", 50, MonsterType.FIRE, 10),
		new MovesImpl("Fossa", 50, MonsterType.FIRE, 10));
	secondEvolution = new MonsterSpeciesSimple("Pippo3", "Info3", MonsterType.FIRE);
	
	firstEvolution = new MonsterSpeciesByLevel("Pippo2", "Info2", MonsterType.FIRE, secondEvolution, SECOND_EVOLUTION_LEVEL);
	
	 species = new MonsterSpeciesByLevel("Paperino", "Info", MonsterType.FIRE, firstEvolution,
		FIRST_EVOLUTION_LEVEL);
	enemyMonster = new MonsterBuilderImpl().stats(new MonsterStatsImpl(500, 20, 20, 20)).exp(0).level(1).isWild(true).species(species).movesList(listOfMoves).build();
	species = new MonsterSpeciesByLevel("Pluto", "Info", MonsterType.FIRE, firstEvolution,
		FIRST_EVOLUTION_LEVEL);
	Monster enemySecondMonster = new MonsterBuilderImpl().stats(new MonsterStatsImpl(500, 20, 20, 20)).exp(0).level(1).isWild(true).species(species).movesList(listOfMoves).build();
	pg = new PlayerImpl("Luca", Gender.OTHER, 0, null);
	ArrayList<Monster> pgList = new ArrayList<>(List.of(pgMonster,enemyMonster,pgMonster,enemyMonster));
	pg.setMonster(pgList);
	pg.addItem(new HealingItem("Cura", 5, "", GameItemTypes.HEAL));
	pg.addItem(new HealingItem("Life Jar", 10, "", GameItemTypes.HEAL));
	pg.addItem(new HealingItem("Sapone", 10, "", GameItemTypes.HEAL));
	pg.addItem(new SimpleItem("Boooble", 5, "", GameItemTypes.MONSTERBALL));
	enemyTrainer = new NpcTrainerImpl("Luca", TypeOfNpc.TRAINER, null, new ArrayList<>(List.of(enemyMonster,enemySecondMonster)), null);
	battle = new MonsterBattleImpl(pg,enemyTrainer);
	ctrl = new BattleControllerImpl(battle);
	GUI = new BattleFrame(ctrl);
	    
    }

}
