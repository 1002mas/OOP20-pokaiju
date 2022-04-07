package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.ImagesLoader;
import controller.PlayerController;
import model.Pair;

public class GameFrame extends JFrame {
    private static final int HEIGHT = 1280;
    private static final int WIDTH = 720;
    private static final long serialVersionUID = -7927156597267134363L;
    static final String NEW_GAME_PANEL = "new game";
    static final String MAP_PANEL = "map game";
    static final String MENU_PANEL = "menu";
    static final String LOGIN_PANEL = "login panel";

    private final CardLayout cLayout = new CardLayout();
    private final Map<String, JPanel> subPanels = new HashMap<>();
    private final ImagesLoader imgLoad = new ImagesLoader();
    private final JPanel mainPanel = new JPanel();
    private final PlayerController playerController;

    public GameFrame(PlayerController playerController) {
	this.playerController = playerController;
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(false);

	mainPanel.setLayout(cLayout);

	LoginPanel loginPanel = new LoginPanel(this.playerController);

	// Pannello di quando clicco continua gioco
	loginPanel.getContinue().addActionListener(e -> {

	    if (!subPanels.containsKey(MAP_PANEL)) {
		JPanel gamePanel = buildMapPanel();
		mainPanel.add(gamePanel, MAP_PANEL);
		subPanels.put(MAP_PANEL, gamePanel);
	    }
	    changePanel(MAP_PANEL);

	    // mapPanel.requestFocusInWindow();

	});

	// Pannello di quando inizio un nuovo gioco
	JPanel newGamePanel = newGamePanel();
	loginPanel.getnewGame().addActionListener(e -> changePanel(NEW_GAME_PANEL));
	// Pannello del menu di gioco
	JPanel menuPanel = buildMenuPanel();

	loginPanel.getquitGame().addActionListener(e -> System.exit(0));

	mainPanel.add(loginPanel, LOGIN_PANEL);
	mainPanel.add(newGamePanel, NEW_GAME_PANEL);

	mainPanel.add(menuPanel, MENU_PANEL);

	subPanels.put(LOGIN_PANEL, loginPanel);
	subPanels.put(NEW_GAME_PANEL, newGamePanel);
	subPanels.put(MENU_PANEL, menuPanel);

	mainPanel.setBounds(0, 0, WIDTH, HEIGHT);
	this.setContentPane(mainPanel);
	this.pack();
	this.setVisible(true);
    }

    private JPanel buildMapPanel() {
	TwoLayersPanel mapPanel = new TwoLayersPanel(playerController, imgLoad, this.getHeight(), this.getWidth());
	mapPanel.addKeyListener(new PlayerCommands(this));
	return mapPanel;
    }

    public void movePlayer(Direction dir) {
	TwoLayersPanel p = (TwoLayersPanel) subPanels.get(MAP_PANEL);
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

	JPanel topPanel = new JPanel(new FlowLayout());
	topPanel.setBorder(BorderFactory.createLineBorder(Color.blue));

	final JButton monster = new JButton("MONSTER");
	final JButton box = new JButton(" BOX ");
	final JButton gameItems = new JButton(" BAG ");
	final JButton playerInfo = new JButton(" PLAYERINFO ");
	final JButton quit = new JButton(" QUIT MENU ");
	final JButton backToMainMenu = new JButton(" BACK TO MAIN MENU ");
	final JButton save = new JButton(" SAVE ");

	topPanel.add(monster);
	topPanel.add(box);
	topPanel.add(gameItems);
	topPanel.add(playerInfo);
	topPanel.add(quit);
	topPanel.add(save);
	topPanel.add(backToMainMenu);

	JPanel bottomPanel = new JPanel();
	bottomPanel.setBorder(BorderFactory.createLineBorder(Color.red));
	bottomPanel.setLayout(cLayout);

	MonsterPanel monsterPanel = new MonsterPanel(this.mainPanel);

	JPanel boxPanel = new JPanel();
	JLabel boxLabel = new JLabel();
	boxPanel.add(boxLabel);

	GameItemPanel gameItemPanel = new GameItemPanel(this.playerController);

	PlayerInfoPanel playerInfoPanel = new PlayerInfoPanel(this.playerController);

	bottomPanel.add(monsterPanel);
	bottomPanel.add(boxPanel);
	bottomPanel.add(gameItemPanel);
	bottomPanel.add(playerInfoPanel);

	monster.addActionListener(e -> cLayout.show(bottomPanel, MONSTERPANEL));
	box.addActionListener(e -> cLayout.show(bottomPanel, BOXPANEL));
	gameItems.addActionListener(e -> cLayout.show(bottomPanel, GAMEITEMSPANEL));
	playerInfo.addActionListener(e -> cLayout.show(bottomPanel, PLAYERINFOPANEL));
	quit.addActionListener(e -> changePanel(MAP_PANEL));
	save.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// TODO salvataggio
	    }
	});
	backToMainMenu.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int result = JOptionPane.showConfirmDialog(null, "Sure? You want to exit?", "Warning",
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.YES_OPTION) {
		    changePanel(LOGIN_PANEL);
		} else if (result == JOptionPane.NO_OPTION) {

		} else {

		}
	    }

	});

	bottomPanel.add(monsterPanel, MONSTERPANEL);
	bottomPanel.add(boxPanel, BOXPANEL);
	bottomPanel.add(gameItemPanel, GAMEITEMSPANEL);
	bottomPanel.add(playerInfoPanel, PLAYERINFOPANEL);

	mainPanel.add(topPanel, BorderLayout.NORTH);
	mainPanel.add(bottomPanel, BorderLayout.CENTER);

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
