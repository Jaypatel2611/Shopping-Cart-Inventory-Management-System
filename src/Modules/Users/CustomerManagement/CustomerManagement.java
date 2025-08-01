package Modules.Users.CustomerManagement;

import Database.Database;
import Modules.Address.Address;
import Modules.Users.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerManagement {

    static User loggedInUser;
    static Scanner sc = new Scanner(System.in);

    CustomerManagement(User loggedInUser) {
        CustomerManagement.loggedInUser = loggedInUser;
    }

    private static void addAddress() throws SQLException {
        System.out.println("Enter Address In formatted way:");

        // Only flush newline if you called sc.nextInt() or sc.next() before this block
        sc.nextLine(); // 🔄 flush leftover newline if any

        System.out.print("Enter Address Line 1: ");
        String addressLine1 = sc.nextLine();

        System.out.print("Enter Address Line 2: ");
        String addressLine2 = sc.nextLine();

        System.out.print("Enter Area: ");
        String area = sc.nextLine();

        System.out.print("Enter City: ");
        String city = sc.nextLine();

        System.out.print("Enter State: ");
        String state = sc.nextLine();

        System.out.print("Enter Pin Code: ");
        int pinCode = sc.nextInt();


        new Address(loggedInUser.getFirstName(), addressLine1, addressLine2, area, city, state, pinCode, loggedInUser);
    }

    private static void payment() throws InterruptedException {
        System.out.println("1.Cash On Delivery\n2.Pay Online\n\nSelect Mode of Payment : ");
        int payMode = sc.nextInt();
        sc.nextLine(); // clear the newline character after nextInt()

        switch (payMode) {
            case 1:
                System.out.println("✅ You have selected *Cash On Delivery*.");
                System.out.println("📦 Your order will be placed successfully and shipped soon!");
                // Place order logic for COD here
                break;

            case 2:
                System.out.println("💳 You have selected *Pay Online*.");
                System.out.println("🔐 Redirecting to payment gateway...");
                Thread.sleep(5000);
                // TODO: add online payment method
                break;

            default:
                System.out.println("❌ Invalid payment mode selected. Please choose 1 or 2.");
                // You may loop back or prompt again
                break;
        }
    }

    private static void searchProduct() throws SQLException {
        System.out.print("Enter Product Name : ");
        String productName = sc.nextLine().toLowerCase();

        String fetchProduct = "SELECT productName , description , Price FROM Product WHERE productName = ?";
        try (PreparedStatement insertStmt = Database.getCon().prepareStatement(fetchProduct)) {
            insertStmt.setString(1, productName);
            ResultSet rs = insertStmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                String name = rs.getString("productName");
                String description = rs.getString("description");
                double price = rs.getDouble("Price");

                System.out.println("Product Name : " + name);
                System.out.println("Description  : " + description);
                System.out.println("Price        : ₹" + price);
                System.out.println("----------------------------------");
            }
            if (!found) {
                System.out.println("No product found with the name: " + productName);
            }
        }
    }

    private static void viewCategories() {

    }

    private static void profileManagement() throws SQLException {
        System.out.println("1.FirstName\n2.LastName\n3.UserName\n4.Password\n5. Add Address\n6.EXIT\n\nEnter your Choice : ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.print("Enter new FirstName : ");
                String newFirstName = sc.next();
                String updateFirstName = "UPDATE users SET first_name = ? WHERE username = ?";
                try (PreparedStatement updateStmt = Database.getCon().prepareStatement(updateFirstName)) {
                    updateStmt.setString(1, newFirstName);
                    updateStmt.setString(2, loggedInUser.getUserName());
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ First Name Updated Successfully");
                loggedInUser.setFirstName(newFirstName);
                break;
            case 2:
                System.out.print("Enter new LastName : ");
                String newLastName = sc.next();
                String updateLastName = "UPDATE users SET last_name = ? WHERE userName = ?";
                try (PreparedStatement updateStmt = Database.getCon().prepareStatement(updateLastName)) {
                    updateStmt.setString(1, newLastName);
                    updateStmt.setString(2, loggedInUser.getLastName());
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ Last Name Updated Successfully");
                loggedInUser.setLastName(newLastName);
                break;
            case 3:
                System.out.print("Enter new UserName : ");
                String newUserName = sc.next();
                String updateUserName = "UPDATE users SET username = ? WHERE username = ?";
                try (PreparedStatement updateStmt = Database.getCon().prepareStatement(updateUserName)) {
                    updateStmt.setString(1, newUserName);
                    updateStmt.setString(2, loggedInUser.getUserName());
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ User Name Updated Successfully");
                loggedInUser.setUserName(newUserName);
                break;
            case 4:
                System.out.println(loggedInUser.getPassword());
                System.out.print("Enter new Password : ");
                String newPassword = sc.next();
                String updatePassword = "UPDATE users SET password = ? WHERE username = ?";
                try (PreparedStatement updateStmt = Database.getCon().prepareStatement(updatePassword)) {
                    updateStmt.setString(1, newPassword);
                    updateStmt.setString(2, loggedInUser.getUserName());
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ Password Updated Successfully");
                loggedInUser.setPassword(newPassword);
                break;
            case 5:
                addAddress();
                System.out.println("✅ Address Added Successfully");
                break;
            case 6:
                System.out.println("EXITING");
                break;
            default:
                System.out.println("Invalid Choice");
        }
    }

    private static void viewCart() throws SQLException, InterruptedException {
        String fetchCart = "SELECT p.productName, c.quantity, c.price " +
                "FROM cart c JOIN product p ON c.product_id = p.product_id " +
                "WHERE c.username = ?";
        try (PreparedStatement fetchCartItems = Database.getCon().prepareStatement(fetchCart)) {
            fetchCartItems.setString(1, loggedInUser.getUserName());
            ResultSet rs = fetchCartItems.executeQuery();

            System.out.println("\n----------- 🛒 Your Cart -----------");
            boolean hasItems = false;
            double total = 0;

            while (rs.next()) {
                hasItems = true;
                String productName = rs.getString("productName");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                double itemTotal = price * quantity;
                total += itemTotal;

                System.out.println("Product Name   : " + productName);
                System.out.println("Quantity       : " + quantity);
                System.out.println("Price per unit : ₹" + price);
                System.out.println("Total          : ₹" + itemTotal);
                System.out.println("-----------------------------------");
            }
            if (!hasItems) {
                System.out.println("🛒 Your cart is empty.");
            } else {
                System.out.println("🧾 Grand Total: ₹" + total);
                System.out.println("1.CheckOut\n2.Back\n\nEnter Choice : ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        // Call your checkout method or logic here
                        System.out.println("✅ Proceeding to checkout...");
                        checkOut();
                        break;
                    case 2:
                        System.out.println("🔙 Returning to previous menu...");
                        break;
                    default:
                        System.out.println("❌ Invalid choice! Please select 1 or 2.");
                }
            }
        }
    }

    private static void checkOut() throws SQLException, InterruptedException {
        String fetchAddress = "SELECT name,address_line_1, address_line_2, area, city, state, pincode " +
                "FROM address WHERE username = ?";
        try (PreparedStatement fetchCartItems = Database.getCon().prepareStatement(fetchAddress)) {
            fetchCartItems.setString(1, loggedInUser.getUserName());
            ResultSet rs = fetchCartItems.executeQuery();
            boolean flag = true;
            boolean found = false;

            while (rs.next()) {
                String name = rs.getString("name");
                String address1 = rs.getString("address_line_1");
                String address2 = rs.getString("address_line_2");
                String area = rs.getString("area");
                String city = rs.getString("city");
                String state = rs.getString("state");
                int pincode = rs.getInt("pincode");

                System.out.println("\n📦 Saved Modules.Address:");
                System.out.println("Name         : " + name);
                System.out.println("Modules.Address Line1: " + address1);
                System.out.println("Modules.Address Line2: " + address2);
                System.out.println("Area         : " + area);
                System.out.println("City         : " + city);
                System.out.println("State        : " + state);
                System.out.println("Pin Code     : " + pincode);
                System.out.println("----------------------------------");

                if (flag) {
                    System.out.println("Want to Delivered to your default address");
                    String choice = sc.next();
                    if (choice.equalsIgnoreCase("yes")) {
                        System.out.println("✅ Delivery address Confirmed.");
                        break;
                    } else {
                        flag = false;
                    }
                }
            }
            if (!found) {
                System.out.println("❌ No address found. You can add a new address");
                addAddress();
                System.out.println("✅ Address Added Successfully");
            }
        }
        System.out.println("Choose your Modules.Address ID : ");
        int address_id = sc.nextInt();
        System.out.println("Delivered to " + address_id);
        System.out.println("1.Proceed To Pay\n2.Back\n\nEnter your choice : ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                payment();
                break;
            case 2:
                System.out.println("↩️ Returning to previous menu...");
                // Possibly call main menu or cart view method again
                break;
            default:
                System.out.println("❌ Invalid choice! Please enter 1 or 2.");
        }
    }

    public static void viewOrders() {

    }

    public static void start(User loggedInUser) throws InterruptedException {
        new CustomerManagement(loggedInUser);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n----- 🛍️ Welcome to Shopping Cart - Inventory Management System -----");
            System.out.println("1. 🔍 Search Product");
            System.out.println("2. 🗂️ Categories");
            System.out.println("3. 🛒 View Cart");
            System.out.println("4. 👤 Profile Management");
            System.out.println("5. 📦 My Orders");
            System.out.println("6. 🚪 Logout / Exit");
            System.out.println("7. 💳 Back To Last Menu");
            System.out.print("Choose an option (1-7): ");

            int choice = sc.nextInt();
            sc.nextLine(); // Database.getCon()some newline

            try {
                switch (choice) {
                    case 1:
                        searchProduct();
                        break;
                    case 2:
                        viewCategories();
                        break;
                    case 3:
                        viewCart();
                        break;
                    case 4:
                        // Fully Working
                        profileManagement();
                        break;
                    case 5:
                        viewOrders();
                        break;
                    case 6:
                        System.out.println("👋 Thank you for visiting! Goodbye.");
                        return;
                    case 7:
                        // TODO : implement logic for back menu
                        break;
                    default:
                        System.out.println("❌ Invalid choice. Please enter between 1 and 7.");
                }
            } catch (SQLException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }
}
