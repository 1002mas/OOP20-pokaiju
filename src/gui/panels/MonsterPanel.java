package gui.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import controller.PlayerController;
import gui.ImagesLoader;

public class MonsterPanel extends JPanel {
    private static final int TEAM_SIZE = 6;
    private static final long serialVersionUID = 4370703393805503452L;
    private final CardLayout cardlayout = new CardLayout();
    private final PlayerController playerController;
    private final ImagesLoader imgLoad;

    /**
     *
     * @param playerController the game controller
     * @param imgLoad          the game ImagesLoader
     */
    public MonsterPanel(final PlayerController playerController, final ImagesLoader imgLoad) {
        this.playerController = playerController;
        this.imgLoad = imgLoad;
    }

    /**
     * Initialize content area.
     * 
     */
    private void init() {
        this.setLayout(cardlayout);
        final JPanel allMonsterPanel = new JPanel(new GridLayout(2, 3));
        final List<JPanel> monsterStatsPanel = new ArrayList<>();
        final List<Integer> monsterIds = this.playerController.getMonstersId();

        for (final Integer id : monsterIds) {
            final MonsterInfoPanel monsterInfoPanel = new MonsterInfoPanel(this, id, playerController, imgLoad);
            monsterStatsPanel.add(monsterInfoPanel);
        }

        int index = 1;
        for (final Integer id : monsterIds) {
            allMonsterPanel.add(monsterPanelArea(id, index));
            index++;
        }
        setPanelProp(allMonsterPanel, monsterIds.size());
        this.add(allMonsterPanel, Integer.toString(0));

        index = 1;
        for (final JPanel p : monsterStatsPanel) {
            this.add(p, Integer.toString(index));
            index++;
        }

    }

    /**
     * Change panel.
     * 
     * @param panelName panel which will be showed
     */
    public void changePanel(final String panelName) {
        cardlayout.show(this, panelName);
    }

    /**
     * set content area.
     * 
     * @param monsterId Monster
     * @param index     panel that contain all statistics of Monster
     */
    private JPanel monsterPanelArea(final int monsterId, final int index) {
        final JPanel panel = new JPanel(new BorderLayout());
        final JLabel singleMonsterInfoLabel = new JLabel();
        final JLabel monsterImgLabel = new JLabel();
        final JButton statsButton = new JButton("STATS");

        final String stats = "<html>" + "name : " + this.playerController.getMonsterNameById(monsterId) + "<br/>"
                + "Level : " + playerController.getMonsterLevel(monsterId) + "<br/>" + "Hp : "
                + playerController.getMonsterHealth(monsterId) + "/" + playerController.getMonsterMaxHealth(monsterId)
                + "</html>";
        singleMonsterInfoLabel.setText(stats);
        setLabelProp(singleMonsterInfoLabel);

        monsterImgLabel.setIcon(new ImageIcon(imgLoad.getMonster(this.playerController.getMonsterNameById(monsterId))));
        setLabelProp(monsterImgLabel);

        statsButton.addActionListener(e -> cardlayout.show(this, Integer.toString(index)));

        panel.add(singleMonsterInfoLabel, BorderLayout.NORTH);
        panel.add(monsterImgLabel, BorderLayout.CENTER);
        panel.add(statsButton, BorderLayout.SOUTH);
        return panel;
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
     * update content area.
     */
    public void update() {
        this.removeAll();
        init();
        this.validate();
    }

    /**
     * set JLabel's properties.
     * 
     * @param label JLabel
     */
    private void setLabelProp(final JLabel label) {
        label.setBorder(BorderFactory.createLineBorder(Color.blue));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }
}
