package gui.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.PlayerController;
import gui.GameFrameImpl;
import gui.ImagesLoader;

public class MenuPanel extends JPanel {
    private static final long serialVersionUID = 5503139907518499045L;
    private final ImagesLoader imgLoad;
    private final int size;
    private final GameFrameImpl gui;
    private final PlayerController playerController;
    private static final String MONSTER_PANEL = "MONSTER";
    private static final String BOX_PANEL = "BOX";
    private static final String GAME_ITEM_PANEL = "ITEMS";
    private static final String PLAYER_INFO_PANEL = "INFO";
    private final JButton monster = new JButton("MONSTER");
    private final JButton box = new JButton(" BOX ");
    private final JButton gameItems = new JButton(" BAG ");
    private final JButton playerInfo = new JButton(" PLAYERINFO ");
    private final JButton quit = new JButton(" QUIT MENU ");
    private final JButton backToMainMenu = new JButton(" BACK TO MAIN MENU ");

    /**
     * 
     * @param playerController
     * @param imgLoad
     * @param size
     * @param gui
     */
    public MenuPanel(final PlayerController playerController, final ImagesLoader imgLoad, final int size,
            final GameFrameImpl gui) {
        this.playerController = playerController;
        this.imgLoad = imgLoad;
        this.size = size;
        this.gui = gui;
        init();
    }

    /**
     * initialize menu panel.
     */
    private void init() {
        final CardLayout cLayout = (CardLayout) this.gui.getContentPane().getLayout();

        this.setLayout(new BorderLayout());

        final JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBorder(BorderFactory.createLineBorder(Color.blue));

        topPanel.add(monster);
        topPanel.add(box);
        topPanel.add(gameItems);
        topPanel.add(playerInfo);
        topPanel.add(quit);
        topPanel.add(backToMainMenu);

        final JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        bottomPanel.setLayout(cLayout);

        final MonsterPanel monsterPanel = new MonsterPanel(this.playerController, this.imgLoad);

        final BoxPanel boxPanel = new BoxPanel(this.playerController);

        final GameItemPanel gameItemPanel = new GameItemPanel(this.playerController, size);

        final PlayerInfoPanel playerInfoPanel = new PlayerInfoPanel(this.playerController, this.gui);

        bottomPanel.add(monsterPanel);
        bottomPanel.add(boxPanel);
        bottomPanel.add(gameItemPanel);
        bottomPanel.add(playerInfoPanel);

        monster.addActionListener(e -> {
            monsterPanel.update();
            cLayout.show(bottomPanel, MONSTER_PANEL);
        });
        box.addActionListener(e -> cLayout.show(bottomPanel, BOX_PANEL));
        gameItems.addActionListener(e -> {
            gameItemPanel.update();
            cLayout.show(bottomPanel, GAME_ITEM_PANEL);
        });
        playerInfo.addActionListener(e -> {
            playerInfoPanel.update();
            cLayout.show(bottomPanel, PLAYER_INFO_PANEL);
        });
        quit.addActionListener(e -> {
            monsterPanel.changePanel(Integer.toString(0));
            gameItemPanel.changePanel(GAME_ITEM_PANEL);
            gui.updateView(GameFrameImpl.MAP_VIEW);
        });

        backToMainMenu.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                final int result = JOptionPane.showConfirmDialog(null, "Sure? You want to exit?", "Warning",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    gui.updateView(GameFrameImpl.LOGIN_VIEW);
                }
            }
        });

        bottomPanel.add(monsterPanel, MONSTER_PANEL);
        bottomPanel.add(boxPanel, BOX_PANEL);
        bottomPanel.add(gameItemPanel, GAME_ITEM_PANEL);
        bottomPanel.add(playerInfoPanel, PLAYER_INFO_PANEL);

        this.addComponentListener(new ComponentListener() {

            @Override
            public void componentShown(final ComponentEvent e) {
                monsterPanel.update();
                gameItemPanel.update();
                playerInfoPanel.update();
                boxPanel.update();
            }

            @Override
            public void componentResized(final ComponentEvent e) {
            }

            @Override
            public void componentMoved(final ComponentEvent e) {
            }

            @Override
            public void componentHidden(final ComponentEvent e) {
            }
        });
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);

    }
}
