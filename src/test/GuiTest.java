package test;

import controller.PlayerControllerImpl;
import gui.GameFrame;

public class GuiTest {

    public static void main(String[] args) {

	
	GameFrame newGame = new GameFrame(new PlayerControllerImpl(null));
	
    }

}
