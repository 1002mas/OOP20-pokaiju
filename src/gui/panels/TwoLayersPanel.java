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

/**
 * 
 * The two layers panel is a panel containing two panels one over another. The
 * bottom panel has a GridLayout containing the map textures. The top panel has
 * no background and allows sprite to be over the map.
 * 
 *
 */
public class TwoLayersPanel extends JPanel {

    private static final long serialVersionUID = 403421258645641464L;
    private final PlayerPanel topPanel;
    private final JPanel bottomPanel;
    private final int rows;
    private final int columns;
    private final List<JLabel> mapBlocks = new ArrayList<>();

    /**
     * 
     * @param playerController game controller istance
     * @param imgLoad          image loader
     * @param panelHeight      panel maximum height
     * @param panelWidth       panel maximum width
     */
    public TwoLayersPanel(final PlayerController playerController, final ImagesLoader imgLoad, final int panelHeight,
            final int panelWidth) {
        this.rows = playerController.getMaximumBlocksInRow();
        this.columns = playerController.getMaximumBlocksInColumn();
        topPanel = new PlayerPanel(playerController.getPlayerPosition(), imgLoad, playerController.getPlayerGender(),
                rows, columns);
        topPanel.setPlayerImage(
                new ImageIcon(imgLoad.getPlayerImages(Direction.DOWN, playerController.getPlayerGender()).get(0)));
        topPanel.setNpcs(playerController.getAllNpcs());
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(final MouseEvent e) {
                topPanel.requestFocusInWindow();
            }

            @Override
            public void mousePressed(final MouseEvent e) {

            }

            @Override
            public void mouseExited(final MouseEvent e) {
            }

            @Override
            public void mouseEntered(final MouseEvent e) {
            }

            @Override
            public void mouseClicked(final MouseEvent e) {
                topPanel.requestFocusInWindow();
            }
        });
        bottomPanel = new JPanel();
        bottomPanel.setOpaque(true);
        bottomPanel.setLayout(new GridLayout(rows, columns));
        final int maxBlocks = rows * columns;
        for (int i = 0; i < maxBlocks; i++) {
            final JLabel l = new JLabel();
            mapBlocks.add(l);
            bottomPanel.add(l);
        }

        this.setLayout(null);
        this.setBounds(0, 0, panelWidth, panelHeight);
        topPanel.setBounds(0, 0, panelWidth, panelHeight);
        bottomPanel.setBounds(0, 0, panelWidth, panelHeight);
        this.add(topPanel);
        this.add(bottomPanel);
        this.setFocusable(true);
    }

    /**
     * 
     * @return the panel with no background.
     */
    public PlayerPanel getTopPanel() {
        return this.topPanel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }

    /**
     * 
     * @return the panel containing the map
     */
    public JPanel getBottomPanel() {
        return this.bottomPanel;
    }

    /**
     * It changes the bottom panel textures.
     * 
     * @param imageGrid list of textures
     */
    public void setMapImage(final List<BufferedImage> imageGrid) {
        final int size = rows * columns;
        for (int i = 0; i < size; i++) {
            mapBlocks.get(i).setIcon(new ImageIcon(imageGrid.get(i)));
        }
    }
}
