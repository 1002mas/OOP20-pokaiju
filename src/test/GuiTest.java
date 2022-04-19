package test;

import controller.DataControllerImpl;
import controller.PlayerControllerImpl;
import gui.GameFrameImpl;

public class GuiTest {

    public static void main(String[] args) {

	new GameFrameImpl(new PlayerControllerImpl(new DataControllerImpl()));

    }

}
