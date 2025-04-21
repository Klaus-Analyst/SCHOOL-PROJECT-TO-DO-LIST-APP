package project.toDoListApp.service;

import project.toDoListApp.utility.DatabaseUtility;
import java.sql.*;
import java.util.logging.Logger;

public class AuthService {
    private static final Logger logger = Logger.getLogger(AuthService.class.getName());
    
    public boolean registerUser(String username, String password, String email) {
        String sql = "INSERT INTO users (username, password_hash, email) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Simple hashing for now (we'll improve this later)
            String hashedPassword = Integer.toString(password.hashCode());
            
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, email);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            logger.severe("Registration failed: " + e.getMessage());
            return false;
        }
    }
    
    public boolean loginUser(String username, String password) {
        String sql = "SELECT password_hash FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                String inputHash = Integer.toString(password.hashCode());
                return storedHash.equals(inputHash);
            }
            return false;
            
        } catch (SQLException e) {
            logger.severe("Login failed: " + e.getMessage());
            return false;
        }
    }
    
    public int getUserId(String username) {
        String sql = "SELECT user_id FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("user_id");
            }
            return -1;
            
        } catch (SQLException e) {
            logger.severe("Failed to get user ID: " + e.getMessage());
            return -1;
        }
    }
}