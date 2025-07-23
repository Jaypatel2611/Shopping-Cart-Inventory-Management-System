package Modules.Users;

public class User {
    String firstName;
    String lastName;
    String userName;
    String password;
    String mobileNo;
    String email;
    String role;

    public User(String firstName, String lastName, String userName, String password, String email, String mobileNo,String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.mobileNo = mobileNo;
        this.email = email;
        this.role = role;
    }

    public static void addLoggedInUser() {
        // TODO : add user to the arraylist
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


    // TODO : Change void to return type
    public void getUserById(int id) {
        // TODO : fetch details of user by id from arrayList

    }
}
