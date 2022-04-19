package gui.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import controller.PlayerController;
import model.gameitem.GameItemTypes;

public class SelectMosterPanel extends JPanel {
    private static final long serialVersionUID = 8185263432699574937L;
    private final PlayerController playerController;
    private final JPanel parentPanel;
    private String itemName;
    private final JButton backButton = new JButton("BACK");
    private static final int TEAM_SIZE = 6;

    /**
     * 
     * @param playerController the game controller
     * @param parentPanel      the parentPanel
     */
    public SelectMosterPanel(final PlayerController playerController, final JPanel parentPanel) {
        this.playerController = playerController;
        this.parentPanel = parentPanel;
    }

    /**
     * Initialize content area.
     * 
     */
    private void init() {
        final CardLayout c1 = (CardLayout) this.parentPanel.getLayout();
        this.setLayout(c1);
        final JPanel containerPanel = new JPanel(new BorderLayout());
        final List<Integer> monsterIds = this.playerController.getMonstersId();

        final JPanel allMonsterPanel = new JPanel(new GridLayout(0, 2));
        for (final int monsterId : monsterIds) {
            final JLabel singleMonsterLabel = new JLabel();
            singleMonsterLabel.setText(getInfoText(monsterId));
            setLabelProp(singleMonsterLabel);

            allMonsterPanel.add(singleMonsterLabel);
            allMonsterPanel.add(createButton(monsterId));
        }

        setPanelProp(allMonsterPanel, monsterIds.size());

        containerPanel.add(allMonsterPanel, BorderLayout.CENTER);
        containerPanel.add(backButton, BorderLayout.SOUTH);
        this.add(containerPanel);

    }

    /**
     * get info text.
     * 
     * @return text of statistics of monster
     */
    private String getInfoText(final int monsterId) {
        return "<html>" + "name : " + this.playerController.getMonsterNameById(monsterId) + "<br/>" + "Level : "
                + playerController.getMonsterLevel(monsterId) + "<br/>" + "Hp : "
                + playerController.getMonsterHealth(monsterId) + "/" + playerController.getMonsterMaxHealth(monsterId)
                + "</html>";
    }

    /**
     * set Item to be used.
     * 
     * @param itemName
     */
    public void setItemName(final String itemName) {
        this.itemName = itemName;
    }

    /**
     * set Panel's properties.
     * 
     * @param panel           JPanel
     * @param numberOfMonster Number of Monster present in player's team
     */
    private void setPanelProp(final JPanel panel, final int numberOfMonster) {
        int cont = TEAM_SIZE - numberOfMonster;
        while (cont > 0) {
            final JLabel label = new JLabel();
            final JButton button = new JButton();
            label.setVisible(false);
            button.setVisible(false);
            panel.add(label);
            panel.add(button);
            cont--;
        }
    }

    /**
     * 
     * @param monsterId
     * @return button
     */
    private JButton createButton(final int monsterId) {
        final JButton checkButton = new JButton("USE ON THIS MONSTER");
        checkButton.addActionListener(e -> {
            if (this.playerController.isItemPresent(itemName)) {
                if (this.playerController.getItemtype(itemName).equals(GameItemTypes.EVOLUTIONTOOL.toString())) {
                    if (this.playerController.canEvolveByItem(itemName, monsterId)) {
                        this.playerController.evolveByItem(itemName, monsterId);
                        this.playerController.useItemOnMonster(this.itemName, monsterId);
                    } else {
                        JOptionPane.showMessageDialog(null, "Can't be evolved by this Item");
                        backButton.doClick();
                    }
                } else {
                    this.playerController.useItemOnMonster(this.itemName, monsterId);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Item finished");
                backButton.doClick();
            }
            update();
        });
        return checkButton;
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

    /**
     * 
     * @return backButton
     */
    public JButton getBackButton() {
        return backButton;
    }
}
