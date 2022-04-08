package gui;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.ImagesLoader;
import controller.PlayerController;
import model.Pair;

public class TwoLayersPanel extends JPanel {

    private static final long serialVersionUID = 403421258645641464L;
    private final PlayerPanel topPanel;
    private final JPanel bottomPanel;

    public TwoLayersPanel(PlayerController playerController, ImagesLoader imgLoad, int windowHeight, int windowWidth) {
	super();
	//TODO use String getGender of Controller
	//TODO use playerController.getPlayerPosition()
	//TODO use playerController.getMaximumBlockNumbers()
	topPanel = new PlayerPanel(new Pair<>(0, 0), imgLoad, "male", 30, 30);
	topPanel.setPlayerImage(new ImageIcon(imgLoad.getPlayerImages(Direction.DOWN, "male").get(0)));

	bottomPanel = new JPanel();
	bottomPanel.setOpaque(true);
	// TODO get Map size
	// TODO get Map image
	bottomPanel.setLayout(new GridLayout(10, 10));
	for (int i = 0; i < 10; i++) {
	    for (int j = 0; j < 10; j++) {
		bottomPanel.add(new JLabel(new ImageIcon(imgLoad.getTerrainImage())));
	    }
	}
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
}
