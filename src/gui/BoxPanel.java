package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.PlayerController;

public class BoxPanel extends JPanel {
    private static final int NUMBEROFMONSTERPERPAGE = 10;
    private final PlayerController playerController;
    private final CardLayout cardLayout = new CardLayout();
    private boolean boxPanelEventsEnabled = false;
    private boolean monsterPanelEventsEnabled = false;
    private List<Integer> playerMonsterIdList = new ArrayList<Integer>();
    private List<Integer> boxMonsterIdList = new ArrayList<Integer>();
    private int idBoxMonster;
    private int idPanelMonster;

    public BoxPanel(PlayerController playerController) {
	this.playerController = playerController;
	this.playerMonsterIdList = playerController.getMonstersId();
	// this.boxMonsterIdList = ; TODO get boxIDMOnsters
	init();
    }

    private void init() {
	this.setLayout(new BorderLayout());

	JPanel playerMonstersPanel = new JPanel(new GridLayout(0, 1));
	playerMonstersPanel.setPreferredSize(new Dimension(200, 500));

	for (int playerMonsterid : playerMonsterIdList) {
	    playerMonstersPanel.add(setPlayermonsterLabel(playerMonsterid, playerMonstersPanel));
	}

	JPanel boxPanel = new JPanel();
	boxPanel.setLayout(cardLayout);
	List<JPanel> contentPanel = new ArrayList<JPanel>();

	int remainelement = boxMonsterIdList.size();
	for (int a = 0; a < numberOfPage(boxMonsterIdList); a++) {
	    int moltiplicatore = NUMBEROFMONSTERPERPAGE * a;
	    JPanel pagePanel = new JPanel(new GridLayout(NUMBEROFMONSTERPERPAGE, 1));
	    for (int i = 0; i < (((remainelement - NUMBEROFMONSTERPERPAGE) > 0) ? NUMBEROFMONSTERPERPAGE
		    : remainelement); i++) {
		int boxId = boxMonsterIdList.get(i + moltiplicatore);
		pagePanel.add(setBoxmonsterLabel(boxId, boxPanel));
	    }
	    remainelement = Math.abs(remainelement - NUMBEROFMONSTERPERPAGE);
	    contentPanel.add(pagePanel);
	}

	int index = 1;
	for (JPanel panel : contentPanel) {
	    boxPanel.add(panel, Integer.toString(index));
	    index++;
	}

	JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
	JButton previusPage = new JButton("Previews box");
	JButton nextPage = new JButton("Next box");
	bottomPanel.add(previusPage);
	bottomPanel.add(nextPage);

	nextPage.addActionListener(e -> cardLayout.next(boxPanel));
	previusPage.addActionListener(e -> cardLayout.previous(boxPanel));

	JPanel topPanel = new JPanel(new GridLayout(1, 5));
	JButton exchange = new JButton("Exchange");
	JButton deposit = new JButton("Deposit");
	JButton take = new JButton("Take");
	JButton cancel = new JButton("Cancel");
	deposit.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		setPanelEnabled(boxPanel, false);
		setMonsterPanelEventsEnabled(true);
	    }
	});
	take.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		setPanelEnabled(playerMonstersPanel, false);
		setBoxPanelEventsEnabled(true);
	    }
	});
	cancel.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int result = JOptionPane.showConfirmDialog(null, "Sure cancel action?", "Warning",
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.YES_OPTION) {
		    update();
		}
	    }
	});
	topPanel.add(exchange);
	topPanel.add(deposit);
	topPanel.add(take);
	topPanel.add(cancel);

	this.add(topPanel, BorderLayout.NORTH);
	this.add(boxPanel, BorderLayout.CENTER);
	this.add(playerMonstersPanel, BorderLayout.EAST);
	this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setLabelProp(JLabel label) {
	label.setBorder(BorderFactory.createLineBorder(Color.blue));
	label.setHorizontalAlignment(SwingConstants.CENTER);
	label.setVerticalAlignment(SwingConstants.CENTER);
    }

    private void setPanelEnabled(java.awt.Container cont, Boolean isEnabled) {
	cont.setEnabled(isEnabled);

	java.awt.Component[] components = cont.getComponents();

	for (int i = 0; i < components.length; i++) {
	    if (components[i] instanceof java.awt.Container) {
		setPanelEnabled((java.awt.Container) components[i], isEnabled);
	    }
	    components[i].setEnabled(isEnabled);
	}
    }

    private void setIdBoxMonster(int idBoxMonster) {
	this.idBoxMonster = idBoxMonster;
    }

    private void setIdPanelMonster(int idPanelMonster) {
	this.idPanelMonster = idPanelMonster;
    }

    private void setBoxPanelEventsEnabled(boolean boxPanelEventsEnabled) {
	this.boxPanelEventsEnabled = boxPanelEventsEnabled;
    }

    private void setMonsterPanelEventsEnabled(boolean monsterPanelEventsEnabled) {
	this.monsterPanelEventsEnabled = monsterPanelEventsEnabled;
    }

    private JLabel setPlayermonsterLabel(int monsterId, JPanel playerMonstersPanel) {
	JLabel label = new JLabel();
	setLabelProp(label);
	label.setText(Integer.toString(monsterId));
	label.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
		if (monsterPanelEventsEnabled) {
		    int result = JOptionPane.showConfirmDialog(null, "Sure? You selected?", "Warning",
			    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    if (result == JOptionPane.YES_OPTION) {
			setPanelEnabled(playerMonstersPanel, false);
			setMonsterPanelEventsEnabled(false);
			JOptionPane.showMessageDialog(null, "Operation successful");
			update();
			// TODO deposit and update
		    }
		}
	    }
	});
	return label;
    }

    private JLabel setBoxmonsterLabel(int boxMonsterId, JPanel boxPanel) {
	JLabel label = new JLabel();
	setLabelProp(label);
	label.setText(Integer.toString(boxMonsterId));
	label.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
		if (boxPanelEventsEnabled) {
		    int result = JOptionPane.showConfirmDialog(null, "Sure? You selected?", "Warning",
			    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    if (result == JOptionPane.YES_OPTION) {
			setPanelEnabled(boxPanel, false);
			setBoxPanelEventsEnabled(false);
			JOptionPane.showMessageDialog(null, "Operation successful");
			update();
			// TODO take and update
		    }
		}
	    }
	});
	return label;
    }

    private int numberOfPage(List<Integer> boxIds) {
	return ((int) boxIds.size() / NUMBEROFMONSTERPERPAGE) + 1;
    }

    public void update() {
	this.removeAll();
	this.init();
	this.validate();
    }
}
