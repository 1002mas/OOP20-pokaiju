package test;

import javax.swing.JFrame;

import gui.EvolutionPanel;
import gui.GameFrame;
import gui.ImagesLoader;
import model.Pair;

public class EvolutionTest {
    private static EvolutionPanel evolve;
    private static JFrame frame;
    public static void main(String[] args) {
	// TODO Auto-generated method stub
	frame = new JFrame();
	frame.setBounds(0, 0, 1024, 1024);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	evolve = new EvolutionPanel(new Pair<String,String>("kracez","bibol"),null, new ImagesLoader(1024,1024,1,1));
	frame.add(evolve);
	evolve.setVisible(false);
	evolve.setVisible(true);
	frame.setVisible(true);
    }

}
