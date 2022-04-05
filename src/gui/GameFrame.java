package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.ImagesLoader;
import controller.PlayerController;
import model.JTableModel;
import model.Pair;
import model.gameitem.*;
import model.monster.*;

public class GameFrame extends JFrame {
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
	loginPanel.getContinue().addActionListener(e -> changePanel(PanelTypes.MAP_PANEL.name()));

	// Pannello di quando inizio un nuovo gioco
	JPanel newGamePanel = newGamePanel();
	loginPanel.getnewGame().addActionListener(e -> changePanel(PanelTypes.NEW_GAME_PANEL.name()));
	// Pannello del menu di gioco
	JPanel menuPanel = buildMenuPanel();

	loginPanel.getquitGame().addActionListener(e -> System.exit(0));

	mainPanel.add(loginPanel, PanelTypes.LOGIN_PANEL.name());
	mainPanel.add(newGamePanel, PanelTypes.NEW_GAME_PANEL.name());
	mainPanel.add(gamePanel, PanelTypes.MAP_PANEL.name());
	mainPanel.add(menuPanel, PanelTypes.MENU_PANEL.name());

	subPanels.put(PanelTypes.LOGIN_PANEL.name(), loginPanel);
	subPanels.put(PanelTypes.NEW_GAME_PANEL.name(), newGamePanel);
	subPanels.put(PanelTypes.MAP_PANEL.name(), gamePanel);
	subPanels.put(PanelTypes.MENU_PANEL.name(), menuPanel);
	this.setContentPane(mainPanel);
	this.setVisible(true);
    }

    private JPanel buildMapPanel() {
	// TODO use current player position
	PlayerPanel topPanel = new PlayerPanel(playerController.getPlayerPosition(), imgLoad);
	topPanel.setPlayerImage(new ImageIcon(imgLoad.getPlayerImages(Direction.DOWN).get(0)));

	JPanel bottomPanel = new JPanel();
	bottomPanel.setOpaque(true);
	// TODO get Map size
	// TODO get Map image
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
	PlayerPanel topPanel = (PlayerPanel) (subPanels.get(PanelTypes.MAP_PANEL.name()).getComponent(0));
	// TODO use controller player
	if (playerController.canPassThrough(dir)) {
	    changePlayerPosition(dir);
	    topPanel.setNextPosition(playerPos);
	    playerController.movePlayer(dir);
	}
	topPanel.animatedMove(dir, true);
	if (playerController.canChangeMap(dir)) {
	    // TODO get Position in new Map
	    // TODO set view Position in new Map
	    // TODO get Map id
	    // TODO load map image by id
	}
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
