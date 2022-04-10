package model.npc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Pair;

public class NpcSimpleImpl implements NpcSimple {

	String name;
	TypeOfNpc typeOfNpc;
	List<String> sentences;
	Pair<Integer,Integer> position;
	
	public NpcSimpleImpl(String name,TypeOfNpc typeOfNpc,List<String> sentences,Pair<Integer,Integer> position) {
	
		this.name = name;
		this.typeOfNpc = typeOfNpc;
		this.sentences = sentences;
		this.position = position;
	}
	
	
	public Optional<String> interactWith() {
		Optional<String> result = Optional.of(sentences.get(0));
		return result;
	}

	@Override
	public TypeOfNpc getTypeOfNpc() {
		return this.typeOfNpc;
	}

	
	@Override
	public String getName() {
		return this.name;
	}


	@Override
	public Pair<Integer, Integer> getPosition() {
		return this.position;
	}
	
}
