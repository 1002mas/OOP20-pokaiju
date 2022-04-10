package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Pair;
import model.battle.Moves;
import model.battle.Moves;
import model.gameitem.GameItems;
import model.monster.EvolutionType;
import model.monster.MonsterStats;
import model.monster.MonsterType;

public class MonsterSpeciesSupport {

	private final Optional<String> evolution;	
	private final String name;
	private final String info;
	private final MonsterType type;
	private final EvolutionType evolutionType;
	private final MonsterStats stats; 
	private final List<Pair<String, Integer>> allMoves;	

	Optional<Integer> evolutionItem; 
	Optional<Integer> evolutionLevel;	
				
	
	public MonsterSpeciesSupport(Optional<String> evolution, String name,String info,
									MonsterType type,EvolutionType evolutionType,MonsterStats stats,
									List<Pair<String, Integer>> allMoves,Optional<Integer> evolutionItem,
									Optional<Integer> evolutionLevel) {
		
		this.evolution = evolution;
		this.name = name;
		this.info = info;
		this.type = type;
		this.evolutionType = evolutionType;
		this.stats = stats;
		this.allMoves = allMoves;
		this.evolutionItem = evolutionItem;			
		this.evolutionLevel = evolutionLevel;
		
		
	}
	
	
	public EvolutionType getEvolutionType() {
		return this.evolutionType;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getInfo() {
		return this.info;
	}
	
	public MonsterType getMonsterType() {
		return this.type;
	}
	public MonsterStats getMonsterStats() {
		return this.stats;
	}
		
	public List<Pair<Moves, Integer>> getAllMoves(List<Moves> movesd) {
		List<Pair<Moves, Integer>> moves = new ArrayList<>();
		
		//TODO translate
		
		return moves;
	}
	public Optional<String> getEvolution() {
		return this.evolution;
	}
	public Optional<GameItems> getEvolutionGameItem(){
		//TODO translate
		return Optional.empty();
	}
	public Optional<Integer> getEvolutionLevel(){
		return this.evolutionLevel;
	}
	
	
	
}
