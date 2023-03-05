package OnlineInvoicingsystem;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import invoicingSystem.item;

public class Invoice implements Serializable {

    private int id;
    private Date date;
    private String customerName;
    private String cus_tel;
    private BigDecimal totalAmount;
    private int shopId;
    private List<Invoice> invoiceDetails;
    ArrayList<Item> items = new ArrayList<Item>();

    

    public int getId() {
        return id;
    }

    public  Date getDate() {
        return date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerTel() {
        return cus_tel;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public int getShopId() {
        return shopId;
    }

    public List<Invoice> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void addInvoiceDetail(Invoice invoiceDetail) {
        invoiceDetails.add(invoiceDetail);
    }

    public void InvoiceSave(Connection conn) throws SQLException {
        String query = "INSERT INTO Invoice(date, customerName, cus_tel, shop_id, total_amount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, date);
            stmt.setString(2, customerName);
            stmt.setString(3, cus_tel);
            stmt.setInt(4, shop.getId());
            stmt.setBigDecimal(5, totalAmount);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating invoice failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating invoice failed, no ID obtained.");
                }
            }
            for (Invoice detail : details) {
                detail.save(conn, id);
            }
        }
    }

    public void delete(Connection conn) throws SQLException {
        String query = "DELETE FROM Invoice WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            for (InvoiceDetail detail : details) {
                detail.delete(conn);
            }
        }
    }

    public BigDecimal calculateTotalAmount() {
        BigDecimal total = BigDecimal.ZERO;
        for (InvoiceDetail detail : details) {
            total = total.add(detail.calculateTotalAmount());
        }
        return total;
    }

    public static List<Invoice> loadAll(Connection conn) throws SQLException {
        String query = "SELECT id, date, customerName, cus_tel, shop_id, total_amount FROM Invoice";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            List<Invoice> invoices = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                Date date = rs.getDate("date");
                String customerName = rs.getString("customerName");
                String cus_tel = rs.getString("cus_tel");
                int shop_id = rs.getInt("shop_id");
                BigDecimal totalAmount = rs.getBigDecimal("total_amount");
                List<InvoiceDetail> details = InvoiceDetail.loadAll(conn, id);
                Shop shop = Shop.load(conn, shop_id);
                Invoice invoice = new Invoice(id, date, customerName, cus_tel, shop, details, totalAmount);
                invoices.add(invoice);
            }
            return invoices;
        }
    }

    public static Invoice load(Connection conn, int id) throws SQLException {
        String query = "SELECT date, customerName, cus_tel, shop_id, total_amount FROM Invoice WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Date date = rs.getDate("date");
                String customerName = rs.getString("customerName");
                String cus_tel = rs.getString("cus_tel");
                int shop_id = rs.getInt("shop_id");
                BigDecimal totalAmount = rs.getBigDecimal("total_amount");
                List<InvoiceDetail> details = InvoiceDetail.loadAll(conn, id);
                Shop shop = Shop.load(conn, shop_id);
                return new Invoice(id, date, customerName, cus_tel, shop, details, totalAmount);
            } else {
                return null;
            }
        }
    }
