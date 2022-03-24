package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.PlayerController;
import model.Pair;
import model.player.Gender;

public class GameFrame extends JFrame {
    private final static int HEIGHT = 1280;
    private final static int WIDTH = 720;
    static final String NEWGAMEPANEL = "new game";
    static final String MAPPANEL = "map game";
    static final String GAMEPANEL = "ingame";
    static final String LOGINPANEL = "login panel";
    private static final long serialVersionUID = -7927156597267134363L;
    private /* final */ PlayerController playerController;

    private final CardLayout cLayout = new CardLayout();
    private final GridLayout gridLayout = new GridLayout(1, 3, 50, 0);
    JPanel mainPanel = new JPanel();

    public GameFrame(PlayerController playerController) {
	this.playerController = playerController;
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(HEIGHT, WIDTH);

	mainPanel.setLayout(cLayout);
	// Pannello di quando apro il gioco

	JPanel loginPanel = new JPanel();
	loginPanel.setLayout(gridLayout);
	final JButton continueGame = new JButton(" CONTINUE ");
	final JButton newGame = new JButton(" NEW GAME ");
	final JButton quitGame = new JButton(" QUIT GAME ");
	loginPanel.add(continueGame);
	loginPanel.add(newGame);
	loginPanel.add(quitGame);

	// Pannello di quando clicco continua gioco
	JPanel gamePanel = buildMapPanel();
	continueGame.addActionListener(e -> {
	    cLayout.show(mainPanel, MAPPANEL);
	});
	// Pannello di quando inizio un nuovo gioco
	JPanel newGamePanel = newGame();
	newGame.addActionListener(e -> {
	    cLayout.show(mainPanel, NEWGAMEPANEL);
	});

	quitGame.addActionListener(e -> System.exit(0));

	mainPanel.add(loginPanel, LOGINPANEL);
	mainPanel.add(newGamePanel, NEWGAMEPANEL);
	mainPanel.add(gamePanel, MAPPANEL);
	this.setContentPane(mainPanel);
	this.setVisible(true);
    }

    // TODO create player + map (panel 2)
    private JPanel buildMapPanel() {
	return new JPanel() {
	};
    }

    // TODO create battle scene (panel 3)
    private JPanel buildBattlePanel() {
	return new JPanel();
    }

    // TODO create menu (panel 4)
    private JPanel buildMenuPanel() {
	JPanel options = new JPanel();
	options.setLayout(new FlowLayout());
	final JButton monster = new JButton("MONSTER");
	final JButton box = new JButton(" BOX ");
	final JButton gameItems = new JButton(" GAMEITEMS ");
	final JButton playerInfo = new JButton(" PLAYERINFO ");
	final JButton quit = new JButton(" QUIT ");

	options.add(monster);
	options.add(box);
	options.add(gameItems);
	options.add(playerInfo);
	options.add(quit);
	return options;
    }

    // TODO create new game menu (panel 5)
    private JPanel newGame() {
	JPanel newgame = new JPanel(new GridBagLayout());
	newgame.setBorder(BorderFactory.createLineBorder(Color.red));

	JLabel nameLabel = new JLabel();
	JTextField nameField = new JTextField(10);
	nameLabel.setText("Insert name :");
	nameField.setSize(100, 100);

	JLabel askGender = new JLabel();
	JComboBox<Gender> gender = new JComboBox<>(Gender.values());
	askGender.setText("Select your gender :");
	askGender.setSize(100, 100);

	Random rand = new Random();
	int a = rand.nextInt(999999) + 100000;
	JLabel trainerNumber = new JLabel();
	trainerNumber.setText("Trainer number is generated randomly : " + a + " ");
	trainerNumber.setSize(100, 100);
	trainerNumber.setFont(new Font("Verdana", Font.CENTER_BASELINE, 20));
	trainerNumber.setEnabled(false);

	GridBagConstraints rows = new GridBagConstraints();
	rows.gridy = 0;
	rows.insets = new Insets(0, 0, 98, 0);
	rows.fill = GridBagConstraints.HORIZONTAL;
	newgame.add(nameLabel, rows);
	newgame.add(nameField, rows);
	rows.gridy++;
	newgame.add(askGender, rows);
	newgame.add(gender, rows);
	rows.gridy++;
	newgame.add(trainerNumber, rows);

	return newgame;

    }

    private void changePanel(String name) {
    }

    // main di prova, si puo togliere in qualsiasi momento
    public static void main(String[] args) {
	GameFrame frame = new GameFrame(new PlayerController() {

	    @Override
	    public void saveData() {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public boolean movePlayer(Pair<Integer, Integer> coord) {
		// TODO Auto-generated method stub
		return false;
	    }

	    @Override
	    public boolean loadData() {
		// TODO Auto-generated method stub
		return false;
	    }

	    @Override
	    public void interact(Pair<Integer, Integer> coord) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public Pair<Integer, Integer> getPlayerPosition() {
		// TODO Auto-generated method stub
		return null;
	    }
	});
    }
}
