package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConnectionManager {
    private final String url;
    private final Properties properties;



    public DatabaseConnectionManager(String host, String databaseName, String username, String password){
        this.url = "jdbc:mysql://"+host+"/"+databaseName+"?serverTimezone=UTC"; // Skal lave denne forbindelsesstreng  "jdbc:mysql://localhost/test_db?serverTimezone=UTC"
        this.properties = new Properties();
        this.properties.setProperty("user", username);
        this.properties.setProperty("password", password);


    }

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(this.url, this.properties);
    }

}
