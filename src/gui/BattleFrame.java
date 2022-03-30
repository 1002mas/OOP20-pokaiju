package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.BattleController;
import model.battle.Moves;
import model.battle.MovesData;
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
    private JLabel backgroundLabel;
    private BufferedImage background;
    private BattleController ctrl;
    private List<Moves> moves;
    private List<Monster> playerTeam;
    private Moves move;
    private Monster monster;
    private CardLayout cLayout;
    
    public BattleFrame(BattleController ctrl) {
	this.ctrl = ctrl;
	start();
    }
    
    String getCurrentPlayerMonsterData() {
	return " " + ctrl.getPlayerCurrentMonsterName() + " " +  ctrl.getPlayerCurrentMonsterHp() + "/" + ctrl.getPlayerCurrentMonsterMaxHealth() + " HP " + "LVL." +ctrl.getPlayerCurrentMonsterLevel();
    }
    
    String getCurrentEnemyMonsterData() {
	return " " + ctrl.getEnemyCurrentMonsterName() + " " +  ctrl.getEnemyCurrentMonsterHp() + "/" + ctrl.getEnemyCurrentMonsterMaxHealth() + " HP " + "LVL." +ctrl.getEnemyCurrentMonsterLevel();
    }
    void setMoves() {
	this.moves = ctrl.getMoves();
	
	this.move = this.moves.get(0);
	this.firstAttButton.setText("" + move.getData().getName() + " " + move.getCurrentPP() + " PP");
	if(!this.move.checkPP()) {
	    this.firstAttButton.setEnabled(false);
	}
	this.move = this.moves.get(1);
	this.secondAttButton.setText("" + move.getData().getName() + " " + move.getCurrentPP() + " PP");
	if(!this.move.checkPP()) {
	    this.secondAttButton.setEnabled(false);
	}
	this.move = this.moves.get(2);
	this.thirdAttButton.setText("" + move.getData().getName() + " " + move.getCurrentPP() + " PP");
	if(!this.move.checkPP()) {
	    this.thirdAttButton.setEnabled(false);
	}
	this.move = this.moves.get(3);
	this.fourthAttButton.setText("" + move.getData().getName() + " " + move.getCurrentPP() + " PP");
	if(!this.move.checkPP()) {
	    this.fourthAttButton.setEnabled(false);
	}
	
    }
    void setMonster() {
  	this.playerTeam = ctrl.getPlayerTeam();
  	this.monster = this.playerTeam.get(0);
  	this.firstMonButton.setText("" + monster.getName());
//  	if(!this.move.checkPP()) {
//  	    this.firstMonButton.setEnabled(false);
//  	}
  	this.monster = this.playerTeam.get(1);
  	this.secondMonButton.setText("" + monster.getName());
//  	if(!this.move.checkPP()) {
//  	    this.secondAttButton.setEnabled(false);
//  	}
  	this.monster = this.playerTeam.get(2);
  	this.thirdMonButton.setText("" + monster.getName());
//  	if(!this.move.checkPP()) {
//  	    this.thirdAttButton.setEnabled(false);
//  	}
  	this.monster = this.playerTeam.get(3);
  	this.fourthMonButton.setText("" + monster.getName());
//  	if(!this.move.checkPP()) {
//  	    this.fourthAttButton.setEnabled(false);
//  	}
  	
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
	this.backgroundLabel = new JLabel(new ImageIcon(background));
	this.actionText = new JTextField("What do you want to do?...");
	this.actionText.setEditable(false);
	this.centerPanel.add(backgroundLabel, BorderLayout.CENTER);
	this.centerPanel.add(actionText, BorderLayout.SOUTH);
	
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
		setMonster();
		actionText.setText("What monster do u choose?");
		cLayout.show(southPanel, "monsters");
	    }
	    
	});
	this.leftSouthButton = new JButton("Equipment");
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
	this.firstAttButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		//actionText.setText("" + ctrl.getPlayerCurrentMonsterName() + " usa " + ctrl.getMoves().get(0).getName());
		
		
		
		ctrl.chooseMove(0);
		refresh();
	    }
	    
	});
	this.secondAttButton = new JButton();
	this.secondAttButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		
		ctrl.chooseMove(1);
		refresh();
	    }
	    
	});
	this.thirdAttButton = new JButton();
	this.thirdAttButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		
		ctrl.chooseMove(2);
		refresh();
	    }
	    
	});
	this.fourthAttButton = new JButton();
	this.fourthAttButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		
		ctrl.chooseMove(3);
		refresh();
	    }
	    
	});
	this.firstMonButton = new JButton();
	this.firstMonButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		
		ctrl.changeMonster(0);
		System.out.println(ctrl.getPlayerCurrentMonsterName());
		refresh();
	    }
	    
	});
	this.secondMonButton = new JButton();
	this.secondMonButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		
		ctrl.changeMonster(1);
		System.out.println(ctrl.getPlayerCurrentMonsterName());
		refresh();
	    }
	    
	});
	this.thirdMonButton = new JButton();
	this.fourthMonButton = new JButton();
	
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
	this.cLayout.show(southPanel,"choose");
	this.mainPanel.add(topPanel, BorderLayout.NORTH);
	this.mainPanel.add(centerPanel,BorderLayout.CENTER);
	this.mainPanel.add(southPanel,BorderLayout.SOUTH);
	
	this.frame.add(mainPanel);
	this.frame.setVisible(true);
    }
}
