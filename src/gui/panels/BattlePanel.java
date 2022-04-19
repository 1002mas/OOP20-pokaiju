package gui.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.BattleController;
import controller.PlayerController;
import gui.GameFrame;
import gui.GameFrameImpl;
import gui.ImagesLoader;

public class BattlePanel extends JPanel {

    private static final long serialVersionUID = -6294780864196246092L;

    private static final String MONSTER = "monsters";
    private static final String CENTER_PANEL = "center";
    private static final String SOUTH_PANEL = "south";
    private static final String ITEM = "items";
    private static final String CHOOSE = "choose";
    private static final String MOVE = "moves";
    private static final int TWO_SECONDS = 2000;
    private static final int THREE_SECONDS = 3000;

    private final Map<String, JPanel> panelMap = new HashMap<>();
    private final JTextField actionText;
    private final JTextField playerMonster;
    private final JTextField enemyMonster;
    private final CardLayout cLayout;
    private boolean itemsFlag;
    private String itemUsed;
    private final JLabel playerMonsterImg = new JLabel();
    private final JLabel enemyMonsterImg = new JLabel();

    private BattleController ctrl;
    private PlayerController playerCtrl;

    private final ImagesLoader img;
    private final GameFrame gameFrame;

    public BattlePanel(final ImagesLoader img, final GameFrame frame) {
        this.img = img;
        this.gameFrame = frame;
        setLayout(new BorderLayout());
        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout());
        this.cLayout = new CardLayout();
        final JPanel southPanel = new JPanel();
        southPanel.setLayout(cLayout);
        this.panelMap.put(SOUTH_PANEL, southPanel);
        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        this.panelMap.put(CENTER_PANEL, centerPanel);
        final JPanel choosePanel = new JPanel();
        choosePanel.setLayout(new GridLayout());
        final JPanel movesPanel = new JPanel();
        movesPanel.setLayout(new GridLayout());
        this.panelMap.put(MOVE, movesPanel);
        final JPanel monsterPanel = new JPanel();
        monsterPanel.setLayout(new GridLayout());
        this.panelMap.put(MONSTER, monsterPanel);
        final JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new GridLayout());
        this.panelMap.put(ITEM, itemsPanel);

        this.playerMonster = new JTextField();
        this.playerMonster.setEditable(false);
        this.enemyMonster = new JTextField();
        this.enemyMonster.setEditable(false);

        topPanel.add(playerMonster);
        topPanel.add(enemyMonster);

        this.actionText = new JTextField();
        this.actionText.setEditable(false);
        centerPanel.add(actionText, BorderLayout.SOUTH);

        final JButton leftNorthButton = new JButton("Attack");
        leftNorthButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                if (ctrl.isOverOfPP()) {
                    ctrl.attackWithExtraMove();
                    checkEnemyStatus();
                } else {
                    loadMoves();
                    actionText.setText("What move do u choose?");
                    cLayout.show(southPanel, MOVE);
                }

            }

        });
        final JButton rightNorthButton = new JButton("Change Monster");
        rightNorthButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                itemsFlag = false;
                loadMonsters();
                actionText.setText("What monster do u choose?");
                cLayout.show(southPanel, MONSTER);
            }

        });
        final JButton leftSouthButton = new JButton("Equipment");
        leftSouthButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                loadItems();
                actionText.setText("What item do u choose?");
                cLayout.show(southPanel, ITEM);
            }

        });
        final JButton rightSouthButton = new JButton("Get Out");
        rightSouthButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                if (ctrl.flee()) {
                    actionText.setText("You successfully escaped");
                    paintImmediately(getBounds());
                    try {
                        Thread.sleep(TWO_SECONDS);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    gameFrame.updateView(GameFrameImpl.MAP_VIEW);

                } else {
                    actionText.setText("You failed to escaped");
                    paintImmediately(getBounds());
                    try {
                        Thread.sleep(TWO_SECONDS);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    refresh();
                }
            }
        });

        choosePanel.add(leftNorthButton);
        choosePanel.add(rightNorthButton);
        choosePanel.add(leftSouthButton);
        choosePanel.add(rightSouthButton);

        southPanel.add(choosePanel, CHOOSE);
        southPanel.add(monsterPanel, MONSTER);
        southPanel.add(movesPanel, MOVE);
        southPanel.add(itemsPanel, ITEM);
        this.cLayout.show(southPanel, CHOOSE);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);

        this.addComponentListener(new ComponentListener() {

            @Override
            public void componentShown(final ComponentEvent e) {
                start();
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
    }

    /***
     * 
     * @param ctrl       The BattleController for this battle
     * @param playerCtrl The controller of the Player
     */
    public void setBattleController(final BattleController ctrl, final PlayerController playerCtrl) {
        this.ctrl = ctrl;
        this.playerCtrl = playerCtrl;
    }

    private String getMonsterData(final int monsterId) {
        return " " + playerCtrl.getMonsterNameById(monsterId) + " " + playerCtrl.getMonsterHealth(monsterId) + "/"
                + playerCtrl.getMonsterMaxHealth(monsterId) + " HP " + "LVL." + playerCtrl.getMonsterLevel(monsterId)
                + " " + playerCtrl.getMonsterType(monsterId);

    }

    private String getEnemyData() {
        return " " + ctrl.getEnemyCurrentMonsterName() + " " + ctrl.getEnemyCurrentMonsterHp() + "/"
                + ctrl.getEnemyCurrentMonsterMaxHealth() + " HP " + "LVL." + ctrl.getEnemyCurrentMonsterLevel() + " "
                + ctrl.getEnemyCurrentMonsterType();
    }

    private void loadImg() {
        playerMonsterImg.setIcon(new ImageIcon(img.getMonster(ctrl.getPlayerCurrentMonsterName())));
        this.panelMap.get(CENTER_PANEL).add(playerMonsterImg, BorderLayout.WEST);
        enemyMonsterImg.setIcon(new ImageIcon(img.getMonster(ctrl.getEnemyCurrentMonsterName())));
        this.panelMap.get(CENTER_PANEL).add(enemyMonsterImg, BorderLayout.EAST);
        this.panelMap.get(CENTER_PANEL).repaint();
        validate();

    }

    private void loadMoves() {
        final Map<JButton, String> movesMap = new HashMap<>();
        this.panelMap.get(MOVE).removeAll();
        for (final var move : ctrl.getMoves()) {
            final JButton button = new JButton(
                    move + " " + playerCtrl.getMovePP(move, ctrl.getPlayerCurrentMonsterId()) + " PP");
            movesMap.put(button, move);
            if (this.ctrl.checkPP(move)) {
                button.setEnabled(false);
            } else {
                button.setEnabled(true);
            }
            button.addActionListener(e -> {
                ctrl.chooseMove(move);
                checkEnemyStatus();

            });
            this.panelMap.get(MOVE).add(button);
        }
        final JButton back = new JButton("Back");
        back.addActionListener(e -> {
            refresh();
        });
        this.panelMap.get(MOVE).add(back);
    }

    private void checkEnemyStatus() {

        if (!ctrl.isAlive(ctrl.getPlayerCurrentMonsterId())) {
            actionText.setText(ctrl.getPlayerCurrentMonsterName() + " is dead");
            playerMonster.setText(getMonsterData(ctrl.getPlayerCurrentMonsterId()));
            if (ctrl.isOver() && ctrl.hasPlayerLost()) {
                // ENDING BATTLE player team dead
                endingBattle("You lose...");

            } else {
                loadMonsters();
                cLayout.show(this.panelMap.get(SOUTH_PANEL), MONSTER);
            }
        } else {
            if (ctrl.isOver() && !ctrl.hasPlayerLost()) {
                // ENDING BATTLE enemy team dead
                endingBattle("You have defeated all the enemies!!");
            } else {
                refresh();

            }
        }
    }

    private void loadMonsters() {
        this.panelMap.get(MONSTER).removeAll();
        final Map<JButton, Integer> monsterMap = new HashMap<>();
        for (final var monsterId : ctrl.getPlayerTeam()) {
            final JButton button = new JButton(getMonsterData(monsterId));
            monsterMap.put(button, monsterId);
            if (!this.ctrl.isAlive(monsterId) && !this.itemsFlag) {
                button.setEnabled(false);
            }
            if (!this.itemsFlag && monsterId == this.ctrl.getPlayerCurrentMonsterId()) {
                button.setEnabled(false);
            }

            button.addActionListener(e -> {
                if (itemsFlag) {

                    ctrl.useItem(itemUsed, monsterMap.get(e.getSource()));

                    this.itemsFlag = false;
                } else {
                    ctrl.changeMonster(monsterMap.get(e.getSource()));
                    loadImg();
                }

                refresh();
            });
            this.panelMap.get(MONSTER).add(button);
        }
        if (ctrl.isAlive(ctrl.getPlayerCurrentMonsterId())) {
            final JButton back = new JButton("Back");
            back.addActionListener(e -> {
                refresh();
            });
            this.panelMap.get(MONSTER).add(back);
        }

    }

    private void loadItems() {
        this.panelMap.get(ITEM).removeAll();
        final Map<JButton, String> itemMap = new HashMap<>();
        for (final var itemName : ctrl.getAllPlayerItems()) {
            final JButton button = createItemButton(itemName, itemMap);
            itemMap.put(button, itemName);
            this.panelMap.get(ITEM).add(button);
        }
        final JButton back = new JButton("Back");
        back.addActionListener(e -> {
            refresh();
        });
        this.panelMap.get(ITEM).add(back);
    }

    private JButton createItemButton(final String itemName, final Map<JButton, String> itemMap) {
        final JButton button = new JButton(itemName + " " + ctrl.getItemNumber(itemName));
        button.addActionListener(e -> {
            this.itemUsed = itemMap.get(e.getSource());
            if (this.ctrl.isCaptureItem(itemUsed)) {
                this.ctrl.useItem(itemUsed, 0);
                if (ctrl.isEnemyCaught()) {
                    // ENDING BATTLE
                    endingBattle("You captured the enemy!!");
                } else {
                    actionText.setText("you failed the capture");
                    this.paintImmediately(getBounds());
                    try {
                        Thread.sleep(TWO_SECONDS);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    refresh();
                }
            } else {
                this.itemsFlag = true;
                loadMonsters();
                actionText.setText("What monster do u choose?");
                cLayout.show(this.panelMap.get(SOUTH_PANEL), MONSTER);
            }
        });
        return button;
    }

    private void refresh() {
        loadImg();
        this.playerMonster.setText(getMonsterData(ctrl.getPlayerCurrentMonsterId()));
        this.enemyMonster.setText(getEnemyData());
        loadMoves();
        cLayout.show(this.panelMap.get(SOUTH_PANEL), CHOOSE);
        this.actionText.setText("What do you want to do?...");

    }

    /***
     * this function load the initial data of the battle.
     */
    public void start() {

        loadImg();
        this.playerMonster.setText(getMonsterData(ctrl.getPlayerCurrentMonsterId()));
        this.enemyMonster.setText(getEnemyData());
        this.actionText.setText("What do you want to do?...");
        cLayout.show(this.panelMap.get(SOUTH_PANEL), CHOOSE);

    }

    private void endingBattle(final String text) {
        refresh();
        this.actionText.setText(text);
        this.paintImmediately(getBounds());
        try {
            Thread.sleep(THREE_SECONDS);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        this.ctrl.endingBattle();
        this.gameFrame.updateView(GameFrameImpl.MAP_VIEW);
    }

}
