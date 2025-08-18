package Modules.Auth;

import Database.Database;
import Modules.Address.Address;
import Modules.Users.AdminManagement.AdminManagement;
import Modules.Users.CustomerManagement.CustomerManagement;
import Modules.Users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;



//import static Modules.Users.CustomerManagement.CustomerManagement.user;

public class Auth {
    Scanner sc = new Scanner(System.in);

    private boolean isValidEmail(String email) {
        // Accepts emails like test@example.com
        String emailPattern = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailPattern);
    }

    private boolean isValidMobileNo(String mobileNo) {
        // Accepts exactly 10 digit mobile numbers
        return mobileNo.matches("\\d{10}");
    }

    private boolean isValidPassword(String password) {
        String pattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:;\"'<>?,./]).{8,}$";
        return password.matches(pattern);
    }

    // Method to check if first name is only alphabets
    public static boolean isValidFirstName(String name) {
        return name.matches("[a-zA-Z]+");
        }
    public static boolean isValidLastName(String name) {
        return name.matches("[a-zA-Z]+");
    }


    public void signUp() throws Exception {
        Connection con = Database.getCon();

        String firstName;
        do {
            System.out.print("Enter First Name : ");
            firstName = sc.next().toLowerCase();
            if (isValidFirstName(firstName)) {
                System.out.println("Valid first name ✅");
                break;
            } else {
                System.out.println("Invalid first name ❌ (only letters allowed)");
            }

        }
        while (true);
        System.out.print("Enter Last Name : ");
        String lastName;
        // String lastName ;
        do {
            System.out.print("Enter last Name : ");
            lastName = sc.next().toLowerCase();
            if (isValidLastName(lastName)) {
                System.out.println("Valid Last name ✅");
                break;
            } else {
                System.out.println("Invalid last name ❌ (only letters allowed)");
            }

        }
        while (true);
        String email;
        sc.nextLine();
        do {
            System.out.print("Enter your email: ");
            email = sc.nextLine().trim();

            if (isValidEmail(email)) {
                System.out.println("✅ Valid email!");
                break;
            } else {
                System.out.println("❌ Invalid email! Please enter a valid email address.");
            }
        } while (true);

        String mobileNo;
        do {
            System.out.print("Enter your mobile number: ");
            mobileNo = sc.nextLine().trim();

            if (isValidMobileNo(mobileNo)) {
                System.out.println("✅ Valid mobile No!");
                break;
            } else {
                System.out.println("❌ Invalid mobile number! It must contain exactly 10 digits.");
            }
        } while (true);
        System.out.print("Enter userName : ");
        String userName = sc.next();
        String password;
        while (true) {
            System.out.print("Enter Password : ");
            password = sc.next();

            if (isValidPassword(password)) {
                break;
            } else {
                System.out.println("❌ Password must be at least 8 characters long, contain:");
                System.out.println("   → At least one uppercase letter");
                System.out.println("   → At least one digit");
                System.out.println("   → At least one special character (!@#$%^&* etc.)");
            }
//            System.out.println("Enter Modules.Address In formatted way ");
//            System.out.println("Enter Modules.Address Line 1 : ");
//            String addressLine1 = sc.nextLine();
//            sc.nextLine();
//            System.out.println("Enter Modules.Address Line 2 : ");
//            String addressLine2 = sc.nextLine();
//            System.out.println("Enter Area : ");
//            String area = sc.nextLine();
//            System.out.println("Enter City : ");
//            String city = sc.nextLine();
//            System.out.println("Enter State : ");
//            String state = sc.nextLine();
//            System.out.println("Enter Pin code : ");
//            int pinCode = sc.nextInt();
//
//            User user = User.getCurrentUser();
//            Address add = new Address(user.getFirstName(), addressLine1, addressLine2, area, city, state, pinCode, user);


        }
//        System.out.println("select your role ");
//        String role = sc.next().toLowerCase();
//        if (role.equalsIgnoreCase("admin")) {
//           // System.out.println("Enter Admin Password");
//            //String Adminpassword ;
//            String Adminpassword = "Admin@123";
//            while (true) {
//                System.out.print("Enter Admin Password : ");
//                password = sc.next();
//
//                if (isValidPassword(password) && password.equalsIgnoreCase("Admin@123")) {
//                    AdminManagement am = new AdminManagement();
//                    break;
//                } else {
//                    System.out.println("❌ Password must be at least 8 characters long, contain:");
//                    System.out.println("   → At least one uppercase letter");
//                    System.out.println("   → At least one digit");
//                    System.out.println("   → At least one special character (!@#$%^&* etc.)");
//                }
//            }

            String insertUser = "INSERT INTO users(first_name,last_name,username,mobile_no,email,password,role) VALUES(?,?,?,?,?,?,?)";
            try (PreparedStatement insertStmt = con.prepareStatement(insertUser)) {

                insertStmt.setString(1, firstName);
                insertStmt.setString(2, lastName);
                insertStmt.setString(3, userName);
                insertStmt.setString(4, mobileNo);
                insertStmt.setString(5, email);
                insertStmt.setString(6, password);
                insertStmt.setString(7, "user");
                int rows = insertStmt.executeUpdate();
                if (rows > 0) {
                    ResultSet keys = insertStmt.getGeneratedKeys();
                    if (keys.next()) {
                        int newUser = keys.getInt(1);
                    }
                    PreparedStatement ps = Database.getCon().prepareStatement("select * from users where user_id = ?");
                    ResultSet rss = ps.executeQuery();
                    if (rss.next()) {
                        User.addLoggedInUser(rss);
                    }
                }

            } catch (Exception e) {
                //throw new RuntimeException(e);
            }
            System.out.println("✅ Signed Up Successfully");
        }



    public int userLogin() throws Exception {
    Scanner sc = new Scanner(System.in);
        Connection con = Database.getCon();
        System.out.print("Enter userName : ");
        String userName = sc.next();
        System.out.print("Enter Password : ");
        String password = sc.next();

        String fetchUserDetails = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement insertStmt = con.prepareStatement(fetchUserDetails)) {
            insertStmt.setString(1, userName);
            insertStmt.setString(2, password);

            ResultSet rs = insertStmt.executeQuery();
            while (rs.next()) {
                String fetchedPassword = rs.getString("password");
                if (password.equals(fetchedPassword)) {
                    System.out.println("✅ Logged In Successfully");
                    User.addLoggedInUser(rs);
                    CustomerManagement.start();
                    return rs.getInt("user_id");
                } else {
                    System.out.println("❌ Invalid Credentials");
                }
            }
        }
        return 0;
    }
}
