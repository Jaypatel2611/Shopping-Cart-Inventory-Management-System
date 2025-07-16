import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

        String insertUser = "INSERT (firstName,lastName,userName,password,role) INTO user VALUES(?,?,?,?,?)";
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

    public void updateUserDetails() throws SQLException {
        System.out.println("1.FirstName\n2.LastName\n3.UserName\n4.Password\n5.EXIT\n\nEnter your Choice : ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.print("Enter new FirstName : ");
                String newFirstName = sc.next();
                user.firstName = newFirstName;
                String updateFirstName = "INSERT firstName INTO user VALUES(?)";
                try (PreparedStatement updateStmt = con.prepareStatement(updateFirstName)) {
                    updateStmt.setString(1,updateFirstName);
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ First Name Updated Successfully");
                break;
            case 2:
                System.out.print("Enter new LastName : ");
                String newLastName = sc.next();
                user.lastName = newLastName;
                String updateLastName = "INSERT lastName INTO user VALUES(?)";
                try (PreparedStatement updateStmt = con.prepareStatement(updateLastName)) {
                    updateStmt.setString(1,updateLastName);
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ Last Name Updated Successfully");
                break;
            case 3:
                System.out.print("Enter new UserName : ");
                String newUserName = sc.next();
                user.firstName = newUserName;
                String updateUserName = "INSERT firstName INTO user VALUES(?)";
                try (PreparedStatement updateStmt = con.prepareStatement(updateUserName)) {
                    updateStmt.setString(1,updateUserName);
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ User Name Updated Successfully");
                break;
            case 4:
                System.out.print("Enter new Password : ");
                String newPassword = sc.next();
                user.firstName = newPassword;
                String updatePassword = "INSERT firstName INTO user VALUES(?)";
                try (PreparedStatement updateStmt = con.prepareStatement(updatePassword)) {
                    updateStmt.setString(1,updatePassword);
                    updateStmt.executeUpdate();
                }
                System.out.println("✅ First Name Updated Successfully");
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

    }
}