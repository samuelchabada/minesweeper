package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.UserInterface;
import minesweeper.core.*;


public class ConsoleUI implements UserInterface {

    private Field field;

    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void newGameStarted(Field field) {
        this.field = field;
        do {
            update();
            processInput();
        } while (field.getState() == GameState.PLAYING);

        update();

        if(field.getState() == GameState.SOLVED)
            System.out.println("Solved");
        else
            System.out.println("Failed");
    }

    @Override
    public void update() {
        {
            System.out.print("  ");
            for (int column = 1; column <= field.getRowCount(); column++) {
                System.out.printf("%c ", '0' + column);
            }
            System.out.println();

            for (int row = 0; row < field.getRowCount(); row++) {
                System.out.printf("%c", 'A' + row);
                for (int column = 0; column < field.getColumnCount(); column++) {

                    Tile tile = field.getTile(row, column);

                    switch (tile.getState()) {
                        case OPEN:
                            if (tile instanceof Clue)
                                System.out.print(" " + ((Clue) tile).getValue());
                            else
                                System.out.print(" X");
                            break;
                        case CLOSED:
                            System.out.print(" -");
                            break;
                        case MARKED:
                            System.out.print(" M");
                            break;
                    }
                }
                System.out.println();
            }
        }
    }

    private void processInput() {

        System.out.println(".....................................");
        System.out.println(" X – ukončenie hry, MA1 – označenie dlaždice v riadku A a stĺpci 1, OB4 – odkrytie dlaždice v riadku B a stĺpci 4.");


        String s = readLine().toUpperCase();
        Pattern p1 = Pattern.compile("O[A-I][0-9]");
        Matcher m1 = p1.matcher(s);

        Pattern p2 = Pattern.compile("M[A-Z][0-9]");
        Matcher m2 = p2.matcher(s);

        Pattern p3 = Pattern.compile("X");
        Matcher m3 = p3.matcher(s);


        if (m1.matches()) {
            int row = s.charAt(1) - 'A';
            int column = s.charAt(2) - '1';
            field.openTile(row, column);
        } else if (m2.matches()) {
            int row = s.charAt(1) - 'A';
            int column = s.charAt(2) - '1';
            field.markTile(row, column);
        }
        else if (m3.matches()) {
            field.setState(GameState.FAILED);
            System.out.println("END GAME");
        }
        else
            System.out.println("Zle zadane vstupne data");
    }
}
