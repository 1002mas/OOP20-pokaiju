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
import controller.ImagesLoader;
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

	JPanel gamePanel = buildMapPanel();
	continueGame.addActionListener(e -> {
	    cLayout.show(mainPanel, MAPPANEL);
	    mapPanel.requestFocusInWindow();
	});

	JPanel mapPanel = buildMapPanel();
	continueGame.addActionListener(e -> changePanel(MAPPANEL));

	// Pannello di quando inizio un nuovo gioco
	JPanel newGamePanel = newGame();
	newGame.addActionListener(e -> changePanel(NEWGAMEPANEL));
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

	// TODO use current player position
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

	monster.addActionListener(e -> changePanel2(underPanel, MONSTERPANEL));
	box.addActionListener(e -> changePanel2(underPanel, BOXPANEL));
	gameItems.addActionListener(e -> changePanel2(underPanel, GAMEITEMSPANEL));
	playerInfo.addActionListener(e -> changePanel2(underPanel, PLAYERINFOPANEL));
	quit.addActionListener(e -> changePanel(MAPPANEL));

	underPanel.add(monsterPanel, MONSTERPANEL);
	underPanel.add(boxPanel, BOXPANEL);
	underPanel.add(gameItemPanel, GAMEITEMSPANEL);
	underPanel.add(playerInfoPanel, PLAYERINFOPANEL);

	mainPanel.add(options, BorderLayout.NORTH);
	mainPanel.add(underPanel, BorderLayout.CENTER);

	return mainPanel;
    }

    // TODO create new game menu (panel 5)

    public JPanel newGame() {
	return new NewGamePanel();
    }

    void changePanel(String name) {
	cLayout.show(mainPanel, name);
    }

    public void changePanel2(JPanel panel, String name) {
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
