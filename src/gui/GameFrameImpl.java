package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import controller.Direction;
import controller.PlayerController;
import gui.panels.BattlePanel;
import gui.panels.LoginPanel;
import gui.panels.MenuPanel;
import gui.panels.MerchantPanel;
import gui.panels.PlayerPanel;
import gui.panels.TwoLayersPanel;

public class GameFrameImpl extends JFrame implements GameFrame {
    private static final long serialVersionUID = -7927156597267134363L;
    private static final int SPACE = 50;
    public static final String MAP_VIEW = "map panel";
    public static final String LOGIN_VIEW = "login panel";
    public static final String NEW_GAME_VIEW = "new game";
    public static final String MENU_VIEW = "menu";
    public static final String BATTLE_VIEW = "battle panel";
    public static final String MERCHANT_VIEW = "merchant panel";
    private final int size;
    private final CardLayout cLayout = new CardLayout();
    private final Map<String, JPanel> subPanels = new HashMap<>();
    private final ImagesLoader imgLoad;
    private final JPanel mainPanel = new JPanel();
    private final PlayerController playerController;

    public GameFrameImpl(PlayerController playerController) {
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Font myFont = new Font("Bold", 0, 16);
	UIDefaults defaultUI = UIManager.getDefaults();
	defaultUI.put("Button.font", myFont);
	defaultUI.put("Label.font", myFont);
	defaultUI.put("ComboBox.font", myFont);
	defaultUI.put("TextArea.font", myFont);
	defaultUI.put("TextField.font", myFont);
	this.playerController = playerController;

	size = getMainPanelSize();
	mainPanel.setPreferredSize(new Dimension(size, size));
	mainPanel.setLayout(cLayout);
	imgLoad = new ImagesLoaderImpl(size, size, playerController.getMaximumBlocksInRow(),
		playerController.getMaximumBlocksInColumn());

	LoginPanel loginPanel = new LoginPanel();

	loginPanel.getnewGame().addActionListener(e -> updateView(NEW_GAME_VIEW));
	loginPanel.getquitGame().addActionListener(e -> System.exit(0));

	JPanel newGamePanel = newGamePanel();

	mainPanel.add(loginPanel, LOGIN_VIEW);
	mainPanel.add(newGamePanel, NEW_GAME_VIEW);

	subPanels.put(LOGIN_VIEW, loginPanel);
	subPanels.put(NEW_GAME_VIEW, newGamePanel);
	this.setResizable(false);
	this.setContentPane(mainPanel);
	this.pack();
	this.setVisible(true);

    }

    /**
     * It calculates the main panel size based on the smallest screen dimension.
     * 
     * @return the main panel size
     */
    private int getMainPanelSize() {
	double percScreen = 5.0 / 6.0;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int s = (int) (screenSize.getHeight() > screenSize.getWidth() ? screenSize.getWidth() : screenSize.getHeight());
	s = (int) (s * percScreen);
	return s;
    }

    /**
     * 
     * @return the panel with map, player and npc
     */
    private JPanel buildMapPanel() {
	TwoLayersPanel mapPanel = new TwoLayersPanel(playerController, imgLoad, size, size);
	mapPanel.addKeyListener(new PlayerCommands(this));
	mapPanel.addFocusListener(new FocusListener() {
	    @Override
	    public void focusLost(FocusEvent e) {
		mapPanel.requestFocusInWindow();
	    }

	    @Override
	    public void focusGained(FocusEvent e) {
	    }
	});
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

    /**
     * It checks if a battle started and it changes to the battle view.
     * 
     * @return true if it changed to battle view
     */
    private boolean changeToBattle() {
	if (playerController.hasBattleStarted()) {
	    BattlePanel b = (BattlePanel) (this.subPanels.get(BATTLE_VIEW));
	    b.setBattleController(this.playerController.getBattleController().get(), this.playerController);
	    updateView(BATTLE_VIEW);
	    return true;
	}
	return false;
    }

    @Override
    public boolean playerInteraction() {
	TwoLayersPanel p = (TwoLayersPanel) subPanels.get(MAP_VIEW);
	PlayerPanel topPanel = p.getTopPanel();
	Optional<String> text = playerController.interact();
	if (text.isPresent()) {
	    topPanel.showText(playerController.getNpcName().get().toUpperCase() + ": " + text.get());

	}
	if (playerController.hasPlayerTriggeredEvent()) {
	    topPanel.setNpcs(this.playerController.getAllNpcs());
	}
	return text.isEmpty();
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
	JPanel panel = new JPanel(new BorderLayout());
	panel.setBorder(BorderFactory.createLineBorder(Color.green));

	JLabel nameLabel = new JLabel();
	JTextField nameField = new JTextField(10);
	nameLabel.setText("Insert name :");

	JLabel genderLabel = new JLabel();
	String[] genderText = new String[this.playerController.getGender().size()];
	for (int i = 0; i < this.playerController.getGender().size(); i++) {
	    genderText[i] = this.playerController.getGender().get(i);
	}
	JComboBox<String> gender = new JComboBox<String>(genderText);
	genderLabel.setText("Select your gender :");

	JLabel trainerNumberLabel = new JLabel();
	trainerNumberLabel.setText("Trainer number is generated randomly : ");
	JTextField trainerNumberField = new JTextField();
	trainerNumberField.setEditable(false);
	trainerNumberLabel.setEnabled(false);

	JButton postData = new JButton("CREATE");
	postData.addActionListener(e -> {
	    if (nameField.getText().equals("")) {
		JOptionPane.showMessageDialog(null, "Name can't be null", "alert", JOptionPane.WARNING_MESSAGE);
	    } else {
		playerController.createNewPlayer(nameField.getText(), gender.getSelectedItem().toString(),
			Integer.parseInt(trainerNumberField.getText()));

		JPanel gamePanel = buildMapPanel();
		JPanel menuPanel = buildMenuPanel();
		JPanel battlePanel = new BattlePanel(imgLoad, this);
		JPanel merchantPanel = new MerchantPanel(this, playerController);
		mainPanel.add(gamePanel, MAP_VIEW);
		mainPanel.add(battlePanel, BATTLE_VIEW);
		mainPanel.add(menuPanel, MENU_VIEW);
		mainPanel.add(merchantPanel, MERCHANT_VIEW);
		subPanels.put(MERCHANT_VIEW, merchantPanel);
		subPanels.put(MENU_VIEW, menuPanel);
		subPanels.put(BATTLE_VIEW, battlePanel);
		subPanels.put(MAP_VIEW, gamePanel);

		updateView(GameFrameImpl.MAP_VIEW);
	    }
	});

	JButton quitButton = new JButton("BACK TO MENU");
	quitButton.addActionListener(e -> updateView(GameFrameImpl.LOGIN_VIEW));

	JPanel topPanel = new JPanel(new FlowLayout());
	topPanel.add(quitButton, FlowLayout.LEFT);

	JPanel underPanel = new JPanel(new GridBagLayout());

	GridBagConstraints rows = new GridBagConstraints();
	rows.gridy = 1;
	rows.insets = new Insets(0, 0, SPACE, 0);
	rows.fill = GridBagConstraints.BOTH;
	underPanel.add(nameLabel, rows);
	underPanel.add(nameField, rows);
	rows.gridy++;
	underPanel.add(genderLabel, rows);
	underPanel.add(gender, rows);
	rows.gridy++;
	underPanel.add(trainerNumberLabel, rows);
	underPanel.add(trainerNumberField, rows);
	rows.gridy++;
	underPanel.add(postData, rows);

	panel.addComponentListener(new ComponentListener() {
	    @Override
	    public void componentShown(ComponentEvent e) {
		Random rand = new Random();
		int a = rand.nextInt(999999) + 100000;
		trainerNumberField.setText(Integer.toString(a));
		nameField.setText("");
	    }

	    @Override
	    public void componentResized(ComponentEvent e) {
	    }

	    @Override
	    public void componentMoved(ComponentEvent e) {
	    }

	    @Override
	    public void componentHidden(ComponentEvent e) {
	    }
	});
	panel.add(topPanel, BorderLayout.NORTH);
	panel.add(underPanel, BorderLayout.CENTER);
	return panel;
    }

    @Override
    public void updateView(String name) {
	cLayout.show(mainPanel, name);
	subPanels.get(name).requestFocusInWindow();
    }

}
