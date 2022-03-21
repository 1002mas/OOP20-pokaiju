package model.npc;

import java.util.ArrayList;
import java.util.Optional;

public class NpcSimpleImpl implements NpcSimple {

	String name;
	TypeOfNpc typeOfNpc;
	ArrayList<String> sentences;
	
	public NpcSimpleImpl(String name,TypeOfNpc typeOfNpc,ArrayList<String> sentences) {
	
		this.name = name;
		this.typeOfNpc = typeOfNpc;
		this.sentences = sentences;
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
	
}
