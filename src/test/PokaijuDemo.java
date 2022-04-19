package test;

import controller.DataControllerImpl;
import controller.PlayerControllerImpl;
import gui.GameFrameImpl;

public final class PokaijuDemo {

    private PokaijuDemo() {

    }

    public static void main(final String[] args) {
        new GameFrameImpl(new PlayerControllerImpl(new DataControllerImpl()));
    }
}
