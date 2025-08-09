package Modules.Users.AdminManagement;

import java.sql.*;
import java.util.Scanner;

public class AdminManagement {
    Scanner sc = new Scanner(System.in);
    static Connection con;

    static {
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/shopping_system",
                    "root",
                    ""
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void Addproduct(Connection con) throws Exception {
        System.out.println("Enter Name of Product - ");
        String name = sc.nextLine();
        System.out.println("Enter description of Product - ");
        String description = sc.nextLine();
        System.out.println("Enter Price of Product - ");
        double price = sc.nextDouble();
        System.out.println("Enter Stock Quantity - ");
        int stock_quantity = sc.nextInt();
        String insert = "Insert into product(name,description,price,stock_quantity) values(?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(insert);
        ps.setString(1, name);
        ps.setString(2, description);
        ps.setDouble(3, price);
        ps.setInt(4, stock_quantity);
        ps.executeUpdate();
        System.out.println("✅ Product Added");
    }

    void Update(Connection con) throws Exception {
        int choice = 0;
        do {
            System.out.println("1 - Update Price of Product");
            System.out.println("2 - Update Stock Quantity");
            System.out.println("3 - Exit");
            System.out.println("Enter your choice - ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter Product id - ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter Updated Price of product");
                    double price = sc.nextDouble();
                    String updateprice = "update product set price = ? where id = ? ";
                    PreparedStatement ps = con.prepareStatement(updateprice);
                    ps.setDouble(1, price);
                    ps.setInt(2, id);
                    ps.executeUpdate();
                    System.out.println("✅ Price Updated");
                    break;
                case 2:
                    System.out.println("Enter Product id - ");
                    int id1 = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter Updated Stock Quantity of product");
                    int stockQuantity = sc.nextInt();
                    String updateStockQuantity = "update product set stock_quantity = ? where id = ? ";
                    PreparedStatement ps1 = con.prepareStatement(updateStockQuantity);
                    ps1.setInt(1, stockQuantity);
                    ps1.setInt(2, id1);
                    ps1.executeUpdate();
                    System.out.println("✅ Stock Quantity Updated");
                    break;
                case 3:
                    System.out.println("👋 Exiting");
                default:
                    System.out.println("❌ Invalid choice");
            }
        }
        while (choice != 3);

    }

    void View(Connection con) throws Exception {
        String select = "select * from product";
        PreparedStatement ps = con.prepareStatement(select);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getDouble(4) + "\t" + rs.getInt(5));
        }

    }
    void delete(Connection con) throws Exception
    {
        System.out.println("Enter id of product that is to be deleted");
        int id = sc.nextInt();
        String delete = "delete from product where id = ?";
        PreparedStatement ps = con.prepareStatement(delete);
        ps.setInt(1,id);
        ps.executeUpdate();
        System.out.println("✅ product deleted successfully");
    }
}
class Admin
{
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        AdminManagement p = new AdminManagement();
        int choice = 0;
        Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/shopping_system",
                    "root",
                    "");


        do {
            System.out.println("1 - Add Product");
            System.out.println("2 - Update Product");
            System.out.println("3 - View Product");
            System.out.println("4 - Delete Product");
            System.out.println("5 - Exit");
            System.out.println("Enter your choice");
            choice = sc.nextInt();
            switch (choice)
            {
                case 1:p.Addproduct(con);break;
                case 2:p.Update(con);break;
                case 3:p.View(con);break;
                case 4:p.delete(con);break;
                case 5:
                    System.out.println("👋 Exiting");break;
                default:
                    System.out.println("❌ Invalid choice");
            }
        }
        while(choice!=5);
    }
}
