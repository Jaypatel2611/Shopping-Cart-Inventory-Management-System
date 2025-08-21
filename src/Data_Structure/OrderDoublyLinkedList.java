package Data_Structure;

import Database.Database;
import Modules.Users.CustomerManagement.Order;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDoublyLinkedList {
    private OrderNode head;
    private OrderNode tail;

    public OrderDoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    // Add new order at end
    public void add(Order order) {
        OrderNode newNode = new OrderNode(order);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        System.out.println("✅ Order added: " + order);
    }

    // Remove particular order by ID
    public boolean remove(int orderId) {
        if (head == null) {
            System.out.println("❌ List is empty.");
            return false;
        }

        OrderNode current = head;
        while (current != null) {
            if (current.order.getOrder_id() == orderId) {
                if (current == head && current == tail) { // only one node
                    head = tail = null;
                } else if (current == head) { // remove head
                    head = head.next;
                    head.prev = null;
                } else if (current == tail) { // remove tail
                    tail = tail.prev;
                    tail.next = null;
                } else { // remove middle
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                System.out.println("🗑️ Removed order: " + current.order);
                return true;
            }
            current = current.next;
        }
        System.out.println("⚠️ Order not found with ID: " + orderId);
        return false;
    }

    // Display all orders (head → tail)
    public void display() {
        if (head == null) {
            System.out.println("📭 No orders to display.");
            return;
        }
        System.out.println("----- 📦 Orders (Oldest to Latest) -----");
        OrderNode temp = head;
        while (temp != null) {
            System.out.println(temp.order);
            temp = temp.next;
        }
    }

    // ✅ Display all orders in reverse (tail → head)
    public void reverseDisplay() {
        if (tail == null) {
            System.out.println("📭 No orders to display.");
            return;
        }
        System.out.println("----- 📦 Orders (Latest to Oldest) -----");
        OrderNode temp = tail;
        while (temp != null) {
            System.out.println(temp.order);
            temp = temp.prev;
        }
    }

    // ✅ Undo last order (remove from DLL + delete from DB)
    public void cancelLastOrder() throws Exception {
        if (tail == null) {
            System.out.println("❌ No order to undo.");
            return;
        }

        Order lastOrder = tail.order;
        try {
            // 1. Delete from DB
            String deleteQuery = "DELETE FROM orders WHERE order_id = ?";
            PreparedStatement ps = Database.getCon().prepareStatement(deleteQuery);
            ps.setInt(1, lastOrder.getOrder_id());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("🗑️ Deleted from DB: " + lastOrder);
            } else {
                System.out.println("⚠️ Order not found in DB: " + lastOrder.getOrder_id());
            }

            // 2. Remove from DLL
            if (head == tail) { // only one node
                head = tail = null;
            } else {
                tail = tail.prev;
                tail.next = null;
            }

            System.out.println("↩️ Undo (removed from list): " + lastOrder);

        } catch (SQLException e) {
            System.out.println("❌ Error while deleting last order: " + e.getMessage());
        }
    }
}


class OrderNode {
    Order order;
    OrderNode prev;
    OrderNode next;

    public OrderNode(Order order) {
        this.order = order;
        this.prev = null;
        this.next = null;
    }
}


