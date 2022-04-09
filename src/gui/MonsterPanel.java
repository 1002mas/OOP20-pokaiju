package gui;

import java.awt.BorderLayout;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.ImagesLoader;
import controller.PlayerController;

/*public class MonsterPanel extends JPanel {

    private final CardLayout cardlayout = new CardLayout();
    private final PlayerController playerController;

    public MonsterPanel(PlayerController playerController) {
	this.playerController = playerController;
	init();
    }

    private void init() {
	this.setLayout(cardlayout);
	JPanel allMonsterPanel = new JPanel(new GridLayout(1, 6));
	List<JPanel> monsterStatsPanel = new ArrayList<>();
	List<JLabel> listMonsterLabel = new ArrayList<>();
	List<String> monsterNames = this.playerController.getMonstersNames();

	for (String name : monsterNames) {//  stats
	    MonsterInfoPanel singleMonsterPanel = new MonsterInfoPanel(this.playerController, this, name);
	    monsterStatsPanel.add(singleMonsterPanel);
	}
	for (String name : monsterNames) {// nome lv vita , img , stats
	    int index = 1;
	    JLabel singleMonsterLabel = new JLabel();
	    singleMonsterLabel.setLayout(new BorderLayout());
	    singleMonsterLabel.setText("name :" + name + " level: " + this.playerController.getMonsterLevel(name)
		    + " Hp: " + this.playerController.getHealth(name) + "/"
		    + this.playerController.getMonsterMaxHealth(name));
	    singleMonsterLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    singleMonsterLabel.setVerticalAlignment(SwingConstants.CENTER);
	    singleMonsterLabel.setBorder(BorderFactory.createLineBorder(Color.blue));

	    JButton statsButton = new JButton("STATS");
	    statsButton.addActionListener(e -> cardlayout.show(this, Integer.toString(index)));

	    singleMonsterLabel.add(statsButton, BorderLayout.SOUTH);
	    listMonsterLabel.add(singleMonsterLabel);
	}
	for (JLabel singleMonsterLabel : listMonsterLabel) {
	    allMonsterPanel.add(singleMonsterLabel);
	}

	this.add(allMonsterPanel, Integer.toString(0));

	for (JPanel p : monsterStatsPanel) {
	    int index = 1;
	    this.add(p, Integer.toString(index));
	}

    }
}*/
//codice di prova
public class MonsterPanel extends JPanel {

    private final CardLayout cardlayout = new CardLayout();
    private final PlayerController playerController;
    private final ImagesLoader imgLoad;

    public MonsterPanel(PlayerController playerController, ImagesLoader imgLoad) {
	this.playerController = playerController;
	this.imgLoad = imgLoad;
	init();
    }

    private void init() {
	this.setLayout(cardlayout);
	JPanel allMonsterPanel = new JPanel(new GridLayout(2, 3));
	List<JPanel> monsterStatsPanel = new ArrayList<>();
	// List<JLabel> listMonsterLabel = new ArrayList<>();
	List<String> monsterNames = new ArrayList<String>();
	monsterNames.add("AA");
	monsterNames.add("BB");
	monsterNames.add("CC");
	monsterNames.add("DD");
	monsterNames.add("EE");
	monsterNames.add("FF");

	for (String name : monsterNames) {// stats
	    MonsterInfoPanel monsterInfoPanel = new MonsterInfoPanel(this);
	    monsterStatsPanel.add(monsterInfoPanel);
	}

	int index = 1;
	for (String name : monsterNames) {// nome lv vita , img , stats
	    allMonsterPanel.add(setMonsterPanel(name, index));
	    index++;
	}

	this.add(allMonsterPanel, Integer.toString(0));

	index = 1;
	for (JPanel p : monsterStatsPanel) {
	    this.add(p, Integer.toString(index));
	    index++;
	}

    }

    public void changePanel(String panelName) {
	cardlayout.show(this, panelName);
    }

    private JPanel setMonsterPanel(String monsterName, int index) {
	JPanel panel = new JPanel(new BorderLayout());
	JLabel singleMonsterInfoLabel = new JLabel();
	JLabel monsterImgLabel = new JLabel();
	JButton statsButton = new JButton("STATS");
	//ImageIcon iconLogo = new ImageIcon("res/monster/bibol.png");//TODO usare imagerloader

	singleMonsterInfoLabel.setText("name :" + monsterName + " level: " + " Hp: ");
	setLabelProp(singleMonsterInfoLabel);

	monsterImgLabel.setIcon(new ImageIcon(imgLoad.getMonster("bibol")));
	setLabelProp(monsterImgLabel);

	statsButton.addActionListener(e -> cardlayout.show(this, Integer.toString(index)));

	panel.add(singleMonsterInfoLabel, BorderLayout.NORTH);
	panel.add(monsterImgLabel, BorderLayout.CENTER);
	panel.add(statsButton, BorderLayout.SOUTH);
	return panel;
    }

    public void update() {
	this.removeAll();
	init();
    }

    private void setLabelProp(JLabel label) {
	label.setBorder(BorderFactory.createLineBorder(Color.blue));
	label.setHorizontalAlignment(SwingConstants.CENTER);
	label.setVerticalAlignment(SwingConstants.CENTER);
    }
}