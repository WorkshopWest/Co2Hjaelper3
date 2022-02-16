package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2db {

    static final String url = "jdbc:h2:~/Co2_db";
    static final String user = "";
    static final String pass = "";

    public H2db(){


    }

    public Connection getConnection()throws SQLException {
        Connection conn;
        conn = DriverManager.getConnection(url, user, pass);

        return conn;
    }
}
