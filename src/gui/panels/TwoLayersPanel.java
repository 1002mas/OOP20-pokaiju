package gui.panels;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Direction;
import controller.PlayerController;
import gui.ImagesLoader;

public class TwoLayersPanel extends JPanel {

    private static final long serialVersionUID = 403421258645641464L;
    private final PlayerPanel topPanel;
    private final JPanel bottomPanel;
    private final int rows;
    private final int columns;
    private final List<JLabel> mapBlocks = new ArrayList<>();

    public TwoLayersPanel(PlayerController playerController, ImagesLoader imgLoad, int windowHeight, int windowWidth) {
	super();
	this.rows = playerController.getMaximumBlocksInRow();
	this.columns = playerController.getMaximumBlocksInColumn();
	topPanel = new PlayerPanel(playerController.getPlayerPosition(), imgLoad, playerController.getPlayerGender(),
		rows, columns);
	topPanel.setPlayerImage(
		new ImageIcon(imgLoad.getPlayerImages(Direction.DOWN, playerController.getPlayerGender()).get(0)));
	topPanel.setNpcs(playerController.getAllNpcs());
	this.addMouseListener(new MouseListener() {
	    @Override
	    public void mouseReleased(MouseEvent e) {
		topPanel.requestFocusInWindow();
	    }
	    @Override
	    public void mousePressed(MouseEvent e) {
		
	    }
	    @Override
	    public void mouseExited(MouseEvent e) {
	    }
	    
	    @Override
	    public void mouseEntered(MouseEvent e) {
	    }
	    @Override
	    public void mouseClicked(MouseEvent e) {
		topPanel.requestFocusInWindow();
	    }
	});
	bottomPanel = new JPanel();
	bottomPanel.setOpaque(true);
	bottomPanel.setLayout(new GridLayout(rows, columns));
	int maxBlocks = rows * columns;
	for (int i = 0; i < maxBlocks; i++) {
	    JLabel l = new JLabel();
	    mapBlocks.add(l);
	    bottomPanel.add(l);
	}
	setMapImage(imgLoad.getMapByID(playerController.getCurrentMapID()));

	this.setLayout(null);
	this.setBounds(0, 0, windowWidth, windowHeight);
	topPanel.setBounds(0, 0, windowWidth, windowHeight);
	bottomPanel.setBounds(0, 0, windowWidth, windowHeight);
	this.add(topPanel);
	this.add(bottomPanel);
	this.setFocusable(true);
    }

    public PlayerPanel getTopPanel() {
	return this.topPanel;
    }

    @Override
    public boolean isOptimizedDrawingEnabled() {
	return false;
    }

    public JPanel getBottomPanel() {
	return this.bottomPanel;
    }

    public void setMapImage(List<BufferedImage> imageGrid) {
	int size = rows * columns;
	for (int i = 0; i < size; i++) {
	    mapBlocks.get(i).setIcon(new ImageIcon(imageGrid.get(i)));
	}
    }
}
