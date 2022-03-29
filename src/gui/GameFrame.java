package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.ImagesLoader;
import controller.PlayerController;
import model.Pair;

public class GameFrame extends JFrame {
    static final String NEW_GAME_PANEL = "new game";
    static final String MAP_PANEL = "map game";
    static final String MENU_PANEL = "menu";
    static final String LOGIN_PANEL = "login panel";

    private static final int HEIGHT = 1280;
    private static final int WIDTH = 720;
    private static final long serialVersionUID = -7927156597267134363L;

    private final CardLayout cLayout = new CardLayout();
    private final Map<String, JPanel> subPanels = new HashMap<>();
    private final ImagesLoader imgLoad = new ImagesLoader();
    private final JPanel mainPanel = new JPanel();
    private /* final */ PlayerController playerController;

    private Pair<Integer, Integer> playerPos = new Pair<>(0, 0); // TODO use controller to get this in local function

    public GameFrame(PlayerController playerController) {
	this.playerController = playerController;
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(HEIGHT, WIDTH);

	mainPanel.setLayout(cLayout);

	LoginPanel loginPanel = new LoginPanel();

	// Pannello di quando clicco continua gioco
	JPanel gamePanel = buildMapPanel();
	loginPanel.getContinue().addActionListener(e -> {
	    changePanel(MAP_PANEL);
	    System.out.println(gamePanel.hasFocus());
	    System.out.println(gamePanel.isFocusable());
	    // mapPanel.requestFocusInWindow();
	});

	// Pannello di quando inizio un nuovo gioco
	JPanel newGamePanel = newGame();
	loginPanel.getnewGame().addActionListener(e -> changePanel(NEW_GAME_PANEL));
	// Pannello del menu di gioco
	JPanel menuPanel = buildMenuPanel();

	loginPanel.getquitGame().addActionListener(e -> System.exit(0));

	mainPanel.add(loginPanel, LOGIN_PANEL);
	mainPanel.add(newGamePanel, NEW_GAME_PANEL);
	mainPanel.add(gamePanel, MAP_PANEL);
	mainPanel.add(menuPanel, MENU_PANEL);

	subPanels.put(LOGIN_PANEL, loginPanel);
	subPanels.put(NEW_GAME_PANEL, newGamePanel);
	subPanels.put(MAP_PANEL, gamePanel);
	subPanels.put(MENU_PANEL, menuPanel);
	this.setContentPane(mainPanel);
	this.setVisible(true);
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
	JPanel mapPanel = new JPanel();
	mapPanel.setLayout(null);
	topPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
	bottomPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
	mapPanel.add(topPanel, 0);
	mapPanel.add(bottomPanel);
	mapPanel.setFocusable(true);
	mapPanel.addKeyListener(new PlayerCommands(this));
	return mapPanel;
    }

    public void movePlayer(Direction dir) {
	changePlayerPosition(dir);
	PlayerPanel topPanel = (PlayerPanel) (subPanels.get(MAP_PANEL).getComponent(0));
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

	monster.addActionListener(e -> cLayout.show(underPanel, MONSTERPANEL));
	box.addActionListener(e -> cLayout.show(underPanel, BOXPANEL));
	gameItems.addActionListener(e -> cLayout.show(underPanel, GAMEITEMSPANEL));
	playerInfo.addActionListener(e -> cLayout.show(underPanel, PLAYERINFOPANEL));
	quit.addActionListener(e -> changePanel(MAP_PANEL));

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
	subPanels.get(name).requestFocusInWindow();
    }

}
