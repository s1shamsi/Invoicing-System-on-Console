package OnlineInvoicingsystem;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class menu {
	private shop shop;
    public menu(shop shop) {
        this.shop = shop;
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String url = "jdbc:sqlserver://localhost:1433;" +
	             "databaseName=HotelDBMS;" +
	             "encrypt=true;" +
	             "trustServerCertificate=true";
	     String user = "said";
	     String pass = "root";
	     boolean stat = true;
	 	Connection conn = null;

	     try {
	Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
	DriverManager.registerDriver(driver);
	         conn = DriverManager.getConnection(url, user, pass);

	         System.out.println("Connected to database successfully.");
	         System.out.println("");
	         System.out.println("");}
	     catch (Exception ex) {
	    		System.err.println(ex);
	    		}
	
	


	}
	
}
