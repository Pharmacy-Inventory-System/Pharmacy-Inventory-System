package Pharmeny;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class JDBC {
    
    public static Connection getConnection() {
        try {
            // First, test connection without database
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Try to connect to MySQL server first
            Connection testConn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/", "root", "Add@1234");
            
            // Check if pharmacy database exists
            var stmt = testConn.createStatement();
            var rs = stmt.executeQuery("SHOW DATABASES LIKE 'pharmacy'");
            
            if (!rs.next()) {
                // Create database if doesn't exist
                stmt.executeUpdate("CREATE DATABASE pharmacy");
                System.out.println("Created 'pharmacy' database");
            }
            
            testConn.close();
            
            // Now connect to pharmacy database
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/pharmacy", "root", "Add@1234");
            
            System.out.println("✅ Database connected successfully!");
            return conn;
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, 
                "MySQL Driver not found!\n" +
                "Add MySQL Connector/J to project libraries.");
            return null;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Cannot connect to MySQL!\n" +
                "Error: " + e.getMessage() + "\n\n" +
                "Check:\n" +
                "1. MySQL service is running\n" +
                "2. Password is 'Add@1234'\n" +
                "3. Port 3306 is not blocked");
            return null;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Testing connection...");
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Success! Database is ready.");
            try { conn.close(); } catch (Exception e) {}
        }
    }
}