package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.ImagesLoader;
import controller.PlayerController;
import model.Pair;

public class GameFrame extends JFrame {
    private static final int HEIGHT = 1280;
    private static final int WIDTH = 720;
    private static final long serialVersionUID = -7927156597267134363L;

    private final CardLayout cLayout = new CardLayout();
    private final Map<String, JPanel> subPanels = new HashMap<>();
    private final ImagesLoader imgLoad = new ImagesLoader();
    private final JPanel mainPanel = new JPanel();
    private final PlayerController playerController;

    public GameFrame(PlayerController playerController) {
	this.playerController = playerController;
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(HEIGHT, WIDTH);

	mainPanel.setLayout(cLayout);

	LoginPanel loginPanel = new LoginPanel();

	// Pannello di quando clicco continua gioco
	loginPanel.getContinue().addActionListener(e -> {
	    if (!subPanels.containsKey(PanelTypes.MAP_PANEL.name())) {
		JPanel gamePanel = buildMapPanel();
		mainPanel.add(gamePanel, PanelTypes.MAP_PANEL.name());
		subPanels.put(PanelTypes.MAP_PANEL.name(), gamePanel);
	    }
	    changePanel(PanelTypes.MAP_PANEL.name());
	});

	// Pannello di quando inizio un nuovo gioco
	JPanel newGamePanel = newGamePanel();
	loginPanel.getnewGame().addActionListener(e -> changePanel(PanelTypes.NEW_GAME_PANEL.name()));
	// Pannello del menu di gioco
	JPanel menuPanel = buildMenuPanel();

	loginPanel.getquitGame().addActionListener(e -> System.exit(0));

	mainPanel.add(loginPanel, PanelTypes.LOGIN_PANEL.name());
	mainPanel.add(newGamePanel, PanelTypes.NEW_GAME_PANEL.name());
	mainPanel.add(menuPanel, PanelTypes.MENU_PANEL.name());

	subPanels.put(PanelTypes.LOGIN_PANEL.name(), loginPanel);
	subPanels.put(PanelTypes.NEW_GAME_PANEL.name(), newGamePanel);
	subPanels.put(PanelTypes.MENU_PANEL.name(), menuPanel);
	this.setContentPane(mainPanel);
	this.setVisible(true);
    }

    private JPanel buildMapPanel() {
	TwoLayersPanel mapPanel = new TwoLayersPanel(playerController, imgLoad, this.getHeight(), this.getWidth());
	mapPanel.addKeyListener(new PlayerCommands(this));
	return mapPanel;
    }

    public void movePlayer(Direction dir) {
	TwoLayersPanel p = (TwoLayersPanel) subPanels.get(PanelTypes.MAP_PANEL.name());
	PlayerPanel topPanel = p.getTopPanel();
	boolean animationOn = true;
	Pair<Integer, Integer> newPosition = playerController.getPlayerPosition();

	if (playerController.canPassThrough(dir)) {
	    newPosition = playerController.movePlayer(dir);
	    topPanel.setNextPosition(newPosition);
	    if (playerController.canChangeMap(dir)) {// hasMapChanged...
		// TODO get Map id
		// TODO load map image by id
		animationOn = false;
	    }
	}

	if (animationOn) {
	    topPanel.animatedMove(dir, playerController.hasPlayerMoved());
	} else {
	    topPanel.staticMove();
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

	JPanel upPanel = new JPanel(new FlowLayout());
	upPanel.setBorder(BorderFactory.createLineBorder(Color.blue));

	final JButton monster = new JButton("MONSTER");
	final JButton box = new JButton(" BOX ");
	final JButton gameItems = new JButton(" BAG ");
	final JButton playerInfo = new JButton(" PLAYERINFO ");
	final JButton quit = new JButton(" QUIT MENU ");

	upPanel.add(monster);
	upPanel.add(box);
	upPanel.add(gameItems);
	upPanel.add(playerInfo);
	upPanel.add(quit);

	JPanel underPanel = new JPanel();
	underPanel.setBorder(BorderFactory.createLineBorder(Color.red));
	underPanel.setLayout(cLayout);

	MonsterPanel monsterPanel = new MonsterPanel();

	JPanel boxPanel = new JPanel();
	JLabel boxLabel = new JLabel();
	boxPanel.add(boxLabel);

	// codice giusto
	// GameItemPanel(playerController.getPlayer().allItems());
	GameItemPanel gameItemPanel = new GameItemPanel();

	JPanel playerInfoPanel = new JPanel();
	JLabel playerInfolabel = new JLabel();
	playerInfoPanel.add(playerInfolabel);

	underPanel.add(monsterPanel);
	underPanel.add(boxPanel);
	underPanel.add(gameItemPanel);
	underPanel.add(playerInfoPanel);

	monster.addActionListener(e -> cLayout.show(underPanel, MONSTERPANEL));
	box.addActionListener(e -> cLayout.show(underPanel, BOXPANEL));
	gameItems.addActionListener(e -> cLayout.show(underPanel, GAMEITEMSPANEL));
	playerInfo.addActionListener(e -> cLayout.show(underPanel, PLAYERINFOPANEL));
	quit.addActionListener(e -> changePanel(PanelTypes.MAP_PANEL.name()));

	underPanel.add(monsterPanel, MONSTERPANEL);
	underPanel.add(boxPanel, BOXPANEL);
	underPanel.add(gameItemPanel, GAMEITEMSPANEL);
	underPanel.add(playerInfoPanel, PLAYERINFOPANEL);

	mainPanel.add(upPanel, BorderLayout.NORTH);
	mainPanel.add(underPanel, BorderLayout.CENTER);

	return mainPanel;
    }

    // TODO create new game menu (panel 5)

    private JPanel newGamePanel() {
	return new NewGamePanel(this.playerController, mainPanel);
    }

    void changePanel(String name) {
	cLayout.show(mainPanel, name);
	subPanels.get(name).requestFocusInWindow();
    }

}
