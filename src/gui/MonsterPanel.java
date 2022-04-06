package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MonsterPanel extends JPanel {

    private final CardLayout cardlayout = new CardLayout();

    public MonsterPanel() {
	init();
    }

    private void init() {
	this.setLayout(cardlayout);
	JPanel allMonsterPanel = new JPanel(new GridLayout(1,6));
	List<JPanel> monsterInfoPanel = new ArrayList<>();
	List<JLabel> monsterLabel = new ArrayList<>();
	List<String> listTry = new ArrayList<>();
	listTry.add("ciao");
	listTry.add("cia");
	listTry.add("ci");
	listTry.add("c");
	listTry.add("c");
	listTry.add("luca");
	/*
	 * for(Monster m : this.playerController.getPlayer().allMonster()) { JLabel
	 * singlePanelJLabel = new JLabel(); singlePanelJLabel.setText(m.getName());
	 * monsterLabel.add(singlePanelJLabel); }
	 */
	for (String m : listTry) {
	    JPanel newPanel = new JPanel(new FlowLayout());
	    JButton backButton = new JButton("Back");
	    JLabel prova = new JLabel("ciao");
	    backButton.addActionListener(e-> cardlayout.show(this,Integer.toString(0)));
	    newPanel.add(backButton);
	    newPanel.add(prova);   
	    monsterInfoPanel.add(newPanel);
	}
	for (String m : listTry) {
	    int index = 1;
	    JLabel singlePanelJLabel = new JLabel();
	    singlePanelJLabel.setLayout(new FlowLayout());
	    JButton singleButton = new JButton("STATS");
	    singleButton.addActionListener(e -> cardlayout.show(this, Integer.toString(index)));
	    singlePanelJLabel.setText(m);
	    singlePanelJLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    singlePanelJLabel.setVerticalAlignment(SwingConstants.CENTER);
	    singlePanelJLabel.setBorder(BorderFactory.createLineBorder(Color.blue));
	    singlePanelJLabel.add(singleButton);
	    monsterLabel.add(singlePanelJLabel);
	}
	for (JLabel j : monsterLabel) {
	    allMonsterPanel.add(j);
	}
	this.add(allMonsterPanel,Integer.toString(0));

	for (JPanel p : monsterInfoPanel) {
	    int index = 1;
	    this.add(p, Integer.toString(index));
	}

    }
}
