package com.example.oops_project;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage; // Static stage for global access

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        MenuScreen menu = new MenuScreen(primaryStage);
        menu.showMenu();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
