package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.ImagesLoader;
import controller.PlayerController;
import model.Pair;

public class GameFrame extends JFrame {
    private static final long serialVersionUID = -7927156597267134363L;
    static final String NEW_GAME_PANEL = "new game";
    static final String LOGIN_PANEL = "login panel";
    static final String MENU_PANEL = "menu";
    static final String MAP_PANEL = "map panel";
    static final String BATTLE_PANEL = "battle panel";

    private final int size;
    private final CardLayout cLayout = new CardLayout();
    private final Map<String, JPanel> subPanels = new HashMap<>();
    private final ImagesLoader imgLoad;
    private final JPanel mainPanel = new JPanel();
    private final PlayerController playerController;

    public GameFrame(PlayerController playerController) {
	this.playerController = playerController;
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(false);
	this.setContentPane(mainPanel);
	this.pack();
	this.setVisible(true);

	size = getMainPanelSize();
	imgLoad = new ImagesLoader(size, size, playerController.getMaximumBlocksInRow(),
		playerController.getMaximumBlocksInColumn());
	mainPanel.setPreferredSize(new Dimension(size, size));
	mainPanel.setBounds(0, 0, size, size);
	mainPanel.setLayout(cLayout);

	LoginPanel loginPanel = new LoginPanel(this.playerController);
	loginPanel.getContinue().addActionListener(e -> {
	    this.playerController.load();
	    // TODO load data this.playerController.load();

	    if (!subPanels.containsKey(MAP_PANEL)) {
		JPanel gamePanel = buildMapPanel();
		mainPanel.add(gamePanel, MAP_PANEL);
		subPanels.put(MAP_PANEL, gamePanel);
	    }
	    changePanel(MAP_PANEL);
	});

	JPanel newGamePanel = newGamePanel();
	loginPanel.getnewGame().addActionListener(e -> changePanel(NEW_GAME_PANEL));

	// Pannello del menu di gioco
	JPanel menuPanel = buildMenuPanel();
//TODO add BattlePanel
	loginPanel.getquitGame().addActionListener(e -> System.exit(0));

	mainPanel.add(loginPanel, LOGIN_PANEL);
	mainPanel.add(newGamePanel, NEW_GAME_PANEL);
	mainPanel.add(menuPanel, MENU_PANEL);

	subPanels.put(LOGIN_PANEL, loginPanel);
	subPanels.put(NEW_GAME_PANEL, newGamePanel);
	subPanels.put(MENU_PANEL, menuPanel);

    }

    private int getMainPanelSize() {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int s = (int) (screenSize.getHeight() > screenSize.getWidth() ? screenSize.getWidth() : screenSize.getHeight());
	s = (5 * s) / 6;
	System.out.println(s);
	return s;
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
	    if (playerController.canChangeMap()) {
		List<BufferedImage> mapImageSequence = imgLoad.getMapByID(this.playerController.getCurrentMapID());
		p.setMapImage(mapImageSequence);
		animationOn = false;
	    }
	}

	if (animationOn) {
	    topPanel.animatedMove(dir, playerController.hasPlayerMoved());
	} else {
	    topPanel.staticMove();
	}
	if (playerController.hasBattleStarted()) {
	    // TODO change to BattlePanel
	}

    }

    public boolean playerInteraction() {
	TwoLayersPanel p = (TwoLayersPanel) subPanels.get(MAP_PANEL);
	PlayerPanel topPanel = p.getTopPanel();
	Optional<String> text = playerController.interact();
	if (text.isPresent()) {
	    topPanel.showText(text.get());
	}
	return text.isPresent();
    }

    public void endPlayerInteraction() {
	TwoLayersPanel p = (TwoLayersPanel) subPanels.get(MAP_PANEL);
	PlayerPanel topPanel = p.getTopPanel();
	topPanel.hideText();
	if (playerController.hasBattleStarted()) {
	    // TODO change to BattlePanel
	}
    }

    private JPanel buildMenuPanel() {
	return new MenuPanel(playerController, imgLoad, size, this);
    }

    private JPanel newGamePanel() {
	return new NewGamePanel(this.playerController, mainPanel);
    }

    void changePanel(String name) {
	cLayout.show(mainPanel, name);
	subPanels.get(name).requestFocusInWindow();
    }

}
