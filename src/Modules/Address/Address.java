package Modules.Address;

import Database.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Address {
    String name;
    String AddressLine1;
    String AddressLine2;
    String City;
    String State;
    int PinCode;

    public Address(String name, String addressLine1, String addressLine2, String area, String city, String state, int pinCode, Modules.Users.User user) throws SQLException {
        this.name = name;
        AddressLine1 = addressLine1;
        AddressLine2 = addressLine2;
        City = city;
        State = state;
        PinCode = pinCode;

        String insertAddress = "INSERT INTO address(user_id,name,address_line_1, address_line_2, area, city, state, pincode) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement insertStmt = Database.getCon().prepareStatement(insertAddress)) {
            insertStmt.setInt(1, user.getUserId());
            insertStmt.setString(2, name);
            insertStmt.setString(3, addressLine1);
            insertStmt.setString(4, addressLine2);
            insertStmt.setString(5, area);
            insertStmt.setString(6, city);
            insertStmt.setString(7, state);
            insertStmt.setInt(8, pinCode);

            insertStmt.executeUpdate();
        }
    }
}
