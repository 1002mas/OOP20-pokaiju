package gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final Map<String, List<BufferedImage>> player = new HashMap<>();
    private final Map<String, BufferedImage> monsters = new HashMap<>();
    private final Map<Integer, BufferedImage> mapTextures = new HashMap<>();
    private final Map<Integer, List<BufferedImage>> maps = new HashMap<>();
    private final Map<String, BufferedImage> npc = new HashMap<>();

    public ImagesLoaderImpl(final int height, final int width, final int maximumCellsInRow,
            final int maximumCellsInHeight) {
        this.height = height;
        this.width = width;
        this.cellSize = new Pair<>(width / maximumCellsInRow, height / maximumCellsInHeight);
        loadMapTextures();
    }

    /**
     * 
     * @return panel maximum height
     */
    public int getHeight() {
        return height;
    }

    /**
     * 
     * @return panel maximum width
     */
    public int getWidth() {
        return width;
    }

    private void loadMapTextures() {
        final InputStream fileStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(TEXTURE_DATA_PATH);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(fileStream));) {
            boolean isNumber = true;
            int texturesID = 0;
            String line;
            do {
                line = in.readLine();
                if (line != null) {
                    if (isNumber) {
                        texturesID = Integer.parseInt(line);
                    } else {
                        try (InputStream textureStream = Thread.currentThread().getContextClassLoader()
                                .getResourceAsStream(line);) {
                            final BufferedImage img = resizeImage(ImageIO.read(textureStream), cellSize.getFirst(),
                                    cellSize.getSecond());
                            mapTextures.put(texturesID, img);
                        }
                    }
                    isNumber = !isNumber;
                }
            } while (line != null);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage getNpcImages(final String name) {
        if (!npc.containsKey(name)) {
            final double dimMultiplier = 1.3;
            final String path = BASE_PATH + "npcs/" + name + ".png";
            try {
                try (InputStream imgStream = Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream(path);) {
                    final BufferedImage img = resizeImage(ImageIO.read(imgStream),
                            (int) (dimMultiplier * this.cellSize.getFirst()),
                            (int) (dimMultiplier * this.cellSize.getSecond()));
                    this.npc.put(name, img);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return npc.get(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BufferedImage> getPlayerImages(final Direction dir, final String gender) {
        if (!player.containsKey(dir.toString() + " " + gender)) {
            final double dimMultiplier = 1.3;
            final String basePath = BASE_PATH + "player/" + gender + "/player_" + dir.toString() + "_";
            final String fileType = ".png";
            final List<BufferedImage> imageSequence = new ArrayList<>();
            try {
                for (int i = 1; i <= PLAYER_SEQUENCE_LENGTH; i++) {
                    final String imgPath = basePath + i + fileType;
                    try (InputStream imgStream = Thread.currentThread().getContextClassLoader()
                            .getResourceAsStream(imgPath);) {
                        imageSequence.add(
                                resizeImage(ImageIO.read(imgStream), (int) (dimMultiplier * this.cellSize.getFirst()),
                                        (int) (dimMultiplier * this.cellSize.getSecond())));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.put(dir.toString() + " " + gender, imageSequence);
        }
        return player.get(dir.toString() + " " + gender);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BufferedImage> getMapByID(final int mapID) {
        if (!maps.containsKey(mapID)) {
            final List<BufferedImage> mapSequence = new ArrayList<>();
            final String mapPath = "data/maps/appearance/map" + mapID + ".dat";
            final InputStream fileStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(mapPath);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(fileStream));) {
                String line;
                do {
                    line = in.readLine();
                    if (line != null) {
                        mapSequence.add(mapTextures.get(Integer.parseInt(line)));
                    }
                } while (line != null);
                this.maps.put(mapID, mapSequence);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this.maps.get(mapID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage getMonster(final String monsterName) {
        if (!monsters.containsKey(monsterName)) {
            final double widthPerc = 0.25;
            final String basePath = MONSTER_PATH + monsterName;
            final String fileType = ".png";
            BufferedImage monsterPng = null;

            try {

                final String imgPath = basePath + fileType;
                try (InputStream imgStream = Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream(imgPath);) {
                    monsterPng = ImageIO.read(imgStream);
                    final double imageRatio = (double) monsterPng.getHeight() / monsterPng.getWidth();
                    final int newWidth = (int) (this.width * widthPerc);
                    final int newHeight = (int) (imageRatio * newWidth);
                    monsterPng = resizeImage(monsterPng, newWidth, newHeight);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            monsters.put(monsterName, monsterPng);
        }
        return monsters.get(monsterName);

    }

    /**
     * It resizes an image.
     * 
     * @param originalImage the image that you want to be resized.
     * @param targetWidth   the new image width
     * @param targetHeight  the new image height
     * @return the original image with the given width and height
     */
    private BufferedImage resizeImage(final BufferedImage originalImage, final int targetWidth,
            final int targetHeight) {
        final BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.BITMASK);

        final Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

}
