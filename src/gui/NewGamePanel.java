package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.PlayerController;
import model.Pair;
import model.map.GameMapDataImpl;
import model.map.GameMapImpl;
import model.player.Gender;
import model.player.Player;
import model.player.PlayerImpl;

public class NewGamePanel extends JPanel {

    private PlayerController playerController;

    public NewGamePanel(PlayerController playerController) {
	this.playerController = playerController;
	init();
    }

    private void init() {

	this.setLayout(new GridBagLayout());
	this.setBorder(BorderFactory.createLineBorder(Color.green));

	JLabel nameLabel = new JLabel();
	JTextField nameField = new JTextField();
	nameLabel.setText("Insert name :");

	JLabel genderLabel = new JLabel();
	JComboBox<Gender> gender = new JComboBox<>(Gender.values());
	genderLabel.setText("Select your gender :");

	Random rand = new Random();
	int a = rand.nextInt(999999) + 100000;
	JLabel trainerNumberLabel = new JLabel();
	trainerNumberLabel.setText("Trainer number is generated randomly : ");
	JTextField trainerNumberField = new JTextField();
	trainerNumberField.setText(String.valueOf(a));
	trainerNumberField.setEditable(false);
	trainerNumberLabel.setFont(new Font("Verdana", Font.CENTER_BASELINE, 20));
	trainerNumberLabel.setEnabled(false);

	JButton postData = new JButton("CREATE");
	postData.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		/*
		 * Player p = new PlayerImpl(nameField.getText(), (Gender)
		 * gender.getSelectedItem(), Integer.valueOf(trainerNumberField.getText()), new
		 * Pair<Integer, Integer>(0, 0)); System.out.println(p);
		 */

		if (nameField.getText().equals("")) {
		    JOptionPane.showMessageDialog(null, "name can't be null", "alert", JOptionPane.WARNING_MESSAGE);
		}
		playerController.setNewPlayer(nameField.getText(), (Gender) gender.getSelectedItem(), a);
		System.out.println(playerController.getPlayer().toString());

	    }
	});

	GridBagConstraints rows = new GridBagConstraints();
	rows.gridy = 0;
	rows.insets = new Insets(0, 0, 50, 0);
	rows.fill = GridBagConstraints.HORIZONTAL;
	this.add(nameLabel, rows);
	this.add(nameField, rows);
	rows.gridy++;
	this.add(genderLabel, rows);
	this.add(gender, rows);
	rows.gridy++;
	this.add(trainerNumberLabel, rows);
	this.add(trainerNumberField, rows);
	rows.gridy++;
	this.add(postData, rows);

    }
}
