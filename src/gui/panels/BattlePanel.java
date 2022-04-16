package gui.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.BattleController;
import controller.PlayerController;
import gui.GameFrame;
import gui.GameFrameImpl;
import gui.ImagesLoader;

public class BattlePanel extends JPanel {

    private static final long serialVersionUID = -6294780864196246092L;

    private final String MONSTER = "monsters";
    private final String CENTER_PANEL = "center";
    private final String SOUTH_PANEL = "south";
    private final String ITEM = "items";
    private final String CHOOSE = "choose";
    private final String MOVE = "moves";

    private final Map<String, JPanel> panelMap = new HashMap<>();
    private JTextField actionText;
    private JTextField playerMonster;
    private JTextField enemyMonster;
    private CardLayout cLayout;
    private boolean itemsFlag;
    private String itemUsed;
    private JLabel playerMonsterImg = new JLabel();
    private JLabel enemyMonsterImg = new JLabel();

    private BattleController ctrl;
   
    private ImagesLoader img;
    private GameFrame gameFrame;

    public BattlePanel(ImagesLoader img,GameFrame frame) {
	this.img = img;
	this.gameFrame = frame;
	this.setLayout(new BorderLayout());
	JPanel topPanel = new JPanel();
	topPanel.setLayout(new GridLayout());
	this.cLayout = new CardLayout();
	JPanel southPanel = new JPanel();
	southPanel.setLayout(cLayout);
	this.panelMap.put(SOUTH_PANEL, southPanel);
	JPanel centerPanel = new JPanel();
	centerPanel.setLayout(new BorderLayout());
	this.panelMap.put(CENTER_PANEL, centerPanel);
	JPanel choosePanel = new JPanel();
	choosePanel.setLayout(new GridLayout());
	JPanel movesPanel = new JPanel();
	movesPanel.setLayout(new GridLayout());
	this.panelMap.put(MOVE, movesPanel);
	JPanel monsterPanel = new JPanel();
	monsterPanel.setLayout(new GridLayout());
	this.panelMap.put(MONSTER, monsterPanel);
	JPanel itemsPanel = new JPanel();
	itemsPanel.setLayout(new GridLayout());
	this.panelMap.put(ITEM, itemsPanel);
	
	this.playerMonster = new JTextField();
	this.playerMonster.setEditable(false);
	this.enemyMonster = new JTextField();
	this.enemyMonster.setEditable(false);
	
	topPanel.add(playerMonster);
	topPanel.add(enemyMonster);
	
	this.actionText = new JTextField();
	this.actionText.setEditable(false);
	centerPanel.add(actionText, BorderLayout.SOUTH);
	

	JButton leftNorthButton = new JButton("Attack");
	leftNorthButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		loadMoves();
		actionText.setText("What move do u choose?");
		cLayout.show(southPanel, MOVE);
	    }

	});
	JButton rightNorthButton = new JButton("Change Monster");
	rightNorthButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		itemsFlag = false;
		loadMonsters();
		actionText.setText("What monster do u choose?");
		cLayout.show(southPanel, MONSTER);
	    }

	});
	JButton leftSouthButton = new JButton("Equipment");
	leftSouthButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		loadItems();
		actionText.setText("What item do u choose?");
		cLayout.show(southPanel, ITEM);
	    }

	});
	JButton rightSouthButton = new JButton("Get Out");
	rightSouthButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (ctrl.flee()) {
		    actionText.setText("You successfully escaped");
		    gameFrame.updateView(GameFrameImpl.MAP_VIEW);

		} else {
		    actionText.setText("You failed to escaped");
		}
	    }
	});
	
	choosePanel.add(leftNorthButton);
	choosePanel.add(rightNorthButton);
	choosePanel.add(leftSouthButton);
	choosePanel.add(rightSouthButton);

	southPanel.add(choosePanel, CHOOSE);
	southPanel.add(monsterPanel, MONSTER);
	southPanel.add(movesPanel, MOVE);
	southPanel.add(itemsPanel, ITEM);
	this.cLayout.show(southPanel, CHOOSE);
	this.add(topPanel, BorderLayout.NORTH);
	this.add(centerPanel, BorderLayout.CENTER);
	this.add(southPanel, BorderLayout.SOUTH);
	
	this.addComponentListener(new ComponentListener() {
	    
	    @Override
	    public void componentShown(ComponentEvent e) {
		start();
	    }
	    
	    @Override
	    public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	    }
	    
	    @Override
	    public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	    }
	    
	    @Override
	    public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	    }
	});
    }

    public void setBattleController(BattleController ctrl) {
	this.ctrl = ctrl;
    }

    private String getCurrentPlayerMonsterData() {
	return " " + ctrl.getPlayerCurrentMonsterName() + " " + ctrl.getPlayerCurrentMonsterHp() + "/"
		+ ctrl.getPlayerCurrentMonsterMaxHealth() + " HP " + "LVL." + ctrl.getPlayerCurrentMonsterLevel();
    }

    private String getCurrentEnemyMonsterData() {
	return " " + ctrl.getEnemyCurrentMonsterName() + " " + ctrl.getEnemyCurrentMonsterHp() + "/"
		+ ctrl.getEnemyCurrentMonsterMaxHealth() + " HP " + "LVL." + ctrl.getEnemyCurrentMonsterLevel();
    }

    private void loadImg() {
	playerMonsterImg.setIcon(new ImageIcon(img.getMonster(ctrl.getPlayerCurrentMonsterName())));
	this.panelMap.get(CENTER_PANEL).add(playerMonsterImg, BorderLayout.WEST);
	enemyMonsterImg.setIcon(new ImageIcon(img.getMonster(ctrl.getEnemyCurrentMonsterName())));
	this.panelMap.get(CENTER_PANEL).add(enemyMonsterImg, BorderLayout.EAST);
	this.panelMap.get(CENTER_PANEL).repaint();

    }

    private void loadMoves() {
	Map<JButton, String> movesMap = new HashMap<>();
	this.panelMap.get(MOVE).removeAll();
	for (var move : ctrl.getMoves()) {
	    JButton button = new JButton("" + move + " " + ctrl.getCurrentPP(move) + " PP");
	    movesMap.put(button, move);
	    if (this.ctrl.checkPP(move)) {
		button.setEnabled(false);
	    } else {
		button.setEnabled(true);
	    }
	    button.addActionListener(e -> {

		ctrl.chooseMove(movesMap.get(e.getSource()));

		if (!ctrl.isAlive(ctrl.getPlayerCurrentMonsterId())) {
		    actionText.setText(ctrl.getPlayerCurrentMonsterName() + " is dead");
		    playerMonster.setText(getCurrentPlayerMonsterData());
		    if (ctrl.isOver()) {
			// ENDING BATTLE player team dead
			actionText.setText("You lose...");
			
			    this.gameFrame.updateView(GameFrameImpl.MAP_VIEW);
			
			
		    } else {
			loadMonsters();
			cLayout.show(this.panelMap.get(SOUTH_PANEL), MONSTER);
		    }
		} else {
		    if (ctrl.isOver()) {
			// ENDING BATTLE enemy team dead
			actionText.setText("You have defeated all the enemies!!");
		
			this.gameFrame.updateView(GameFrameImpl.MAP_VIEW);
			
		    } else {
			refresh();
		    }
		}
	    });
	    this.panelMap.get(MOVE).add(button);
	}
    }

    private void loadMonsters() {
	this.panelMap.get(MONSTER).removeAll();
	Map<JButton, Integer> monsterMap = new HashMap<>();
	for (var monsterId : ctrl.getPlayerTeam()) {
	    JButton button = new JButton(ctrl.getMonsterName(monsterId));
	    monsterMap.put(button, monsterId);
	    if (!this.ctrl.isAlive(monsterId)) {
		button.setEnabled(false);
	    }

	    button.addActionListener(e -> {
		if (itemsFlag) {

		    ctrl.useItem(itemUsed, monsterMap.get(e.getSource()));

		    this.itemsFlag = false;
		} else {
		    ctrl.changeMonster(monsterMap.get(e.getSource()));
		    loadImg();
		}

		refresh();
	    });
	    this.panelMap.get(MONSTER).add(button);
	}
    }

    private void loadItems() {
	this.panelMap.get(ITEM).removeAll();
	Map<JButton, String> itemMap = new HashMap<>();
	for (var itemName : ctrl.getAllPlayerItems()) {
	    JButton button = new JButton(itemName + " " + ctrl.getItemNumber(itemName));
	    itemMap.put(button, itemName);

	    button.addActionListener(e -> {
		this.itemUsed = itemMap.get(e.getSource());
		if (this.ctrl.isCaptureItem(itemUsed)) {
		    this.ctrl.useItem(itemUsed, 0);
		    if (ctrl.isEnemyCaught()) {
			// ENDING BATTLE
			actionText.setText("You have captured a new Monster!!");
			this.gameFrame.updateView(GameFrameImpl.MAP_VIEW);
		    } else {
			refresh();
		    }
		} else {
		    this.itemsFlag = true;
		    loadMonsters();
		    actionText.setText("What monster do u choose?");
		    cLayout.show(this.panelMap.get(SOUTH_PANEL), MONSTER);
		}
	    });
	    this.panelMap.get(ITEM).add(button);
	}
	JButton back = new JButton("Back");
	back.addActionListener(e -> {
	    refresh();
	});
	this.panelMap.get(ITEM).add(back);
    }

    private void refresh() {
	this.playerMonster.setText(getCurrentPlayerMonsterData());
	this.enemyMonster.setText(getCurrentEnemyMonsterData());
	this.loadMoves();
	cLayout.show(this.panelMap.get(SOUTH_PANEL), CHOOSE);
	this.actionText.setText("What do you want to do?...");

    }

    public void start() {

	loadImg();
	this.playerMonster.setText(this.getCurrentPlayerMonsterData());
	this.enemyMonster.setText(this.getCurrentEnemyMonsterData());
	this.actionText.setText("What do you want to do?...");
	cLayout.show(this.panelMap.get(SOUTH_PANEL), CHOOSE);
	
	

    }
}
