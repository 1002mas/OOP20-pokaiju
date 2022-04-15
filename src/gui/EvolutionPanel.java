package gui;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import controller.PlayerController;
import model.Pair;

public class EvolutionPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -6050733230779811852L;
    private ImagesLoader imgLoader;
    private Pair<String, String> monster;
    private JTextField actionText;
    private JLabel imgMonster;
    private String returnPanelName;
    private final PlayerController playerController;

    public void setReturnPanel(String returnPanelName) {
	this.returnPanelName = returnPanelName;
    }

    public EvolutionPanel(PlayerController ctrl, GameFrame frame, ImagesLoader imgLoader) {
	this.monster = ctrl.getEvolvedMonster();
	this.imgLoader = imgLoader;
	this.imgMonster = new JLabel();
	this.imgMonster.setHorizontalAlignment(JLabel.CENTER);
	this.actionText = new JTextField();
	this.actionText.setEditable(false);
	this.setLayout(new BorderLayout());
	this.add(imgMonster, BorderLayout.CENTER);
	this.add(actionText, BorderLayout.SOUTH);
	this.returnPanelName = GameFrame.MAP_PANEL;
	this.playerController = ctrl;
	init();
	this.addMouseListener(new MouseListener() {

	    @Override
	    public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void mouseClicked(MouseEvent e) {
		evolve();
		try {
		    Thread.sleep(3000);
		} catch (InterruptedException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		frame.changePanel(returnPanelName);
	    }
	});
	this.addComponentListener(new ComponentListener() {

	    @Override
	    public void componentShown(ComponentEvent e) {

		init();
	    }

	    @Override
	    public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	    }
	});
    }

    public EvolutionPanel(Pair<String, String> monster, GameFrame frame, ImagesLoader imgLoader) {// TEST

	this.monster = monster;
	this.imgLoader = imgLoader;
	this.imgMonster = new JLabel();
	this.imgMonster.setHorizontalAlignment(JLabel.CENTER);
	this.actionText = new JTextField();
	this.actionText.setEditable(false);
	this.setLayout(new BorderLayout());
	this.add(imgMonster, BorderLayout.CENTER);
	this.add(actionText, BorderLayout.SOUTH);
	// init();
	this.addMouseListener(new MouseListener() {

	    @Override
	    public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void mouseClicked(MouseEvent e) {
		evolve();
		try {
		    Thread.sleep(3000);
		} catch (InterruptedException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		frame.changePanel(GameFrame.MAP_PANEL);
	    }
	});
	this.addComponentListener(new ComponentListener() {

	    @Override
	    public void componentShown(ComponentEvent e) {

		init();
	    }

	    @Override
	    public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	    }
	});
    }

    private void init() {
	this.monster = returnPanelName.equals(GameFrame.MAP_PANEL) ? this.playerController.getEvolvedMonster()
		: this.playerController.evolveByItem(returnPanelName, ABORT);
	this.imgMonster.setIcon(new ImageIcon(imgLoader.getMonster(monster.getFirst())));
	actionText.setText(monster.getFirst() + " is evolving...");
    }

    private void evolve() {
	this.imgMonster.setIcon(new ImageIcon(imgLoader.getMonster(monster.getSecond())));
	actionText.setText(monster.getFirst() + " evolved into " + monster.getSecond());
	this.paintImmediately(getBounds());
    }
}
