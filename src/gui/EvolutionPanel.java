package gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.PlayerController;

public class EvolutionPanel extends JPanel{
    
   
    private PlayerController ctrl;
    
    public EvolutionPanel(PlayerController ctrl) {
	this.ctrl = ctrl;
	init();
    }
    private void init() {
	JTextField actionText = new JTextField();
	JLabel img;
	
	this.setLayout(new BorderLayout());
	
	this.add(actionText,BorderLayout.SOUTH);
    }
}
