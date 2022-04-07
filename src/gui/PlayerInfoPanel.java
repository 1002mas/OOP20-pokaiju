package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controller.PlayerController;

/*public class PlayerInfoPanel extends JPanel {
    private final PlayerController playerController;

    public PlayerInfoPanel(PlayerController playerController) {
	this.playerController = playerController;
	init();
    }

    private void init() {
	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	JLabel playerName = new JLabel("Name : " + this.playerController.getPlayerName());
	JLabel trainerNumber = new JLabel("Trainer number : " + this.playerController.getTrainerNumber());
	JLabel gender = new JLabel("Gender : " + this.playerController.getGender());
	JLabel MonsterCaptured = new JLabel();
	JLabel money = new JLabel("Money : " + this.playerController.getMoney() + " $ ");
	this.add(playerName);
	this.add(trainerNumber);
	this.add(gender);
	this.add(MonsterCaptured);
	this.add(money);
    }
}*/
//codice di prova
public class PlayerInfoPanel extends JPanel {
    private final PlayerController playerController;
    

    public PlayerInfoPanel(PlayerController playerController) {
	this.playerController = playerController;
	init();
    }

    private void init() {
	this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	JLabel playerName = new JLabel("Name : ");
	JLabel trainerNumber = new JLabel("Trainer number : ");
	JLabel gender = new JLabel("Gender : ");
	JLabel NumbMonsterCaptured = new JLabel();
	JLabel money = new JLabel("Money : ");
	setLabelProperties(playerName);
	setLabelProperties(trainerNumber);
	setLabelProperties(gender);
	setLabelProperties(NumbMonsterCaptured);
	setLabelProperties(money);

	this.add(playerName);
	this.add(trainerNumber);
	this.add(gender);
	this.add(NumbMonsterCaptured);
	this.add(money);
    }

    private void setLabelProperties(JLabel label) {
	label.setFont(new Font("SansSerif Bold Italic", Font.PLAIN, 22));
	label.setBorder(BorderFactory.createLineBorder(Color.black));
	label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
    }
}
