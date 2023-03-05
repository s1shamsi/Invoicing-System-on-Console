package OnlineInvoicingsystem;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import invoicingSystem.invoice;
import invoicingSystem.item;

public class shop implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private String tel;
    private String fax;
    private String email;
    private String website;
    ArrayList<Item> items= new ArrayList<Item>();
    ArrayList<Invoice> invoices = new ArrayList<Invoice>();
 
    
    public static int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    	public void saveshop(Connection conn) throws SQLException {
    	    String sql = "INSERT INTO Shop (name, tel, fax, email, website) VALUES (?, ?, ?, ?, ?)";
    	    PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    	    stmt.setString(1, this.name);
    	    stmt.setString(2, this.tel);
    	    stmt.setString(3, this.fax);
    	    stmt.setString(4, this.email);
    	    stmt.setString(5, this.website);
    	    int affectedRows = stmt.executeUpdate();

    	    if (affectedRows == 0) {
    	        throw new SQLException("Creating shop failed, no rows affected.");
    	    }

    	    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
    	        if (generatedKeys.next()) {
    	            this.id = generatedKeys.getInt(1);
    	        } else {
    	            throw new SQLException("Creating shop failed, no ID obtained.");
    	        }
    	    }
    	}

    	public static shop loadshop(Connection conn, int id) throws SQLException {
    	    String sql = "SELECT * FROM Shop WHERE id = ?";
    	    PreparedStatement stmt = conn.prepareStatement(sql);
    	    stmt.setInt(1, id);
    	    ResultSet rs = stmt.executeQuery();

    	    if (rs.next()) {
    	        String name = rs.getString("name");
    	        String tel = rs.getString("tel");
    	        String fax = rs.getString("fax");
    	        String email = rs.getString("email");
    	        String website = rs.getString("website");
   
    	    }

    	    return null;
    	}
}