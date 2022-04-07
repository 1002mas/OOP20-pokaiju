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
    final int height;
    final int width;

    private BufferedImage terrain;
    private Map<Direction, List<BufferedImage>> player = new HashMap<>();

    public ImagesLoader(int height, int width) {
	super();
	this.height = height;
	this.width = width;
    }

    public BufferedImage getTerrainImage() {
	final double percSize = 0.05;
	if (terrain == null) {
	    final String imgPath = BASE_PATH + "green.png";
	    try {
		terrain = resizeImage(ImageIO.read(new File(imgPath)), (int) (percSize), (int) (percSize));
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
	final double percSize = 0.1;
	if (!player.containsKey(dir)) {
	    final String basePath = BASE_PATH + "player" + File.separator + gender + File.separator + "player_"
		    + dir.toString() + "_";
	    final String fileType = ".png";
	    List<BufferedImage> imageSequence = new ArrayList<>();
	    try {
		for (int i = 1; i <= PLAYER_SEQUENCE_LENGTH; i++) {
		    String imgPath = basePath + i + fileType;
		    imageSequence.add(resizeImage(ImageIO.read(new File(imgPath)), (int) (percSize), (int) (percSize)));
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    player.put(dir, imageSequence);
	}
	return player.get(dir);
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
	BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.BITMASK);

	Graphics2D graphics2D = resizedImage.createGraphics();
	graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
	graphics2D.dispose();
	return resizedImage;
    }

}
