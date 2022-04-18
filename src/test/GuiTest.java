package test;

import controller.PlayerControllerImpl;
import controller.json.DataControllerImpl;
import gui.GameFrameImpl;

public class GuiTest {

    public static void main(String[] args) {

	new GameFrameImpl(new PlayerControllerImpl(new DataControllerImpl()));

    }

}
