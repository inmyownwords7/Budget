package org.java.financial.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.opencsv.CSVWriter;
import org.java.financial.entity.Transaction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Utility class to generate financial reports in JSON, CSV, and PDF formats.
 */
public class ReportGenerator {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts transactions to JSON and saves to a file.
     */
    public static void generateJsonReport(List<Transaction> transactions, String filePath) {
        try {
            objectMapper.writeValue(new File(filePath), transactions);
            System.out.println("✅ JSON Report Generated: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts transactions to CSV and saves to a file.
     */
    public static void generateCsvReport(List<Transaction> transactions, String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(new String[]{"ID", "Category", "Amount", "Date"});
            for (Transaction transaction : transactions) {
                writer.writeNext(new String[]{
                        String.valueOf(transaction.getId()),
                        transaction.getCategory().getCategoryName(),
                        String.valueOf(transaction.getAmount()),
                        transaction.getDate().toString()
                });
            }
            System.out.println("✅ CSV Report Generated: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts transactions to PDF and saves to a file.
     */
    public static void generatePdfReport(List<Transaction> transactions, String filePath) {
        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("📊 Financial Report").setBold());

            Table table = new Table(4);
            table.addCell("ID");
            table.addCell("Category");
            table.addCell("Amount");
            table.addCell("Date");

            for (Transaction transaction : transactions) {
                table.addCell(String.valueOf(transaction.getId()));
                table.addCell(transaction.getCategory().getCategoryName());
                table.addCell(String.valueOf(transaction.getAmount()));
                table.addCell(transaction.getDate().toString());
            }

            document.add(table);
            document.close();
            System.out.println("✅ PDF Report Generated: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
