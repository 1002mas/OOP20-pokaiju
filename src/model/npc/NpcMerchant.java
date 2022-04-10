package model.npc;

import java.util.Map;
import model.gameitem.GameItem;

public interface NpcMerchant extends NpcSimple {
    /**
     * 
     * @return list of sold game items
     */
    Map<GameItem, Integer> getInventory();

}
