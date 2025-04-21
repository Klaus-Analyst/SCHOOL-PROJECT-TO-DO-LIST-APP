package project.toDoListApp.dao;

import project.toDoListApp.model.Task;
import project.toDoListApp.utility.DatabaseInitializer;
import project.toDoListApp.utility.DatabaseUtility;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskDAO {
    public List<Task> getTasksForUser(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE user_id = ?";
        
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Task task = new Task(
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getDate("due_date") != null ? rs.getDate("due_date").toLocalDate() : null
                );
                task.setId(rs.getInt("task_id"));
                task.setPriority(rs.getString("priority"));
                task.setActiveStatus("Completed".equals(rs.getString("status")));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
    
    public boolean addTask(Task task, int userId) {
        String sql = "INSERT INTO tasks (title, description, due_date, status, priority, user_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtility.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, task.getTaskName());
            pstmt.setString(2, task.getDescription());
            pstmt.setDate(3, task.getDueDate() != null ? Date.valueOf(task.getDueDate()) : null);
            pstmt.setString(4, task.getStatus() ? "Completed" : "Pending");
            pstmt.setString(5, task.getPriority());
            pstmt.setInt(6, userId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        task.setId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes the specified task from the database.
     *
     * @param task The task to be removed.
     */
    public void removeTask(Task task) {
        // Implementation to remove the task from the database
        // Example: Assuming you have a database connection and a table named "tasks"
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection connection = DatabaseUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, task.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Updates the specified task in the database.
     *
     * @param task The task to be updated.
     */
    public void updateTask(Task task) {
        // Implementation to update the task in the database
        // Example: Assuming you have a database connection and a table named "tasks"
        String sql = "UPDATE tasks SET title = ?, description = ?, due_date = ?, status = ?, priority = ? WHERE id = ?";
        try (Connection connection = DatabaseUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, task.getTaskName());
            statement.setString(2, task.getDescription());
            statement.setDate(3, Date.valueOf(task.getDueDate()));
            statement.setString(4, task.getStatus() ? "Completed" : "Pending");
            statement.setString(5, task.getPriority());
            statement.setInt(6, task.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Retrieves all tasks from the database.
     *
     * @return A list of all tasks.
     */
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        try (Connection connection = DatabaseUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Task task = new Task(
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("category"),
                        resultSet.getDate("due_date") != null ? resultSet.getDate("due_date").toLocalDate() : null
                );
                task.setPriority(resultSet.getString("priority"));
                task.setActiveStatus("Completed".equals(resultSet.getString("status")));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;

    }
    /**
     * Retrieves tasks by category from the database.
     *
     * @param category The category to filter tasks by.
     * @return A list of tasks in the specified category.
     */
    public List<Task> getTasksByCategory(String category) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE category = ?";
        try (Connection connection = DatabaseUtility.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task(
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("category"),
                        resultSet.getDate("due_date") != null ? resultSet.getDate("due_date").toLocalDate() : null
                );
                task.setPriority(resultSet.getString("priority"));
                task.setActiveStatus("Completed".equals(resultSet.getString("status")));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * Retrieves all uncompleted tasks from the database.
     *
     * @return A list of uncompleted tasks.
     */
    public List<Task> getAllUncompletedTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE status != 'Completed'";
        try (Connection connection = DatabaseUtility.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Task task = new Task(
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("category"),
                        resultSet.getDate("due_date") != null ? resultSet.getDate("due_date").toLocalDate() : null
                );
                task.setPriority(resultSet.getString("priority"));
                task.setActiveStatus("Completed".equals(resultSet.getString("status")));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
