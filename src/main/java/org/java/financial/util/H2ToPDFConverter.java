package org.java.financial.util;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.sql.*;

public class H2ToPDFConverter {
    private static final String JDBC_URL = "jdbc:h2:file:./data/budgetdb";

    public static boolean generatePDF(int transactionId, String outputFilePath) {
        String sql = "SELECT * FROM transactions WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, "sa", "");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // ✅ Create PDF
                PdfWriter writer = new PdfWriter(outputFilePath);
                PdfDocument pdfDoc = new PdfDocument(writer);
                Document document = new Document(pdfDoc);

                document.add(new Paragraph("Transaction ID: " + rs.getInt("id")));
                document.add(new Paragraph("Category: " + rs.getString("category")));
                document.add(new Paragraph("Amount: $" + rs.getDouble("amount")));
                document.close();

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
