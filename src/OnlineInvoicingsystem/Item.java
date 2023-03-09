package OnlineInvoicingsystem;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private int shopID;
	private int itemID;
    private String itemName;
    private double unitPrice;
    private int quantity;
     private double totalPrice;
    private double qtyAmount;
    
    public  void setitemID(int itemId2) {
    	this.itemID= itemId2;
    }
    public int getItemID() {
        return itemID;
    }

    public void setItemName(String name) {
    	this.itemName= name;
    }
    public String getItemName() {
        return itemName;
    }
    public void setUnitPrice(double uprice) {
    	this.unitPrice= uprice;
    }
    public double getUnitPrice() {
        return unitPrice;
    }
    public void setQuantity(int quan) {
    	this.quantity= quan;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQtyAmount(double qty) {
    	this.qtyAmount= qty;
    }
    public double getQtyAmount() {
        return qtyAmount;
    }
    public void setTotalPrice(double total) {
    	this.totalPrice= total;
    
    }
    public double getTotalPrice() {
        return totalPrice;
    }

    
    public void  ItemgetById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM items WHERE id = ?";
        try (
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	 int itemId = rs.getInt("itemID");
                    String name = rs.getString("itemName");
                    double price = rs.getDouble("unitPrice");
                    int quantity = rs.getInt("quantity");
                } 
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred while adding the item: " + ex.getMessage());
        }
		
    }

    public void saveItem(Connection conn, int shopId) throws SQLException {
        String sql = "INSERT INTO Item (shopId, itemID, itemName,unitPrice,quantity) VALUES (?, ?, ?, ?, ?)";
       
        try(  PreparedStatement stmt = conn.prepareStatement(sql)) {
        	
            stmt.setInt(1, shopId);
            stmt.setInt(2, itemID);
            stmt.setString(3, itemName);
            stmt.setDouble(4, unitPrice);
            stmt.setInt(5, quantity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to insert items: " + e.getMessage());
        }
        
    }
    public void ItemUpdate(Connection conn, int id) throws SQLException {
        String sql = "UPDATE Item SET itemID = ?, itemName = ?, unitPrice = ?, quantity = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setString(2, itemName);
             stmt.setDouble(3, unitPrice);
             stmt.setInt(1, quantity);
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

	

