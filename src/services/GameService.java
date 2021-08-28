package services;

import models.Game;

public class GameService {

    private Game game;

    public void playGame() {
        game = new Game(true);
        for (int i = 0; i < 9;i++) {
            game.showGameDetails();
            game.nextRound();
        }
    }

    // Method for unit-testing game
    public Game getGame() {
        return game;
    }
}
