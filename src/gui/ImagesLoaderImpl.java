package gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.imageio.ImageIO;

import controller.Direction;
import model.Pair;

public class ImagesLoaderImpl implements ImagesLoader {
    private static final int PLAYER_SEQUENCE_LENGTH = 3;
    private static final String BASE_PATH = "textures/";
    private static final String MONSTER_PATH = "monster/";
    private static final String TEXTURE_DATA_PATH = "data/map_textures.dat";

    private final int height;
    private final int width;
    private final Pair<Integer, Integer> cellSize;

    private Map<Direction, List<BufferedImage>> player = new HashMap<>();
    private Map<String, BufferedImage> monsters = new HashMap<>();
    private Map<Integer, BufferedImage> mapTextures = new HashMap<>();
    private Map<Integer, List<BufferedImage>> maps = new HashMap<>();
    private Map<String, BufferedImage> npc = new HashMap<>();

    public ImagesLoaderImpl(int height, int width, int maximumCellsInRow, int maximumCellsInHeight) {
	super();
	this.height = height;
	this.width = width;
	this.cellSize = new Pair<>(width / maximumCellsInRow, height / maximumCellsInHeight);
	loadMapTextures();
    }

    public int getHeight() {
	return height;
    }

    public int getWidth() {
	return width;
    }

    private void loadMapTextures() {
	InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream(TEXTURE_DATA_PATH);
	try (BufferedReader in = new BufferedReader(new InputStreamReader(fileStream));) {
	    boolean isNumber = true;
	    int texturesID = 0;
	    String line;
	    while ((line = in.readLine()) != null) {
		if (isNumber) {
		    texturesID = Integer.parseInt(line);
		} else {
		    InputStream textureStream = this.getClass().getClassLoader().getResourceAsStream(line);
		    BufferedImage img = resizeImage(ImageIO.read(textureStream), cellSize.getFirst(),
			    cellSize.getSecond());

		    mapTextures.put(texturesID, img);
		}
		isNumber = !isNumber;
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e1) {
	    e1.printStackTrace();
	}
    }

    @Override
    public BufferedImage getNpcImages(String name) {
	if (!npc.containsKey(name)) {
	    final double dimMultiplier = 1.3;
	    final String path = BASE_PATH + "npcs/" + name + ".png";
	    try {
		InputStream imgStream = this.getClass().getClassLoader().getResourceAsStream(path);
		BufferedImage img = resizeImage(ImageIO.read(imgStream),
			(int) (dimMultiplier * this.cellSize.getFirst()),
			(int) (dimMultiplier * this.cellSize.getSecond()));
		this.npc.put(name, img);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return npc.get(name);
    }

    @Override
    public List<BufferedImage> getPlayerImages(Direction dir, String gender) {
	if (!player.containsKey(dir)) {
	    final double dimMultiplier = 1.3;
	    final String basePath = BASE_PATH + "player/" + gender + "/player_"
		    + dir.toString() + "_";
	    final String fileType = ".png";
	    List<BufferedImage> imageSequence = new ArrayList<>();
	    try {
		for (int i = 1; i <= PLAYER_SEQUENCE_LENGTH; i++) {
		    String imgPath = basePath + i + fileType;
		    InputStream imgStream = this.getClass().getClassLoader().getResourceAsStream(imgPath);
		    imageSequence
			    .add(resizeImage(ImageIO.read(imgStream), (int) (dimMultiplier * this.cellSize.getFirst()),
				    (int) (dimMultiplier * this.cellSize.getSecond())));
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    player.put(dir, imageSequence);
	}
	return player.get(dir);
    }

    @Override
    public List<BufferedImage> getMapByID(int mapID) {
	if (!maps.containsKey(mapID)) {
	    List<BufferedImage> mapSequence = new ArrayList<>();
	    String mapPath = "data/maps/appearance/map"
		    + mapID + ".dat";
	    InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream(mapPath);
	    try (BufferedReader in = new BufferedReader(new InputStreamReader(fileStream));) {
		String line = null;
		while ((line = in.readLine()) != null) {
		    int texturesID = Integer.parseInt(line);
		    mapSequence.add(mapTextures.get(texturesID));
		}
		this.maps.put(mapID, mapSequence);
	    } catch (FileNotFoundException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return this.maps.get(mapID);
    }

    @Override
    public BufferedImage getMonster(String monsterName) {
	if (!monsters.containsKey(monsterName)) {
	    final String basePath = MONSTER_PATH + monsterName;
	    final String fileType = ".png";
	    BufferedImage monsterPng = null;

	    try {

		String imgPath = basePath + fileType;
		InputStream imgStream = this.getClass().getClassLoader().getResourceAsStream(imgPath);
		monsterPng = ImageIO.read(imgStream);
		double imageRatio = monsterPng.getHeight() / monsterPng.getWidth();
		int newWidth = (int) (this.width * 0.25);
		int newHeight = (int) (imageRatio * newWidth);
		monsterPng = resizeImage(monsterPng, newWidth, newHeight);

	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    monsters.put(monsterName, monsterPng);
	}
	return monsters.get(monsterName);

    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
	BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.BITMASK);

	Graphics2D graphics2D = resizedImage.createGraphics();
	graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
	graphics2D.dispose();
	return resizedImage;
    }

}
