package Modules.Auth;

import Modules.Users.User;

import java.sql.*;
import java.util.Scanner;

public class Auth {
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

        String insertUser = "INSERT INTO user(firstName,lastName,userName,password,role) VALUES(?,?,?,?,?)";
        try (PreparedStatement insertStmt = con.prepareStatement(insertUser)) {
            insertStmt.setString(1, firstName);
            insertStmt.setString(2, lastName);
            insertStmt.setString(3, userName);
            insertStmt.setString(4, password);
            insertStmt.executeUpdate();
        }

        System.out.println("✅ Signed Up Successfully");
    }

    public void userLogin() throws SQLException {
        System.out.print("Enter userName : ");
        String userName = sc.next();
        System.out.print("Enter Password : ");
        String password = sc.next();

        String fetchUserDetails = "SELECT * FROM user WHERE userName = ? AND password = ?";
        try (PreparedStatement insertStmt = con.prepareStatement(fetchUserDetails)) {
            insertStmt.setString(1, userName);
            insertStmt.setString(2, password);

            ResultSet rs = insertStmt.executeQuery();
            if (rs.next()) {
                String fetchedPassword = rs.getString("password");
                if (password.equals(fetchedPassword)) {
                    System.out.println("✅ Logged In Successfully");
                    // TODO : add a heterogeneous primitive data type to add user
                    User.addLoggedInUser();
                } else {
                    System.out.println("❌ Invalid Credentials");
                }
            }
        }
    }
}
