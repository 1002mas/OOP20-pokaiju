package controller;

import java.awt.Color;
import java.awt.Graphics2D;
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
    final int scale = 3;

    private BufferedImage terrain;
    private Map<Direction, List<BufferedImage>> player = new HashMap<>();

    public BufferedImage getTerrainImage() {
	if (terrain == null) {
	    final String imgPath = BASE_PATH + "green.png";
	    try {
		terrain = resizeImage(ImageIO.read(new File(imgPath)));
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return terrain;
    }

    public Color getTerrainColor() {
	return new Color(34, 177, 76);
    }

    public List<BufferedImage> getPlayerImages(Direction dir, String gender) {
	if (!player.containsKey(dir)) {
	    final String basePath = BASE_PATH + "player" + File.separator + gender + File.separator + "player_"
		    + dir.toString() + "_";
	    final String fileType = ".png";
	    List<BufferedImage> imageSequence = new ArrayList<>();
	    try {
		for (int i = 1; i <= PLAYER_SEQUENCE_LENGTH; i++) {
		    String imgPath = basePath + i + fileType;
		    imageSequence.add(resizeImage(ImageIO.read(new File(imgPath))));
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    player.put(dir, imageSequence);
	}
	return player.get(dir);
    }

    private BufferedImage resizeImage(BufferedImage originalImage) {
	BufferedImage resizedImage = new BufferedImage(scale * originalImage.getWidth(),
		scale * originalImage.getHeight(), BufferedImage.BITMASK);

	Graphics2D graphics2D = resizedImage.createGraphics();
	graphics2D.drawImage(originalImage, 0, 0, scale * originalImage.getWidth(), scale * originalImage.getHeight(),
		null);
	graphics2D.dispose();
	return resizedImage;
    }

}
