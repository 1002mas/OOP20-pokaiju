package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import controller.Direction;

public class PlayerCommands implements KeyListener {
    private static final int INPUT_RESPONSE_TIME = 40;

    private static final char MOVE_UP = 'w';
    private static final char MOVE_DOWN = 's';
    private static final char MOVE_LEFT = 'a';
    private static final char MOVE_RIGHT = 'd';
    private static final char MENU_COMMAND = 'x';
    private static final char INTERACTION_COMMAND = 'z';
    private final GameFrame gui;

    private long lastPressProcessed;
    private boolean canMove = true;

    public PlayerCommands(final GameFrame gui) {
        this.gui = gui;
    }

    @Override
    public void keyTyped(final KeyEvent e) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        if (System.currentTimeMillis() - lastPressProcessed > INPUT_RESPONSE_TIME) {
            if (canMove) {
                switch (e.getKeyChar()) {
                case MOVE_UP:
                    gui.movePlayer(Direction.UP);
                    break;
                case MOVE_DOWN:
                    gui.movePlayer(Direction.DOWN);
                    break;
                case MOVE_LEFT:
                    gui.movePlayer(Direction.LEFT);
                    break;
                case MOVE_RIGHT:
                    gui.movePlayer(Direction.RIGHT);
                    break;
                case MENU_COMMAND:
                    gui.updateView("menu");
                    break;
                case INTERACTION_COMMAND:
                    this.canMove = gui.playerInteraction();
                    break;

                default:
                    break;
                }
            } else if (e.getKeyChar() == INTERACTION_COMMAND) {
                gui.endPlayerInteraction();
                this.canMove = true;
            }
            lastPressProcessed = System.currentTimeMillis();
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {

    }

}
