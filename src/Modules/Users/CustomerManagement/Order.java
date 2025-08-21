package Modules.Users.CustomerManagement;

import java.util.Date;

public class Order {
    int user_id;
    int order_id;
    int product_id;
    String product_name;
    int quantity;
    double total_price;
    Date order_date;

    public Order(String product_name, int user_id, int order_id, int product_id, int quantity, double total_price, Date order_date) {
        this.product_name = product_name;
        this.user_id = user_id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.total_price = total_price;
        this.order_date = order_date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "user_id=" + user_id +
                ", order_id=" + order_id +
                ", product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                ", quantity=" + quantity +
                ", total_price=" + total_price +
                ", order_date=" + order_date +
                '}';
    }

    public int getOrder_id() {
        return order_id;
    }
}
