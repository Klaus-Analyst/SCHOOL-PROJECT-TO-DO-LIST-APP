package project.toDoListApp.utility;

import java.sql.*;
import java.util.logging.Logger;

public class DatabaseUtility {
    private static final Logger logger = Logger.getLogger(DatabaseUtility.class.getName());
    private static final String DB_URL = "jdbc:mysql://localhost:3306/todo_app_db";
    private static final String DB_USER = "root"; // Change to your MySQL username
    private static final String DB_PASSWORD = "klaus2014"; // Change to your MySQL password

    static {
        try {
            // Initialize database tables if they don't exist
            initializeDatabase();
        } catch (SQLException e) {
            logger.severe("Failed to initialize database: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            logger.severe("MySQL JDBC Driver not found");
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }

    private static void initializeDatabase() throws SQLException {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                + "user_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "username VARCHAR(50) NOT NULL UNIQUE, "
                + "password_hash VARCHAR(255) NOT NULL, "
                + "email VARCHAR(100), "
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

        String createTasksTable = "CREATE TABLE IF NOT EXISTS tasks ("
                + "task_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "title VARCHAR(255) NOT NULL, "
                + "description TEXT, "
                + "due_date DATE, "
                + "status ENUM('Pending', 'Completed') DEFAULT 'Pending', "
                + "priority ENUM('Low', 'Medium', 'High') DEFAULT 'Medium', "
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, "
                + "user_id INT, "
                + "FOREIGN KEY (user_id) REFERENCES users(user_id))";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createTasksTable);
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.warning("Failed to close database connection");
            }
        }
    }
}