package repositories;

import java.sql.ResultSet;

public class GameRepository extends Repository {

    public void updateGameTable(int round, int currentAmountOfWins) {

        currentAmountOfWins++;
        int totalWins = currentAmountOfWins;
        updateTable("UPDATE game_table SET " +
                "win = " + totalWins +
                " WHERE round = " + round + ";",true);
    }

    public ResultSet getGameTableInfo() {
        return getResultSet("SELECT * FROM game_table;");
    }
}
