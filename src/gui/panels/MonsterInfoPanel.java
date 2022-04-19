package gui.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import controller.PlayerController;
import gui.ImagesLoader;
import model.monster.MonsterImpl;

public class MonsterInfoPanel extends JPanel {
    private static final long serialVersionUID = -5191191420756038008L;
    private final JPanel parentPanel;
    private final int monsterId;
    private final PlayerController playerController;
    private final ImagesLoader imgLoad;

    /**
     * @param parentPanel      the parentPanel
     * @param monsterId        the Monster
     * @param playerController the game controller
     * @param imgLoad          the game ImagesLoader
     */
    public MonsterInfoPanel(final JPanel parentPanel, final int monsterId, final PlayerController playerController,
            final ImagesLoader imgLoad) {
        this.parentPanel = parentPanel;
        this.monsterId = monsterId;
        this.playerController = playerController;
        this.imgLoad = imgLoad;
        init();
    }

    /**
     * Initialize content area.
     * 
     */
    private void init() {
        final CardLayout c1 = (CardLayout) this.parentPanel.getLayout();
        this.setLayout(c1);
        final JPanel containerPanel = new JPanel(new BorderLayout());

        final JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> c1.show(this.parentPanel, Integer.toString(0)));

        containerPanel.add(backButton, BorderLayout.SOUTH);
        containerPanel.add(contentPanelArea(), BorderLayout.CENTER);
        this.add(containerPanel);

    }

    /**
     * set JLabel's properties.
     * 
     * @param label JLabel
     */
    private void setLabelProp(final JLabel label) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.red));
    }

    /**
     * set text of moves.
     * 
     * @return text of moves learned and remaining pp
     */
    private String getStringMovesNameAndPP() {
        String moves = "<html>" + "Moves Learned <br/>";
        for (final String moveName : this.playerController.getMovesNames(monsterId)) {
            moves += moveName + " PP : " + this.playerController.getMovePP(moveName, monsterId) + "<br/>";
        }
        return moves + "</html>";
    }

    /**
     * set content area.
     * 
     */
    private JPanel contentPanelArea() {
        final JPanel monsterInfoPanel = new JPanel(new GridLayout(1, 3));
        final JLabel infoLabel = new JLabel();
        final String stats = "<html>" + "Name : " + this.playerController.getMonsterNameById(monsterId) + "<br/>"
                + "Level : " + playerController.getMonsterLevel(monsterId) + "<br/>" + "Exp :"
                + playerController.getMonsterExp(monsterId) + "/" + MonsterImpl.EXP_CAP + "<br/>" + "Hp : "
                + playerController.getMonsterHealth(monsterId) + "/" + playerController.getMonsterMaxHealth(monsterId)
                + "<br/>" + "Atk : " + playerController.getMonsterAttack(monsterId) + "<br/>" + "Defence : "
                + playerController.getMonsterDefense(monsterId) + "<br/>" + "Speed : "
                + playerController.getMonsterSpeed(monsterId) + "<br/>" + "</html>";
        infoLabel.setText(stats);
        setLabelProp(infoLabel);

        final JLabel monsterImgLabel = new JLabel();
        final ImageIcon iconLogo = new ImageIcon(
                imgLoad.getMonster(this.playerController.getMonsterNameById(monsterId)));
        monsterImgLabel.setIcon(iconLogo);
        setLabelProp(monsterImgLabel);

        final JLabel movesLabel = new JLabel();
        movesLabel.setText(getStringMovesNameAndPP());
        setLabelProp(movesLabel);
        monsterInfoPanel.add(infoLabel);
        monsterInfoPanel.add(monsterImgLabel);
        monsterInfoPanel.add(movesLabel);
        return monsterInfoPanel;
    }
}
