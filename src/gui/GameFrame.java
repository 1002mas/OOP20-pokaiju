package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.ImagesLoader;
import controller.PlayerController;
import model.Pair;

public class GameFrame extends JFrame {
    private static final long serialVersionUID = -7927156597267134363L;
    static final String NEW_GAME_PANEL = "new game";
    static final String MAP_PANEL = "map game";
    static final String MENU_PANEL = "menu";
    static final String LOGIN_PANEL = "login panel";

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
	// TODO get cells number from controller
	imgLoad = new ImagesLoader(size, size, 20, 20);
	mainPanel.setPreferredSize(new Dimension(size, size));
	mainPanel.setBounds(0, 0, size, size);
	mainPanel.setLayout(cLayout);

	LoginPanel loginPanel = new LoginPanel(this.playerController);
	loginPanel.getContinue().addActionListener(e -> {
	    this.playerController.load();
	    if (!subPanels.containsKey(MAP_PANEL)) {
		JPanel gamePanel = buildMapPanel();
		mainPanel.add(gamePanel, MAP_PANEL);
		subPanels.put(MAP_PANEL, gamePanel);
	    }
	    changePanel(MAP_PANEL);
	    // mapPanel.requestFocusInWindow();
	});

	JPanel newGamePanel = newGamePanel();
	loginPanel.getnewGame().addActionListener(e -> changePanel(NEW_GAME_PANEL));

	JPanel menuPanel = buildMenuPanel();
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
	    if (playerController.canChangeMap()) {// hasMapChanged...
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

    public boolean playerInteraction() {
	TwoLayersPanel p = (TwoLayersPanel) subPanels.get(MAP_PANEL);
	PlayerPanel topPanel = p.getTopPanel();
	// TODO get Text from controller
	boolean res = new Random().nextBoolean();
	if (!res) {
	    String s = "<html>";
	    for (int i = 1; i < 100; i++) {
		s += "c";
	    }
	    s += "<br>";
	    for (int i = 1; i < 100; i++) {
		s += "a";
	    }
	    s += "</html>";
	    topPanel.showText(s);
	}
	return res;
    }

    public void endPlayerInteraction() {
	TwoLayersPanel p = (TwoLayersPanel) subPanels.get(MAP_PANEL);
	PlayerPanel topPanel = p.getTopPanel();
	topPanel.hideText();
    }

    // TODO create battle scene (panel 3)
    private JPanel buildBattlePanel() {
	return new JPanel();
    }

    // TODO create menu (panel 4)
    private JPanel buildMenuPanel() {
	return new MenuPanel(playerController, imgLoad, size, this);
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
