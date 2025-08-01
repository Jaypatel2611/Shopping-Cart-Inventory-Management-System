package Modules.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class User {
    static HashMap<Integer, User> loggedInUser = new HashMap<>();

    int user_id;
    String firstName;
    String lastName;
    String userName;
    String password;
    String mobileNo;
    String email;
    String role;

    public User(int user_id, String firstName, String lastName, String userName, String password, String email, String mobileNo, String role) {
        this.user_id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.mobileNo = mobileNo;
        this.email = email;
        this.role = role;
    }

    public static void addLoggedInUser(ResultSet userData) throws SQLException {
        loggedInUser.put(userData.getInt("user_id"), new User(userData.getInt("user_id"), userData.getString("first_name"), userData.getString("last_name"), userData.getString("username"), userData.getString("password"), userData.getString("email"), userData.getString("mobile_no"), userData.getString("role")));
    }

    public static User getUserById(int id) {
        return loggedInUser.get(id);
    }

    public int getUserId() {
        return user_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }
}
