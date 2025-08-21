import Database.Database;
import Modules.Auth.Auth;
import Modules.Users.AdminManagement.AdminManagement;
import Modules.Users.User;

import java.sql.PreparedStatement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String billTable = "create table if not exists bills(bill_id int auto_increment primary key,customer_id int references users(user_id),bill_date date references orders(order_date),bill longblob)";
        PreparedStatement ps = Database.getCon().prepareStatement(billTable);
        ps.executeUpdate();
        Scanner sc = new Scanner(System.in);
        Auth auth = new Auth();
        int choice;
        do {
            System.out.println("\n🔐 --------- Authentication Menu ---------");
            System.out.println("1. 📝 Sign Up");
            System.out.println("2. 🔓 Login");
            System.out.println("3. 🚪 Exit");
            System.out.print("👉 Enter your choice: ");

            choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        System.out.println("📝 Sign Up selected.");
                        auth.signUp();
                        break;
                    case 2:
                        System.out.println("🔓 Login selected.");
                        int userId = auth.userLogin();

                        if (User.getCurrentUser().getUserId() != 0) {
                            if (User.getCurrentUser().getRole().equalsIgnoreCase("admin")) {
                                try {
                                    AdminManagement.main(args);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                // CustomerManagement.start(User.getUserById(userId));
                            }
                        } else {
                            System.out.println("❌ Login failed. Invalid credentials.");
                        }

                        /* TODO : add logic for multiple user login and role management
                           User user = auth.getUser();
                        if (user != null) {
                            CustomerManagement.start(auth.getUser());
                        }*/
                        break;
                    case 3:
                        System.out.println("👋 Exiting... Thank you for visiting!");
                        //CustomerManagement customerManagement = new CustomerManagement();

                        System.exit(0);
                        break;
                    default:
                        System.out.println("❌ Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("❌ Database error: " + e.getMessage());
            }
        }
        while (choice != 3);
    }
}