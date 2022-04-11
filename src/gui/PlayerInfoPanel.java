package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.PlayerController;

public class PlayerInfoPanel extends JPanel {
    private static final long serialVersionUID = 8776923375870422485L;
    private final PlayerController playerController;

    public PlayerInfoPanel(PlayerController playerController) {
	this.playerController = playerController;
	init();
    }

    private void init() {
	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	JLabel playerName = new JLabel("Name : " + this.playerController.getPlayerName());
	JLabel trainerNumber = new JLabel("Trainer number : " + this.playerController.getTrainerNumber());
	JLabel gender = new JLabel("Gender : " + this.playerController.getPlayerGender());
	JLabel money = new JLabel("Money : " + this.playerController.getPlayerMoney() + " $ ");
	setLabelProperties(playerName);
	setLabelProperties(trainerNumber);
	setLabelProperties(gender);
	setLabelProperties(money);

	this.add(playerName);
	this.add(trainerNumber);
	this.add(gender);
	this.add(money);
    }

    private void setLabelProperties(JLabel label) {
	label.setFont(new Font("SansSerif Bold Italic", Font.PLAIN, 22));
	label.setBorder(BorderFactory.createLineBorder(Color.black));
	label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
    }

    public void update() {
	this.removeAll();
	this.init();
    };
}
