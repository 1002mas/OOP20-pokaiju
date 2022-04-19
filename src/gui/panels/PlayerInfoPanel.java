package gui.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import controller.PlayerController;
import gui.GameFrameImpl;

public class PlayerInfoPanel extends JPanel {
    private static final long serialVersionUID = 8776923375870422485L;
    private final PlayerController playerController;
    private final GameFrameImpl frame;

    /**
     * 
     * @param playerController
     * @param frame
     */
    public PlayerInfoPanel(final PlayerController playerController, final GameFrameImpl frame) {
        this.playerController = playerController;
        this.frame = frame;
    }

    /**
     * 
     * Initialize content area.
     * 
     */
    private void init() {
        this.setLayout(new GridLayout(4, 0));
        final JLabel playerName = new JLabel("Name :  " + this.playerController.getPlayerName());
        final JLabel trainerNumber = new JLabel("Trainer number :  " + this.playerController.getTrainerNumber());
        final JLabel gender = new JLabel("Gender :  " + this.playerController.getPlayerGender());
        final JLabel money = new JLabel("Money :  " + this.playerController.getPlayerMoney() + " $ ");
        setLabelProperties(playerName);
        setLabelProperties(trainerNumber);
        setLabelProperties(gender);
        setLabelProperties(money);
        this.add(playerName);
        this.add(trainerNumber);
        this.add(gender);
        this.add(money);
    }

    /**
     * set JLabel's properties.
     * 
     * @param label JLabel
     */
    private void setLabelProperties(final JLabel label) {
        final double heightPerc = 0.02;
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, (int) (heightPerc * this.frame.getHeight())));
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setHorizontalAlignment(JLabel.CENTER);
    }

    /**
     * update content area.
     */
    public void update() {
        this.removeAll();
        this.init();
        this.validate();
    };
}
