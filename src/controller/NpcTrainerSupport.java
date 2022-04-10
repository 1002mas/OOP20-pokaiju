package controller;

import java.util.ArrayList;
import java.util.List;

import model.Pair;
import model.monster.Monster;
import model.npc.TypeOfNpc;

public class NpcTrainerSupport {

	private String name;
	private TypeOfNpc typeOfNpc;
	private List<String> sentences;
	private List<Integer> monstersOwned;
	private Pair<Integer, Integer> position;

	public NpcTrainerSupport(String name, TypeOfNpc typeOfNpc, List<String> sentences, List<Integer> monstersOwned,
			Pair<Integer, Integer> position) {
		this.name = name;
		this.typeOfNpc = typeOfNpc;
		this.sentences = sentences;
		this.monstersOwned = monstersOwned;
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public TypeOfNpc getTypeOfNpc() {
		return typeOfNpc;
	}

	public List<String> getSentences() {
		return sentences;
	}

	public Pair<Integer, Integer> getPosition() {
		return position;
	}

	public List<Monster> getTranslatedMonsterList(List<Monster> list) {
		List<Monster> monster = new ArrayList<>();
		for (int id : this.monstersOwned) {
			for (Monster md : list) {
				if (md.getId() == id) {
					monster.add(md);
				}
			}
		}
		return monster;
	}
}
