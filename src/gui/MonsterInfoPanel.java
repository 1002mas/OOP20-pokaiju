package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.PlayerController;
//codice di giusto(teoricamente)
/*public class MonsterInfoPanel extends JPanel {

    private final CardLayout cardlayout = new CardLayout();
    private JPanel mainPanel;
    private final PlayerController playerController;
    private final String MonsterName;

    public MonsterInfoPanel(PlayerController playerController, JPanel mainPanel, String MonsterName) {
	this.playerController = playerController;
	this.mainPanel = mainPanel;
	this.MonsterName = MonsterName;
	init();
    }

    private void init() {
	this.setLayout(cardlayout);
	JPanel singleMonsterPanel = new JPanel(new BorderLayout());

	JLabel infoLabel = new JLabel();
	String stats = "<html>" + "name : " + MonsterName + "<br/>" + "Level : "
		+ playerController.getMonsterLevel(MonsterName) + "<br/>" + "Hp : "
		+ playerController.getHealth(MonsterName) + "/" + playerController.getMonsterMaxHealth(MonsterName)
		+ "<br/>" + "Atk : " + playerController.getAttack(MonsterName) + "<br/>" + "Defence : "
		+ playerController.getDefense(MonsterName) + "<br/>" + "Speed : "
		+ playerController.getSpeed(MonsterName) + "</html>";
	infoLabel.setText(stats);

	JLabel movesLabel = new JLabel();
	String moves = "<html>" + "Moves Learned <br/>" + playerController.getMovesNames(MonsterName).toString()
		+ "</html>";
	movesLabel.setText(moves);

	JButton backButton = new JButton("Back");
	backButton.addActionListener(e -> cardlayout.show(this.mainPanel, Integer.toString(0)));

	singleMonsterPanel.add(backButton, BorderLayout.SOUTH);
	singleMonsterPanel.add(infoLabel, BorderLayout.WEST);
	singleMonsterPanel.add(movesLabel, BorderLayout.EAST);
	this.add(singleMonsterPanel);

    }
}*/
//codice di prova
public class MonsterInfoPanel extends JPanel {
    private JPanel mainPanel;

    public MonsterInfoPanel(JPanel mainPanel) {
	this.mainPanel = mainPanel;
	init();
    }

    private void init() {
	CardLayout c1 = (CardLayout) this.mainPanel.getLayout();
	this.setLayout(c1);
	JPanel singleMonsterPanel = new JPanel(new GridLayout(1, 3));

	JLabel infoLabel = new JLabel();
	String stats = "<html> hello <br/> ciao </br/> hola </html>";
	infoLabel.setText(stats);
	infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
	infoLabel.setVerticalAlignment(SwingConstants.CENTER);
	infoLabel.setBorder(BorderFactory.createLineBorder(Color.red));

	JLabel movesLabel = new JLabel();
	String moves = "<html>" + "Moves Learned <br/>" + "fireball" + "<br/>" + "waterball" + "</html>";
	movesLabel.setText(moves);
	movesLabel.setHorizontalAlignment(SwingConstants.CENTER);
	movesLabel.setVerticalAlignment(SwingConstants.CENTER);
	movesLabel.setBorder(BorderFactory.createLineBorder(Color.red));

	JButton backButton = new JButton("Back");
	backButton.addActionListener(e -> c1.show(this.mainPanel, Integer.toString(0)));

	singleMonsterPanel.add(backButton);
	singleMonsterPanel.add(infoLabel);
	singleMonsterPanel.add(movesLabel);
	this.add(singleMonsterPanel);

    }
}
