package gui.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
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
    private final PlayerController playerController;
    private final CardLayout cardLayout = new CardLayout();
    private List<Integer> playerMonsterIdList = new ArrayList<Integer>();
    private List<Integer> boxMonsterIdList;
    private int idBoxMonster;
    private int idPlayerMonster;
    private final JButton exchange = new JButton("Exchange");
    private final JButton deposit = new JButton("Deposit");
    private final JButton take = new JButton("Take");
    private final JButton cancel = new JButton("Cancel");
    private int exchangecont;

    public BoxPanel(PlayerController playerController) {
	this.playerController = playerController;
	setButtons();
    }

    private void setList() {
	playerMonsterIdList = this.playerController.getMonstersId();
	boxMonsterIdList = this.playerController.getBoxMonsters();
    }

    private void setButtons() {
	exchange.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		playerController.exchangeMonster(idPlayerMonster, idBoxMonster);
		update();
	    }
	});
	deposit.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (playerController.getMonstersId().size() > 1) {
		    playerController.depositMonster(idPlayerMonster);
		} else {
		    JOptionPane.showMessageDialog(null, "You can deposit if you have more than one monster");
		}
		update();

	    }
	});
	take.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (!playerController.isTeamFull()) {
		    playerController.withdrawMonster(idBoxMonster);
		} else {
		    JOptionPane.showMessageDialog(null, "You can take if your team is not full");
		}
		update();
	    }
	});
	cancel.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int result = JOptionPane.showConfirmDialog(null, "Sure cancel?", "Warning", JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.YES_OPTION) {
		    update();
		}
	    }
	});
    }

    private void init() {
	this.setLayout(new BorderLayout());
	setList();
	exchangecont = 0;
	exchange.setEnabled(false);
	deposit.setEnabled(false);
	take.setEnabled(false);

	JPanel playerMonstersPanel = new JPanel(new GridLayout(0, 1));
	playerMonstersPanel.setPreferredSize(new Dimension(300, getPreferredSize().height));

	for (int playerMonsterid : playerMonsterIdList) {
	    playerMonstersPanel.add(setMonsterPanel(playerMonsterid, playerMonstersPanel, true));
	}
	setPanelProp(playerMonstersPanel, playerMonsterIdList.size());

	JPanel boxPanel = new JPanel();
	boxPanel.setLayout(cardLayout);
	List<JPanel> contentPanel = new ArrayList<JPanel>();

	for (int a = 0; a < playerController.getBoxNumbers(); a++) {
	    JPanel pagePanel = new JPanel(new GridLayout(this.playerController.getMonstersForEachBox(), 1));
	    for (int b = 0; b < this.boxMonsterIdList.size(); b++) {
		pagePanel.add(setMonsterPanel(boxMonsterIdList.get(b), boxPanel, false));
	    }
	    contentPanel.add(pagePanel);
	}

	int index = 1;
	for (JPanel panel : contentPanel) {
	    boxPanel.add(panel, Integer.toString(index));
	    index++;
	}

	this.add(setTopPanel(), BorderLayout.NORTH);
	this.add(boxPanel, BorderLayout.CENTER);
	this.add(playerMonstersPanel, BorderLayout.EAST);
	this.add(setBottomPanel(boxPanel), BorderLayout.SOUTH);
    }

    private JPanel setTopPanel() {
	JPanel topPanel = new JPanel(new GridLayout(2, 1));
	JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
	JPanel titlePanel = new JPanel(new BorderLayout());

	JLabel box = new JLabel(this.playerController.getCurrentBoxName());
	setLabelProp(box);
	box.setBorder(BorderFactory.createLineBorder(Color.black));
	JLabel team = new JLabel("Team");
	setLabelProp(team);
	team.setBorder(BorderFactory.createLineBorder(Color.black));
	team.setPreferredSize(new Dimension(300, getPreferredSize().height));

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

    private JPanel setBottomPanel(JPanel boxPanel) {
	JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
	JButton previusPage = new JButton("Previews box");
	JButton nextPage = new JButton("Next box");
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

    private void setJCheckBoxProp(JCheckBox checkbox) {
	checkbox.setBorder(BorderFactory.createLineBorder(Color.blue));
	checkbox.setBorderPainted(true);
	checkbox.setHorizontalAlignment(SwingConstants.CENTER);
	checkbox.setVerticalAlignment(SwingConstants.CENTER);
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

    private void setIdPlayerMonster(int idPlayerMonster) {
	this.idPlayerMonster = idPlayerMonster;
    }

    private JPanel setMonsterPanel(int monsterId, JPanel MonstersPanel, boolean isTeam) {
	JPanel panel = new JPanel(new GridLayout(1, 2));
	panel.setBorder(BorderFactory.createLineBorder(Color.black));
	JLabel label = new JLabel();
	label.setText(" " + this.playerController.getMonsterNameById(monsterId) + "  "
		+ this.playerController.getMonsterLevel(monsterId) + "  "
		+ this.playerController.getMonsterHealth(monsterId) + "/"
		+ this.playerController.getMonsterMaxHealth(monsterId));
	setLabelProp(label);
	JCheckBox checkBoxPlayer = new JCheckBox();
	setJCheckBoxProp(checkBoxPlayer);
	checkBoxPlayer.setText(Integer.toString(monsterId));
	checkBoxPlayer.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		JCheckBox cb = (JCheckBox) e.getSource();
		int result = JOptionPane.showConfirmDialog(null, "Sure?", "Warning", JOptionPane.YES_NO_OPTION,
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
			setPanelEnabled(MonstersPanel, false);
			exchangecont++;
			if (exchangecont == 2) {
			    exchange.setEnabled(true);
			}
		    }
		} else {
		    cb.setSelected(false);
		}
	    }
	});
	panel.add(label);
	panel.add(checkBoxPlayer);
	return panel;

    }

    private void setPanelProp(JPanel panel, int numberOfMonster) {
	int cont = 6 - numberOfMonster;
	while (cont > 0) {
	    JLabel label = new JLabel();
	    label.setVisible(false);
	    panel.add(label);
	    cont--;
	}
    }

    private void setLabelProp(JLabel label) {
	label.setHorizontalAlignment(SwingConstants.CENTER);
	label.setVerticalAlignment(SwingConstants.CENTER);
    }

    public void update() {
	this.removeAll();
	this.init();
	this.validate();
    }

}
