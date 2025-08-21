package Modules.Utils;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * SmartShoppingSystem
 * Tracks purchase dates and estimates the next likely purchase day.
 */
public class SmartShoppingSystem {

    // -------- Inner class to hold purchase history --------
    public static class Stats {
        private final List<Integer> purchaseDays = new ArrayList<>();

        public void addPurchase(int day) {
            purchaseDays.add(day);
        }

        public void addPurchase(Timestamp ts) {
            if (ts == null) return;
            int day = (int) (ts.getTime() / (1000L * 60L * 60L * 24L));
            purchaseDays.add(day);
        }

        public int getAvgInterval() {
            if (purchaseDays.size() < 2) return -1;
            int total = 0;
            for (int i = 1; i < purchaseDays.size(); i++) {
                total += (purchaseDays.get(i) - purchaseDays.get(i - 1));
            }
            return total / (purchaseDays.size() - 1);
        }

        public int getLastDay() {
            return purchaseDays.isEmpty() ? -1 : purchaseDays.get(purchaseDays.size() - 1);
        }

        public Integer predictNextDay() {
            int avg = getAvgInterval();
            int last = getLastDay();
            if (avg == -1 || last == -1) return null;
            return last + avg;
        }

        public LocalDate predictNextLocalDate() {
            Integer next = predictNextDay();
            if (next == null) return null;
            long millis = next.longValue() * 24L * 60L * 60L * 1000L;
            return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }

    /** Build Stats for a user by scanning their orders in chronological order. */
    public static Stats buildStatsFromDb(Connection con, int userId) throws SQLException {
        String sql = "SELECT order_date FROM orders WHERE user_id = ? ORDER BY order_date ASC";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                Stats s = new Stats();
                while (rs.next()) {
                    s.addPurchase(rs.getTimestamp(1));
                }
                return s;
            }
        }
    }
}
