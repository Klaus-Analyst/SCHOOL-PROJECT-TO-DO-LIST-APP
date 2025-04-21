package project.toDoListApp.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import project.toDoListApp.service.AuthService;

public class RegistrationScreen {
    private final Stage stage;
    private final AuthService authService;
    
    public RegistrationScreen(Stage primaryStage) {
        this.stage = primaryStage;
        this.authService = new AuthService();
    }
    
    public void show() {
        stage.setTitle("TODO App - Register");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        // Create UI components
        Label titleLabel = new Label("Create New Account");
        titleLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Choose a username");
        
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Your email address");
        
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Create a password");
        
        Label confirmLabel = new Label("Confirm Password:");
        PasswordField confirmField = new PasswordField();
        confirmField.setPromptText("Re-enter your password");
        
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        
        Button backButton = new Button("Back to Login");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");
        
        // Add components to grid
        grid.add(titleLabel, 0, 0, 2, 1);
        grid.add(usernameLabel, 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(passwordLabel, 0, 3);
        grid.add(passwordField, 1, 3);
        grid.add(confirmLabel, 0, 4);
        grid.add(confirmField, 1, 4);
        grid.add(registerButton, 1, 5);
        grid.add(backButton, 1, 6);
        grid.add(messageLabel, 0, 7, 2, 1);
        
        // Set button actions
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirm = confirmField.getText();
            
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please fill in all fields");
                return;
            }
            
            if (!password.equals(confirm)) {
                messageLabel.setText("Passwords don't match!");
                return;
            }
            
            if (authService.registerUser(username, password, email)) {
                messageLabel.setText("Registration successful! Please login.");
            } else {
                messageLabel.setText("Registration failed. Username may be taken.");
            }
        });
        
        backButton.setOnAction(e -> {
            new LoginScreen(stage).show();
        });
        
        // Create scene and show stage
        Scene scene = new Scene(grid, 450, 400);
        stage.setScene(scene);
        stage.show();
    }
}