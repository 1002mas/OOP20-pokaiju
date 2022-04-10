package gui;

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
import controller.ImagesLoader;
import controller.PlayerController;

public class MonsterInfoPanel extends JPanel {
    private static final long serialVersionUID = -5191191420756038008L;
    private JPanel mainPanel;
    private final int monsterId;
    private final PlayerController playerController;
    private final ImagesLoader imgLoad;

    public MonsterInfoPanel(JPanel mainPanel, int monsterId, PlayerController playerController, ImagesLoader imgLoad) {
	this.mainPanel = mainPanel;
	this.monsterId = monsterId;
	this.playerController = playerController;
	this.imgLoad = imgLoad;
	init();
    }

    private void init() {
	CardLayout c1 = (CardLayout) this.mainPanel.getLayout();
	this.setLayout(c1);
	JPanel containerPanel = new JPanel(new BorderLayout());

	JButton backButton = new JButton("Back");
	backButton.addActionListener(e -> c1.show(this.mainPanel, Integer.toString(0)));

	containerPanel.add(backButton, BorderLayout.SOUTH);
	containerPanel.add(setContentPanel(), BorderLayout.CENTER);
	this.add(containerPanel);

    }

    private void setLabelProp(JLabel label) {
	label.setHorizontalAlignment(SwingConstants.CENTER);
	label.setVerticalAlignment(SwingConstants.CENTER);
	label.setBorder(BorderFactory.createLineBorder(Color.red));
    }

    private JPanel setContentPanel() {
	JPanel monsterInfoPanel = new JPanel(new GridLayout(1, 3));
	JLabel infoLabel = new JLabel();
	String stats = "<html>" + "name : " + this.playerController.getMonsterNameById(monsterId) + "<br/>" + "Level : "
		+ playerController.getMonsterLevel(monsterId) + "<br/>" + "Hp : "
		+ playerController.getMonsterHealth(monsterId) + "/" + playerController.getMonsterMaxHealth(monsterId)
		+ "<br/>" + "Atk : " + playerController.getMonsterAttack(monsterId) + "<br/>" + "Defence : "
		+ playerController.getMonsterDefense(monsterId) + "<br/>" + "Speed : "
		+ playerController.getMonsterSpeed(monsterId) + "</html>";
	infoLabel.setText(stats);
	setLabelProp(infoLabel);

	JLabel monsterImgLabel = new JLabel();
	ImageIcon iconLogo = new ImageIcon(imgLoad.getMonster(this.playerController.getMonsterNameById(monsterId)));
	monsterImgLabel.setIcon(iconLogo);
	setLabelProp(monsterImgLabel);

	JLabel movesLabel = new JLabel();
	String moves = "<html>" + "Moves Learned <br/>" + playerController.getMovesNames(monsterId) + "</html>";
	movesLabel.setText(moves);
	setLabelProp(movesLabel);
	monsterInfoPanel.add(infoLabel);
	monsterInfoPanel.add(monsterImgLabel);
	monsterInfoPanel.add(movesLabel);
	return monsterInfoPanel;
    }
}
