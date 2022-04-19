package gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.SwingConstants;

import controller.PlayerController;
import gui.GameFrameImpl;

public class MerchantPanel extends JPanel {
    private static final long serialVersionUID = -8620443097250013546L;
    private static final int MINIMUM_QUANTITY = 0;

    private final GameFrameImpl frame;
    private final PlayerController playerController;
    private final Map<String, Integer> buyedItems = new HashMap<>();
    private final JButton buyButton = new JButton("buy");

    private final JLabel balanceLabel = new JLabel();
    private final JLabel totalPriceField = new JLabel();
    private final List<SpinnerModel> itemQuantity = new ArrayList<>();

    /**
     * 
     * @param frame            the parent view
     * @param playerController the game controller
     */
    public MerchantPanel(final GameFrameImpl frame, final PlayerController playerController) {
        this.setLayout(new BorderLayout());
        this.playerController = playerController;
        this.frame = frame;

        balanceLabel.setOpaque(true);
        balanceLabel.setBackground(Color.GREEN);
        final JPanel actionPanel = new JPanel();
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

        final JButton exitButton = new JButton("exit");
        actionPanel.add(exitButton);
        exitButton.addActionListener(e -> {
            frame.updateView(GameFrameImpl.MAP_VIEW);
        });

        final JPanel itemPanel = new JPanel();
        this.add(itemPanel, BorderLayout.CENTER);
        this.add(actionPanel, BorderLayout.SOUTH);
        this.add(balanceLabel, BorderLayout.NORTH);
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentShown(final ComponentEvent e) {
                init();
                updateItemList(itemPanel);
            }

            @Override
            public void componentHidden(final ComponentEvent e) {
            }

            @Override
            public void componentResized(final ComponentEvent e) {
            }

            @Override
            public void componentMoved(final ComponentEvent e) {
            }

        });
    }

    /**
     * reset items quantity and get current player balance.
     */
    private void init() {
        this.balanceLabel.setText("Player's balance: " + this.playerController.getPlayerMoney() + "$");
        this.totalPriceField.setText("0$");
        for (final SpinnerModel spinner : this.itemQuantity) {
            spinner.setValue(0);
        }
    }

    /**
     * update item list on new merchant interaction.
     * 
     * @param itemPanel panel where items are listed
     */
    private void updateItemList(final JPanel itemPanel) {
        itemPanel.removeAll();
        final List<String> gameItems = playerController.getMerchantItems();
        itemPanel.setLayout(new GridLayout(0, 4));

        for (final String gameItem : gameItems) {
            // name
            itemPanel.add(new JLabel(gameItem));
            // Description
            final JLabel descLabel = new JLabel("<html><p>" + playerController.getItemDescription(gameItem) + "</p></html>",
                    SwingConstants.LEFT);
            final double heightPerc = 0.02;
            descLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, (int) (heightPerc * this.frame.getHeight())));
            itemPanel.add(descLabel);
            // Price
            final JLabel priceLabel = new JLabel(
                    Integer.toString(playerController.getMerchantItemPrice(gameItem)) + "$");
            priceLabel.setHorizontalAlignment(JLabel.CENTER);
            itemPanel.add(priceLabel);

            // Quantity
            final SpinnerModel model = new SpinnerNumberModel(0, MINIMUM_QUANTITY, Integer.MAX_VALUE, 1);
            this.itemQuantity.add(model);
            final JSpinner spinner = new JSpinner(model);
            itemPanel.add(spinner);
            spinner.setEditor(new JSpinner.DefaultEditor(spinner));
            spinner.addChangeListener(e -> {
                buyedItems.put(gameItem, (int) model.getValue());
                final int totalPrice = playerController.getMerchantTotalPrice(buyedItems);
                final boolean canbuy = playerController.canPlayerBuyFromMerchant(buyedItems);
                buyButton.setEnabled(canbuy);
                totalPriceField.setText(Integer.toString(totalPrice) + "$");
                totalPriceField.setForeground(canbuy ? Color.BLACK : Color.RED);

            });

        }
        itemPanel.validate();
    }
}
