package gui.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.PlayerController;

public class PlayerInfoPanel extends JPanel {
    private static final long serialVersionUID = 8776923375870422485L;
    private final PlayerController playerController;

    /**
     * 
     * @param playerController the game controller
     */
    public PlayerInfoPanel(PlayerController playerController) {
	this.playerController = playerController;
    }

    /**
     * Initialize content area
     * 
     */
    private void init() {
	this.setLayout(new GridLayout(4, 0));
	JLabel playerName = new JLabel("Name :  " + this.playerController.getPlayerName());
	JLabel trainerNumber = new JLabel("Trainer number :  " + this.playerController.getTrainerNumber());
	JLabel gender = new JLabel("Gender :  " + this.playerController.getPlayerGender());
	JLabel money = new JLabel("Money :  " + this.playerController.getPlayerMoney() + " $ ");
	setLabelProperties(playerName);
	setLabelProperties(trainerNumber);
	setLabelProperties(gender);
	setLabelProperties(money);
	this.add(playerName);
	this.add(trainerNumber);
	this.add(gender);
	this.add(money);
    }

    /**
     * set JLabel's properties
     * 
     * @param label JLabel
     */
    private void setLabelProperties(JLabel label) {
	label.setFont(new Font("SansSerif Bold Italic", Font.PLAIN, 22));
	label.setBorder(BorderFactory.createLineBorder(Color.black));
	label.setHorizontalAlignment(JLabel.CENTER);
    }

    /**
     * update content area
     */
    public void update() {
	this.removeAll();
	this.init();
	this.validate();
    };
}
