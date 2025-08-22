package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    static Connection con = null;
    static public Connection getCon() throws Exception
    {

        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/Shopping_Cart?allowPublicKeyRetrieval=true&useSSL=false",
                "root",
                "pass"
        );
        return con;
    }




}
