module project.toDoListApp {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  
  opens project.toDoListApp to javafx.graphics;
  opens project.toDoListApp.controller to javafx.graphics;
  opens project.toDoListApp.view to javafx.graphics;
  opens project.toDoListApp.model to javafx.graphics;
}