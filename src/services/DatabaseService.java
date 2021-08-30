package services;

import models.Game;
import repositories.GameRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseService {

    private GameRepository repository = new GameRepository();

    public void showChances() {
        ResultSet res = repository.getGameTableInfo();

        // Local Attributes
        int totalWins = 0;
        int[] wins = new int[11];
        int round = 0;

        // Puts database values into attributes
        try {
            while (res.next()) {
                round++;
                totalWins += res.getInt("wins");
                wins[round-1] = res.getInt("wins");
            }
        }
        catch (Exception e) {
            new Printer().writeExceptionErr("Trouble with database",e);
        }

        // Calculates chances
        int[] tempWins = new int[11];
        for (int i = 0; i < wins.length;i++) {
            tempWins[i] = totalWins/wins[i] * 100;
        }
        wins = tempWins;

        new Printer().writeMessage("Chances for a win are:\n\n" +
                                    "--------------------------------------" +
                                    "\tRound 1 - " + wins[0] + "\n" +
                                    "\tRound 2 - " + wins[1] + "\n" +
                                    "\tRound 3 - " + wins[2] + "\n" +
                                    "\tRound 4 - " + wins[3] + "\n" +
                                    "\tRound 5 - " + wins[4] + "\n" +
                                    "\tRound 6 - " + wins[5] + "\n" +
                                    "\tRound 7 - " + wins[6] + "\n" +
                                    "\tRound 8 - " + wins[7] + "\n" +
                                    "\tRound 9 - " + wins[8] + "\n" +
                                    "\tRound 10 - " + wins[9] + "\n" +
                                    "\n\tChance of losing is - " + wins[10] + "\n" +
                                    "--------------------------------------\n");

        repository.closeConnection();
    }

    public void insertWin(Game game) {

        ResultSet res = repository.getGameTableInfo();
        int currentAmountOfWins = -1;

            try {
                while (res.next()) {
                    if (res.getInt("round")==game.getRound()) {
                        currentAmountOfWins = res.getInt("wins");
                    }
                }
            }
            catch (SQLException throwables) {
                new Printer().writeExceptionErr("Trouble with database",throwables);
            }

        if (currentAmountOfWins!=-1) {
            repository.updateGameTable(game.getRound(),currentAmountOfWins);
        }
        repository.closeConnection();
    }

    public void insertLose(Game game) {
        ResultSet res = repository.getGameTableInfo();
        int currentAmountOfWins = -1;

        try {
            while (res.next()) {
                if (res.getInt("round")==11) {
                    currentAmountOfWins = res.getInt("wins");
                }
            }
        }
        catch (SQLException throwables) {
            new Printer().writeExceptionErr("Trouble with database",throwables);
        }

        if (currentAmountOfWins!=-1) {
            repository.updateGameTable(11,currentAmountOfWins);
        }
        repository.closeConnection();
    }
}
