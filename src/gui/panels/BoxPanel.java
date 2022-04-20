package gui.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import controller.PlayerController;

public class BoxPanel extends JPanel {
    private static final long serialVersionUID = 67133453355728035L;
    private static final int TEAM_SIZE = 6;
    private static final int WIDTH = 400;
    private final PlayerController playerController;
    private final CardLayout cardLayout = new CardLayout();
    private List<Integer> playerMonsterIdList = new ArrayList<>();
    private List<Integer> boxMonsterIdList;
    private int idBoxMonster;
    private int idPlayerMonster;
    private final JButton exchange = new JButton("Exchange");
    private final JButton deposit = new JButton("Deposit");
    private final JButton take = new JButton("Take");
    private final JButton cancel = new JButton("Cancel");
    private int exchangeCont;

    /**
     * 
     * @param playerController the game controller.
     */
    public BoxPanel(final PlayerController playerController) {
        this.playerController = playerController;
        setButtons();
    }

    /**
     * update team and box after an operation.
     * 
     */
    private void setList() {
        playerMonsterIdList = this.playerController.getMonstersId();
        boxMonsterIdList = this.playerController.getBoxMonsters();
    }

    /**
     * set buttons action.
     * 
     */
    private void setButtons() {
        exchange.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                playerController.exchangeMonster(idPlayerMonster, idBoxMonster);
                update();
            }
        });
        deposit.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (playerController.getMonstersId().size() > 1) {
                    playerController.depositMonster(idPlayerMonster);
                } else {
                    JOptionPane.showMessageDialog(null, "You can deposit if you have more than one monster");
                }
                update();

            }
        });
        take.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (!playerController.isTeamFull()) {
                    playerController.withdrawMonster(idBoxMonster);
                } else {
                    JOptionPane.showMessageDialog(null, "You can take if your team is not full");
                }
                update();
            }
        });
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                final int result = JOptionPane.showConfirmDialog(null, "Sure cancel?", "Warning",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    update();
                }
            }
        });
    }

    /**
     * Initialize content area.
     * 
     */
    private void init() {
        this.setLayout(new BorderLayout());
        setList();
        exchangeCont = 0;
        exchange.setEnabled(false);
        deposit.setEnabled(false);
        take.setEnabled(false);

        final JPanel playerMonstersPanel = new JPanel(new GridLayout(0, 1));
        playerMonstersPanel.setPreferredSize(new Dimension(WIDTH, getPreferredSize().height));

        for (final int playerMonsterid : playerMonsterIdList) {
            playerMonstersPanel.add(monsterPanelArea(playerMonsterid, playerMonstersPanel, true));
        }
        setPanelProp(playerMonstersPanel, playerMonsterIdList.size());

        final JPanel boxPanel = new JPanel();
        boxPanel.setLayout(cardLayout);
        final List<JPanel> contentPanel = new ArrayList<>();

        for (int a = 0; a < playerController.getBoxNumbers(); a++) {
            final JPanel pagePanel = new JPanel(new GridLayout(this.playerController.getMonstersForEachBox(), 1));
            for (int b = 0; b < this.boxMonsterIdList.size(); b++) {
                pagePanel.add(monsterPanelArea(boxMonsterIdList.get(b), boxPanel, false));
            }
            contentPanel.add(pagePanel);
        }

        int index = 1;
        for (final JPanel panel : contentPanel) {
            boxPanel.add(panel, Integer.toString(index));
            index++;
        }

        this.add(topPanelArea(), BorderLayout.NORTH);
        this.add(boxPanel, BorderLayout.CENTER);
        this.add(playerMonstersPanel, BorderLayout.EAST);
        this.add(bottomPanelArea(boxPanel), BorderLayout.SOUTH);
    }

    /**
     * set the top part of content area.
     * 
     */
    private JPanel topPanelArea() {
        final JPanel topPanel = new JPanel(new GridLayout(2, 1));
        final JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        final JPanel titlePanel = new JPanel(new BorderLayout());

        final JLabel box = new JLabel(this.playerController.getCurrentBoxName());
        setLabelProp(box);
        box.setBorder(BorderFactory.createLineBorder(Color.black));
        final JLabel team = new JLabel("Team");
        setLabelProp(team);
        team.setBorder(BorderFactory.createLineBorder(Color.black));
        team.setPreferredSize(new Dimension(WIDTH, getPreferredSize().height));

        titlePanel.add(box, BorderLayout.CENTER);
        titlePanel.add(team, BorderLayout.EAST);

        buttonPanel.add(exchange);
        buttonPanel.add(deposit);
        buttonPanel.add(take);
        buttonPanel.add(cancel);

        topPanel.add(buttonPanel);
        topPanel.add(titlePanel);
        return topPanel;
    }

    /**
     * set the bottom part of content area.
     * 
     * @param boxPanel JPanel
     * 
     */
    private JPanel bottomPanelArea(final JPanel boxPanel) {
        final JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        final JButton previusPage = new JButton("Previous box");
        final JButton nextPage = new JButton("Next box");
        bottomPanel.add(previusPage);
        bottomPanel.add(nextPage);

        nextPage.addActionListener(e -> {
            this.playerController.nextBox();
            update();
            cardLayout.next(boxPanel);
        });
        previusPage.addActionListener(e -> {
            this.playerController.previousBox();
            update();
            cardLayout.previous(boxPanel);
        });
        return bottomPanel;
    }

    /**
     * set JCheckBox's properties.
     * 
     * @param checkbox JCheckBox
     */
    private void setJCheckBoxProp(final JCheckBox checkbox) {
        checkbox.setBorder(BorderFactory.createLineBorder(Color.blue));
        checkbox.setBorderPainted(true);
        checkbox.setHorizontalAlignment(SwingConstants.CENTER);
        checkbox.setVerticalAlignment(SwingConstants.CENTER);
    }

    /**
     * enable or disable panel's components.
     * 
     * @param cont      container of components
     * @param isEnabled condition for enable or disable
     */
    private void setPanelEnabled(final Container cont, final Boolean isEnabled) {
        cont.setEnabled(isEnabled);

        final Component[] components = cont.getComponents();
        for (final Component compo : components) {
            if (compo instanceof Container) {
                setPanelEnabled((Container) compo, isEnabled);
            }
            compo.setEnabled(isEnabled);
        }
    }

    /**
     * set box's monster which will be took, exchanged.
     * 
     * @param idBoxMonster Box's monster's id
     */
    private void setIdBoxMonster(final int idBoxMonster) {
        this.idBoxMonster = idBoxMonster;
    }

    /**
     * set team's monster which will be deposited, exchanged.
     * 
     * @param idPlayerMonster Player's monster's id
     */
    private void setIdPlayerMonster(final int idPlayerMonster) {
        this.idPlayerMonster = idPlayerMonster;
    }

    /**
     * set team and box area.
     * 
     * @param monsterId
     * @param monsterPanel box/team's panel
     * @param isTeam       condition that specifies which panel will be set up
     */
    private JPanel monsterPanelArea(final int monsterId, final JPanel monsterPanel, final boolean isTeam) {
        final JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        final JLabel label = new JLabel();
        label.setText(getMonsterInfo(monsterId));
        setLabelProp(label);
        panel.add(label);
        panel.add(createCheckBox(monsterId, isTeam, monsterPanel));
        return panel;

    }

    /**
     * create JCheckBox.
     * 
     * @param monsterId
     * @param isTeam
     * @param monsterPanel
     * @return
     */
    private JCheckBox createCheckBox(final int monsterId, final boolean isTeam, final JPanel monsterPanel) {
        final JCheckBox checkBoxPlayer = new JCheckBox();
        setJCheckBoxProp(checkBoxPlayer);
        checkBoxPlayer.setText(Integer.toString(monsterId));
        checkBoxPlayer.addActionListener(createJCheckBoxActionListener(isTeam, monsterPanel));
        return checkBoxPlayer;
    }

    /**
     * create ActionListener for JChecBox.
     * 
     * @param isTeam
     * @param monsterPanel
     * @return
     */
    private ActionListener createJCheckBoxActionListener(final boolean isTeam, final JPanel monsterPanel) {
        return e -> {
            final JCheckBox cb = (JCheckBox) e.getSource();
            final int result = JOptionPane.showConfirmDialog(null, "Sure?", "Warning", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                if (cb.isSelected()) {
                    if (isTeam) {
                        setIdPlayerMonster(Integer.parseInt(cb.getText()));
                        deposit.setEnabled(true);
                    } else {
                        setIdBoxMonster(Integer.parseInt(cb.getText()));
                        take.setEnabled(true);
                    }
                    setPanelEnabled(monsterPanel, false);
                    exchangeCont++;
                    if (exchangeCont == 2) {
                        exchange.setEnabled(true);
                    }
                }
            } else {
                cb.setSelected(false);
            }
        };
    }

    /**
     * get text about monster general info.
     * 
     * @return monster general info
     */
    private String getMonsterInfo(final int monsterId) {
        return "<html> Name : " + this.playerController.getMonsterNameById(monsterId) + "<br/>" + " Lv : "
                + this.playerController.getMonsterLevel(monsterId) + "<br/>" + " Hp : "
                + this.playerController.getMonsterHealth(monsterId) + "/"
                + this.playerController.getMonsterMaxHealth(monsterId) + "</html>";
    }

    /**
     * set JPanel's properties.
     * 
     * @param panel           JPanel
     * @param numberOfMonster Number of Monster present in player's team
     */
    private void setPanelProp(final JPanel panel, final int numberOfMonster) {
        int cont = TEAM_SIZE - numberOfMonster;
        while (cont > 0) {
            final JLabel label = new JLabel();
            label.setVisible(false);
            panel.add(label);
            cont--;
        }
    }

    /**
     * set JLabel's properties.
     * 
     * @param label JLabel
     */
    private void setLabelProp(final JLabel label) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }

    /**
     * update content area.
     */
    public void update() {
        this.removeAll();
        this.init();
        this.validate();
    }

}
