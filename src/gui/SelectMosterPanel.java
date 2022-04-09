package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.PlayerController;
import controller.PlayerControllerImpl;

public class SelectMosterPanel extends JPanel {
    private final PlayerController playerController;
    private final JPanel parentPanel;
    private final CardLayout cardlayout = new CardLayout();
    private String itemName;
    // private final List<String> monsterNames;

    public SelectMosterPanel(PlayerController playerController, JPanel parentPanel) {
	this.playerController = playerController;
	this.parentPanel = parentPanel;
	init();
    }

    private void init() {
	CardLayout c1 = (CardLayout) this.parentPanel.getLayout();
	this.setLayout(c1);
	JPanel containerPanel = new JPanel(new BorderLayout());
	List<String> monsterNames = new ArrayList<String>();
	monsterNames.add("AA");
	monsterNames.add("BB");
	monsterNames.add("CC");
	monsterNames.add("DD");
	monsterNames.add("EE");
	monsterNames.add("FF");
	JPanel allMonsterPanel = new JPanel(new GridLayout(6, 2));
	for (String name : monsterNames) {// nome lv vita , img , stats
	    JLabel singleMonsterLabel = new JLabel();
	    singleMonsterLabel.setText(name);
	    setLabelProp(singleMonsterLabel);

	    JButton checkButton = new JButton("USE ON THIS MONSTER");
	    checkButton.addActionListener(e -> {
		// TODO usare l'item this.playerController.useItem(this.itemName,
		// singleMonsterLabel.getText());
		System.out.println(singleMonsterLabel.getText());
		this.repaint();
		//TODO update
	    });

	    allMonsterPanel.add(singleMonsterLabel);
	    allMonsterPanel.add(checkButton);
	}

	JButton backButton = new JButton("BACK");
	backButton.addActionListener(e -> c1.show(this.parentPanel, "ITEMS"));

	containerPanel.add(allMonsterPanel, BorderLayout.CENTER);
	containerPanel.add(backButton, BorderLayout.SOUTH);
	this.add(containerPanel);

    }

    public void SetItemName(String ItemName) {
	this.itemName = ItemName;
    }

    private void setLabelProp(JLabel label) {
	label.setLayout(new BorderLayout());
	label.setBorder(BorderFactory.createLineBorder(Color.blue));
	label.setHorizontalAlignment(SwingConstants.CENTER);
	label.setVerticalAlignment(SwingConstants.CENTER);
    }

}
