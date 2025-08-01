package Modules.Auth;

import Modules.Users.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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

    public void signUp() throws SQLException {
        System.out.print("Enter First Name : ");
        String firstName = sc.next();
        System.out.print("Enter Last Name : ");
        String lastName = sc.next();
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


        }

        String insertUser = "INSERT INTO users(first_name,last_name,username,mobile_no,email,password,role) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement insertStmt = Database.Database.getCon().prepareStatement(insertUser)) {
            insertStmt.setString(1, firstName);
            insertStmt.setString(2, lastName);
            insertStmt.setString(3, userName);
            insertStmt.setString(4, mobileNo);
            insertStmt.setString(5, email);
            insertStmt.setString(6, password);
            insertStmt.setString(7, "user");
            insertStmt.executeUpdate();
        }
        System.out.println("✅ Signed Up Successfully");
    }

    public int userLogin() throws SQLException {
        System.out.print("Enter userName : ");
        String userName = sc.next();
        System.out.print("Enter Password : ");
        String password = sc.next();

        String fetchUserDetails = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement insertStmt = Database.Database.getCon().prepareStatement(fetchUserDetails)) {
            insertStmt.setString(1, userName);
            insertStmt.setString(2, password);

            ResultSet rs = insertStmt.executeQuery();
            if (rs.next()) {
                String fetchedPassword = rs.getString("password");
                if (password.equals(fetchedPassword)) {
                    System.out.println("✅ Logged In Successfully");
                    User.addLoggedInUser(rs);
                    return rs.getInt("user_id");
                } else {
                    System.out.println("❌ Invalid Credentials");
                }
            }
        }
        return 0;
    }
}
