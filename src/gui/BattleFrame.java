package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BattleFrame {
    private JFrame frame;
    private JTextField actionText; 
    private JTextField playerMonster; 
    private JTextField enemyMonster;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel southPanel;
    private JButton leftNorthButton;
    private JButton rightNorthButton;
    private JButton leftSouthButton;
    private JButton rightSouthButton;
    private JLabel backgroundLabel;
    BufferedImage background;
    
    public BattleFrame() {
	this.frame= new JFrame();
	this.frame.setSize(1024, 792);
	this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.mainPanel = new JPanel();
	this.mainPanel.setBounds(0, 0, 1024, 792);
	this.mainPanel.setLayout(new BorderLayout());
	this.topPanel = new JPanel();
	this.topPanel.setLayout(new GridLayout());
	this.centerPanel = new JPanel();
	this.centerPanel.setLayout(new BorderLayout());
	this.southPanel = new JPanel();
	this.southPanel.setLayout(new GridLayout());
	
	this.playerMonster = new JTextField(); //ctrl function
	this.enemyMonster = new JTextField(); //ctrl function
	this.topPanel.add(playerMonster);
	this.topPanel.add(enemyMonster);
	
	try {
	    this.background = ImageIO.read(new File("Background.png"));
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	this.backgroundLabel = new JLabel(new ImageIcon(background));
	this.actionText = new JTextField("Cosa vuoi fare?...");
	this.centerPanel.add(backgroundLabel, BorderLayout.CENTER);
	this.centerPanel.add(actionText, BorderLayout.SOUTH);
	
	this.leftNorthButton = new JButton("Attack");
	this.rightNorthButton = new JButton("Capture");
	this.leftSouthButton = new JButton("Equipment");
	this.rightSouthButton = new JButton("Get Out");
	this.southPanel.add(leftNorthButton);
	this.southPanel.add(rightNorthButton);
	this.southPanel.add(leftSouthButton);
	this.southPanel.add(rightSouthButton);
	
	this.mainPanel.add(topPanel, BorderLayout.NORTH);
	this.mainPanel.add(centerPanel,BorderLayout.CENTER);
	this.mainPanel.add(southPanel,BorderLayout.SOUTH);
	
	this.frame.add(mainPanel);
	this.frame.setVisible(true);
    }
}
