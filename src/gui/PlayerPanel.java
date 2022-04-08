package gui;

import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.ImagesLoader;
import model.Pair;

public class PlayerPanel extends JPanel {
    private static final long serialVersionUID = -7016352522753786674L;
    private static final int CHARACTER_STEP = 2;

    private final ImagesLoader imgLoader;
    private final JLabel player = new JLabel();
    private final String player_gender;
    private final int size = 10;

    private Pair<Integer, Integer> playerNextPos;
    private Pair<Integer, Integer> playerPos;
    private boolean leftLeg = false;

    public PlayerPanel(Pair<Integer, Integer> playerPos, ImagesLoader imgLoader, String player_gender) {
	super();
	this.playerNextPos = playerPos;
	this.playerPos = playerPos;
	this.imgLoader = imgLoader;
	this.player_gender = player_gender;
	this.add(player);
	this.setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	if (this.getComponentCount() > 0 && this.getComponent(0) != null) {
	    this.getComponent(0).setLocation(playerPos.getFirst(), playerPos.getSecond());
	}
    }

    public void setPlayerImage(Icon img) {
	this.player.setIcon(img);
    }

    public JLabel getPlayerComponent() {
	return this.player;
    }

    public void animatedMove(Direction dir, boolean canMove) {
	// face the next step direction
	int diff = dir.isVertical() ? playerPos.getSecond() - playerNextPos.getSecond()
		: playerPos.getFirst() - playerNextPos.getFirst();
	JLabel player = (JLabel) this.getComponent(0);
	player.setIcon(new ImageIcon(imgLoader.getPlayerImages(dir, player_gender).get(0)));
	this.paintImmediately(this.getBounds());
	sleepMillisec(100);
	if (canMove) {
	    // starts moving
	    player.setIcon(new ImageIcon(imgLoader.getPlayerImages(dir, player_gender).get(leftLeg ? 1 : 2)));
	    this.paintImmediately(this.getBounds());
	    while (diff != 0) {
		int x = playerPos.getFirst();
		int y = playerPos.getSecond();
		if (dir.isVertical()) {
		    y += dir == Direction.DOWN ? CHARACTER_STEP : -CHARACTER_STEP;
		} else {
		    x += dir == Direction.RIGHT ? CHARACTER_STEP : -CHARACTER_STEP;
		}
		playerPos = new Pair<>(x, y);
		diff = dir.isVertical() ? playerPos.getSecond() - playerNextPos.getSecond()
			: playerPos.getFirst() - playerNextPos.getFirst();
		sleepMillisec(20);
	    }
	} else {

	}
	// end movement
	player.setIcon(new ImageIcon(imgLoader.getPlayerImages(dir, player_gender).get(0)));
	leftLeg = !leftLeg;
    }

    public void staticMove() {
	this.playerPos = this.playerNextPos;
	this.paintImmediately(this.getBounds());
    }

    // TODO analyze good ratio logic move/view move/steps
    public void setNextPosition(Pair<Integer, Integer> nextPos) {
	this.playerNextPos = new Pair<>(nextPos.getFirst() * size, nextPos.getSecond() * size);
    }

    private void sleepMillisec(long millisec) {
	try {
	    Thread.sleep(millisec);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }
}
