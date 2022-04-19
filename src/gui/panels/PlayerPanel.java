package gui.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import controller.Direction;
import gui.ImagesLoader;
import model.Pair;

/**
 * Panel with no background containing player and npcs.
 * 
 *
 */
public class PlayerPanel extends JPanel {
    private static final long serialVersionUID = -7016352522753786674L;
    private static final int CHARACTER_STEP = 1;
    private static final int STEP_TIME = 300;
    private static final int CHARACTER_TURN_TIME = 60;

    private final ImagesLoader imgLoader;
    private final JLabel player = new JLabel();
    private final JTextArea textLabel = new JTextArea();
    private final int maximumCellsInRow;
    private final int maximumCellsInColumn;
    private final String playerGender;

    private Map<JLabel, Pair<Integer, Integer>> npcsLabels = new HashMap<>();
    private Pair<Integer, Integer> playerNextPos;
    private Pair<Integer, Integer> playerPos;
    private Pair<Integer, Integer> movementStep;
    private Pair<Integer, Integer> cellRelativePos;
    private boolean leftLeg;

    /**
     * 
     * @param playerPos            current player pos
     * @param imgLoader            images loader
     * @param playerGender         player gender to change between player sprites
     * @param maximumCellsInRow    maximum map height
     * @param maximumCellsInColumn maximum map width
     */
    public PlayerPanel(final Pair<Integer, Integer> playerPos, final ImagesLoader imgLoader, final String playerGender,
            final int maximumCellsInRow, final int maximumCellsInColumn) {
        super();
        this.playerNextPos = playerPos;
        this.playerPos = playerPos;
        this.imgLoader = imgLoader;
        this.maximumCellsInRow = maximumCellsInRow;
        this.maximumCellsInColumn = maximumCellsInColumn;
        this.playerGender = playerGender;
        this.add(player);
        this.setOpaque(false);
        this.textLabel.setOpaque(true);
        this.textLabel.setBackground(Color.WHITE);
        final Border labelBorder = BorderFactory.createLineBorder(Color.BLACK, 5);
        this.textLabel.setBorder(labelBorder);
        this.textLabel.setEditable(false);
        this.textLabel.setLineWrap(true);
        this.textLabel.setVisible(false);
        this.add(textLabel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        player.setLocation(playerPos.getFirst(), playerPos.getSecond());
        final double percHeight = 0.1;
        final int textLabelHeight = (int) (this.getHeight() * percHeight);
        textLabel.setBounds(0, this.getHeight() - textLabelHeight, this.getWidth(), textLabelHeight);
        for (final JLabel npc : npcsLabels.keySet()) {
            final Pair<Integer, Integer> npcPos = calculateViewPosition(this.npcsLabels.get(npc));
            npc.setLocation(npcPos.getFirst(), npcPos.getSecond());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBounds(final int x, final int y, final int width, final int height) {
        super.setBounds(x, y, width, height);

        final int horizontalMovementStep = width / this.maximumCellsInRow;
        final int verticalMovementStep = height / this.maximumCellsInColumn;
        final int cellRelativePosX = horizontalMovementStep / 2 - player.getIcon().getIconWidth() / 2;
        final int cellRelativePosY = verticalMovementStep / 2 - player.getIcon().getIconHeight();

        this.movementStep = new Pair<>(horizontalMovementStep, verticalMovementStep);
        this.cellRelativePos = new Pair<>(cellRelativePosX, cellRelativePosY);

        final double fontRatio = 0.022;
        this.textLabel.setFont(new Font(Font.SERIF, Font.PLAIN, (int) (fontRatio * height)));

        this.setNextPosition(this.playerNextPos);
        this.playerPos = playerNextPos;
        this.repaint();
    }

    /**
     * It changes the player image.
     * 
     * @param img image that the player will take
     */
    public void setPlayerImage(final Icon img) {
        this.player.setIcon(img);
    }

    /**
     * 
     * @return the player sprite in a JLabel
     */
    public JLabel getPlayerComponent() {
        return this.player;
    }

    /**
     * It is used when the player goes around the map. DO NOT use on map change, use
     * {@link #staticMove() staticMove()} instead.
     * 
     * @param dir     direction where the player is going to.
     * @param canMove if the player cannot move, it will just turn in the given
     *                direction
     */
    public void animatedMove(final Direction dir, final boolean canMove) {
        // face the next step direction
        player.setIcon(new ImageIcon(imgLoader.getPlayerImages(dir, playerGender).get(0)));
        this.paintImmediately(this.getBounds());
        sleepMillisec(CHARACTER_TURN_TIME);

        // starts moving
        player.setIcon(new ImageIcon(imgLoader.getPlayerImages(dir, playerGender).get(leftLeg ? 1 : 2)));
        this.paintImmediately(this.getBounds());

        // change position
        if (canMove) {
            if (dir.isVertical()) {
                verticalMove(dir);
            } else {
                horizontalMove(dir);
            }
        }

        // end movement
        player.setIcon(new ImageIcon(imgLoader.getPlayerImages(dir, playerGender).get(0)));
        leftLeg = !leftLeg;
    }

    private void horizontalMove(final Direction dir) {
        int diff = playerPos.getFirst() - playerNextPos.getFirst();
        while (Math.abs(diff) >= CHARACTER_STEP) {
            final int step = dir == Direction.RIGHT ? CHARACTER_STEP : -CHARACTER_STEP;
            this.playerPos = new Pair<>(this.playerPos.getFirst() + step, this.playerPos.getSecond());
            diff = playerPos.getFirst() - playerNextPos.getFirst();
            this.paintImmediately(this.getBounds());
            sleepMillisec(STEP_TIME / (this.movementStep.getFirst()));
        }
    }

    private void verticalMove(final Direction dir) {
        int diff = playerPos.getSecond() - playerNextPos.getSecond();
        while (Math.abs(diff) >= CHARACTER_STEP) {
            final int step = dir == Direction.DOWN ? CHARACTER_STEP : -CHARACTER_STEP;
            this.playerPos = new Pair<>(this.playerPos.getFirst(), this.playerPos.getSecond() + step);
            diff = playerPos.getSecond() - playerNextPos.getSecond();
            this.paintImmediately(this.getBounds());
            sleepMillisec(STEP_TIME / (this.movementStep.getSecond()));
        }
    }

    /**
     * It is used to just change the player position.
     */
    public void staticMove() {
        this.playerPos = this.playerNextPos;
        this.paintImmediately(this.getBounds());
    }

    private Pair<Integer, Integer> calculateViewPosition(final Pair<Integer, Integer> pos) {
        final int targetX = pos.getFirst() * this.movementStep.getFirst() + cellRelativePos.getFirst();
        final int targetY = pos.getSecond() * this.movementStep.getSecond() + cellRelativePos.getSecond();
        return new Pair<>(targetX, targetY);
    }

    /**
     * It set the position where the player will have to go.
     * 
     * @param nextPos the next position
     */
    public void setNextPosition(final Pair<Integer, Integer> nextPos) {
        this.playerNextPos = calculateViewPosition(nextPos);
    }

    /**
     * It substitutes the npc in the map with the given ones.
     * 
     * @param npcsPosition the npc name and its position
     */
    public void setNpcs(final Map<String, Pair<Integer, Integer>> npcsPosition) {
        for (final JLabel l : npcsLabels.keySet()) {
            this.remove(l);
        }
        this.npcsLabels = new HashMap<>();
        final List<String> npcs = new ArrayList<>(npcsPosition.keySet());
        for (final String npcName : npcs) {
            final JLabel npcLabel = new JLabel(new ImageIcon(imgLoader.getNpcImages(npcName)));
            npcsLabels.put(npcLabel, npcsPosition.get(npcName));
            this.add(npcLabel);
        }
        this.validate();
        this.repaint();
    }

    /**
     * It display a text in the bottom page.
     * 
     * @param text the text to be displayed
     */
    public void showText(final String text) {
        textLabel.setText(text);
        textLabel.setVisible(true);
    }

    /**
     * It hides the displayed text in the bottom page.
     * 
     */
    public void hideText() {
        textLabel.setVisible(false);
        this.paintImmediately(this.getBounds());
    }

    private void sleepMillisec(final long millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
