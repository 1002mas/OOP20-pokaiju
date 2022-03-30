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

import controller.BattleController;
import model.battle.Moves;
import model.battle.MovesData;

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
    private JButton leftNorthButton;
    private JButton rightNorthButton;
    private JButton leftSouthButton;
    private JButton rightSouthButton;
    private JButton firstAttButton;
    private JButton secondAttButton;
    private JButton thirdAttButton;
    private JButton fourthAttButton;
    private JLabel backgroundLabel;
    private BufferedImage background;
    private BattleController ctrl;
    private List<Moves> moves;
    private MovesData move;
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
	this.move = this.moves.get(0).getData();
	this.firstAttButton.setText("" + move.getName() + " " + move.getPP() + " PP");
	this.move = this.moves.get(1).getData();
	this.secondAttButton.setText("" + move.getName() + " " + move.getPP() + " PP");
	this.move = this.moves.get(2).getData();
	this.thirdAttButton.setText("" + move.getName() + " " + move.getPP() + " PP");
	this.move = this.moves.get(3).getData();
	this.fourthAttButton.setText("" + move.getName() + " " + move.getPP() + " PP");
	
    }
    void refresh() {
	this.playerMonster.setText(getCurrentPlayerMonsterData());
	System.out.println(ctrl.getEnemyCurrentMonsterHp());
	this.enemyMonster.setText(getCurrentEnemyMonsterData());
	
	
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
	this.rightNorthButton = new JButton("Capture");
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
		
		ctrl.chooseMove(0);
		refresh();
	    }
	    
	});
	this.secondAttButton = new JButton();
	this.thirdAttButton = new JButton();
	this.fourthAttButton = new JButton();
	
	this.movesPanel.add(firstAttButton);
	this.movesPanel.add(secondAttButton);
	this.movesPanel.add(thirdAttButton);
	this.movesPanel.add(fourthAttButton);
	
	
	this.choosePanel.add(leftNorthButton);
	this.choosePanel.add(rightNorthButton);
	this.choosePanel.add(leftSouthButton);
	this.choosePanel.add(rightSouthButton);
	
	this.southPanel.add(choosePanel,"choose");
	this.southPanel.add(movesPanel,"moves");
	this.cLayout.show(southPanel,"choose");
	this.mainPanel.add(topPanel, BorderLayout.NORTH);
	this.mainPanel.add(centerPanel,BorderLayout.CENTER);
	this.mainPanel.add(southPanel,BorderLayout.SOUTH);
	
	this.frame.add(mainPanel);
	this.frame.setVisible(true);
    }
}
