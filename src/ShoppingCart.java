import java.util.*;
import java.sql.*;
class User extends ShoppingCart
{
    //int id;
    String name;
    String phoneno;
    String email;
    String password;
    String role;
    Connection con;
    String ans;
    String adminpassword = "admin@123";


    public User(String name, String phoneno, String email, String password,String role,Connection con,String ans) throws SQLException {
        //this.id = id;
        this.name = name;
        this.phoneno = phoneno;
        this.email = email;
        this.password = password;
        this.role = role;
        this.con = con;
        this.ans = ans;

        if(role.equals("admin"))
        {
            if(ans.equalsIgnoreCase("no")) {
                String insert = "Insert into admin(name,email,phone,password) values(?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(insert);
                ps.setString(1, name);
                ps.setString(2, phoneno);
                ps.setString(3, email);
                ps.setString(4, password);
                ps.executeUpdate();
                System.out.println("sign-up Successfully");
            }
            else
            {
                System.out.println("Signin Successfully");
            }

        }
        else if (role.equals("customer"))
        {
            if(ans.equalsIgnoreCase("no")) {
                String insert = "Insert into customer(customer_name,customer_email,customer_phoneno,customer_password) values(?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(insert);
                ps.setString(1, name);
                ps.setString(2, phoneno);
                ps.setString(3, email);
                ps.setString(4, password);
                ps.executeUpdate();
            }
            else
            {
                System.out.println("Signin Successfully");
            }

        }
        else
        {
            System.out.println("invalid role");

        }
    }

}
class Admin
{
    // adding product in database
}
class Customer
{

}
public class ShoppingCart {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String dburl = "jdbc:mysql://localhost:3306/shopping_system";
        String dbuser = "root";
        String dbpass = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        Connection con = DriverManager.getConnection(dburl,dbuser,dbpass);
        System.out.println(" By Admin or Customer");
        String choice = sc.next().toLowerCase();
        if(choice.equalsIgnoreCase("admin"))
        {
            System.out.println("Enter Admin Password - ");
            String pass = sc.next();
            if(pass.equalsIgnoreCase("admin@123"))
            {
                System.out.println("Correct password");
            }
            else {
                System.out.println("Incorrect password");
            }
        }
        System.out.println("Already have account ?(yes/no)");
        String ans = sc.next();
        if(ans.equalsIgnoreCase("no")) {
            System.out.println("Enter Name - ");
            String Name = sc.nextLine();
            sc.nextLine();
            System.out.println("Enter Phone Number - ");
            String number = sc.next();
            sc.nextLine();
            System.out.println("Enter your Email - ");
            String email = sc.next();
            sc.nextLine();
            System.out.println("Enter password - ");
            String password = sc.next();


            User u = new User(Name, number, email, password, choice, con, ans);
        }


    }
}
