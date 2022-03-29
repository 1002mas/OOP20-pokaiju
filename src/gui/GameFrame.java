package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import controller.PlayerController;
import model.Pair;
import model.player.Gender;
import model.player.Player;
import model.player.PlayerImpl;

public class GameFrame extends JFrame {
    private final static int HEIGHT = 1280;
    private final static int WIDTH = 720;
    static final String NEWGAMEPANEL = "new game";
    static final String MAPPANEL = "map game";
    static final String MENUPANEL = "menu";
    static final String LOGINPANEL = "login panel";
    private static final long serialVersionUID = -7927156597267134363L;
    private /* final */ PlayerController playerController;

    private final CardLayout cLayout = new CardLayout();
    JPanel mainPanel = new JPanel();

    public GameFrame(PlayerController playerController) {
	this.playerController = playerController;
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(HEIGHT, WIDTH);

	mainPanel.setLayout(cLayout);
	// Pannello di quando apro il gioco

	JPanel loginPanel = new JPanel(new GridBagLayout());
	final JButton continueGame = new JButton(" CONTINUE ");
	final JButton newGame = new JButton(" NEW GAME ");
	final JButton quitGame = new JButton(" QUIT GAME ");
	// loginPanel.setLayout(gridLayout);
	final GridBagConstraints cons = new GridBagConstraints();
	cons.gridy = 0;
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.ipady = 50;
	cons.weightx = 0;
	cons.insets = new Insets(50, 0, 0, 0);
	loginPanel.add(continueGame, cons);
	cons.gridy++;
	loginPanel.add(newGame, cons);
	cons.gridy++;
	loginPanel.add(quitGame, cons);

	// Pannello di quando clicco continua gioco
	JPanel mapPanel = buildMapPanel();
	continueGame.addActionListener(e -> changePanel(mainPanel, MAPPANEL));
	// Pannello di quando inizio un nuovo gioco
	JPanel newGamePanel = newGame();
	newGame.addActionListener(e -> changePanel(mainPanel, NEWGAMEPANEL));
	// Pannello del menu di gioco
	JPanel menuPanel = buildMenuPanel();

	quitGame.addActionListener(e -> System.exit(0));

	mainPanel.add(loginPanel, LOGINPANEL);
	mainPanel.add(newGamePanel, NEWGAMEPANEL);
	mainPanel.add(mapPanel, MAPPANEL);
	mainPanel.add(menuPanel, MENUPANEL);
	this.setContentPane(mainPanel);
	this.setVisible(true);
    }

    // TODO create player + map (panel 2)
    private JPanel buildMapPanel() {
	JPanel mapPanel = new JPanel();
	mapPanel.setLayout(new FlowLayout());
	final JButton changePanel = new JButton("menu");
	changePanel.addActionListener(e -> changePanel(mainPanel, MENUPANEL));
	mapPanel.add(changePanel);
	return mapPanel;

    }

    // TODO create battle scene (panel 3)
    private JPanel buildBattlePanel() {
	return new JPanel();
    }

    // TODO create menu (panel 4)
    private JPanel buildMenuPanel() {
	final String MONSTERPANEL = "MONSTER";
	final String BOXPANEL = "BOX";
	final String GAMEITEMSPANEL = "ITEMS";
	final String PLAYERINFOPANEL = "INFO";

	JPanel mainPanel = new JPanel(new BorderLayout());

	JPanel options = new JPanel(new FlowLayout());
	options.setBorder(BorderFactory.createLineBorder(Color.blue));

	final JButton monster = new JButton("MONSTER");
	final JButton box = new JButton(" BOX ");
	final JButton gameItems = new JButton(" GAMEITEMS ");
	final JButton playerInfo = new JButton(" PLAYERINFO ");
	final JButton quit = new JButton(" QUIT MENU ");

	options.add(monster);
	options.add(box);
	options.add(gameItems);
	options.add(playerInfo);
	options.add(quit);

	JPanel underPanel = new JPanel();
	underPanel.setBorder(BorderFactory.createLineBorder(Color.red));
	underPanel.setLayout(cLayout);

	JPanel monsterPanel = new JPanel();
	JLabel monsterLabel = new JLabel();
	monsterLabel.setText("panel 1");
	monsterPanel.add(monsterLabel);

	JPanel boxPanel = new JPanel();
	JLabel boxLabel = new JLabel();
	boxLabel.setText("panel 2");
	boxPanel.add(boxLabel);

	JPanel gameItemPanel = new JPanel();
	JLabel gameItemLabel = new JLabel();
	gameItemLabel.setText("panel 3");
	gameItemPanel.add(gameItemLabel);

	JPanel playerInfoPanel = new JPanel();
	JLabel playerInfolabel = new JLabel();
	playerInfolabel.setText("panel 4");
	playerInfoPanel.add(playerInfolabel);

	underPanel.add(monsterPanel);
	underPanel.add(boxPanel);
	underPanel.add(gameItemPanel);
	underPanel.add(playerInfoPanel);

	monster.addActionListener(e -> changePanel(underPanel, MONSTERPANEL));
	box.addActionListener(e -> changePanel(underPanel, BOXPANEL));
	gameItems.addActionListener(e -> changePanel(underPanel, GAMEITEMSPANEL));
	playerInfo.addActionListener(e -> changePanel(underPanel, PLAYERINFOPANEL));
	quit.addActionListener(e -> changePanel(this.mainPanel, MAPPANEL));

	underPanel.add(monsterPanel, MONSTERPANEL);
	underPanel.add(boxPanel, BOXPANEL);
	underPanel.add(gameItemPanel, GAMEITEMSPANEL);
	underPanel.add(playerInfoPanel, PLAYERINFOPANEL);

	mainPanel.add(options, BorderLayout.NORTH);
	mainPanel.add(underPanel, BorderLayout.CENTER);

	return mainPanel;
    }

    // TODO create new game menu (panel 5)
    private JPanel newGame() {
	return new NewGamePanel();
    }

    private void changePanel(JPanel panel, String name) {
	cLayout.show(panel, name);
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
