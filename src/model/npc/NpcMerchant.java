package model.npc;

import java.util.Map;
import model.gameitem.GameItem;

public interface NpcMerchant extends NpcSimple{
	Map<GameItem,Integer> getInventory();
}
