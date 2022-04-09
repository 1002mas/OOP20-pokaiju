package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import model.map.*;
import model.player.*;

public class NewGamePanel extends JPanel {
    private JPanel parentPanel;
    private PlayerController playerController;

    public NewGamePanel(PlayerController playerController, JPanel mainPanel) {
	this.playerController = playerController;
	this.parentPanel = mainPanel;
	init();
    }

    private void init() {
	CardLayout c1 = (CardLayout) this.parentPanel.getLayout();
	this.setLayout(new BorderLayout());
	this.setBorder(BorderFactory.createLineBorder(Color.green));

	JLabel tipsLabel = new JLabel();
	tipsLabel.setText("Creating a new game will delete the old savedata");
	tipsLabel.setFont(new Font("SansSerif Bold Italic", Font.CENTER_BASELINE, 22));
	tipsLabel.setForeground(Color.red);

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
	trainerNumberLabel.setFont(new Font("SansSerif Bold Italic", Font.CENTER_BASELINE, 22));
	trainerNumberLabel.setEnabled(false);

	JButton postData = new JButton("CREATE");
	postData.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		if (nameField.getText().equals("")) {
		    JOptionPane.showMessageDialog(null, "name can't be null", "alert", JOptionPane.WARNING_MESSAGE);
		} else {
		    playerController.createNewPlayer(nameField.getText(), (Gender) gender.getSelectedItem(), a);
		}
		 c1.show(parentPanel, GameFrame.MAP_PANEL);
	    }
	});

	JButton quitButton = new JButton("BACK TO MENU");
	quitButton.addActionListener(e -> c1.show(this.parentPanel, GameFrame.LOGIN_PANEL));

	JPanel topPanel = new JPanel(new FlowLayout());
	topPanel.add(quitButton, FlowLayout.LEFT);

	JPanel underPanel = new JPanel(new GridBagLayout());

	GridBagConstraints rows = new GridBagConstraints();
	rows.gridy = 1;
	underPanel.add(nameLabel, rows);
	rows.insets = new Insets(0, 0, 50, 0);
	rows.fill = GridBagConstraints.HORIZONTAL;
	underPanel.add(nameLabel, rows);
	underPanel.add(nameField, rows);
	rows.gridy++;
	underPanel.add(genderLabel, rows);
	underPanel.add(gender, rows);
	rows.gridy++;
	underPanel.add(trainerNumberLabel, rows);
	underPanel.add(trainerNumberField, rows);
	rows.gridy++;
	underPanel.add(postData, rows);
	rows.gridy++;
	underPanel.add(tipsLabel, rows);

	this.add(topPanel, BorderLayout.LINE_START);
	this.add(underPanel, BorderLayout.CENTER);

    }
}
