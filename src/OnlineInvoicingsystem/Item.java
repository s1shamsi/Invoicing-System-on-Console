package OnlineInvoicingsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Item {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private int shopId;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getShopId() {
        return shopId;
    }
    

    public void itemSave(Connection conn) throws SQLException {
        String sql = "INSERT INTO Item (name, price, quantity, shop_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, quantity);
            stmt.setInt(4, shop.getId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating item failed, no ID obtained.");
                }
            }
        }
    }

    public static void getItemById(Connection conn, int id) throws SQLException {
        String sql = "SELECT name, price, quantity, shop_id FROM Item WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
       
        }
    }

    public void ItemUpdate(Connection conn, int id) throws SQLException {
        String sql = "UPDATE Item SET name = ?, price = ?, quantity = ?, shop_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, quantity);
            stmt.setInt(4, shopId);
            stmt.setInt(5, id);
            stmt.executeUpdate();
        }
    }

    public void ItemDelete(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM Item WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
