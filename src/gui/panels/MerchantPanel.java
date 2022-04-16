package gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import controller.PlayerController;
import gui.GameFrame;
import gui.GameFrameImpl;

public class MerchantPanel extends JPanel {
    private static final long serialVersionUID = -8620443097250013546L;
    private static final int MINIMUM_QUANTITY = 0;
    private static final int MAXIMUM_QUANTITY = 99;

    private final PlayerController playerController;
    private final Map<String, Integer> buyedItems = new HashMap<>();
    private final JButton buyButton = new JButton("buy");

    private JLabel balanceLabel = new JLabel();
    private JLabel totalPriceField = new JLabel();
    private List<SpinnerModel> itemQuantity = new ArrayList<>();

    public MerchantPanel(GameFrame frame, PlayerController playerController) {
	this.setLayout(new BorderLayout());
	this.playerController = playerController;

	balanceLabel.setOpaque(true);
	balanceLabel.setBackground(Color.GREEN);
	JPanel actionPanel = new JPanel();
	actionPanel.setLayout(new GridLayout(0, 3));
	actionPanel.add(totalPriceField);
	totalPriceField.setOpaque(true);
	totalPriceField.setHorizontalAlignment(JLabel.CENTER);
	totalPriceField.setBackground(Color.white);
	buyButton.addActionListener(e -> {
	    this.playerController.buyMerchantItems(buyedItems);
	    init();
	});
	actionPanel.add(buyButton);

	JButton exitButton = new JButton("exit");
	actionPanel.add(exitButton);
	exitButton.addActionListener(e -> {
	    frame.updateView(GameFrameImpl.MAP_VIEW);
	});

	JPanel itemPanel = new JPanel();
	// init();
	this.add(itemPanel, BorderLayout.CENTER);
	this.add(actionPanel, BorderLayout.SOUTH);
	this.add(balanceLabel, BorderLayout.NORTH);
	this.addComponentListener(new ComponentListener() {
	    @Override
	    public void componentShown(ComponentEvent e) {
		init();
		updateItemList(itemPanel);
	    }

	    @Override
	    public void componentHidden(ComponentEvent e) {
	    }

	    @Override
	    public void componentResized(ComponentEvent e) {
	    }

	    @Override
	    public void componentMoved(ComponentEvent e) {
	    }

	});
    }

    /**
     * reset items quantity and get current player balance
     */
    private void init() {
	this.balanceLabel.setText("Player's balance: " + this.playerController.getPlayerMoney());
	this.totalPriceField.setText("0");
	for (SpinnerModel spinner : this.itemQuantity) {
	    spinner.setValue(0);
	}
    }

    /**
     * update item list on new merchant interaction
     * 
     * @param itemPanel panel where items are listed
     */
    private void updateItemList(JPanel itemPanel) {
	itemPanel.removeAll();
	List<String> gameItems = playerController.getMerchantItems();
	itemPanel.setLayout(new GridLayout(0, 4));

	for (String gameItem : gameItems) {
	    // name
	    itemPanel.add(new JLabel(gameItem));
	    // Description
	    itemPanel.add(new JLabel(playerController.getItemDescription(gameItem)));
	    // Price
	    itemPanel.add(new JLabel(Integer.toString(playerController.getMerchantItemPrice(gameItem))));

	    // Quantity
	    SpinnerModel model = new SpinnerNumberModel(0, MINIMUM_QUANTITY, MAXIMUM_QUANTITY, 1);
	    this.itemQuantity.add(model);
	    JSpinner spinner = new JSpinner(model);
	    itemPanel.add(spinner);
	    spinner.addChangeListener(e -> {
		buyedItems.put(gameItem, (int) model.getValue());

		int totalPrice = playerController.getMerchantTotalPrice(buyedItems);
		boolean canbuy = playerController.canPlayerBuyFromMerchant(buyedItems);
		buyButton.setEnabled(canbuy);
		totalPriceField.setText(Integer.toString(totalPrice));
		totalPriceField.setForeground(canbuy ? Color.BLACK : Color.RED);

	    });

	}
    }
}
