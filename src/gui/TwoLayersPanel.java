package gui;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.ImagesLoader;
import controller.PlayerController;

public class TwoLayersPanel extends JPanel {

    private static final long serialVersionUID = 403421258645641464L;
    private final PlayerPanel topPanel;
    private final JPanel bottomPanel;
    private final int rows;
    private final int columns;

    public TwoLayersPanel(PlayerController playerController, ImagesLoader imgLoad, int windowHeight, int windowWidth) {
	super();
	this.rows = playerController.getMaximumBlocksInRow();
	this.columns = playerController.getMaximumBlocksInColumn();
	topPanel = new PlayerPanel(playerController.getPlayerPosition(), imgLoad, playerController.getPlayerGender(),
		rows, columns);
	topPanel.setPlayerImage(new ImageIcon(imgLoad.getPlayerImages(Direction.DOWN, "male").get(0)));
	topPanel.setNpcs(playerController.getAllNpcs());

	bottomPanel = new JPanel();
	bottomPanel.setOpaque(true);
	bottomPanel.setLayout(new GridLayout(rows, columns));
	setMapImage(imgLoad.getMapByID(playerController.getCurrentMapID()));

	this.setLayout(null);
	topPanel.setBounds(0, 0, windowWidth, windowHeight);
	bottomPanel.setBounds(0, 0, windowWidth, windowHeight);

	this.add(topPanel);
	this.add(bottomPanel);
	this.setFocusable(true);
    }

    public PlayerPanel getTopPanel() {
	return this.topPanel;
    }

    public JPanel getBottomPanel() {
	return this.bottomPanel;
    }

    public void setMapImage(List<BufferedImage> imageGrid) {
	bottomPanel.removeAll();
	bottomPanel.setLayout(new GridLayout(rows, columns));
	int size = rows * columns;
	for (int i = 0; i < size; i++) {
	    bottomPanel.add(new JLabel(new ImageIcon(imageGrid.get(i))));
	}
    }
}
