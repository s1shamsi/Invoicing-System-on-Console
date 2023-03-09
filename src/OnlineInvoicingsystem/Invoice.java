package OnlineInvoicingsystem;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import invoicingSystem.item;

public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;
    private int numberOfItems;
    private int invoiceNo = 0;
    private String customerFullName;
    private String customerPhone;
    private double totalAmount =0;
    private double paidAmount =0;
    private double balance =0;
    private Date invoiceDate;
     ArrayList<Item> items = new ArrayList<Item>();
    
    public void setCustomerFullName(String name) {
    	this.customerFullName= name;
    }
    public String getCustomerFullName() {
        return customerFullName;
    }
    public void setCustomerPhone(String Pnumber) {
    	this.customerPhone= Pnumber;
    }
    public String getCustomerPhone() {
        return customerPhone;
    }
    public void setInvoiceDate(Date date) {
    	this.invoiceDate= date;
    }
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setTotalAmount(double tprice) {
    	this.totalAmount= tprice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public void setPaidAmount1(double paid) {
    	this.paidAmount= paid;
        
        
    }
    public double getPaidAmount() {
        return paidAmount;
    }
    public void setBalance(double blnc) {
    	this.balance= blnc;
    	
        
    }
    public double getBalance() {
        return balance;
    }

	public void setInvoiceNo(int no) {
		
	this.invoiceNo = no;

	
	}
	 public int getInvoiceNo() {
	        return invoiceNo;
	    }
	 
	public void setNumberOfItems(int no2) {
		
		this.numberOfItems = no2;

		
		}
		public int getNumberOfItems() {
			
			return numberOfItems;
		}
    public ArrayList<Item> getItems() {
        return items;
    }
    public void addItem(Item item) {
        for (Item i : items) {
            if (i.getItemID()==(item.getItemID())) {
                System.out.println("Error: Item with ID " + item.getItemID() + " already exists.");
                return;
            }
        }
        items.add(item);
        numberOfItems++;
        totalAmount += item.getTotalPrice();
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

    public void saveVoice(Connection conn, int shopId) throws SQLException {
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
   