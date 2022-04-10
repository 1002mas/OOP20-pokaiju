package model.npc;

import java.util.ArrayList;
import java.util.Optional;

import model.Pair;

public class NpcSimpleImpl implements NpcSimple {

    private final String name;
    private TypeOfNpc typeOfNpc;
    private ArrayList<String> sentences;
    private Pair<Integer, Integer> position;

    public NpcSimpleImpl(String name, TypeOfNpc typeOfNpc, ArrayList<String> sentences,
	    Pair<Integer, Integer> position) {
	this.name = name;
	this.typeOfNpc = typeOfNpc;
	this.sentences = sentences;
	this.position = position;
    }

    @Override
    public String getName() {
	return this.name;
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
    public Pair<Integer, Integer> getPosition() {
	return this.position;
    }

    @Override
    public void changeNpcPosition(Pair<Integer, Integer> newPosition) {
	this.position = newPosition;
    }

}
