package model.npc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Pair;

public class NpcSimpleImpl implements NpcSimple {

    private final String name;
    private TypeOfNpc typeOfNpc;
    private List<String> sentences;
    private int currentSentence = 0;
    private Pair<Integer, Integer> position;
    private boolean isVisible;
    private boolean isEnabled;

    public NpcSimpleImpl(String name, TypeOfNpc typeOfNpc, List<String> sentences, Pair<Integer, Integer> position,
	    boolean isVisible, boolean isEnabled) {
	this.name = name;
	this.typeOfNpc = typeOfNpc;
	this.sentences = sentences;
	this.position = position;
	this.isVisible = isVisible;
	this.isEnabled = isEnabled;
    }

    @Override
    public String getName() {
	return this.name;
    }

    public Optional<String> interactWith() {
	Optional<String> result = Optional.of(sentences.get(currentSentence));
	return result;
    }

    @Override
    public void setDialogueText(int textID) {
	if (textID >= 0 && textID < this.sentences.size()) {
	    this.currentSentence = textID;
	}
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

    @Override
    public boolean isVisible() {
	return this.isVisible;
    }

    @Override
    public void setVisible(boolean visible) {
	this.isVisible = visible;
    }

    @Override
    public boolean isEnabled() {
	return this.isEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
	this.isEnabled = enabled;
    }

}
