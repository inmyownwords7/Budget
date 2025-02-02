package org.java.financial.util;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.util.Optional;
public class H2ToJsonConverter  {
    private static final String JDBC_URL = "jdbc:h2:file:./data/budgetdb";

    public static Optional<String> fetchRecordAsJson(int transactionId) {
        String sql = "SELECT * FROM transactions WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, "sa", "");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // ✅ Convert to DTO
                Transaction transaction = new Transaction(
                        rs.getInt("id"),
                        rs.getString("category"),
                        rs.getDouble("amount")
                );

                // ✅ Convert to JSON
                ObjectMapper objectMapper = new ObjectMapper();
                return Optional.of(objectMapper.writeValueAsString(transaction));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // DTO Class for Transaction
    public static class Transaction {
        public int id;
        public String category;
        public double amount;

        public Transaction(int id, String category, double amount) {
            this.id = id;
            this.category = category;
            this.amount = amount;
        }
    }
}
