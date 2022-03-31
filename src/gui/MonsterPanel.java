package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MonsterPanel extends JPanel {
    private final static String MONSTERINFO1 = "monster info 1";
    private final static String MONSTERINFO2 = "monster info 2";
    private final static String MONSTERINFO3 = "monster info 3";
    private final static String MONSTERINFO4 = "monster info 4";
    private final static String MONSTERINFO5 = "monster info 5";
    private final static String MONSTERINFO6 = "monster info 6";
    private final CardLayout cardlayout = new CardLayout();

    public MonsterPanel() {
	init();
    }

    private void init() {
	this.setLayout(cardlayout);
	JPanel monsterInfoPanel = new JPanel(new GridLayout(2,3));
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
	    JLabel singlePanelJLabel = new JLabel();
	    singlePanelJLabel.setText(m);
	    singlePanelJLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    singlePanelJLabel.setVerticalAlignment(SwingConstants.CENTER);
	    singlePanelJLabel.setBorder(BorderFactory.createLineBorder(Color.blue));
	    singlePanelJLabel.addMouseListener(new MouseAdapter(){
		 public void mouseClicked(MouseEvent e)  
		    {  
		     monsterInfoPanel.show();
		    }  
	    });
	    monsterLabel.add(singlePanelJLabel);
	}
	for (JLabel j : monsterLabel) {
	    monsterInfoPanel.add(j);
	}
	
	this.add(monsterInfoPanel);

    }
}
