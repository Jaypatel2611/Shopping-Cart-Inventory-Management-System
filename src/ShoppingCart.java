import java.sql.*;
import java.util.Scanner;

class User {
    String firstName;
    String lastName;
    String userName;
    String password;
    String role;

    public User(String firstName, String lastName, String userName, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }
}
class User_Management {
    static Connection con;
    static User user;
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

    public void addUser() throws SQLException {
        System.out.print("Enter First Name : ");
        String firstName = sc.next();
        System.out.print("Enter Last Name : ");
        String lastName = sc.next();
        System.out.print("Enter userName : ");
        String userName = sc.next();
        System.out.print("Enter Password : ");
        String password = sc.next();
        System.out.print("Enter Role : ");
        String role = sc.next();

        String insertUser = "INSERT INTO user(firstName,lastName,userName,password,role) VALUES(?,?,?,?,?)";
        try (PreparedStatement insertStmt = con.prepareStatement(insertUser)) {
            insertStmt.setString(1, firstName);
            insertStmt.setString(2, lastName);
            insertStmt.setString(3, userName);
            insertStmt.setString(4, password);
            insertStmt.setString(5, role);
            insertStmt.executeUpdate();
        }

        user = new User(firstName, lastName, userName, password, role);
        System.out.println("✅ Signed Up Successfully");
    }

    public void userLogin() throws SQLException {
        System.out.print("Enter userName : ");
        String userName = sc.next();
        System.out.print("Enter Password : ");
        String password = sc.next();

        String fetchUserDetails = "SELECT password FROM user WHERE userName = ? AND password = ?";
        try (PreparedStatement insertStmt = con.prepareStatement(fetchUserDetails)) {
            insertStmt.setString(1, userName);
            insertStmt.setString(2, password);

            ResultSet rs = insertStmt.executeQuery();
            if(rs.next()) {
                String fetchedPassword = rs.getString("password");
                if (password.equals(fetchedPassword)) {
                    System.out.println("✅ Logged In Successfully");
                } else {
                    System.out.println("❌ Invalid Credentials");
                }
            }
        }
    }

    public void updateUserDetails() throws SQLException {
        System.out.println("1.FirstName\n2.LastName\n3.UserName\n4.Password\n5.EXIT\n\nEnter your Choice : ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.print("Enter new FirstName : ");
                String newFirstName = sc.next();
                String updateFirstName = "UPDATE user SET firstName = ? WHERE userName = ?";
                try (PreparedStatement updateStmt = con.prepareStatement(updateFirstName)) {
                    updateStmt.setString(1,newFirstName);
                    updateStmt.setString(2,user.userName);
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ First Name Updated Successfully");
                user.firstName = newFirstName;
                break;
            case 2:
                System.out.print("Enter new LastName : ");
                String newLastName = sc.next();
                String updateLastName = "UPDATE user SET lastName = ? WHERE userName = ?";
                try (PreparedStatement updateStmt = con.prepareStatement(updateLastName)) {
                    updateStmt.setString(1,newLastName);
                    updateStmt.setString(2, user.lastName);
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ Last Name Updated Successfully");
                user.lastName = newLastName;
                break;
            case 3:
                System.out.print("Enter new UserName : ");
                String newUserName = sc.next();
                String updateUserName = "UPDATE user SET userName = ? WHERE userName = ?";
                try (PreparedStatement updateStmt = con.prepareStatement(updateUserName)) {
                    updateStmt.setString(1,newUserName);
                    updateStmt.setString(2, user.userName);
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ User Name Updated Successfully");
                user.userName = newUserName;
                break;
            case 4:
                System.out.print("Enter new Password : ");
                String newPassword = sc.next();
                String updatePassword = "UPDATE user SET password = ? WHERE userName = ?";
                try (PreparedStatement updateStmt = con.prepareStatement(updatePassword)) {
                    updateStmt.setString(1,newPassword);
                    updateStmt.setString(2, user.password);
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ Password Updated Successfully");
                user.password = newPassword;
                break;
            case 5:
                System.out.println("EXITING");
                break;
            default:
                System.out.println("Invalid Choice");
        }
    }
}
class ShoppingCart {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        User_Management um = new User_Management();

        while (true) {
            System.out.println("\n========== User Management Menu ==========");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        um.addUser();
                        break;
                    case 2:
                        um.userLogin();
                        break;
                    case 3:
                        System.out.println("👋 Exiting the program.");
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