package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import gui.Direction;

public class ImagesLoader {
    static final private int PLAYER_SEQUENCE_LENGTH = 3;
    static final String BASE_PATH = "res" + File.separator + "textures" + File.separator;
    private BufferedImage terrain;
    private Map<Direction, List<BufferedImage>> player = new HashMap<>();

    public BufferedImage getTerrainImage() {
	if (terrain == null) {
	    final String imgPath = BASE_PATH + "green.png";
	    try {
		terrain = ImageIO.read(new File(imgPath));
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return terrain;
    }

    public Color getTerrainColor() {
	return new Color(34, 177, 76);
    }

    public List<BufferedImage> getPlayerImages(Direction dir) {
	if (!player.containsKey(dir)) {
	    final String basePath = BASE_PATH + "player" + File.separator + "player_" + dir.toString() + "_";
	    final String fileType = ".png";
	    List<BufferedImage> imageSequence = new ArrayList<>();
	    try {
		for (int i = 1; i <= PLAYER_SEQUENCE_LENGTH; i++) {
		    String imgPath = basePath + i + fileType;
		    imageSequence.add(ImageIO.read(new File(imgPath)));
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    player.put(dir, imageSequence);
	}
	return player.get(dir);
    }
}
