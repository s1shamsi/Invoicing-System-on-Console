package OnlineInvoicingsystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class shop implements Serializable {

	private int shopId; 
	private String shopName;
      private String tel;
      private String fax;
      private String email;
      private String website;
       ArrayList<Item> items= new ArrayList<Item>();
       ArrayList<Invoice> invoices = new ArrayList<Invoice>();


      public void setShopName(String shopName) {
          this.shopName = shopName;
      }

      public String getShopName() {
          return shopName;
      }

      public void setTel(String tel) {
          this.tel = tel;
      }

      public String getTel() {
          return tel;
      }

      public void setFax(String fax) {
          this.fax = fax;
      }

      public String getFax() {
          return fax;
      }

      public void setEmail(String email) {
          this.email = email;
      }

      public String getEmail() {
          return email;
      }

      public void setWebsite(String website) {
          this.website = website;
      }

      public String getWebsite() {
          return website;
      }

      public void addItem(Item item) {
          items.add(item);
      }

      public void deleteItem(int itemId) {
    	    for (int i = 0; i < this.items.size(); i++) {
    	        if (items.get(i).getId() == itemId) {
    	            this.items.remove(i);
    	            break;
    	        }
    	    }
    	}



      public void changeItemPrice(int itemId, double newPrice) {
    	    for (int i = 0; i < this.items.size(); i++) {
    	        if (this.items.get(i).getId() == itemId) {
    	            this.items.get(i).setUnitPrice(newPrice);
    	            break;
    	        }
    	    }
    	}



    

      public ArrayList<item> getAllItems() {
          return items;
      }

      public void createNewInvoice(invoice invoice) {
          invoices.add(invoice);
      }

      public ArrayList<invoice> getAllInvoices() {
          return invoices;
      }

      public invoice getInvoice(int invoiceNo) {
          for (int i = 0; i < this.invoices.size(); i++) {
              if (this.invoices.get(i).getInvoiceNo() == invoiceNo) {
                  return invoices.get(i);
              }
          }
          return null;
      }

      public int getNumberOfItems() {
          return items.size();
      }

      public int getNumberOfInvoices() {
          return invoices.size();
      }

      public double getTotalSales() {
          double totalSales = 0;
          for (int i = 0; i < invoices.size(); i++) {
              totalSales += invoices.get(i).getTotalAmount();
          }
          return totalSales;
      }

      public boolean loadData() {

    	    try {
    	        // Loading items
    	        FileInputStream fis = new FileInputStream("..\\items.txt");
    	        ObjectInputStream ois = new ObjectInputStream(fis);
    	        this.items = (ArrayList<item>) ois.readObject();
    	        ois.close();
    	        fis.close();

    	        // Loading invoices
    	        FileInputStream fis1 = new FileInputStream("..\\invoices.txt");
    	        ObjectInputStream ois1 = new ObjectInputStream(fis1);
    	        this.invoices = (ArrayList<invoice>) ois1.readObject();
    	        ois1.close();
    	        fis1.close();

    	        // Loading shop details
    	        FileInputStream fis2 = new FileInputStream("..\\shopDetails.txt");
    	        ObjectInputStream ois2 = new ObjectInputStream(fis2);
    	        this.shopName = ois2.readUTF();
    	        this.tel = ois2.readUTF();
    	        this.fax = ois2.readUTF();
    	        this.email = ois2.readUTF();
    	        this.website = ois2.readUTF();
    	        ois2.close();
    	        fis2.close();
    	 
    	}
    	        		catch (FileNotFoundException e) {
    	        System.out.println("Error loading data: File not found.");
    	        return false;
    	    } catch (IOException e) {
    	        System.out.println("Error loading data: IOException.");
    	        return false;
    	    } catch (ClassNotFoundException e) {
    	        System.out.println("Error loading data: Class not found.");
    	        return false;
    	    } catch (Exception e) {
    	        System.out.println("Error loading data: " + e.getMessage());
    	        return false;
    	    }
    	    System.out.println("Data loaded successfully.");
    	    return true;
    	}



      public void saveData() {
    	    try {
    	        // Saving items
    	        FileOutputStream fos = new FileOutputStream("..\\items.txt");
    	        ObjectOutputStream oos = new ObjectOutputStream(fos);
    	        oos.writeObject(Item);
    	        oos.close();
    	        fos.close();

    	        // Saving invoices
    	        FileOutputStream fos1 = new FileOutputStream("..\\invoices.txt");
    	        ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
    	        oos1.writeObject(Invoice);
    	        oos1.close();
    	        fos1.close();

    	        // Saving shop details
    	        FileOutputStream fos2 = new FileOutputStream("..\\shopDetails.txt");
    	        ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
    	        oos2.writeUTF(shopName);
    	        oos2.writeUTF(tel);
    	        oos2.writeUTF(fax);
    	        oos2.writeUTF(email);
    	        oos2.writeUTF(website);
    	        oos2.close();
    	        fos2.close();
    	        } catch (Exception e) {
    	        System.out.println("Error saving data: " + e.getMessage());
    	        }
    	        }




    public void saveShop(Connection conn) throws SQLException {
        String sql = "INSERT INTO Shop (name, tel, fax, email, website) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, this.shopName);
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
                this.shopId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating shop failed, no ID obtained.");
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public static shop loadShop(Connection conn, int shopId) throws SQLException {
        String sql = "SELECT * FROM Shop WHERE shop_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, shopId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            shop s = new shop();
            s.setShopId(rs.getInt("shop_id"));
            s.setName(rs.getString("name"));
            s.setTel(rs.getString("tel"));
            s.setFax(rs.getString("fax"));
            s.setEmail(rs.getString("email"));
            s.setWebsite(rs.getString("website"));
            return s;
        } else {
            throw new SQLException("Shop with ID " + shopId + " not found.");
        }
    }
    public void addItem(String name, double price, int quantity) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setShop(this);

        try  {
            item.itemSave(null);
            System.out.println("Item added successfully!");
        } catch (SQLException ex) {
            System.out.println("An error occurred while adding the item: " + ex.getMessage());
        }
    


    }
}

    