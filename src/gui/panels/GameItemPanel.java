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
import javax.swing.JTextArea;

import controller.PlayerController;

public class GameItemPanel extends JPanel {
    private static final long serialVersionUID = -5473716174748395743L;
    private final PlayerController playerController;
    private final CardLayout cardlayout = new CardLayout();
    private final static int NUMBER_OF_ELEMENTS = 10;
    private final static int WIDTH = 50;
    private final int size;
    private SelectMosterPanel selection;

    /**
     * @param playerController the game controller
     * @param size
     */
    public GameItemPanel(PlayerController playerController, int size) {
	this.playerController = playerController;
	this.size = size;
	selection = new SelectMosterPanel(playerController, this);
	selection.getBackButton().addActionListener(e -> {
	    update();
	    this.cardlayout.show(this, "ITEMS");
	});
    }

    /**
     * Initialize content area
     * 
     */
    private void init() {
	this.setLayout(cardlayout);

	JPanel containerPanel = new JPanel(new BorderLayout());
	List<String> listItemsName = this.playerController.getPlayerItemsName();

	JPanel topPanel = new JPanel(new GridLayout(1, 5));
	setTopPanel(topPanel);

	JPanel subPanel = new JPanel(new GridLayout(0, 5));

	for (String itemName : listItemsName) {
	    JTextArea nameItem = new JTextArea();
	    nameItem.setPreferredSize(new Dimension(WIDTH, size / NUMBER_OF_ELEMENTS));
	    JLabel quantity = new JLabel();
	    JTextArea description = new JTextArea();
	    JLabel type = new JLabel();
	    nameItem.setEditable(false);
	    description.setEditable(false);
	    description.setLineWrap(true);
	    type.setHorizontalAlignment(JLabel.CENTER);
	    quantity.setHorizontalAlignment(JLabel.CENTER);
	    JButton useItemButton = new JButton("USE THIS ITEM");
	    setButtonProp(useItemButton, itemName);
	    nameItem.setText(itemName);
	    quantity.setText(Integer.toString(this.playerController.getItemQuantity(itemName)));
	    description.setText(this.playerController.getItemDescription(itemName));
	    type.setText(this.playerController.getItemtype(itemName).toString());
	    useItemButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    selection.update();
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
	scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	containerPanel.add(topPanel, BorderLayout.NORTH);
	containerPanel.add(scrollPane, BorderLayout.CENTER);
	this.add(containerPanel, "ITEMS");
	this.add(selection, "SELECTIONPANEL");

    }

    /**
     * set the view properties of content area
     * 
     * @param listItemsName the list of Item's name
     * @param subPanel      JPanel
     */
    private void setShowProp(List<String> listItemsName, JPanel subPanel) {
	if (NUMBER_OF_ELEMENTS > listItemsName.size()) {
	    int n = (NUMBER_OF_ELEMENTS - listItemsName.size()) * 5;
	    for (int q = 0; q < n; q++) {
		JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(50, size / NUMBER_OF_ELEMENTS));
		subPanel.add(label);
	    }
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
     * set JButton's properties
     * 
     * @param button   JButton
     * @param itemName Item which will be verified if it can be used in bag
     */
    private void setButtonProp(JButton button, String itemName) {
	button.setEnabled(this.playerController.canUseItem(itemName));
    }

    /**
     * Change panel
     * 
     * @param panelName panel which will be showed
     */
    public void changePanel(String panelName) {
	cardlayout.show(this, panelName);
    }

    /**
     * set the top part of content area
     * 
     * @param topPanel JPanel
     */
    private void setTopPanel(JPanel topPanel) {
	topPanel.setBorder(BorderFactory.createLineBorder(Color.red));
	JLabel nameLabel = new JLabel("ITEM : ");
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
