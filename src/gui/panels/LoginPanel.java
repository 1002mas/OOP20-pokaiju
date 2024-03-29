package gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LoginPanel extends JPanel {
    private static final long serialVersionUID = -7589477617567743318L;
    private static final int SPACE = 50;
    private final JButton newGame = new JButton(" NEW GAME ");
    private final JButton quitGame = new JButton(" QUIT GAME ");

    public LoginPanel() {
        this.setLayout(new GridBagLayout());
        final GridBagConstraints cons = new GridBagConstraints();
        cons.gridy = 0;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipady = SPACE;
        cons.weightx = 0;
        cons.insets = new Insets(SPACE, 0, 0, 0);
        this.add(newGame, cons);
        cons.gridy++;
        this.add(quitGame, cons);
    }

    /**
     * 
     * @return newGameButton
     */
    public JButton getnewGame() {
        return this.newGame;
    }

    /**
     * 
     * @return quitButton
     */
    public JButton getquitGame() {
        return this.quitGame;
    }

}
