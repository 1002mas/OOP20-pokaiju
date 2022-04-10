package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.BattleController;
import controller.ImagesLoader;
import model.battle.Moves;
import model.battle.Moves;
import model.gameitem.GameItem;
import model.monster.Monster;

public class BattleFrame {
    private JFrame frame;
    private JTextField actionText; 
    private JTextField playerMonster; 
    private JTextField enemyMonster;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel southPanel;
    private JPanel centerPanel;
    private JPanel choosePanel;
    private JPanel movesPanel;
    private JPanel monsterPanel;
    private JPanel itemsPanel;
    private JButton leftNorthButton;
    private JButton rightNorthButton;
    private JButton leftSouthButton;
    private JButton rightSouthButton;
    private JButton firstAttButton;
    private JButton secondAttButton;
    private JButton thirdAttButton;
    private JButton fourthAttButton;
    private JButton firstMonButton;
    private JButton secondMonButton;
    private JButton thirdMonButton;
    private JButton fourthMonButton;
    private JButton firstItemsButton;
    private JButton secondItemsButton;
    private JButton thirdItemsButton;
    private JButton fourthItemsButton;
    //private JLabel backgroundLabel;
    private BufferedImage background;
    private BattleController ctrl;
    private ImagesLoader img;
    private List<String> moves;
    private List<Integer> playerTeam;
    private List<String> playerItems;
    private String item;
    private String move;
    private String monsterName;
    private CardLayout cLayout;
    private boolean itemsFlag;
    private String itemUsed;
    private Map<JButton,Integer> monsterMap;
    private Map<JButton,String> itemMap;
    private Map<JButton,String> movesMap;
    private JLabel playerMonsterImg = new JLabel();
    private JLabel enemyMonsterImg = new JLabel();
    
    public BattleFrame(BattleController ctrl, ImagesLoader img) {
	this.ctrl = ctrl;
	this.img = img;
	start();
    }
    
    String getCurrentPlayerMonsterData() {
	return " " + ctrl.getPlayerCurrentMonsterName() + " " +  ctrl.getPlayerCurrentMonsterHp() + "/" + ctrl.getPlayerCurrentMonsterMaxHealth() + " HP " + "LVL." +ctrl.getPlayerCurrentMonsterLevel();
    }
    
    String getCurrentEnemyMonsterData() {
	return " " + ctrl.getEnemyCurrentMonsterName() + " " +  ctrl.getEnemyCurrentMonsterHp() + "/" + ctrl.getEnemyCurrentMonsterMaxHealth() + " HP " + "LVL." +ctrl.getEnemyCurrentMonsterLevel();
    }
    
    void setImg() {
	playerMonsterImg.setIcon(new ImageIcon(img.getMonster(ctrl.getPlayerCurrentMonsterName())));
	this.centerPanel.add(playerMonsterImg, BorderLayout.WEST);
	enemyMonsterImg.setIcon(new ImageIcon(img.getMonster(ctrl.getEnemyCurrentMonsterName())));
	this.centerPanel.add(enemyMonsterImg, BorderLayout.EAST);
	this.centerPanel.repaint();
    }
    void setMoves() {
	this.moves = ctrl.getMoves();
	this.movesMap = new HashMap<>();
	this.move = this.moves.get(0);
	this.firstAttButton.setText("" + move + " " + ctrl.getCurrentPP(move) + " PP");
	this.movesMap.put(firstAttButton, move);
	if(!this.ctrl.checkPP(move)) {
	    this.firstAttButton.setEnabled(false);
	}
	else {
	    this.firstAttButton.setEnabled(true);
	}
	this.move = this.moves.get(1);
	this.secondAttButton.setText("" + move + " " + ctrl.getCurrentPP(move) + " PP");
	this.movesMap.put(secondAttButton, move);
	if(!this.ctrl.checkPP(move)) {
	    this.secondAttButton.setEnabled(false);
	}
	else {
	    this.secondAttButton.setEnabled(true);
	}
	this.move = this.moves.get(2);
	this.thirdAttButton.setText("" + move+ " " + ctrl.getCurrentPP(move) + " PP");
	this.movesMap.put(thirdAttButton, move);
	if(!this.ctrl.checkPP(move)) {
	    this.thirdAttButton.setEnabled(false);
	}
	else {
	    this.thirdAttButton.setEnabled(true);
	}
	this.move = this.moves.get(3);
	this.fourthAttButton.setText("" + move + " " + ctrl.getCurrentPP(move) + " PP");
	this.movesMap.put(fourthAttButton, move);
	if(!this.ctrl.checkPP(move)) {
	    this.fourthAttButton.setEnabled(false);
	}
	else {
	    this.fourthAttButton.setEnabled(true);
	}
	
    }
    void setMonster() {
	monsterMap=new HashMap<>();
  	this.playerTeam = ctrl.getPlayerTeam();
  	this.monsterName = ctrl.getMonsterName(this.playerTeam.get(0));
  	this.firstMonButton.setText("" + monsterName);
  	this.monsterMap.put(firstMonButton,this.playerTeam.get(0));
  	System.out.println(monsterName+"-->"+this.playerTeam.get(0));
  	if(!this.ctrl.isAlive(this.playerTeam.get(0))) { 
  	    this.firstMonButton.setEnabled(false);
  	}
  	
  	this.monsterName = ctrl.getMonsterName(this.playerTeam.get(1));
  	this.secondMonButton.setText("" + monsterName);
  	this.monsterMap.put(secondMonButton,this.playerTeam.get(1));
  	System.out.println(monsterName+"-->"+this.playerTeam.get(1));
  	if(!this.ctrl.isAlive(this.playerTeam.get(1))) {
  	    this.secondMonButton.setEnabled(false);
  	}
  	this.monsterName = ctrl.getMonsterName(this.playerTeam.get(2));
  	this.thirdMonButton.setText("" + monsterName);
  	this.monsterMap.put(thirdMonButton,this.playerTeam.get(2));
  	System.out.println(monsterName+"-->"+this.playerTeam.get(2));
  	if(!this.ctrl.isAlive(this.playerTeam.get(2))) {
  	    this.thirdMonButton.setEnabled(false);
  	}
  	this.monsterName = ctrl.getMonsterName(this.playerTeam.get(3));
  	this.fourthMonButton.setText("" + monsterName);
  	this.monsterMap.put(fourthMonButton,this.playerTeam.get(3));
  	System.out.println(monsterName+"-->"+this.playerTeam.get(3));
  	if(!this.ctrl.isAlive(this.playerTeam.get(3))) {
  	    this.fourthMonButton.setEnabled(false);
  	}
  	
    }
    void setItems() {
	itemMap = new HashMap<>();
   	this.playerItems = ctrl.getAllPlayerItems();
   	this.item = this.playerItems.get(0);
   	itemMap.put(firstItemsButton,item);
   	this.firstItemsButton.setText("" + item+ " " + ctrl.getItemNumber(item));  	
   	this.item = this.playerItems.get(1);
   	itemMap.put(secondItemsButton,item);
   	this.secondItemsButton.setText("" + item+ " " + ctrl.getItemNumber(item));
   	
   	this.item = this.playerItems.get(2);
   	itemMap.put(thirdItemsButton,item);
   	this.thirdItemsButton.setText("" + item + " " + ctrl.getItemNumber(item));
   	
   	this.item = this.playerItems.get(3);
   	itemMap.put(fourthItemsButton,item);
   	this.fourthItemsButton.setText("" + item + " " + ctrl.getItemNumber(item));
   	
   	
     }
    void refresh() {
	this.playerMonster.setText(getCurrentPlayerMonsterData());
	this.enemyMonster.setText(getCurrentEnemyMonsterData());
	
	
	this.setMoves();
//	try {
//	    Thread.sleep(1000);
//	} catch (InterruptedException e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}
	cLayout.show(southPanel, "choose");
	
	this.actionText.setText("What do you want to do?...");

    }
    void start() {
	this.frame= new JFrame();
	this.frame.setSize(1024, 792);
	this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.mainPanel = new JPanel();
	this.mainPanel.setBounds(0, 0, 1024, 792);
	this.mainPanel.setLayout(new BorderLayout());
	this.topPanel = new JPanel();
	this.topPanel.setLayout(new GridLayout());
	this.cLayout = new CardLayout();
	this.southPanel = new JPanel();
	this.southPanel.setLayout(cLayout);
	this.centerPanel = new JPanel();
	this.centerPanel.setLayout(new BorderLayout());
	this.choosePanel = new JPanel();
	this.choosePanel.setLayout(new GridLayout());
	this.movesPanel = new JPanel();
	this.movesPanel.setLayout(new GridLayout());
	this.monsterPanel = new JPanel();
	this.monsterPanel.setLayout(new GridLayout());
	this.itemsPanel = new JPanel();
	this.itemsPanel.setLayout(new GridLayout());
	
	this.playerMonster = new JTextField(this.getCurrentPlayerMonsterData()); 
	this.playerMonster.setEditable(false);
	this.enemyMonster = new JTextField(this.getCurrentEnemyMonsterData()); 
	this.enemyMonster.setEditable(false);
	this.topPanel.add(playerMonster);
	this.topPanel.add(enemyMonster);
	
	try {
	    this.background = ImageIO.read(new File("Background.png"));
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	//this.backgroundLabel = new JLabel(new ImageIcon(background));
	this.actionText = new JTextField("What do you want to do?...");
	this.actionText.setEditable(false);
	//this.centerPanel.add(backgroundLabel, BorderLayout.CENTER);
	this.centerPanel.add(actionText, BorderLayout.SOUTH);
	this.setImg();
	this.leftNorthButton = new JButton("Attack");
	this.leftNorthButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		setMoves();
		actionText.setText("What move do u choose?");
		cLayout.show(southPanel, "moves");
	    }
	    
	});
	this.rightNorthButton = new JButton("Change Monster");
	this.rightNorthButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		itemsFlag = false;
		setMonster();
		actionText.setText("What monster do u choose?");
		cLayout.show(southPanel, "monsters");
	    }
	    
	});
	this.leftSouthButton = new JButton("Equipment");
	this.leftSouthButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		setItems();
		actionText.setText("What item do u choose?");
		cLayout.show(southPanel, "items");
	    }
	    
	});
	this.rightSouthButton = new JButton("Get Out");
	this.rightSouthButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(ctrl.flee()) {
		    actionText.setText("You successfully escaped");
		    //return to map
		}else {
		    actionText.setText("You failed to escaped");
		    
		}
	    }
	    
	});
	
	this.firstAttButton = new JButton();
	this.firstAttButton.addActionListener(e-> {

	   
		//actionText.setText("" + ctrl.getPlayerCurrentMonsterName() + " usa " + ctrl.getMoves().get(0).getName());
		
		
		
		ctrl.chooseMove(this.movesMap.get(e.getSource()));
		
		if(!ctrl.isAlive(ctrl.getPlayerCurrentMonsterId())) {
		    actionText.setText(ctrl.getPlayerCurrentMonsterName() + " is dead");
		    playerMonster.setText(getCurrentPlayerMonsterData());
		    System.out.println("STATO BATTLE:"+ctrl.isOver());
		    if(ctrl.isOver()) {
			//ENDING BATTLE player team dead
		    }else {
			setMonster();
			cLayout.show(southPanel, "monsters");
		    }
		    
		    
		}
		else {
		    if(ctrl.isOver()) {
			//ENDING BATTLE enemy team dead
			System.out.println("i nemici sono morti");
		    }
		    else {
			refresh();  
		    }
		}
		
		
		
	    
	    
	});
	this.secondAttButton = new JButton();
	this.secondAttButton.addActionListener(e-> {

		   
		//actionText.setText("" + ctrl.getPlayerCurrentMonsterName() + " usa " + ctrl.getMoves().get(0).getName());
		
		
		
	    	ctrl.chooseMove(this.movesMap.get(e.getSource()));
		
		if(!ctrl.isAlive(ctrl.getPlayerCurrentMonsterId())) {
		    actionText.setText(ctrl.getPlayerCurrentMonsterName() + " is dead");
		    playerMonster.setText(getCurrentPlayerMonsterData());
		    System.out.println("STATO BATTLE:"+ctrl.isOver());
		    if(ctrl.isOver()) {
			//ENDING BATTLE player team dead
		    }else {
			setMonster();
			cLayout.show(southPanel, "monsters");
		    }
		    
		    
		}
		else {
		    if(ctrl.isOver()) {
			//ENDING BATTLE enemy team dead
			System.out.println("i nemici sono morti");
		    }
		    else {
			refresh();  
		    }
		}
		
		
		
	    
	    
	});
	this.thirdAttButton = new JButton();
	this.thirdAttButton.addActionListener(e-> {

		   
		//actionText.setText("" + ctrl.getPlayerCurrentMonsterName() + " usa " + ctrl.getMoves().get(0).getName());
		
		
		
	    	ctrl.chooseMove(this.movesMap.get(e.getSource()));
		
		if(!ctrl.isAlive(ctrl.getPlayerCurrentMonsterId())) {
		    actionText.setText(ctrl.getPlayerCurrentMonsterName() + " is dead");
		    playerMonster.setText(getCurrentPlayerMonsterData());
		    System.out.println("STATO BATTLE:"+ctrl.isOver());
		    if(ctrl.isOver()) {
			//ENDING BATTLE player team dead
			actionText.setText("Your monsters are dead!! You lose!!");
		    }else {
			setMonster();
			cLayout.show(southPanel, "monsters");
		    }
		    
		    
		}
		else {
		    if(ctrl.isOver()) {
			//ENDING BATTLE enemy team dead
			System.out.println("i nemici sono morti");
			actionText.setText("You have defeated your enemy!! You won!!");
		    }
		    else {
			refresh();  
		    }
		}
		
		
		
	    
	    
	});
	this.fourthAttButton = new JButton();
	this.fourthAttButton.addActionListener(e-> {

		   
		//actionText.setText("" + ctrl.getPlayerCurrentMonsterName() + " usa " + ctrl.getMoves().get(0).getName());
		
		
		
	    	ctrl.chooseMove(this.movesMap.get(e.getSource()));
		
		if(!ctrl.isAlive(ctrl.getPlayerCurrentMonsterId())) {
		    actionText.setText(ctrl.getPlayerCurrentMonsterName() + " is dead");
		    playerMonster.setText(getCurrentPlayerMonsterData());
		    System.out.println("STATO BATTLE:"+ctrl.isOver());
		    if(ctrl.isOver()) {
			//ENDING BATTLE player team dead
		    }else {
			setMonster();
			cLayout.show(southPanel, "monsters");
		    }
		    
		    
		}
		else {
		    if(ctrl.isOver()) {
			//ENDING BATTLE enemy team dead
			System.out.println("i nemici sono morti");
		    }
		    else {
			refresh();  
		    }
		}
		
		
		
	    
	    
	});
	this.firstMonButton = new JButton();
	this.firstMonButton.addActionListener(e-> {

	   
		if(itemsFlag) {
		    
		    ctrl.useItem(itemUsed, monsterMap.get(e.getSource()) );
		    this.itemsFlag =false;
		}
		else {
		    ctrl.changeMonster(monsterMap.get(e.getSource()));
		    System.out.println(ctrl.getPlayerCurrentMonsterName());
		    this.setImg();
		}
		
		refresh();
	    
	    
	});
	this.secondMonButton = new JButton();
	this.secondMonButton.addActionListener(e-> {

		   
		if(itemsFlag) {
		    
		    ctrl.useItem(itemUsed, monsterMap.get(e.getSource()) );
		    System.out.println(this.monsterMap.get(e.getSource()));
		    this.itemsFlag =false;
		}
		else { System.out.println(this.monsterMap.get(e.getSource()));
			ctrl.changeMonster(monsterMap.get(e.getSource()));
			this.setImg();
		
		   
		}
		
		refresh();
	    
	    
	});
	this.thirdMonButton = new JButton();
	this.thirdMonButton.addActionListener(e-> {

		   
		if(itemsFlag) {
		    
		    ctrl.useItem(itemUsed, monsterMap.get(e.getSource()) );
		    this.itemsFlag =false;
		}
		else {
		    ctrl.changeMonster(monsterMap.get(e.getSource()));
		    System.out.println(ctrl.getPlayerCurrentMonsterName());
		}
		
		refresh();
	    
	    
	});
	this.fourthMonButton = new JButton();
	this.fourthMonButton.addActionListener(e-> {

		   
		if(itemsFlag) {
		    
		    ctrl.useItem(itemUsed, monsterMap.get(e.getSource()) );
		    this.itemsFlag =false;
		}
		else {
		    ctrl.changeMonster(monsterMap.get(e.getSource()));
		    System.out.println(ctrl.getPlayerCurrentMonsterName());
		}
		
		refresh();
	    
	    
	});
	
	this.firstItemsButton = new JButton();
	this.firstItemsButton.addActionListener(e -> {
	    	this.itemUsed = this.itemMap.get(e.getSource());
	    	this.itemsFlag=true;
	    	setMonster();
		actionText.setText("What monster do u choose?");
		cLayout.show(southPanel, "monsters");
   
	});
	this.secondItemsButton = new JButton();
	this.secondItemsButton.addActionListener(e -> {
	    	this.itemUsed = this.itemMap.get(e.getSource());
	    	this.itemsFlag=true;
	    	setMonster();
		actionText.setText("What monster do u choose?");
		cLayout.show(southPanel, "monsters");

	});
	this.thirdItemsButton = new JButton();
	this.thirdItemsButton.addActionListener(e -> {
	    	this.itemUsed = this.itemMap.get(e.getSource());
	    	this.itemsFlag=true;
	    	setMonster();
		actionText.setText("What monster do u choose?");
		cLayout.show(southPanel, "monsters");

	});
	this.fourthItemsButton = new JButton();
	this.fourthItemsButton.addActionListener(e -> {
	    	this.itemUsed = this.itemMap.get(e.getSource());
	    	if(this.ctrl.isCaptureItem(itemUsed)) {
	    	    this.ctrl.useItem(itemUsed, 0);
	    	    if(ctrl.isEnemyCaught()) {
	    		//ENDING BATTLE
	    	    }else {
	    		refresh();
	    	    }
	    	    
	    	}else {
	    	    this.itemsFlag=true;
	    	    setMonster();
	    	    actionText.setText("What monster do u choose?");
	    	    cLayout.show(southPanel, "monsters");
	    	}
	    	

	});
	
	this.itemsPanel.add(firstItemsButton);
	this.itemsPanel.add(secondItemsButton);
	this.itemsPanel.add(thirdItemsButton);
	this.itemsPanel.add(fourthItemsButton);
	
	this.movesPanel.add(firstAttButton);
	this.movesPanel.add(secondAttButton);
	this.movesPanel.add(thirdAttButton);
	this.movesPanel.add(fourthAttButton);
	
	this.monsterPanel.add(firstMonButton);
	this.monsterPanel.add(secondMonButton);
	this.monsterPanel.add(thirdMonButton);
	this.monsterPanel.add(fourthMonButton);
	
	this.choosePanel.add(leftNorthButton);
	this.choosePanel.add(rightNorthButton);
	this.choosePanel.add(leftSouthButton);
	this.choosePanel.add(rightSouthButton);
	
	this.southPanel.add(choosePanel,"choose");
	this.southPanel.add(monsterPanel,"monsters");
	this.southPanel.add(movesPanel,"moves");
	this.southPanel.add(itemsPanel,"items");
	this.cLayout.show(southPanel,"choose");
	this.mainPanel.add(topPanel, BorderLayout.NORTH);
	this.mainPanel.add(centerPanel,BorderLayout.CENTER);
	this.mainPanel.add(southPanel,BorderLayout.SOUTH);
	
	this.frame.add(mainPanel);
	this.frame.setVisible(true);
    }
}
