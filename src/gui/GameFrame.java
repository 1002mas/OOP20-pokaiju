package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
<<<<<<< HEAD
=======
import java.awt.Component;
import java.awt.Dimension;
>>>>>>> gui1.1
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
<<<<<<< HEAD

import controller.ImagesLoader;
=======
>>>>>>> gui1.1
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

    private JPanel mapPanel;
    final private ImagesLoader imgLoad = new ImagesLoader();
    private Pair<Integer, Integer> playerPos = new Pair<>(0, 0); // TODO use controller to get this in local function

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
<<<<<<< HEAD
	JPanel gamePanel = buildMapPanel();
	continueGame.addActionListener(e -> {
	    cLayout.show(mainPanel, MAPPANEL);
	    mapPanel.requestFocusInWindow();
	});
=======
	JPanel mapPanel = buildMapPanel();
	continueGame.addActionListener(e -> changePanel(mainPanel, MAPPANEL));
>>>>>>> gui1.1
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

    public String getMenuPanelName() {
	return NEWGAMEPANEL;
    }

    private JPanel buildMapPanel() {
<<<<<<< HEAD
	//TODO use current player position
	PlayerPanel topPanel = new PlayerPanel(new Pair<>(0, 0), imgLoad);
	topPanel.setPlayerImage(new ImageIcon(imgLoad.getPlayerImages(Direction.DOWN).get(0)));

	JPanel bottomPanel = new JPanel();
	bottomPanel.setOpaque(true);
	// TODO get Map size and get Map from file
	bottomPanel.setLayout(new GridLayout(10, 10));
	for (int i = 0; i < 10; i++) {
	    for (int j = 0; j < 10; j++) {
		bottomPanel.add(new JLabel(new ImageIcon(imgLoad.getTerrainImage())));
	    }
	}
	mapPanel = new JPanel();
	mapPanel.setLayout(null);
	System.out.println(this.getBounds());
	topPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
	bottomPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
	// added panel in position 0
	mapPanel.add(topPanel, 0);
	mapPanel.add(bottomPanel);
	mapPanel.setFocusable(true);
	mapPanel.addKeyListener(new PlayerCommands(this));
	return mapPanel;
    }

    public void movePlayer(Direction dir) {
	changePlayerPosition(dir);
	PlayerPanel topPanel = (PlayerPanel) (mapPanel.getComponent(0));
	// TODO use controller player
	topPanel.setNextPosition(playerPos);
	topPanel.animatedMove(dir, true);// TODO use controller function boolean changePlayerPosition(Direction dir)
	// TODO check if map has to be changed

    }

    private void changePlayerPosition(Direction dir) {
	// TODO use controller player
	switch (dir) {
	case UP:
	    this.playerPos = new Pair<>(playerPos.getFirst(), playerPos.getSecond() - 10);
	    break;
	case DOWN:
	    this.playerPos = new Pair<>(playerPos.getFirst(), playerPos.getSecond() + 10);
	    break;
	case LEFT:
	    this.playerPos = new Pair<>(playerPos.getFirst() - 10, playerPos.getSecond());
	    break;
	case RIGHT:
	    this.playerPos = new Pair<>(playerPos.getFirst() + 10, playerPos.getSecond());
	    break;

	default:
	    break;
	}

    }

    public void changePanel(String name) {
	cLayout.show(mainPanel, name);
=======
	JPanel mapPanel = new JPanel();
	mapPanel.setLayout(new FlowLayout());
	final JButton changePanel = new JButton("menu");
	changePanel.addActionListener(e -> changePanel(mainPanel, MENUPANEL));
	mapPanel.add(changePanel);
	return mapPanel;

>>>>>>> gui1.1
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
<<<<<<< HEAD
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

=======
	return new NewGamePanel();
    }

    private void changePanel(JPanel panel, String name) {
	cLayout.show(panel, name);
    }

>>>>>>> gui1.1
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
