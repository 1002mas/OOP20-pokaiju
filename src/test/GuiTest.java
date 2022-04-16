package test;

import controller.PlayerControllerImpl;
import controller.json.DataControllerImpl;
import gui.GameFrame;
import gui.GameFrameImpl;

public class GuiTest {

    public static void main(String[] args) {

	
	GameFrame newGame = new GameFrameImpl(new PlayerControllerImpl(new DataControllerImpl()));
	
    }

}
