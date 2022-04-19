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
    private static final int NUMBER_OF_ELEMENTS = 10;
    private static final int WIDTH = 50;
    private final int size;
    private final SelectMosterPanel selection;

    /**
     * @param playerController the game controller
     * @param size
     */
    public GameItemPanel(final PlayerController playerController, final int size) {
        this.playerController = playerController;
        this.size = size;
        selection = new SelectMosterPanel(playerController, this);
        selection.getBackButton().addActionListener(e -> {
            update();
            this.cardlayout.show(this, "ITEMS");
        });
    }

    /**
     * Initialize content area.
     * 
     */
    private void init() {
        this.setLayout(cardlayout);

        final JPanel containerPanel = new JPanel(new BorderLayout());
        final List<String> listItemsName = this.playerController.getPlayerItemsName();

        final JPanel topPanel = new JPanel(new GridLayout(1, 5));
        setTopPanel(topPanel);

        final JPanel subPanel = new JPanel(new GridLayout(0, 5));

        for (final String itemName : listItemsName) {
            final JTextArea nameItem = new JTextArea();
            nameItem.setPreferredSize(new Dimension(WIDTH, size / NUMBER_OF_ELEMENTS));
            final JLabel quantity = new JLabel();
            final JTextArea description = new JTextArea();
            final JLabel type = new JLabel();
            nameItem.setEditable(false);
            description.setEditable(false);
            description.setLineWrap(true);
            type.setHorizontalAlignment(JLabel.CENTER);
            quantity.setHorizontalAlignment(JLabel.CENTER);
            final JButton useItemButton = new JButton("USE THIS ITEM");
            setButtonProp(useItemButton, itemName);
            nameItem.setText(itemName);
            quantity.setText(Integer.toString(this.playerController.getItemQuantity(itemName)));
            description.setText(this.playerController.getItemDescription(itemName));
            type.setText(this.playerController.getItemtype(itemName).toString());
            useItemButton.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
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
        final JScrollPane scrollPane = new JScrollPane(subPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        containerPanel.add(topPanel, BorderLayout.NORTH);
        containerPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(containerPanel, "ITEMS");
        this.add(selection, "SELECTIONPANEL");

    }

    /**
     * set the view properties of content area.
     * 
     * @param listItemsName the list of Item's name
     * @param subPanel      JPanel
     */
    private void setShowProp(final List<String> listItemsName, final JPanel subPanel) {
        if (NUMBER_OF_ELEMENTS > listItemsName.size()) {
            final int n = (NUMBER_OF_ELEMENTS - listItemsName.size()) * 5;
            for (int q = 0; q < n; q++) {
                final JLabel label = new JLabel();
                label.setPreferredSize(new Dimension(WIDTH, size / NUMBER_OF_ELEMENTS));
                subPanel.add(label);
            }
        }
    }

    /**
     * update content area.
     */
    public void update() {
        this.removeAll();
        init();
        this.validate();
    }

    /**
     * set JButton's properties.
     * 
     * @param button   JButton
     * @param itemName Item which will be verified if it can be used in bag
     */
    private void setButtonProp(final JButton button, final String itemName) {
        button.setEnabled(this.playerController.canUseItem(itemName));
    }

    /**
     * Change panel.
     * 
     * @param panelName panel which will be showed
     */
    public void changePanel(final String panelName) {
        cardlayout.show(this, panelName);
    }

    /**
     * set the top part of content area.
     * 
     * @param topPanel JPanel
     */
    private void setTopPanel(final JPanel topPanel) {
        topPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        final JLabel nameLabel = new JLabel("ITEM : ");
        final JLabel quantityLabel = new JLabel("QUANTITY : ");
        final JLabel descriptionLabel = new JLabel("DESCRIPTION : ");
        final JLabel typeLabel = new JLabel("TYPE : ");
        final JLabel useLabel = new JLabel("USE");
        topPanel.add(nameLabel);
        topPanel.add(quantityLabel);
        topPanel.add(descriptionLabel);
        topPanel.add(typeLabel);
        topPanel.add(useLabel);
    }

}
