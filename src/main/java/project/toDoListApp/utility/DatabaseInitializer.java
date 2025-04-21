package project.toDoListApp.utility;

import java.sql.*;

public class DatabaseInitializer {
    public static void initializeDatabase() {
        String createCategoriesTable = "CREATE TABLE IF NOT EXISTS categories (" +
                "category_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL UNIQUE, " +
                "description TEXT, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
                
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(255) NOT NULL UNIQUE, " +
                "password_hash VARCHAR(255) NOT NULL, " +
                "email VARCHAR(255), " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
                
        String createTasksTable = "CREATE TABLE IF NOT EXISTS tasks (" +
                "task_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "title VARCHAR(255) NOT NULL, " +
                "description TEXT, " +
                "due_date DATE, " +
                "status ENUM('Pending', 'Completed') DEFAULT 'Pending', " +
                "priority ENUM('Low', 'Medium', 'High') DEFAULT 'Medium', " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "user_id INT NOT NULL, " +
                "FOREIGN KEY (user_id) REFERENCES users(user_id))";
                
        try (Connection conn = DatabaseUtility.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(createCategoriesTable);
            stmt.execute(createUsersTable);
            stmt.execute(createTasksTable);
            
            // Insert default user if not exists
            stmt.execute("INSERT IGNORE INTO users (username, password_hash, email) VALUES " +
                    "('admin', 'hashed_password', 'admin@todoapp.com')");
            
            // Insert some default categories if not exists
            stmt.execute("INSERT IGNORE INTO categories (name, description) VALUES " +
                    "('Work', 'Work-related tasks'), " +
                    "('Personal', 'Personal tasks'), " +
                    "('Shopping', 'Shopping list items')");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}