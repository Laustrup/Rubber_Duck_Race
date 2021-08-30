package repositories.connection;

import services.Printer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Database {

    /*
    Controls the connection of database,
    but since there's no created an pom.xml file, there's not an driver for mysql yet...
     */

    private Properties properties;
    private Connection connection;

    private Access access = new Access();

    private String dbConnection;
    private String username;
    private String password;

    private boolean isConnectionOpen = false;

    // Method to create connection, if it fails it returns null, otherwise returns the created connection
    public boolean openConnection() {
        try (InputStream stream = new FileInputStream("src/main/resources/application.properties")){
            properties = new Properties();
            properties.load(stream);
            dbConnection = access.getConnection();
            username = access.getUsername();
            password = access.getPassword();
            connection = DriverManager.getConnection(dbConnection,username,password);
            new Printer().writeMessage("Connection " + connection + " is opened!");
            isConnectionOpen = true;
            return true;
        }
        catch (Exception e) {
            new Printer().writeExceptionErr("Couldn't create connection...",e);
            return false;
        }
    }

    public boolean closeConnection() {
        try {
            connection.close();
            isConnectionOpen = false;
            return true;
        }
        catch (java.lang.Exception e) {
            new Printer().writeErr("Couldn't close connection...");
            return false;
        }
    }

    public boolean isConnectionCurrentlyOpen() {
        return isConnectionOpen;
    }

    public Connection getConnection() {
        return connection;
    }
}

