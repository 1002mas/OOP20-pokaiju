package gui;

import java.awt.image.BufferedImage;
import java.util.List;

import controller.Direction;

public interface ImagesLoader {
    /**
     * 
     * @param name npc name
     * @return npc image
     */
    BufferedImage getNpcImages(String name);

    /**
     * 
     * @param dir    direction that is pointing at the player
     * @param gender player's gender
     * @return player images list, one for each step moment while it's moving
     */
    List<BufferedImage> getPlayerImages(Direction dir, String gender);

    /**
     * 
     * @param mapID map id
     * @return map textures
     */
    List<BufferedImage> getMapByID(int mapID);

    /**
     * 
     * @param monsterName monster name
     * @return monster image
     */
    BufferedImage getMonster(String monsterName);
}
