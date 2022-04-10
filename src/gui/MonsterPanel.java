package gui;

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

import controller.ImagesLoader;
import controller.PlayerController;

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
	List<Integer> monsterIds = this.playerController.getMonstersId();

	for (Integer id : monsterIds) {// stats
	    MonsterInfoPanel monsterInfoPanel = new MonsterInfoPanel(this, id, playerController, imgLoad);
	    monsterStatsPanel.add(monsterInfoPanel);
	}

	int index = 1;
	for (Integer id : monsterIds) {// nome lv vita , img , stats
	    allMonsterPanel.add(setMonsterPanel(id, index));
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

    private JPanel setMonsterPanel(int monsterId, int index) {
	JPanel panel = new JPanel(new BorderLayout());
	JLabel singleMonsterInfoLabel = new JLabel();
	JLabel monsterImgLabel = new JLabel();
	JButton statsButton = new JButton("STATS");

	String stats = "<html>" + "name : " + this.playerController.getMonsterNameById(monsterId) + "<br/>" + "Level : "
		+ playerController.getMonsterLevel(monsterId) + "<br/>" + "Hp : "
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