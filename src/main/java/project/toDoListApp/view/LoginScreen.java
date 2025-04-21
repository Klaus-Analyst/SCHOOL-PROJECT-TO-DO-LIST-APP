package project.toDoListApp.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import project.toDoListApp.service.AuthService;

public class LoginScreen {
    private final Stage stage;
    private final AuthService authService;
    
    public LoginScreen(Stage primaryStage) {
        this.stage = primaryStage;
        this.authService = new AuthService();
    }
    
    public void show() {
        stage.setTitle("TODO App - Login");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        // Create UI components
        Label titleLabel = new Label("TODO App Login");
        titleLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");
        
        // Add components to grid
        grid.add(titleLabel, 0, 0, 2, 1);
        grid.add(usernameLabel, 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(loginButton, 1, 3);
        grid.add(registerButton, 1, 4);
        grid.add(messageLabel, 0, 5, 2, 1);
        
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            if (authService.loginUser(username, password)) {
                // Close current login window
                ((Stage)loginButton.getScene().getWindow()).close();
                
                // Open main application window
                Stage mainStage = new Stage();
                ToDoListAppGUI mainApp = new ToDoListAppGUI(mainStage, username);
                mainApp.show();
            } else {
                messageLabel.setText("Login failed. Please try again.");
            }
        });
        
        registerButton.setOnAction(e -> {
            new RegistrationScreen(stage).show();
        });
        
        // Create scene and show stage
        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}