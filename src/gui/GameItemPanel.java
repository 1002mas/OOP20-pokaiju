package gui;

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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.PlayerController;
import model.gameitem.*;

//codice di prova
public class GameItemPanel extends JPanel {
    private final PlayerController playerController;
    private final CardLayout cardlayout = new CardLayout();

    public GameItemPanel(PlayerController playerController) {
	this.playerController = playerController;
	init();
    }

    /**
     * 
     */
    private void init() {

	// List<GameItems> listGameItems = this.playerController.getItemList();
	this.setLayout(cardlayout);

	JPanel containerPanel = new JPanel(new BorderLayout());
	List<GameItems> listGameItems = new ArrayList<>();
	listGameItems.add(new GameItemImpl("Monsterball", 10, "catch 50%"));
	listGameItems.add(new HealingItem("Heal3123", 5, "heal 10as0 hp", 50));
	listGameItems.add(new HealingItem("Heal123", 1, "heal 10sda0 hp", 20));
	listGameItems.add(new GameItemImpl("Masterball", 2, "catch 100%"));
	listGameItems.add(new HealingItem("Heal", 10, "heal 100 hp"));
	listGameItems.add(new GameItemImpl("Monsterball", 10, "catch 50%"));
	listGameItems.add(new HealingItem("Heal3123", 5, "heal 10as0 hp", 50));
	listGameItems.add(new HealingItem("Heal123", 1, "heal 10sda0 hp", 20));
	listGameItems.add(new GameItemImpl("Masterball", 2, "catch 100%"));
	listGameItems.add(new HealingItem("Heal", 10, "heal 100 hp"));

	JPanel topPanel = new JPanel(new GridLayout(1, 5));
	setTopPanel(topPanel);

	// boxlayout version

	/*JPanel subPanel = new JPanel();
	BoxLayout jbL = new BoxLayout(subPanel, BoxLayout.PAGE_AXIS);
	subPanel.setLayout(jbL);

	SelectMosterPanel selection = new SelectMosterPanel(playerController, this);

	for (GameItems item : listGameItems) {
	    subPanel.add(rowPanel(item, selection));
	}*/

	// gridlayout version
	JPanel subPanel = new JPanel(new GridLayout(0, 5));
	SelectMosterPanel selection = new SelectMosterPanel(playerController, this);

	for (GameItems item : listGameItems) {
	    JLabel nameItem = new JLabel();
	    JLabel quantity = new JLabel();
	    JLabel description = new JLabel();
	    JLabel type = new JLabel();
	    JButton useItemButton = new JButton("USE THIS ITEM");
	    setButtonProp(useItemButton, item);
	    nameItem.setText(item.getNameItem());
	    quantity.setText(Integer.toString(item.getNumber()));
	    description.setText(item.getDescription());
	    type.setText(item.getType().toString());
	    useItemButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    selection.SetItemName(nameItem.getText());
		    changePanel("SELECTIONPANEL");
		}
	    });
	    subPanel.add(nameItem);
	    subPanel.add(quantity);
	    subPanel.add(description);
	    subPanel.add(type);
	    subPanel.add(useItemButton);
	}

	JScrollPane scrollPane = new JScrollPane(subPanel);
	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	containerPanel.add(topPanel, BorderLayout.NORTH);
	containerPanel.add(scrollPane, BorderLayout.CENTER);
	this.add(containerPanel, "ITEMS");
	this.add(selection, "SELECTIONPANEL");

    }

    public void update() {
	this.removeAll();
	init();
    }

    private void setButtonProp(JButton button, GameItems item) {
	if (item.getType() == GameItemTypes.MONSTERBALL) {
	    button.setEnabled(false);
	}
    }

    private void changePanel(String panelName) {
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

    private JPanel rowPanel(GameItems item, SelectMosterPanel selection) {
	// create here columns
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
	panel.setBorder(BorderFactory.createLineBorder(Color.blue));
	JLabel nameItem = new JLabel();
	JLabel quantity = new JLabel();
	JLabel description = new JLabel();
	JLabel type = new JLabel();
	JButton useItemButton = new JButton("USE THIS ITEM");
	setButtonProp(useItemButton, item);
	nameItem.setText(item.getNameItem());
	quantity.setText(Integer.toString(item.getNumber()));
	description.setText(item.getDescription());
	type.setText(item.getType().toString());
	useItemButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		selection.SetItemName(nameItem.getText());
		changePanel("SELECTIONPANEL");
	    }
	});
	panel.add(nameItem);
	panel.add(quantity);
	panel.add(description);
	panel.add(type);
	panel.add(useItemButton);
	//panel.add(Box.createRigidArea(new Dimension(0, 20)));// horizontal span
	return panel;

    }

}

/*
 * public class GameItemPanel extends JPanel { private final PlayerController
 * playerController;
 * 
 * public GameItemPanel(PlayerController playerController) {
 * this.playerController = playerController; init(); }
 * 
 * private void init() {
 * 
 * // List<GameItems> listGameItems = this.playerController.getItemList();
 * this.setLayout(new BorderLayout()); List<String> listGameItemsName =
 * this.playerController.getPlayerItemsName();
 * 
 * 
 * JPanel topPanel = new JPanel(new GridLayout(1, 5)); JPanel subPanel = new
 * JPanel(new GridLayout(0, 5));
 * 
 * topPanel.setBorder(BorderFactory.createLineBorder(Color.red));
 * 
 * JLabel nameLabel = new JLabel("NAMEITEM : "); JLabel quantityLabel = new
 * JLabel("QUANTITY : "); JLabel descriptionLabel = new
 * JLabel("DESCRIPTION : "); JLabel typeLabel = new JLabel("TYPE : "); JLabel
 * useLabel = new JLabel("USE");
 * 
 * topPanel.add(nameLabel); topPanel.add(quantityLabel);
 * topPanel.add(descriptionLabel); topPanel.add(typeLabel);
 * topPanel.add(useLabel);
 * 
 * for (String itemname : listGameItemsName) { JLabel nameItem = new JLabel();
 * JLabel quantity = new JLabel(); JLabel description = new JLabel(); JLabel
 * type = new JLabel(); JButton useItemButton = new JButton("USE THIS ITEM");
 * setButtonProp(useItemButton, itemname); nameItem.setText(itemname);
 * quantity.setText(Integer.toString(this.playerController.getItemQuantity(
 * itemname)));
 * description.setText(this.playerController.getItemDescription(itemname));
 * type.setText(this.playerController.getItemtype(itemname));
 * subPanel.add(nameItem); subPanel.add(quantity); subPanel.add(description);
 * subPanel.add(type); subPanel.add(useItemButton);
 * 
 * }
 * 
 * JScrollPane scrollPane = new JScrollPane(subPanel);
 * scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
 * 
 * this.add(topPanel, BorderLayout.NORTH); this.add(scrollPane,
 * BorderLayout.CENTER);
 * 
 * }
 * 
 * public void update() { this.removeAll(); init(); }
 * 
 * private void setButtonProp(JButton button, String itemname) { if
 * (this.playerController.getItemtype(itemname) ==
 * GameItemTypes.MONSTERBALL.name()) { button.setEnabled(false); } }
 * 
 * }
 */
