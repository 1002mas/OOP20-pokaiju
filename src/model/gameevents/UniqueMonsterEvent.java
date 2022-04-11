package model.gameevents;

import java.util.Optional;

import model.battle.MonsterBattle;
import model.battle.MonsterBattleImpl;
import model.monster.Monster;
import model.player.Player;

public class UniqueMonsterEvent extends AbstractGameEvent {
    private final Monster monster;
    private final Player player;
    private Optional<MonsterBattle> monsterBattle = Optional.empty();

    public UniqueMonsterEvent(int id, boolean isActive, boolean isToActiveImmediatly, Monster monster, Player player) {
	super(id, isActive, true, isToActiveImmediatly);
	this.monster = monster;
	this.player = player;
    }

    @Override
    public void activateEvent() {
	this.monsterBattle = Optional.of(new MonsterBattleImpl(player, monster));
    }

    /**
     * 
     * @return The battle against a monster if the event has been activated.
     *         Optional.empty otherwise
     */
    public Optional<MonsterBattle> getMonsterBattle() {
	return this.monsterBattle;
    }

}