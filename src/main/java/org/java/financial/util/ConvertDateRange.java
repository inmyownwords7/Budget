package org.java.financial.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConvertDateRange {
    private static final String JDBC_URL = "jdbc:h2:file:./data/budgetdb";

    public static List<Transaction> getTransactionsBetweenDates(String startDate, String endDate) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE transaction_date BETWEEN ? AND ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, "sa", "");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, startDate);
            stmt.setString(2, endDate);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt("id"),
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        rs.getDate("transaction_date").toLocalDate()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    // DTO Class for Transaction
    public static class Transaction {
        public int id;
        public String category;
        public double amount;
        public java.time.LocalDate date;

        public Transaction(int id, String category, double amount, java.time.LocalDate date) {
            this.id = id;
            this.category = category;
            this.amount = amount;
            this.date = date;
        }

        @Override
        public String toString() {
            return "Transaction{id=" + id + ", category='" + category + "', amount=" + amount + ", date=" + date + '}';
        }
    }
}
