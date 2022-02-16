// Singleton for connection

package DB;

import java.sql.Connection;
import java.sql.SQLException;

public class SingleConnection {

    private static SingleConnection singleConnection;

    static {
        try {
            singleConnection = new SingleConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection connection;

    private SingleConnection()throws SQLException {

        /*DatabaseConnectionManager dbConnectionManager = new DatabaseConnectionManager("localhost", "vare_db", "root", "dbManager2021.");
        connection = dbConnectionManager.getConnection();
        */
        H2db h2db = new H2db();
        connection = h2db.getConnection();

    }

    public static SingleConnection getSingleton (){

      return singleConnection;
    }

    public Connection getSingleConnection (){

    return connection;
    }
    public void closeConnection() throws SQLException {
        connection.close();

    }


}
