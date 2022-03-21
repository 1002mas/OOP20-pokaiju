package model.npc;

import java.util.HashMap;

public interface NpcMerchant extends NpcSimple{
	HashMap<Object,Integer> getInventory();
}
