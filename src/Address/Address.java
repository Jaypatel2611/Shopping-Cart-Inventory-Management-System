package Address;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Address {
    static Connection con;
    static {
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/myjdbc_2025?allowPublicKeyRetrieval=true&useSSL=false",
                    "jay_mysql",
                    "pass"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    String AddressLine1;
    String AddressLine2;
    String City;
    String State;
    int PinCode;
    String Country;

    public Address( String name,String addressLine1, String addressLine2, String area, String city, String state, int pinCode,Model.User user) throws SQLException {
        //User_id = user_id;
        AddressLine1 = addressLine1;
        AddressLine2 = addressLine2;
        City = city;
        State = state;
        PinCode = pinCode;
        String insertAddress = "INSERT INTO address(address_line_1, address_line_2, area, city, state, pincode) VALUES(?,?,?,?,?,?) WHERE username = ?";
        try (PreparedStatement insertStmt = con.prepareStatement(insertAddress)) {
            insertStmt.setString(1, addressLine1);
            insertStmt.setString(2, addressLine2);
            insertStmt.setString(3, area);
            insertStmt.setString(4, city);
            insertStmt.setString(5, state);
            insertStmt.setInt(6, pinCode);
            insertStmt.setString(7, user.getUserName());

            insertStmt.executeUpdate();
        }
    }
}
