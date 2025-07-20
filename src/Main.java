import Modules.Auth.Auth;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Auth auth = new Auth();

        while (true) {
            System.out.println("\n🔐 --------- Authentication Menu ---------");
            System.out.println("1. 📝 Sign Up");
            System.out.println("2. 🔓 Login");
            System.out.println("3. 🚪 Exit");
            System.out.print("👉 Enter your choice: ");

            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        System.out.println("📝 Sign Up selected.");
                        auth.signUp();
                        break;
                    case 2:
                        System.out.println("🔓 Login selected.");
                        auth.userLogin();
                        /* TODO : add logic for multiple user login and role management
                           User user = auth.getUser();
                        if (user != null) {
                            CustomerManagement.start(auth.getUser());
                        }*/
                        break;
                    case 3:
                        System.out.println("👋 Exiting... Thank you for visiting!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("❌ Invalid choice! Please try again.");
                }
            } catch (SQLException e) {
                System.out.println("❌ Database error: " + e.getMessage());
            }
        }
    }
}