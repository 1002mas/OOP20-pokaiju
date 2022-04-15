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

import controller.Direction;
import controller.PlayerController;
import gui.panels.BattlePanel;
import gui.panels.EvolutionPanel;
import gui.panels.LoginPanel;
import gui.panels.MenuPanel;
import gui.panels.NewGamePanel;
import gui.panels.PlayerPanel;
import gui.panels.TwoLayersPanel;

public class GameFrameImpl extends JFrame implements GameFrame {
    public static final String MAP_VIEW = "map panel";
    public static final String LOGIN_VIEW = "login panel";
    public static final String EVOLVE_PANEL = "evolve panel";

    private static final long serialVersionUID = -7927156597267134363L;
    private static final String NEW_GAME_VIEW = "new game";
    private static final String MENU_VIEW = "menu";
    private static final String BATTLE_VIEW = "battle panel";
    private static final String MERCHANT_VIEW = "merchant panel";

    private final int size;
    private final CardLayout cLayout = new CardLayout();
    private final Map<String, JPanel> subPanels = new HashMap<>();
    private final ImagesLoader imgLoad;
    private final JPanel mainPanel = new JPanel();
    private final PlayerController playerController;

    public GameFrameImpl(PlayerController playerController) {
	this.playerController = playerController;
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(false);
	this.setContentPane(mainPanel);

	size = getMainPanelSize();
	imgLoad = new ImagesLoaderImpl(size, size, playerController.getMaximumBlocksInRow(),
		playerController.getMaximumBlocksInColumn());
	mainPanel.setPreferredSize(new Dimension(size, size));
	mainPanel.setBounds(0, 0, size, size);
	mainPanel.setLayout(cLayout);

	LoginPanel loginPanel = new LoginPanel();

	if (!subPanels.containsKey(MAP_VIEW)) {
	    JPanel gamePanel = buildMapPanel();
	    mainPanel.add(gamePanel, MAP_VIEW);
	    subPanels.put(MAP_VIEW, gamePanel);
	}

	loginPanel.getnewGame().addActionListener(e -> updateView(NEW_GAME_VIEW));
	loginPanel.getquitGame().addActionListener(e -> System.exit(0));

	JPanel evolvePanel = new EvolutionPanel(playerController, this, imgLoad);

	JPanel newGamePanel = newGamePanel();

	JPanel menuPanel = buildMenuPanel();

	JPanel battlePanel = new BattlePanel(imgLoad, this);

	mainPanel.add(loginPanel, LOGIN_VIEW);
	mainPanel.add(newGamePanel, NEW_GAME_VIEW);
	mainPanel.add(menuPanel, MENU_VIEW);
	mainPanel.add(battlePanel, BATTLE_VIEW);
	mainPanel.add(evolvePanel, EVOLVE_PANEL);
	mainPanel.add(battlePanel, BATTLE_VIEW);

	subPanels.put(LOGIN_VIEW, loginPanel);
	subPanels.put(NEW_GAME_VIEW, newGamePanel);
	subPanels.put(MENU_VIEW, menuPanel);
	subPanels.put(BATTLE_VIEW, battlePanel);
	subPanels.put(EVOLVE_PANEL, evolvePanel);
	subPanels.put(BATTLE_VIEW, battlePanel);

	this.pack();
	this.setVisible(true);

    }

    private int getMainPanelSize() {
	double percScreen = 5 / 6;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int s = (int) (screenSize.getHeight() > screenSize.getWidth() ? screenSize.getWidth() : screenSize.getHeight());
	s = (int) (s * percScreen);
	return s;
    }

    private JPanel buildMapPanel() {
	TwoLayersPanel mapPanel = new TwoLayersPanel(playerController, imgLoad, this.getHeight(), this.getWidth());
	mapPanel.addKeyListener(new PlayerCommands(this));
	return mapPanel;
    }

    @Override
    public void movePlayer(Direction dir) {
	TwoLayersPanel p = (TwoLayersPanel) subPanels.get(MAP_VIEW);
	PlayerPanel topPanel = p.getTopPanel();
	boolean animationOn = true;
	boolean canPlayerMove = this.playerController.movePlayer(dir);
	if (canPlayerMove) {
	    topPanel.setNextPosition(this.playerController.getPlayerPosition());
	    if (playerController.hasPlayerChangedMap()) {
		List<BufferedImage> mapImageSequence = imgLoad.getMapByID(this.playerController.getCurrentMapID());
		p.setMapImage(mapImageSequence);
		topPanel.setNpcs(this.playerController.getAllNpcs());
		animationOn = false;
	    }
	}

	if (animationOn) {
	    topPanel.animatedMove(dir, canPlayerMove);
	} else {
	    topPanel.staticMove();
	}
	changeToBattle();
	if (playerController.hasPlayerTriggeredEvent()) {
	    topPanel.setNpcs(this.playerController.getAllNpcs());
	}
    }

    private void changeToBattle() {
	if (playerController.hasBattleStarted()) {
	    BattlePanel b = (BattlePanel) (this.subPanels.get(BATTLE_VIEW));
	    b.setBattleController(this.playerController.getBattleController().get(), this.playerController);
	    updateView(BATTLE_VIEW);
	}
    }

    @Override
    public boolean playerInteraction() {
	TwoLayersPanel p = (TwoLayersPanel) subPanels.get(MAP_VIEW);
	PlayerPanel topPanel = p.getTopPanel();
	Optional<String> text = playerController.interact();
	if (text.isPresent()) {
	    topPanel.showText(text.get());
	}
	if (playerController.hasPlayerTriggeredEvent()) {
	    topPanel.setNpcs(this.playerController.getAllNpcs());
	}
	return text.isPresent();
    }

    @Override
    public void endPlayerInteraction() {
	TwoLayersPanel p = (TwoLayersPanel) subPanels.get(MAP_VIEW);
	PlayerPanel topPanel = p.getTopPanel();
	topPanel.hideText();
	changeToBattle();
	if (this.playerController.hasMerchantInteractionOccurred()) {
	    updateView(MERCHANT_VIEW);
	}
    }

    private JPanel buildMenuPanel() {
	return new MenuPanel(playerController, imgLoad, size, this);
    }

    private JPanel newGamePanel() {
	return new NewGamePanel(this.playerController, mainPanel, this);
    }

    public void updateView(String name) {
	cLayout.show(mainPanel, name);
	subPanels.get(name).requestFocusInWindow();
    }

}
