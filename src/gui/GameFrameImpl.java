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
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

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
    /**
     * Constant used in {@link #updateView(String) updateView(String)} to change to
     * Map view.
     */
    public static final String MAP_VIEW = "map panel";
    /**
     * Constant used in {@link #updateView(String) updateView(String)} to change to
     * main menu view.
     */
    public static final String LOGIN_VIEW = "login panel";
    /**
     * Constant used in {@link #updateView(String) updateView(String)} to change to
     * new character view.
     */
    public static final String NEW_GAME_VIEW = "new game";
    /**
     * Constant used in {@link #updateView(String) updateView(String)} to change to
     * game menu view.
     */
    public static final String MENU_VIEW = "menu";
    /**
     * Constant used in {@link #updateView(String) updateView(String)} to change to
     * battle view.
     */
    public static final String BATTLE_VIEW = "battle panel";
    /**
     * Constant used in {@link #updateView(String) updateView(String)} to change to
     * merchant shop view.
     */
    public static final String MERCHANT_VIEW = "merchant panel";

    private static final long serialVersionUID = -7927156597267134363L;
    private static final int SPACE = 50;
    private final int size;
    private final CardLayout cLayout = new CardLayout();
    private final Map<String, JPanel> subPanels = new HashMap<>();
    private final transient ImagesLoader imgLoad;
    private final JPanel mainPanel = new JPanel();
    private final transient PlayerController playerController;

    public GameFrameImpl(final PlayerController playerController) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("POKAIJU");
        final Font myFont = new Font("Bold", 0, 16);
        final UIDefaults defaultUI = UIManager.getDefaults();
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

        final LoginPanel loginPanel = new LoginPanel();

        loginPanel.getnewGame().addActionListener(e -> updateView(NEW_GAME_VIEW));
        loginPanel.getquitGame().addActionListener(e -> dispose());

        final JPanel newGamePanel = newGamePanel();

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
        final double percScreen = 5.0 / 6.0;
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int s = (int) (screenSize.getHeight() > screenSize.getWidth() ? screenSize.getWidth() : screenSize.getHeight());
        s = (int) (s * percScreen);
        return s;
    }

    /**
     * 
     * @return the panel with map, player and npc
     */
    private JPanel buildMapPanel() {
        final TwoLayersPanel mapPanel = new TwoLayersPanel(playerController, imgLoad, size, size);
        mapPanel.setMapImage(imgLoad.getMapByID(playerController.getCurrentMapID()));
        mapPanel.addKeyListener(new PlayerCommands(this));
        mapPanel.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(final FocusEvent e) {
                mapPanel.requestFocusInWindow();
            }

            @Override
            public void focusGained(final FocusEvent e) {
            }
        });
        return mapPanel;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void movePlayer(final Direction dir) {
        final TwoLayersPanel p = (TwoLayersPanel) subPanels.get(MAP_VIEW);
        final PlayerPanel topPanel = p.getTopPanel();
        boolean animationOn = true;
        final boolean canPlayerMove = this.playerController.movePlayer(dir);
        if (canPlayerMove) {
            topPanel.setNextPosition(this.playerController.getPlayerPosition());
            if (playerController.hasPlayerChangedMap()) {
                final List<BufferedImage> mapImageSequence = imgLoad
                        .getMapByID(this.playerController.getCurrentMapID());
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
            final BattlePanel b = (BattlePanel) (this.subPanels.get(BATTLE_VIEW));
            b.setBattleController(this.playerController.getBattleController().get(), this.playerController);
            updateView(BATTLE_VIEW);
            return true;
        }
        return false;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean playerInteraction() {
        final TwoLayersPanel p = (TwoLayersPanel) subPanels.get(MAP_VIEW);
        final PlayerPanel topPanel = p.getTopPanel();
        final Optional<String> text = playerController.interact();
        if (text.isPresent()) {
            String npcName = playerController.getNpcName().get();
            npcName = npcName.toUpperCase(Locale.ENGLISH);
            topPanel.showText(npcName + ": " + text.get());

        }
        return text.isEmpty();
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void endPlayerInteraction() {
        final TwoLayersPanel p = (TwoLayersPanel) subPanels.get(MAP_VIEW);
        final PlayerPanel topPanel = p.getTopPanel();
        topPanel.hideText();
        changeToBattle();
        if (playerController.hasPlayerTriggeredEvent()) {
            topPanel.setNpcs(this.playerController.getAllNpcs());
        }
        if (this.playerController.hasMerchantInteractionOccurred()) {
            updateView(MERCHANT_VIEW);
        }
    }

    private JPanel buildMenuPanel() {
        return new MenuPanel(playerController, imgLoad, size, this);
    }

    private JPanel newGamePanel() {
        final JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.green));

        final JLabel nameLabel = new JLabel();
        final JTextField nameField = new JTextField(10);
        nameLabel.setText("Insert name :");

        final JLabel genderLabel = new JLabel();
        String[] genderText = new String[this.playerController.getGender().size()];
        for (int i = 0; i < this.playerController.getGender().size(); i++) {
            genderText[i] = this.playerController.getGender().get(i);
        }
        final JComboBox<String> gender = new JComboBox<>(genderText);
        genderLabel.setText("Select your gender :");

        final JLabel trainerNumberLabel = new JLabel();
        trainerNumberLabel.setText("Trainer number is generated randomly : ");
        final JTextField trainerNumberField = new JTextField();
        trainerNumberField.setEditable(false);
        trainerNumberLabel.setEnabled(false);

        final JButton postData = new JButton("CREATE");
        postData.addActionListener(e -> {
            if ("".equals(nameField.getText())) {
                JOptionPane.showMessageDialog(null, "Name can't be null", "alert", JOptionPane.WARNING_MESSAGE);
            } else {
                playerController.createNewPlayer(nameField.getText(), gender.getSelectedItem().toString(),
                        Integer.parseInt(trainerNumberField.getText()));

                final JPanel gamePanel = buildMapPanel();
                final JPanel menuPanel = buildMenuPanel();
                final JPanel battlePanel = new BattlePanel(imgLoad, this);
                final JPanel merchantPanel = new MerchantPanel(this, playerController);
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

        final JButton quitButton = new JButton("BACK TO MENU");
        quitButton.addActionListener(e -> updateView(GameFrameImpl.LOGIN_VIEW));

        final JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(quitButton, FlowLayout.LEFT);

        final JPanel underPanel = new JPanel(new GridBagLayout());

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
            public void componentShown(final ComponentEvent e) {
                final int a = ThreadLocalRandom.current().nextInt(999_999) + 100_000;
                trainerNumberField.setText(Integer.toString(a));
                nameField.setText("");
            }

            public void componentResized(final ComponentEvent e) {
            }

            @Override
            public void componentMoved(final ComponentEvent e) {
            }

            @Override
            public void componentHidden(final ComponentEvent e) {
            }
        });
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(underPanel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateView(final String name) {
        cLayout.show(mainPanel, name);
        subPanels.get(name).requestFocusInWindow();
    }

}
