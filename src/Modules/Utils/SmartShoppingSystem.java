package Modules.Utils;

import Database.Database;
import Modules.Users.User;

import java.awt.dnd.DropTarget;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SmartShoppingSystem
 * Tracks purchase dates and estimates the next likely purchase day.
 */
public class SmartShoppingSystem {

    public static List<String> getSmartReminders(int userId) throws Exception {
        String sql = "SELECT product_name, order_date " +
                "FROM orders WHERE user_id = ? ORDER BY product_name, order_date ASC";

        Map<String, List<LocalDate>> productOrders = new HashMap<>();

        // Step 1: Collect all orders grouped by product
        try (PreparedStatement ps = Database.getCon().prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String product = rs.getString("product_name");
                    LocalDate date = rs.getDate("order_date").toLocalDate();
                    productOrders.computeIfAbsent(product, k -> new ArrayList<>()).add(date);
                }
            }
        }

        List<String> reminders = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // Step 2: Analyze each product's purchase pattern
        for (Map.Entry<String, List<LocalDate>> entry : productOrders.entrySet()) {
            String product = entry.getKey();
            List<LocalDate> dates = entry.getValue();

            if (dates.size() < 3) continue; // Need at least 3 orders

            // Step 3: Calculate intervals between orders
            List<Long> intervals = new ArrayList<>();
            for (int i = 1; i < dates.size(); i++) {
                long gap = ChronoUnit.DAYS.between(dates.get(i - 1), dates.get(i));
                intervals.add(gap);
            }

            // Step 4: Check if intervals are consistent (allow ±1 day variation)
            long avg = (long) intervals.stream().mapToLong(Long::longValue).average().orElse(0);
            boolean consistent = intervals.stream().allMatch(d -> Math.abs(d - avg) <= 1);

            if (!consistent) continue;

            // Step 5: Predict next purchase date
            LocalDate lastPurchase = dates.get(dates.size() - 1);
            LocalDate predictedNext = lastPurchase.plusDays(avg);

            if (!today.isBefore(predictedNext)) {
                reminders.add("🔔 You usually order " + product + " every " + avg +
                        " days. Last bought on " + lastPurchase +
                        ". You should order again .");
            }
        }

        return reminders;
    }


    // -------- Inner class to hold purchase history --------
    public static class Stats {
        private static final List<Integer> purchaseDays = new ArrayList<>();

        public static void addPurchase(int day) {
            purchaseDays.add(day);
        }

        public static void addPurchase(Timestamp ts) {
            if (ts == null) return;
            int day = (int) (ts.getTime() / (1000L * 60L * 60L * 24L));
            purchaseDays.add(day);
        }

        public static int getAvgInterval() {
            if (purchaseDays.size() < 2) return -1;
            int total = 0;
            for (int i = 1; i < purchaseDays.size(); i++) {
                total += (purchaseDays.get(i) - purchaseDays.get(i - 1));
            }
            return total / (purchaseDays.size() - 1);
        }

        public static int getLastDay() {
            return purchaseDays.isEmpty() ? -1 : purchaseDays.get(purchaseDays.size() - 1);
        }

        public static Integer predictNextDay() {
            int avg = getAvgInterval();
            int last = getLastDay();
            if (avg == -1 || last == -1) return null;
            return last + avg;
        }

        public static LocalDate predictNextLocalDate() {
            Integer next = predictNextDay();
            if (next == null) return null;
            long millis = next.longValue() * 24L * 60L * 60L * 1000L;
            return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }

    // -------- New Inactivity Alert Logic (based on average gap) --------
    public static List<String> smartInactivityAlerts(int userId) throws Exception {
        String sql = "SELECT product_name, order_date FROM orders " +
                "WHERE user_id = ? ORDER BY product_name, order_date ASC";

        Map<String, List<LocalDate>> productOrders = new HashMap<>();

        // Collect all orders grouped by product
        try (PreparedStatement ps = Database.getCon().prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String product = rs.getString("product_name");
                    LocalDate date = rs.getDate("order_date").toLocalDate();
                    productOrders.computeIfAbsent(product, k -> new ArrayList<>()).add(date);
                }
            }
        }

        List<String> alerts = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Map.Entry<String, List<LocalDate>> entry : productOrders.entrySet()) {
            String product = entry.getKey();
            List<LocalDate> orderHistory = entry.getValue();

            if (orderHistory.size() < 2) continue; // not enough history

            // Calculate average gap for this product
            long totalGap = 0;
            for (int i = 1; i < orderHistory.size(); i++) {
                totalGap += ChronoUnit.DAYS.between(orderHistory.get(i - 1), orderHistory.get(i));
            }
            long avgGap = totalGap / (orderHistory.size() - 1);

            LocalDate lastOrder = orderHistory.get(orderHistory.size() - 1);
            LocalDate expectedNext = lastOrder.plusDays(avgGap);

            // Allow ±1 day tolerance
            LocalDate maxExpected = expectedNext.plusDays(1);

            if (today.isAfter(maxExpected)) {
                alerts.add("⚠️ You usually buy " + product +
                        " every ~" + avgGap + " days. Last bought on " + lastOrder +
                        ", but no order since then!");
            }
        }

        return alerts;
    }

}
