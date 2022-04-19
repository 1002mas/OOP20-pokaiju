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
    private JPanel parentPanel;
    private String itemName;
    private JButton backButton = new JButton("BACK");

    /**
     * 
     * @param playerController the game controller
     * @param parentPanel      the parentPanel
     */
    public SelectMosterPanel(PlayerController playerController, JPanel parentPanel) {
	this.playerController = playerController;
	this.parentPanel = parentPanel;
    }

    /**
     * Initialize content area
     * 
     */
    private void init() {
	CardLayout c1 = (CardLayout) this.parentPanel.getLayout();
	this.setLayout(c1);
	JPanel containerPanel = new JPanel(new BorderLayout());
	List<Integer> monsterIds = this.playerController.getMonstersId();

	JPanel allMonsterPanel = new JPanel(new GridLayout(0, 2));
	for (int monsterId : monsterIds) {
	    JLabel singleMonsterLabel = new JLabel();
	    String stats = "<html>" + "name : " + this.playerController.getMonsterNameById(monsterId) + "<br/>"
		    + "Level : " + playerController.getMonsterLevel(monsterId) + "<br/>" + "Hp : "
		    + playerController.getMonsterHealth(monsterId) + "/"
		    + playerController.getMonsterMaxHealth(monsterId) + "</html>";
	    singleMonsterLabel.setText(stats);
	    setLabelProp(singleMonsterLabel);

	    JButton checkButton = new JButton("USE ON THIS MONSTER");
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
	    allMonsterPanel.add(singleMonsterLabel);
	    allMonsterPanel.add(checkButton);
	}

	setPanelProp(allMonsterPanel, monsterIds.size());

	containerPanel.add(allMonsterPanel, BorderLayout.CENTER);
	containerPanel.add(backButton, BorderLayout.SOUTH);
	this.add(containerPanel);

    }

    /**
     * set Item to be used
     * 
     * @param ItemName
     */
    public void setItemName(String ItemName) {
	this.itemName = ItemName;
    }

    /**
     * set Panel's properties
     * 
     * @param panel           JPanel
     * @param numberOfMonster Number of Monster present in player's team
     */
    private void setPanelProp(JPanel panel, int numberOfMonster) {
	int cont = 6 - numberOfMonster;
	while (cont > 0) {
	    JLabel label = new JLabel();
	    JButton button = new JButton();
	    label.setVisible(false);
	    button.setVisible(false);
	    panel.add(label);
	    panel.add(button);
	    cont--;
	}
    }

    /**
     * update content area
     */
    public void update() {
	this.removeAll();
	init();
	this.validate();
    }

    /**
     * set JLabel's properties
     * 
     * @param label JLabel
     */
    private void setLabelProp(JLabel label) {
	label.setBorder(BorderFactory.createLineBorder(Color.blue));
	label.setHorizontalAlignment(SwingConstants.CENTER);
	label.setVerticalAlignment(SwingConstants.CENTER);
    }

    /**
     * get JButton
     * 
     * return backButton
     */
    public JButton getBackButton() {
	return backButton;
    }
}
