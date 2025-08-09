package Modules.Users.CustomerManagement;

import Database.Database;
import Modules.Address.Address;
import Modules.Users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerManagement {

    static User user;
    static Scanner sc = new Scanner(System.in);

    private static void addAddress() throws SQLException {
        System.out.println("Enter Modules.Address In formatted way ");
        System.out.println("Enter Modules.Address Line 1 : ");
        String addressLine1 = sc.nextLine();
        System.out.println("Enter Modules.Address Line 2 : ");
        String addressLine2 = sc.nextLine();
        System.out.println("Enter Area : ");
        String area = sc.nextLine();
        System.out.println("Enter City : ");
        String city = sc.nextLine();
        System.out.println("Enter State : ");
        String state = sc.nextLine();
        System.out.println("Enter Pin code : ");
        int pinCode = sc.nextInt();

        Address add = new Address(user.getFirstName(), addressLine1, addressLine2, area, city, state, pinCode, user);
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
                Thread.sleep(10000);
                // TODO: add online payment method
                break;

            default:
                System.out.println("❌ Invalid payment mode selected. Please choose 1 or 2.");
                // You may loop back or prompt again
                break;
        }
    }

    private static void searchProduct() throws Exception {
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

    private static void viewCategories() throws Exception {
        int choice = 0;
        Connection con = Database.getCon();
        do {
            System.out.println("1 - Grocery");
            System.out.println("2 - Electronics");
            System.out.println("3 - Personal Care");
            System.out.println("4 - Beverages");
            System.out.println("5 - Home & Kitchen Appliances");
            System.out.println("6 - Stationery Items");
            System.out.println("7 - Fashion");
            System.out.println("8 - Cleaning Supplies");
            System.out.println("9 - Exit");
            System.out.println("Enter your choice - ");
            choice = sc.nextInt();
            switch (choice)
            {
                case 1:
                    int choice1 = 0;
                    do {
                        System.out.println("1 - Fruits");
                        System.out.println("2 - Vegetables");
                        System.out.println("3 - Snacks");
                        System.out.println("4 - Exit");
                        System.out.println("Enter your choice");
                        choice1 = sc.nextInt();
                        switch (choice1)
                        {
                            case 1:
                                String select = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps = con.prepareStatement(select);
                                ps.setInt(1,1001);
                                ps.setInt(2,101);
                                ResultSet rs = ps.executeQuery();
                                while(rs.next())
                                {
                                    System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getInt(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getDouble(6)+"\t"+rs.getInt(7));
                                }
//                                String insert = "insert into cart(product_id,product_name,description,price,quantity) values(?,?,?,?,?)";
//                                System.out.println("Enter Quantity - ");
//                                int quantity = sc.nextInt();
//                                ps.setInt(1,rs.getInt(2));
//                                ps.setString(2, rs.getString(3));
//                                ps.setString(3, rs.getString(4));
//                                ps.setDouble(4,rs.getDouble(5));
//                                ps.setInt(5,quantity);
//                                ps.executeUpdate();

                                break;
                            case 2:String select1 = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps1 = con.prepareStatement(select1);
                                ps1.setInt(1,1001);
                                ps1.setInt(2,101);
                                ResultSet rs1 = ps1.executeQuery();
                                while(rs1.next())
                                {
                                    System.out.println(rs1.getInt(1)+"\t"+rs1.getInt(2)+"\t"+rs1.getInt(3)+"\t"+rs1.getString(4)+"\t"+rs1.getString(5)+"\t"+rs1.getDouble(6)+"\t"+rs1.getInt(7));
                                }break;
                            case 3:String select2 = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps2 = con.prepareStatement(select2);
                                ps2.setInt(1,1001);
                                ps2.setInt(2,101);
                                ResultSet rs2 = ps2.executeQuery();
                                while(rs2.next())
                                {
                                    System.out.println(rs2.getInt(1)+"\t"+rs2.getInt(2)+"\t"+rs2.getInt(3)+"\t"+rs2.getString(4)+"\t"+rs2.getString(5)+"\t"+rs2.getDouble(6)+"\t"+rs2.getInt(7));
                                }break;
                            case 4:
                                System.out.println("Exiting");break;
                            default:
                                System.out.println("Invalid Choice");
                                break;
                        }
                    }
                    while (choice1!=4);
                    break;

                case 2:
                    int choice2 = 0;
                    do {
                        System.out.println("1 - Mobiles Phones");
                        System.out.println("2 - Laptops");
                        System.out.println("3 - Phone and Laptop Accessories");
                        System.out.println("4 - Exit");
                        System.out.println("Enter your choice - ");
                        choice2 = sc.nextInt();
                        switch (choice2)
                        {
                            case 1: String select = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps = con.prepareStatement(select);
                                ps.setInt(1,1001);
                                ps.setInt(2,101);
                                ResultSet rs = ps.executeQuery();
                                while(rs.next())
                                {
                                    System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getInt(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getDouble(6)+"\t"+rs.getInt(7));
                                }break;
                            case 2:String select1 = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps1 = con.prepareStatement(select1);
                                ps1.setInt(1,1001);
                                ps1.setInt(2,101);
                                ResultSet rs1 = ps1.executeQuery();
                                while(rs1.next())
                                {
                                    System.out.println(rs1.getInt(1)+"\t"+rs1.getInt(2)+"\t"+rs1.getInt(3)+"\t"+rs1.getString(4)+"\t"+rs1.getString(5)+"\t"+rs1.getDouble(6)+"\t"+rs1.getInt(7));
                                }break;
                            case 3:String select2 = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps2 = con.prepareStatement(select2);
                                ps2.setInt(1,1001);
                                ps2.setInt(2,101);
                                ResultSet rs2 = ps2.executeQuery();
                                while(rs2.next())
                                {
                                    System.out.println(rs2.getInt(1)+"\t"+rs2.getInt(2)+"\t"+rs2.getInt(3)+"\t"+rs2.getString(4)+"\t"+rs2.getString(5)+"\t"+rs2.getDouble(6)+"\t"+rs2.getInt(7));
                                }break;
                            case 4:
                                System.out.println("Exiting");break;
                            default:
                                System.out.println("Invalid Choice");
                                break;
                        }
                    }
                    while (choice2!=4);
                    break;


                case 3:
                    int choice3 = 0;
                    do {
                        System.out.println("1 - Skincare");
                        System.out.println("2 - Haircare");
                        System.out.println("3 - Exit");
                        choice3 = sc.nextInt();
                        switch (choice3)
                        {
                            case 1: String select = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps = con.prepareStatement(select);
                                ps.setInt(1,1001);
                                ps.setInt(2,101);
                                ResultSet rs = ps.executeQuery();
                                while(rs.next())
                                {
                                    System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getInt(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getDouble(6)+"\t"+rs.getInt(7));
                                }break;
                            case 2: String select1 = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps1 = con.prepareStatement(select1);
                                ps1.setInt(1,1001);
                                ps1.setInt(2,101);
                                ResultSet rs1 = ps1.executeQuery();
                                while(rs1.next())
                                {
                                    System.out.println(rs1.getInt(1)+"\t"+rs1.getInt(2)+"\t"+rs1.getInt(3)+"\t"+rs1.getString(4)+"\t"+rs1.getString(5)+"\t"+rs1.getDouble(6)+"\t"+rs1.getInt(7));
                                }break;
                            case 3:
                                System.out.println("Exiting");break;
                            default:
                                System.out.println("Invalid choice");
                                break;
                        }
                    }
                    while (choice3!=3);


                    break;
                case 4:
                    int choice4 = 0;
                    do {
                        System.out.println("1 - Juices");
                        System.out.println("2 - Soft Drinks");
                        System.out.println("3 - Exit");
                        choice4 = sc.nextInt();
                        switch (choice4)
                        {
                            case 1:String select = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps = con.prepareStatement(select);
                                ps.setInt(1,1001);
                                ps.setInt(2,101);
                                ResultSet rs = ps.executeQuery();
                                while(rs.next())
                                {
                                    System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getInt(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getDouble(6)+"\t"+rs.getInt(7));
                                }break;
                            case 2:String select1 = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps1 = con.prepareStatement(select1);
                                ps1.setInt(1,1001);
                                ps1.setInt(2,101);
                                ResultSet rs1 = ps1.executeQuery();
                                while(rs1.next())
                                {
                                    System.out.println(rs1.getInt(1)+"\t"+rs1.getInt(2)+"\t"+rs1.getInt(3)+"\t"+rs1.getString(4)+"\t"+rs1.getString(5)+"\t"+rs1.getDouble(6)+"\t"+rs1.getInt(7));
                                }break;
                            case 3:
                                System.out.println("Exiting");break;
                            default:
                                System.out.println("Invalid Choice");
                                break;
                        }
                    }
                    while (choice4!=3);
                        break;

                case 5:
                    int choice5 = 0;
                    do {
                        System.out.println("1 - Cookware");
                        System.out.println("2 - Dining");
                        System.out.println("3 - Exit");
                        System.out.println("Enter your choice - ");
                        choice5 = sc.nextInt();
                        switch (choice5)
                        {
                            case 1: String select = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps = con.prepareStatement(select);
                                ps.setInt(1,1001);
                                ps.setInt(2,101);
                                ResultSet rs = ps.executeQuery();
                                while(rs.next())
                                {
                                    System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getInt(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getDouble(6)+"\t"+rs.getInt(7));
                                }break;
                            case 2: String select1 = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps1 = con.prepareStatement(select1);
                                ps1.setInt(1,1001);
                                ps1.setInt(2,101);
                                ResultSet rs1 = ps1.executeQuery();
                                while(rs1.next())
                                {
                                    System.out.println(rs1.getInt(1)+"\t"+rs1.getInt(2)+"\t"+rs1.getInt(3)+"\t"+rs1.getString(4)+"\t"+rs1.getString(5)+"\t"+rs1.getDouble(6)+"\t"+rs1.getInt(7));
                                }break;
                            case 3:
                                System.out.println("Exiting"); break;
                            default:
                                System.out.println("Invalid Choice");
                                break;
                        }
                    }
                    while (choice5!=3);
                    break;

                case 6:
                    int choice6 = 0;
                    do {
                        System.out.println("1 - Notebooks");
                        System.out.println("2 - Pens");
                        System.out.println("3 - Exit");
                        System.out.println("Enter your choice - ");
                        choice6 = sc.nextInt();
                        switch (choice6)
                        {
                            case 1:String select = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps = con.prepareStatement(select);
                                ps.setInt(1,1001);
                                ps.setInt(2,101);
                                ResultSet rs = ps.executeQuery();
                                while(rs.next())
                                {
                                    System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getInt(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getDouble(6)+"\t"+rs.getInt(7));
                                }break;
                            case 2: String select1 = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps1 = con.prepareStatement(select1);
                                ps1.setInt(1,1001);
                                ps1.setInt(2,101);
                                ResultSet rs1 = ps1.executeQuery();
                                while(rs1.next())
                                {
                                    System.out.println(rs1.getInt(1)+"\t"+rs1.getInt(2)+"\t"+rs1.getInt(3)+"\t"+rs1.getString(4)+"\t"+rs1.getString(5)+"\t"+rs1.getDouble(6)+"\t"+rs1.getInt(7));
                                }break;
                            case 3:
                                System.out.println("Exiting");break;
                            default:
                                System.out.println("Invalid Choice");
                        }
                    }
                    while (choice6!=3);



                    break;
                case 7:
                    int choice7 = 0;
                    do {
                        System.out.println("1 - Men's Wear");
                        System.out.println("2 - Women's Wear");
                        System.out.println("3 - Exit");
                        System.out.println("Enter your Choice - ");
                        choice7 = sc.nextInt();
                        switch (choice7)
                        {
                            case 1: String select = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps = con.prepareStatement(select);
                                ps.setInt(1,1001);
                                ps.setInt(2,101);
                                ResultSet rs = ps.executeQuery();
                                while(rs.next())
                                {
                                    System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getInt(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getDouble(6)+"\t"+rs.getInt(7));
                                }break;
                            case 2: String select1 = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps1 = con.prepareStatement(select1);
                                ps1.setInt(1,1001);
                                ps1.setInt(2,101);
                                ResultSet rs1 = ps1.executeQuery();
                                while(rs1.next())
                                {
                                    System.out.println(rs1.getInt(1)+"\t"+rs1.getInt(2)+"\t"+rs1.getInt(3)+"\t"+rs1.getString(4)+"\t"+rs1.getString(5)+"\t"+rs1.getDouble(6)+"\t"+rs1.getInt(7));
                                }break;
                            case 3:
                                System.out.println("Exiting");break;
                            default:
                                System.out.println("Invalid Choice");
                        }
                    }while (choice7!=3);
                    break;

                case 8:
                    int choice8 = 0;
                    do {
                        System.out.println("1 - Floor Cleaner");
                        System.out.println("2 - Detergents");
                        System.out.println("3 - Exit");
                        System.out.println("Enter your Choice");
                        switch (choice8)
                        {
                            case 1:String select = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps = con.prepareStatement(select);
                                ps.setInt(1,1001);
                                ps.setInt(2,101);
                                ResultSet rs = ps.executeQuery();
                                while(rs.next())
                                {
                                    System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getInt(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getDouble(6)+"\t"+rs.getInt(7));
                                }break;
                            case 2:String select1 = "select * from product where category_id = ? and subcategory_id = ? ";
                                PreparedStatement ps1 = con.prepareStatement(select1);
                                ps1.setInt(1,1001);
                                ps1.setInt(2,101);
                                ResultSet rs1 = ps1.executeQuery();
                                while(rs1.next())
                                {
                                    System.out.println(rs1.getInt(1)+"\t"+rs1.getInt(2)+"\t"+rs1.getInt(3)+"\t"+rs1.getString(4)+"\t"+rs1.getString(5)+"\t"+rs1.getDouble(6)+"\t"+rs1.getInt(7));
                                }break;
                            case 3:
                                System.out.println("Exit");break;
                            default:
                                System.out.println("Invalid Choice");
                                break;
                        }
                    }
                    while (choice8!=3);




                    break;
                case 9:break;
                default:
                    System.out.println("Invalid Choice");
            }
        }
        while (choice!=9);

    }

    private static void profileManagement() throws Exception {
        System.out.println("1.FirstName\n2.LastName\n3.UserName\n4.Password\n5. Add Modules.Address\n6.EXIT\n\nEnter your Choice : ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.print("Enter new FirstName : ");
                String newFirstName = sc.next();
                String updateFirstName = "UPDATE user SET firstName = ? WHERE userName = ?";
                try (PreparedStatement updateStmt = Database.getCon().prepareStatement(updateFirstName)) {
                    updateStmt.setString(1, newFirstName);
                    updateStmt.setString(2, user.getUserName());
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ First Name Updated Successfully");
                user.setFirstName(newFirstName);
                break;
            case 2:
                System.out.print("Enter new LastName : ");
                String newLastName = sc.next();
                String updateLastName = "UPDATE user SET lastName = ? WHERE userName = ?";
                try (PreparedStatement updateStmt = Database.getCon().prepareStatement(updateLastName)) {
                    updateStmt.setString(1, newLastName);
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
                try (PreparedStatement updateStmt = Database.getCon().prepareStatement(updateUserName)) {
                    updateStmt.setString(1, newUserName);
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
                try (PreparedStatement updateStmt = Database.getCon().prepareStatement(updatePassword)) {
                    updateStmt.setString(1, newPassword);
                    updateStmt.setString(2, user.getPassword());
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ Password Updated Successfully");
                user.setPassword(newPassword);
                break;
            case 5:
                addAddress();
                System.out.println("✅ Modules.Address Added Successfully");
                break;
            case 6:
                System.out.println("EXITING");
                break;
            default:
                System.out.println("Invalid Choice");
        }
    }

    private static void viewCart() throws Exception {
        String fetchCart = "SELECT p.productName, c.quantity, c.price " +
                "FROM cart c JOIN product p ON c.product_id = p.product_id " +
                "WHERE c.username = ?";
        try (PreparedStatement fetchCartItems = Database.getCon().prepareStatement(fetchCart)) {
            fetchCartItems.setString(1, user.getUserName());
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

    private static void checkOut() throws Exception {
        String fetchAddress = "SELECT name,address_line_1, address_line_2, area, city, state, pincode " +
                "FROM Modules.Address WHERE username = ?";
        try (PreparedStatement fetchCartItems = Database.getCon().prepareStatement(fetchAddress)) {
            fetchCartItems.setString(1, user.getUserName());
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
                        System.out.println("✅ Delivery address Database.getCon()firmed.");
                        break;
                    } else {
                        flag = false;
                    }
                }
            }
            if (!found) {
                System.out.println("❌ No address found. You can add a new address");
                addAddress();
                System.out.println("✅ Modules.Address Added Successfully");
            }
        }
        System.out.println("Choose your Modules.Address ID : ");
        int address_id = sc.nextInt();
        System.out.println("Delivered to " + address_id);
        System.out.println("1.Proceed To Pay\n2.Back\n\nEnter your choice : ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
//                payment();
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

    public static void start(User loggedInUser) throws Exception {
//        Customer customer = new Customer(loggedInUser);
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
            sc.nextLine(); // Database.getCon()sume newline

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
