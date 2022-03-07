package model.npc;

import java.util.ArrayList;
import java.util.Optional;

abstract class NpcSimpleImpl implements NpcSimple {

	String name;
	String typeOfNpc;
	ArrayList<String> prhases;
	
	public NpcSimpleImpl(String name,String typeOfNpc,ArrayList<String> prhases) {
	
		this.name = name;
		this.typeOfNpc = typeOfNpc;
		this.prhases = prhases;
	}
	
	
	public Optional<String> interactWith() {
		Optional<String> result = Optional.of("---");
		return result;
	}

	@Override
	public String getTypeOfNpc() {
		return this.typeOfNpc;
	}

	@Override
	public String getName() {
		return this.name;
	}

	
}
