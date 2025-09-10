import Database.Database;
import Modules.Auth.Auth;
import Modules.Users.AdminManagement.AdminManagement;
import Modules.Users.CustomerManagement.CustomerManagement;
import Modules.Users.User;
import Modules.Utils.SmartShoppingSystem;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Scanner;

import static Modules.Users.CustomerManagement.CustomerManagement.checkInactivityAlerts;

public class Main {
    private static void showSmartReminders(int userId) {
        try {
            List<String> reminders = SmartShoppingSystem.getSmartReminders(userId);

            if (reminders.isEmpty()) {
                System.out.println("✅ No pending reminders. You're all caught up!");
            } else {
                System.out.println("\n--- 📌 Smart Shopping Suggestions ---");
                for (String msg : reminders) {
                    System.out.println(msg);
                }
                System.out.println("------------------------------------\n");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Could not fetch smart reminders: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        String billTable = "create table if not exists bills(bill_id int auto_increment primary key,customer_id int references users(user_id),bill_date date references orders(order_date),bill longblob,total_amount double)";
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

                        if (userId != 0) {
                            if (User.getCurrentUser().getRole().equalsIgnoreCase("admin")) {
                                try {
                                    System.out.println("✅ Login successful! Welcome " + User.getCurrentUser().getUserName());
                                    AdminManagement.main(args);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                try {
                                    System.out.println("✅ Login successful! Welcome " + User.getCurrentUser().getUserName());
                                    // 🔔 Show smart reminders and ⚠️ Inactivity Alerts after login
                                    showSmartReminders(userId);
                                    checkInactivityAlerts();
                                    CustomerManagement.start();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        } else {
                            System.out.println("❌ Login failed. Invalid credentials.");
                        }
                        break;
                    case 3:
                        System.out.println("👋 Exiting... Thank you for visiting!");

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