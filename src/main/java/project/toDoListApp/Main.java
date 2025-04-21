package project.toDoListApp;

import javafx.application.Application;
import javafx.stage.Stage;
import project.toDoListApp.utility.DatabaseInitializer;
import project.toDoListApp.view.LoginScreen;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        DatabaseInitializer.initializeDatabase();
        new LoginScreen(primaryStage).show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}