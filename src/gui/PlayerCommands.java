package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerCommands implements KeyListener {
    private final char moveUp = 'w';
    private final char moveDown = 's';
    private final char moveLeft = 'a';
    private final char moveRight = 'd';
    private final char menuCommand = 'x';
    private final GameFrame gui;
    private long lastPressProcessed = 0;

    public PlayerCommands(GameFrame gui) {
    this.gui = gui;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    if (System.currentTimeMillis() - lastPressProcessed > 80) {
        switch (e.getKeyChar()) {
        case moveUp:
        gui.movePlayer(Direction.UP);
        break;
        case moveDown:
        gui.movePlayer(Direction.DOWN);
        break;
        case moveLeft:
        gui.movePlayer(Direction.LEFT);
        break;
        case moveRight:
        gui.movePlayer(Direction.RIGHT);
        break;
        case menuCommand:
        gui.changePanel(GameFrame.MENU_PANEL);
        break;

        default:
        break;
        }
        lastPressProcessed = System.currentTimeMillis();
    }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}



