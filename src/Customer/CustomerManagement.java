package Customer;

import Address.Address;
import Model.User;

import java.sql.*;
import java.util.Scanner;
class Customer {
    Model.User user;
    Scanner sc =new Scanner(System.in);
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

    public Customer(Model.User user) {
        this.user = user;
    }

    private void addAddress() throws SQLException {
        System.out.println("Enter Address In formatted way ");
        System.out.println("Enter Address Line 1 : ");
        String addressLine1 = sc.nextLine();
        System.out.println("Enter Address Line 2 : ");
        String addressLine2 = sc.nextLine();
        System.out.println("Enter Area : ");
        String area = sc.nextLine();
        System.out.println("Enter City : ");
        String city = sc.nextLine();
        System.out.println("Enter State : ");
        String state = sc.nextLine();
        System.out.println("Enter Pin code : ");
        int pinCode = sc.nextInt();

        Address add = new Address(user.getFirstName(),addressLine1,addressLine2,area,city,state,pinCode,user);
    }

    private void payment() {
        System.out.println("1.Cash On Delivery\n2.Pay Online\n\nSelect Mode of Payment : ");
        int payMode = sc.nextInt();
        sc.nextLine(); // clear the newline character after nextInt()

        switch (payMode) {
            case 1:
                System.out.println("✅ You have selected *Cash On Delivery*.");
                System.out.println("📦 Your order will be placed and shipped soon!");
                // Place order logic for COD here
                break;

            case 2:
                System.out.println("💳 You have selected *Pay Online*.");
                System.out.println("Redirecting to online payment gateway...");
                // Call your payment method here, e.g., payOnline();
                break;

            default:
                System.out.println("❌ Invalid payment mode selected. Please choose 1 or 2.");
                // You may loop back or prompt again
                break;
        }
    }

    public void searchProduct() throws SQLException {
        System.out.print("Enter Product Name : ");
        String productName = sc.nextLine().toLowerCase();

        String fetchProduct = "SELECT productName , description , Price FROM Product WHERE productName = ?";
        try (PreparedStatement insertStmt = con.prepareStatement(fetchProduct)) {
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

    public void viewCategories() {

    }

    public void profileManagement() throws SQLException {
        System.out.println("1.FirstName\n2.LastName\n3.UserName\n4.Password\n5. Add Address\n6.EXIT\n\nEnter your Choice : ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.print("Enter new FirstName : ");
                String newFirstName = sc.next();
                String updateFirstName = "UPDATE user SET firstName = ? WHERE userName = ?";
                try (PreparedStatement updateStmt = con.prepareStatement(updateFirstName)) {
                    updateStmt.setString(1,newFirstName);
                    updateStmt.setString(2,user.getUserName());
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ First Name Updated Successfully");
                user.setFirstName(newFirstName);
                break;
            case 2:
                System.out.print("Enter new LastName : ");
                String newLastName = sc.next();
                String updateLastName = "UPDATE user SET lastName = ? WHERE userName = ?";
                try (PreparedStatement updateStmt = con.prepareStatement(updateLastName)) {
                    updateStmt.setString(1,newLastName);
                    updateStmt.setString(2, user.getLastName());
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ Last Name Updated Successfully");
                user.setLastName(newLastName);
                break;
            case 3:
                System.out.print("Enter new UserName : ");
                String newUserName = sc.next();
                String updateUserName = "UPDATE user SET userName = ? WHERE userName = ?";
                try (PreparedStatement updateStmt = con.prepareStatement(updateUserName)) {
                    updateStmt.setString(1,newUserName);
                    updateStmt.setString(2, user.getUserName());
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ User Name Updated Successfully");
                user.setUserName(newUserName);
                break;
            case 4:
                System.out.print("Enter new Password : ");
                String newPassword = sc.next();
                String updatePassword = "UPDATE user SET password = ? WHERE userName = ?";
                try (PreparedStatement updateStmt = con.prepareStatement(updatePassword)) {
                    updateStmt.setString(1,newPassword);
                    updateStmt.setString(2, user.getPassword());
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ Password Updated Successfully");
                user.setPassword(newPassword);
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

    public void viewCart() throws SQLException {
        String fetchCart = "SELECT p.productName, c.quantity, c.price "+
                            "FROM cart c JOIN product p ON c.product_id = p.product_id " +
                            "WHERE c.username = ?";
        try (PreparedStatement fetchCartItems = con.prepareStatement(fetchCart)) {
            fetchCartItems.setString(1,user.getUserName());
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

    private void checkOut() throws SQLException {
        String fetchAddress = "SELECT name,address_line_1, address_line_2, area, city, state, pincode "+
                                "FROM Address WHERE username = ?";
        try (PreparedStatement fetchCartItems = con.prepareStatement(fetchAddress)) {
            fetchCartItems.setString(1, user.getUserName());
            ResultSet rs = fetchCartItems.executeQuery();
            boolean flag = true;
            boolean found = false;

            while(rs.next()) {
                String name = rs.getString("name");
                String address1 = rs.getString("address_line_1");
                String address2 = rs.getString("address_line_2");
                String area = rs.getString("area");
                String city = rs.getString("city");
                String state = rs.getString("state");
                int pincode = rs.getInt("pincode");

                System.out.println("\n📦 Saved Address:");
                System.out.println("Name         : " + name);
                System.out.println("Address Line1: " + address1);
                System.out.println("Address Line2: " + address2);
                System.out.println("Area         : " + area);
                System.out.println("City         : " + city);
                System.out.println("State        : " + state);
                System.out.println("Pin Code     : " + pincode);
                System.out.println("----------------------------------");

                if(flag) {
                    System.out.println("Want to Delivered to your default address");
                    String choice = sc.next();
                    if(choice.equalsIgnoreCase("yes")) {
                        System.out.println("✅ Delivery address confirmed.");
                        break;
                    }else {
                        flag=false;
                    }
                }
            }
            if (!found) {
                System.out.println("❌ No address found. You can add a new address");
                addAddress();
                System.out.println("✅ Address Added Successfully");
            }
        }
        System.out.println("Choose your Address ID : ");
        int address_id = sc.nextInt();
        System.out.println("Delivered to "+address_id);
        System.out.println("1.Proceed To Pay\n2.Back\n\nEnter your choice : ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                // Proceed to payment logic
                System.out.println("🔐 Redirecting to payment gateway...");
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

    public void viewOrders() {

    }



}
public class CustomerManagement {
    public static void start(User loggedInUser) {
        Customer customer = new Customer(loggedInUser);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n----- 🛍️ Welcome to Shopping Cart - Inventory Management System -----");
            System.out.println("1. 🔍 Search Product");
            System.out.println("2. 🗂️ Categories");
            System.out.println("3. 🛒 View Cart");
            System.out.println("4. 👤 Profile Management");
            System.out.println("5. 📦 My Orders");
//            System.out.println("6. 💳 Wallet & Payments");
            System.out.println("7. 🚪 Logout / Exit");
            System.out.print("Choose an option (1-7): ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1:
                        customer.searchProduct();
                        break;
                    case 2:
                        customer.viewCategories();
                        break;
                    case 3:
                        customer.viewCart();
                        break;
                    case 4:
                        customer.profileManagement();
                        break;
                    case 5:
                        customer.viewOrders();
                        break;
                    /*case 6:
                        customer.walletSection();
                        break;*/
                    case 7:
                        System.out.println("👋 Thank you for visiting! Goodbye.");
                        return;
                    default:
                        System.out.println("❌ Invalid choice. Please enter between 1 and 7.");
                }
            } catch (SQLException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }
}
