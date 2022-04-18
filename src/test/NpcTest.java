package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.Pair;
import model.battle.MonsterBattle;
import model.battle.MonsterBattleImpl;
import model.battle.Moves;
import model.battle.MovesImpl;
import model.gameevents.GameEvent;
import model.gameevents.MonsterGift;
import model.gameitem.GameItem;
import model.gameitem.GameItemImpl;
import model.monster.Monster;
import model.monster.MonsterBuilderImpl;
import model.monster.MonsterSpecies;
import model.monster.MonsterSpeciesBuilderImpl;
import model.monster.MonsterType;
import model.npc.NpcHealerImpl;
import model.npc.NpcMerchant;
import model.npc.NpcMerchantImpl;
import model.npc.NpcSimple;
import model.npc.NpcSimpleImpl;
import model.npc.NpcTrainer;
import model.npc.NpcTrainerImpl;
import model.player.Gender;
import model.player.Player;
import model.player.PlayerImpl;

public class NpcTest {

	private NpcSimple npc1;
	private NpcSimple npc2;
	private NpcMerchant npc3;
	private NpcTrainer npc4;
	private NpcTrainer npc5;
	private Pair<Integer, Integer> position;
	private List<String> sentences;
	private Monster monster1;
	private Monster monster2;
	private Monster monster3;
	private Player player;
	private List<Monster> monsterList;
	private List<Monster> monsterList2;
	private GameItem item1;
	private GameItem item2;
	private List<Pair<GameItem, Integer>> list;
	private GameEvent npcEvent;

	@org.junit.Before
	public void initFactory() {

		sentences = new ArrayList<>();
		sentences.add("Frase 1");
		sentences.add("Frase 2");
		sentences.add("Frase 3");
		position = new Pair<>(1, 1);
		player = new PlayerImpl("player", Gender.WOMAN, 0, position, null);
		// event

		// item
		item1 = new GameItemImpl("pietra", "descrizione");
		item2 = new GameItemImpl("carta", "descrizione");
		Map<GameItem, Integer> inventory = new HashMap<>();
		inventory.put(item1, 4);
		inventory.put(item2, 3);
		// monster
		Moves m1 = new MovesImpl("mossa1", 3, MonsterType.GRASS, 9);
		Moves m2 = new MovesImpl("mossa2", 3, MonsterType.FIRE, 3);
		List<Pair<Moves, Integer>> listOfMoves = List.of(new Pair<>(m1, 5), new Pair<>(m2, 6));
		List<Moves> listMoves = List.of(m1, m2);
		MonsterSpecies species = new MonsterSpeciesBuilderImpl().name("nome1").info("Info1")
				.monsterType(MonsterType.FIRE).health(20).attack(30).defense(20).speed(1).movesList(listMoves).build();
		monster1 = new MonsterBuilderImpl().health(15).attack(50).defense(9).speed(32).exp(8).level(3).isWild(false)
				.species(species).movesList(listOfMoves).build();

		monster2 = new MonsterBuilderImpl().health(10).attack(5).defense(1).speed(32).exp(8).level(3).isWild(false)
				.species(species).movesList(listOfMoves).build();

		monster3 = new MonsterBuilderImpl().health(25).attack(70).defense(25).speed(32).exp(8).level(3).isWild(false)
				.species(species).movesList(listOfMoves).build();

		monsterList = List.of(monster2);
		monsterList2 = List.of(monster3);
		this.player.addMonster(monster1);
		// npc
		this.npc1 = new NpcSimpleImpl("nome1", sentences, position, false, false);
		this.npc2 = new NpcHealerImpl("nome2", sentences, position, player, false, false);
		this.npc3 = new NpcMerchantImpl("nome3", sentences, position, false, false, inventory);
		list = List.of(new Pair<GameItem, Integer>(item1, 2));
		this.npc4 = new NpcTrainerImpl("nome4", sentences, position, false, false, monsterList, false);
		this.npc5 = new NpcTrainerImpl("nome5", sentences, position, false, false, monsterList2, false);
		this.npcEvent = new MonsterGift(8, true, true, false, List.of(this.monster1), player);
		npc1.addGameEvent(this.npcEvent);

	}

	@org.junit.Test
	public void CommonFields() {
		assertEquals("nome1", this.npc1.getName());
		assertEquals(0, this.npc1.getCurrentSetence());
		assertEquals(this.position, this.npc1.getPosition());
		// enabled : false
		assertEquals(Optional.empty(), this.npc1.interactWith());
		// enabled : true
		this.npc1.setEnabled(true);
		assertEquals(Optional.of(this.sentences.get(0)), this.npc1.interactWith());
		// enabled : true and event not active
		npcEvent.setActivity(false);
		npc1.interactWith();
		assertEquals(Optional.empty(), this.npc1.getTriggeredEvent());
		// enabled : true and event active
		npcEvent.setActivity(true);
		npc1.interactWith();
		assertEquals(Optional.of(npcEvent), this.npc1.getTriggeredEvent());
	}

	@org.junit.Test
	public void testNpcHealer() {
		npc2.interactWith();
		assertEquals(monster1.getMaxHealth(), this.player.getAllMonsters().get(0).getStats().getHealth());
	}

	@org.junit.Test
	public void testNpcTrainer() {

		assertEquals(monsterList, npc4.getMonstersOwned());
		MonsterBattle battle = new MonsterBattleImpl(player, npc4);
		battle.movesSelection(0);
		assertTrue(npc4.isDefeated());

		MonsterBattle battle2 = new MonsterBattleImpl(player, npc5);
		battle2.movesSelection(0);
		assertFalse(npc5.isDefeated());
		battle2.enemyAttack();
		assertTrue(battle.isOver());
		assertFalse(npc5.isDefeated());

	}

	@org.junit.Test
	public void testNpcMerhant() {
		int money = this.player.getMoney();
		assertEquals(8, npc3.getTotalPrice(list));
		assertTrue(npc3.buyItem(list, player));
		assertEquals(money - 8, this.player.getMoney());
	}
}
