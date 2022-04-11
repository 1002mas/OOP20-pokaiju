package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import controller.BattleController;
import controller.ImagesLoader;

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
    private BattleController ctrl;
    private ImagesLoader img;
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
	this.movesMap = new HashMap<>();
	movesPanel.removeAll();
	for(var move :  ctrl.getMoves()) {
	    JButton button = new JButton("" + move + " " + ctrl.getCurrentPP(move) + " PP");
	    this.movesMap.put(button,move);
	    if(this.ctrl.checkPP(move)) {
		    button.setEnabled(false);
		}
		else {
		    button.setEnabled(true);
		}
	    button.addActionListener(e-> {
		
		ctrl.chooseMove(this.movesMap.get(e.getSource()));
	
		if(!ctrl.isAlive(ctrl.getPlayerCurrentMonsterId())) {
		    actionText.setText(ctrl.getPlayerCurrentMonsterName() + " is dead");
		    playerMonster.setText(getCurrentPlayerMonsterData());
		    if(ctrl.isOver()) {
			//ENDING BATTLE player team dead
			actionText.setText("You lose...");
		    }else {
			setMonster();
			cLayout.show(southPanel, "monsters");
		    }  
		}
		else {
		    if(ctrl.isOver()) {
			//ENDING BATTLE enemy team dead
			actionText.setText("You have defeated all the enemies!!");
			System.out.println("i nemici sono morti");
		    }
		    else {
			refresh();  
		    }
		}
	    });
	    movesPanel.add(button);
	}
    }
    void setMonster() {
	monsterPanel.removeAll();
	monsterMap=new HashMap<>();
  	for(var monsterId : ctrl.getPlayerTeam()) {
  	    JButton button = new JButton(ctrl.getMonsterName(monsterId));
  	    this.monsterMap.put(button,monsterId);
  	    if(!this.ctrl.isAlive(monsterId)) { 
  		button.setEnabled(false);
    	    }
  	    
  	    button.addActionListener(e-> {  
		if(itemsFlag) {
		    System.out.println(itemUsed);
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
  	    monsterPanel.add(button);
  	}
    }
    void setItems() {
	itemsPanel.removeAll();
	itemMap = new HashMap<>();
   	for(var itemName :ctrl.getAllPlayerItems()) {
   	    JButton button = new JButton(itemName + " " + ctrl.getItemNumber(itemName));
   	    itemMap.put(button,itemName);
   	    
   	    button.addActionListener(e -> {
	    	this.itemUsed = this.itemMap.get(e.getSource());
	    	if(this.ctrl.isCaptureItem(itemUsed)) {
	    	    this.ctrl.useItem(itemUsed, 0);
	    	    if(ctrl.isEnemyCaught()) {
	    		//ENDING BATTLE
	    		actionText.setText("You have captured a new Monster!!");
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
   	    itemsPanel.add(button);
   	}	
     }
    void refresh() {
	this.playerMonster.setText(getCurrentPlayerMonsterData());
	this.enemyMonster.setText(getCurrentEnemyMonsterData());
	this.setMoves();
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
	
	
	
	this.actionText = new JTextField("What do you want to do?...");
	this.actionText.setEditable(false);
	
	this.centerPanel.add(actionText, BorderLayout.SOUTH);
	this.setImg();
	this.leftNorthButton = new JButton("Attack");
	this.leftNorthButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		setMoves();
		actionText.setText("What move do u choose?");
		cLayout.show(southPanel, "moves");
	    }
	    
	});
	this.rightNorthButton = new JButton("Change Monster");
	this.rightNorthButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
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
		setItems();
		actionText.setText("What item do u choose?");
		cLayout.show(southPanel, "items");
	    }
	    
	});
	this.rightSouthButton = new JButton("Get Out");
	this.rightSouthButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if(ctrl.flee()) {
		    actionText.setText("You successfully escaped");
		    //return to map
		}else {
		    actionText.setText("You failed to escaped");    
		}
	    } 
	});
	
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
