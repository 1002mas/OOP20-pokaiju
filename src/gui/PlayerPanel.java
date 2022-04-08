package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import controller.ImagesLoader;
import model.Pair;

public class PlayerPanel extends JPanel {
    private static final long serialVersionUID = -7016352522753786674L;
    private static final int CHARACTER_STEP = 1;
    private static final int STEP_TIME = 500;// ms
    private static final int CHARACTER_TURN_TIME = 60;// ms

    private final ImagesLoader imgLoader;
    private final JLabel player = new JLabel();
    private final JLabel textLabel = new JLabel();
    private final int maximumCellsInRow;
    private final int maximumCellsInColumn;
    private final String player_gender;

    private Pair<Integer, Integer> playerNextPos;
    private Pair<Integer, Integer> playerPos;
    private int horizontalMovementStep;
    private int verticalMovementStep;
    private int speed = 1;
    private boolean leftLeg = false;

    public PlayerPanel(Pair<Integer, Integer> playerPos, ImagesLoader imgLoader, String player_gender,
	    int maximumCellsInRow, int maximumCellsInColumn) {
	super();
	this.playerNextPos = playerPos;
	this.playerPos = playerPos;
	this.imgLoader = imgLoader;
	this.maximumCellsInRow = maximumCellsInRow;
	this.maximumCellsInColumn = maximumCellsInColumn;
	this.player_gender = player_gender;
	this.add(player);
	this.setOpaque(false);

	textLabel.setOpaque(true);
	textLabel.setBackground(Color.WHITE);
	Border labelBorder = BorderFactory.createLineBorder(Color.BLACK, 5);
	textLabel.setBorder(labelBorder);
	textLabel.setVerticalAlignment(SwingUtilities.TOP);
	textLabel.setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	if (this.getComponentCount() > 0 && this.getComponent(0) != null) {
	    this.getComponent(0).setLocation(playerPos.getFirst(), playerPos.getSecond());
	}

	int textLabelHeight = (int) (this.getHeight() * 0.1);
	textLabel.setBounds(0, this.getHeight() - textLabelHeight, this.getWidth(), textLabelHeight);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
	super.setBounds(x, y, width, height);
	this.horizontalMovementStep = width / this.maximumCellsInRow;
	this.verticalMovementStep = height / this.maximumCellsInColumn;
	this.setNextPosition(this.playerNextPos);
	this.playerPos = playerNextPos;
	this.repaint();
    }

    public void setPlayerImage(Icon img) {
	this.player.setIcon(img);
    }

    public JLabel getPlayerComponent() {
	return this.player;
    }

    public void animatedMove(Direction dir, boolean canMove) {
	// face the next step direction
	player.setIcon(new ImageIcon(imgLoader.getPlayerImages(dir, player_gender).get(0)));
	this.paintImmediately(this.getBounds());
	sleepMillisec(CHARACTER_TURN_TIME / speed);

	// starts moving
	player.setIcon(new ImageIcon(imgLoader.getPlayerImages(dir, player_gender).get(leftLeg ? 1 : 2)));
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
	player.setIcon(new ImageIcon(imgLoader.getPlayerImages(dir, player_gender).get(0)));
	leftLeg = !leftLeg;
    }

    private void horizontalMove(Direction dir) {
	int diff = playerPos.getFirst() - playerNextPos.getFirst();
	while (Math.abs(diff) >= CHARACTER_STEP) {
	    int step = dir == Direction.RIGHT ? CHARACTER_STEP : -CHARACTER_STEP;
	    this.playerPos = new Pair<>(this.playerPos.getFirst() + step, this.playerPos.getSecond());
	    diff = playerPos.getFirst() - playerNextPos.getFirst();
	    this.paintImmediately(this.getBounds());
	    sleepMillisec(STEP_TIME / (speed * this.horizontalMovementStep));
	}
    }

    private void verticalMove(Direction dir) {
	int diff = playerPos.getSecond() - playerNextPos.getSecond();
	while (Math.abs(diff) >= CHARACTER_STEP) {
	    int step = dir == Direction.DOWN ? CHARACTER_STEP : -CHARACTER_STEP;
	    this.playerPos = new Pair<>(this.playerPos.getFirst(), this.playerPos.getSecond() + step);
	    diff = playerPos.getSecond() - playerNextPos.getSecond();
	    this.paintImmediately(this.getBounds());
	    sleepMillisec(STEP_TIME / (speed * this.verticalMovementStep));
	}
    }

    public void staticMove() {
	this.playerPos = this.playerNextPos;
	this.paintImmediately(this.getBounds());
    }

    public void setNextPosition(Pair<Integer, Integer> nextPos) {
	int targetX = nextPos.getFirst() * this.horizontalMovementStep + (this.horizontalMovementStep / 2);
	int targetY = nextPos.getSecond() * this.verticalMovementStep + (this.verticalMovementStep / 2);
	this.playerNextPos = new Pair<>(targetX, targetY);
    }

    public void showText(String text) {
	textLabel.setText(text);
	textLabel.setVisible(true);
    }

    public void hideText() {
	textLabel.setVisible(false);
    }

    private void sleepMillisec(long millisec) {
	try {
	    Thread.sleep(millisec);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }
}
