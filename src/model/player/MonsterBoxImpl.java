package model.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Pair;
import model.monster.Monster;

public class MonsterBoxImpl implements MonsterBox {

	private static final int COLUMNS = 5;
	private static final int ROWS = 5;

	private Map<Pair<Integer, Integer>, Monster> monsters;
	private List<Pair<Integer, Integer>> freeCoord;
	private String name;

	public MonsterBoxImpl(String name) {
		this.name = name;
		this.monsters = new HashMap<>();
		freeCoord = new ArrayList<>();
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				freeCoord.add(new Pair<>(i, j));
			}
		}
	}

		
	public MonsterBoxImpl(String name, List<Monster> monsters) {
		this(name);
		for(Monster monster : monsters) {
			addMonster(monster);
		}
	}
	
	private void sortList() {
		Collections.sort(freeCoord, new Comparator<Pair<Integer, Integer>>() {
			@Override
			public int compare(final Pair<Integer, Integer> o1, final Pair<Integer, Integer> o2) {

				if (o1.getFirst() < o2.getFirst()) {
					return -1;
				}
				if (o1.getFirst() > o2.getFirst()) {
					return 1;
				}
				if (o1.getFirst() == o2.getFirst()) {
					if (o1.getSecond() < o2.getSecond()) {
						return -1;
					}
					if (o1.getSecond() > o2.getSecond()) {
						return 1;
					}
				}
				return 0;
			}
		});

	}

	@Override
	public List<Monster> getAllMonsters() {
		List<Monster> monsters = new ArrayList<>();
		for (Entry<Pair<Integer, Integer>, Monster> monst : this.monsters.entrySet()) {
			monsters.add(monst.getValue());
			}
		return monsters; 
	}

	@Override
	public void addMonster(Monster monster) {
		this.monsters.put(freeCoord.get(0), monster);
		this.freeCoord.remove(0);
	}

	@Override
	public boolean takeMonster(Monster monster) {
		Pair<Integer, Integer> coord = null;
		Monster m = null;

		for (Entry<Pair<Integer, Integer>, Monster> monst : this.monsters.entrySet()) {
			if (monst.getValue().equals(monster)) {
				coord = monst.getKey();
				m = monst.getValue();
			}
			break;
		}

		if (this.monsters.remove(coord, m)) {
			this.freeCoord.add(coord);
			sortList();
			return true;
		}

		return false;
	}

	@Override
	public boolean isFull() { 
		return (monsters.size() != (COLUMNS * ROWS));
	}

	@Override
	public void exchange(Monster toBox, Monster fromBox) {
		if (takeMonster(fromBox)) {
			addMonster(toBox);
		}

	}
	
	public String getName() {
		return this.name;
	}

}
