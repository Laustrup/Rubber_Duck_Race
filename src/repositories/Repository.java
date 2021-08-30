package repositories;

import repositories.connection.Database;
import services.Printer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class Repository {

    private Database database = new Database();
    private Connection connection;

    // Executes PreparedStatement and returns the ResultSet, needs a close connection.
    protected ResultSet getResultSet(String sql) {
        if (connectionStatus()) {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                return statement.executeQuery();
            }
            catch (Exception e) {
                new Printer().writeExceptionErr("Couldn't execute update...",e);
                database.closeConnection();
                return null;
            }
        }
        return null;
    }

    // Executes an sql-statement that will update an table in db, return false, if can not open connection...
    protected boolean updateTable(String sql, boolean closeConnection) {
        if(connectionStatus()) {
            System.out.println(database.isConnectionCurrentlyOpen());
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                System.out.println("statement prepared!");
                statement.executeUpdate();
                System.out.println("statement executed!");
            }
            catch (Exception e) {
                new Printer().writeExceptionErr("Couldn't execute update...",e);
                database.closeConnection();
            }
            if (closeConnection) {
                database.closeConnection();
            }
            return true;
        }
        return false;
    }

    private boolean connectionStatus() {
        if (!database.isConnectionCurrentlyOpen()) {
            if (database.openConnection()) {
                connection = database.getConnection();
            }
            else {
                return false;
            }
        }
        return true;
    }

    public boolean closeConnection() {
        if (database.isConnectionCurrentlyOpen()) {
            try {
                connection.close();
            }
            catch (Exception e) {
                new Printer().writeErr("Couldn't close connection...");
                return false;
            }
            return true;
        }
        return false;
    }

}
