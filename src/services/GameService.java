package services;

import models.Game;

import java.util.Random;
import java.util.Scanner;

public class GameService {

    private Game game;
    private Printer printer = new Printer();
    private Analyst analyst = new Analyst();

    public void playMenu() {

        boolean typedIsValid = false;

        while (!typedIsValid) {
            printer.writeMessage("Pick an duck number from 1 to 100: (Press R for random)");

            String typed = new Scanner(System.in).nextLine();
            if (typed.equalsIgnoreCase("r")) {
                typedIsValid = true;
                playGame(renderRandomDuckId());
            }
            if (analyst.analyseChosenDuckId(typed)) {
                typedIsValid = true;
                playGame(Integer.parseInt(typed));
            }
        }
    }

    private int renderRandomDuckId() {
        int duckId = new Random().nextInt(100)+1;
        printer.writeMessage("You're duck number is " + duckId);
        return duckId;
    }

    private void playGame(int playersDuckId) {
        game = new Game(true,playersDuckId);
        for (int i = 0; i < 9;i++) {
            game.showGameDetails();

            if (game.hasWon()) {
                break;
            }
            game.nextRound();
        }
    }

}
