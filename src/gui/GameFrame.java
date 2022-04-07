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
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    static final String NEW_GAME_PANEL = "new game";
    static final String MAP_PANEL = "map game";
    static final String MENU_PANEL = "menu";
    static final String LOGIN_PANEL = "login panel";

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

	LoginPanel loginPanel = new LoginPanel(this.playerController);

	// Pannello di quando clicco continua gioco
	JPanel gamePanel = buildMapPanel();
	loginPanel.getContinue().addActionListener(e -> {
	    changePanel(MAP_PANEL);
	    System.out.println(gamePanel.hasFocus());
	    System.out.println(gamePanel.isFocusable());
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
