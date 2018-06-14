package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;

/**
 * Main application class.
 */
public class Minesweeper {
    /**
     * Constructor.
     */
    private Minesweeper() {
        ConsoleUI userInterface = new ConsoleUI();
        Field field = new Field(9, 9, 1);
        userInterface.newGameStarted(field);
        //nieco svoje napisane
    }

    /**
     * Main method.
     * @param args arguments
     */
    public static void main(String[] args) {
        new Minesweeper();
    }

}
