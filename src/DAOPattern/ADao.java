package DAOPattern;


import DB.SingleConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class ADao {

    protected final Connection connection;

    // We might give the constructor an actual connection
    public ADao ()throws SQLException {

        connection = SingleConnection.getSingleton().getSingleConnection();
        System.out.println(connection.toString()); // Should print out the same object, as we are using a singleton

    }

}
