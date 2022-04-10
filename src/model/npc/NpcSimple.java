package model.npc;

import java.util.Optional;

import model.Pair;

public interface NpcSimple {

    /**
     * 
     * @return npc Name
     */
    String getName();
    /**
     * 
     * @return npc statement if it is a talking one
     */
    Optional<String> interactWith();

    /**
     * 
     * @return npc type (for more info look at @{TypeOfNpc})
     */
    TypeOfNpc getTypeOfNpc();

    /**
     * 
     * @return NpcPosition
     */
    Pair<Integer, Integer> getPosition();

    /**
     * change the npc position in the map
     * @param newPosition new position in the map
     */
    void changeNpcPosition(Pair<Integer, Integer> newPosition);
}
