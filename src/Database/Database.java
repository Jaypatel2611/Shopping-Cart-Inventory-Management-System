package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    static Connection con;

    static {
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/Shopping_Cart?allowPublicKeyRetrieval=true&useSSL=false",
                    "root",
                    "pass"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getCon() {
        return con;
    }
}
