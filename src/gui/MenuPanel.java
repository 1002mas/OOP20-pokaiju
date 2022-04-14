package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.PlayerController;

public class MenuPanel extends JPanel {
    private static final long serialVersionUID = 5503139907518499045L;
    private final ImagesLoader imgLoad;
    private int size;
    private final GameFrame gui;
    private final PlayerController playerController;
    private static final String MONSTERPANEL = "MONSTER";
    private static final String BOXPANEL = "BOX";
    private static final String GAMEITEMSPANEL = "ITEMS";
    private static final String PLAYERINFOPANEL = "INFO";

    public MenuPanel(PlayerController playerController, ImagesLoader imgLoad, int size, GameFrame gui) {
	this.playerController = playerController;
	this.imgLoad = imgLoad;
	this.size = size;
	this.gui = gui;
	init();
    }

    private void init() {
	CardLayout cLayout = (CardLayout) this.gui.getContentPane().getLayout();

	this.setLayout(new BorderLayout());

	JPanel topPanel = new JPanel(new FlowLayout());
	topPanel.setBorder(BorderFactory.createLineBorder(Color.blue));

	final JButton monster = new JButton("MONSTER");
	final JButton box = new JButton(" BOX ");
	final JButton gameItems = new JButton(" BAG ");
	final JButton playerInfo = new JButton(" PLAYERINFO ");
	final JButton quit = new JButton(" QUIT MENU ");
	final JButton backToMainMenu = new JButton(" BACK TO MAIN MENU ");
	/*final JButton save = new JButton(" SAVE ");*/

	topPanel.add(monster);
	topPanel.add(box);
	topPanel.add(gameItems);
	topPanel.add(playerInfo);
	topPanel.add(quit);
	/*topPanel.add(save);*/
	topPanel.add(backToMainMenu);

	JPanel bottomPanel = new JPanel();
	bottomPanel.setBorder(BorderFactory.createLineBorder(Color.red));
	bottomPanel.setLayout(cLayout);

	MonsterPanel monsterPanel = new MonsterPanel(this.playerController, this.imgLoad);

	BoxPanel boxPanel = new BoxPanel(this.playerController);

	GameItemPanel gameItemPanel = new GameItemPanel(this.playerController, size);

	PlayerInfoPanel playerInfoPanel = new PlayerInfoPanel(this.playerController);

	bottomPanel.add(monsterPanel);
	bottomPanel.add(boxPanel);
	bottomPanel.add(gameItemPanel);
	bottomPanel.add(playerInfoPanel);

	monster.addActionListener(e -> {
	    monsterPanel.update();
	    cLayout.show(bottomPanel, MONSTERPANEL);
	});
	box.addActionListener(e -> cLayout.show(bottomPanel, BOXPANEL));
	gameItems.addActionListener(e -> {
	    gameItemPanel.update();
	    cLayout.show(bottomPanel, GAMEITEMSPANEL);

	});
	playerInfo.addActionListener(e -> {
	    playerInfoPanel.update();
	    cLayout.show(bottomPanel, PLAYERINFOPANEL);
	});
	quit.addActionListener(e -> {
	    monsterPanel.changePanel(Integer.toString(0));
	    gameItemPanel.changePanel(GAMEITEMSPANEL);
	    gui.changePanel(GameFrame.MAP_PANEL);
	});

	/*save.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		playerController.save();

	    }
	});*/
	backToMainMenu.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int result = JOptionPane.showConfirmDialog(null, "Sure? You want to exit?", "Warning",
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.YES_OPTION) {
		    gui.changePanel(GameFrame.LOGIN_PANEL);
		} 
	    }
	});

	bottomPanel.add(monsterPanel, MONSTERPANEL);
	bottomPanel.add(boxPanel, BOXPANEL);
	bottomPanel.add(gameItemPanel, GAMEITEMSPANEL);
	bottomPanel.add(playerInfoPanel, PLAYERINFOPANEL);

	this.add(topPanel, BorderLayout.NORTH);
	this.add(bottomPanel, BorderLayout.CENTER);

    }
}
