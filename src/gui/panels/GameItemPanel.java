package gui.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import controller.PlayerController;
import gui.GameFrame;

public class GameItemPanel extends JPanel {
    private static final long serialVersionUID = -5473716174748395743L;
    private final PlayerController playerController;
    private final CardLayout cardlayout = new CardLayout();
    private final static int NUMBEROFELEMENTS = 10;
    private final static int WIDTH = 50;
    private final int size;
    private final GameFrame gui;

    public GameItemPanel(PlayerController playerController, int size, GameFrame gui) {
	this.playerController = playerController;
	this.size = size;
	this.gui = gui;
	init();
    }

    private void init() {
	this.setLayout(cardlayout);

	JPanel containerPanel = new JPanel(new BorderLayout());
	List<String> listItemsName = this.playerController.getPlayerItemsName();

	JPanel topPanel = new JPanel(new GridLayout(1, 5));
	setTopPanel(topPanel);

	JPanel subPanel = new JPanel(new GridLayout(0, 5));
	SelectMosterPanel selection = new SelectMosterPanel(playerController, this, this.gui);

	for (String itemName : listItemsName) {
	    JLabel nameItem = new JLabel();
	    nameItem.setPreferredSize(new Dimension(WIDTH, size / NUMBEROFELEMENTS));
	    JLabel quantity = new JLabel();
	    JLabel description = new JLabel();
	    JLabel type = new JLabel();
	    JButton useItemButton = new JButton("USE THIS ITEM");
	    setButtonProp(useItemButton, itemName);
	    nameItem.setText(itemName);
	    quantity.setText(Integer.toString(this.playerController.getItemQuantity(itemName)));
	    description.setText(this.playerController.getItemDescription(itemName));
	    type.setText(this.playerController.getItemtype(itemName).toString());
	    useItemButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    selection.setItemName(nameItem.getText());
		    changePanel("SELECTIONPANEL");
		}
	    });
	    subPanel.add(nameItem);
	    subPanel.add(quantity);
	    subPanel.add(description);
	    subPanel.add(type);
	    subPanel.add(useItemButton);
	}
	setShowProp(listItemsName, subPanel);
	JScrollPane scrollPane = new JScrollPane(subPanel);
	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	containerPanel.add(topPanel, BorderLayout.NORTH);
	containerPanel.add(scrollPane, BorderLayout.CENTER);
	this.add(containerPanel, "ITEMS");
	this.add(selection, "SELECTIONPANEL");

    }

    private void setShowProp(List<String> listItemsName, JPanel subPanel) {
	if (NUMBEROFELEMENTS > listItemsName.size()) {
	    int n = (NUMBEROFELEMENTS - listItemsName.size()) * 5;
	    for (int q = 0; q < n; q++) {
		JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(50, size / NUMBEROFELEMENTS));
		subPanel.add(label);
	    }
	}
    }

    public void update() {
	this.removeAll();
	init();
    }

    private void setButtonProp(JButton button, String itemName) {
	button.setEnabled(this.playerController.canUseItem(itemName));
    }

    public void changePanel(String panelName) {
	cardlayout.show(this, panelName);
    }

    private void setTopPanel(JPanel topPanel) {
	topPanel.setBorder(BorderFactory.createLineBorder(Color.red));
	JLabel nameLabel = new JLabel("NAMEITEM : ");
	JLabel quantityLabel = new JLabel("QUANTITY : ");
	JLabel descriptionLabel = new JLabel("DESCRIPTION : ");
	JLabel typeLabel = new JLabel("TYPE : ");
	JLabel useLabel = new JLabel("USE");
	topPanel.add(nameLabel);
	topPanel.add(quantityLabel);
	topPanel.add(descriptionLabel);
	topPanel.add(typeLabel);
	topPanel.add(useLabel);
    }

}
