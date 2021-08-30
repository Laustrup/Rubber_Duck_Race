package repositories.connection;

public class Access {

    /*
    Class created for hiding accessinformations of database
     */

    private String connection;
    private String username;
    private String password;

    protected String getConnection() {
        return connection;
    }

    protected String getUsername() {
        return username;
    }

    protected String getPassword() {
        return password;
    }
}
