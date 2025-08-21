package Modules.Address;

import Database.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;



import Database.Database;
import Modules.Users.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Address {
    private String name;
    private String addressLine;
    private String area;
    private String city;
    private String state;
    private int pinCode;

    public Address(String name, String addressLine, String area,
                   String city, String state, int pinCode, User user) throws Exception {

        this.name = name;

        this.addressLine = addressLine;
        this.area = area;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;

        String insertAddress = "INSERT INTO address(user_id, name, address_line, area, city, state, pincode) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement insertStmt = Database.getCon().prepareStatement(insertAddress)) {
            insertStmt.setInt(1, User.getCurrentUser().getUserId());
            insertStmt.setString(2, name);

            insertStmt.setString(3, addressLine);
            insertStmt.setString(4, area);
            insertStmt.setString(5, city);
            insertStmt.setString(6, state);
            insertStmt.setInt(7, pinCode);

            insertStmt.executeUpdate();
            System.out.println("✅ Address saved successfully!");
        }
    }

    // Method to ask details from user
    public static void askAndSaveAddress(User user) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter recipient name: ");
        String name = sc.nextLine();

        System.out.print("Enter Address Line : ");
        String addressLine = sc.nextLine();

        System.out.print("Enter Area: ");
        String area = sc.nextLine();

        System.out.print("Enter City: ");
        String city = sc.nextLine();

        System.out.print("Enter State: ");
        String state = sc.nextLine();

        System.out.print("Enter Pin Code: ");
        int pinCode = sc.nextInt();

        try {
            new Address(name,addressLine, area, city, state, pinCode, user);
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
