package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.PlayerController;

public class GameFrame extends JFrame {

    private static final long serialVersionUID = -7927156597267134363L;
    private /* final */ PlayerController playerController;

    public GameFrame(PlayerController playerController) {
	this.playerController = playerController;
	/*
	 * TODO set size, cardLayout, first menu (panel 1) 
	 * */
	
    }

    //TODO create player + map (panel 2)
    private JPanel buildMapPanel() {
	return new JPanel();
    }

    //TODO create battle scene (panel 3)
    private JPanel buildBattlePanel() {
	return new JPanel();
    }

    //TODO create menu (panel 4)
    private JPanel buildMenuPanel() {
	return new JPanel();
    }
    private void changePanel(String name) {}
}
