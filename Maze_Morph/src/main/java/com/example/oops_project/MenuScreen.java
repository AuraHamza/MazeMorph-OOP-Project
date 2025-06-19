package com.example.oops_project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuScreen {
    private Stage primaryStage;

    public MenuScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showMenu() {
        VBox root = new VBox(30); // spacing between elements
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #0D1B2A;"); // Very dark blue background

        // ✅ Add Maze Morph Logo
        Image logoImage = new Image(getClass().getResourceAsStream("/MazeMorphlogo.png")); // Put logo image here
        ImageView logoView = new ImageView(logoImage);
        logoView.setFitWidth(250);
        logoView.setPreserveRatio(true);

        // ✅ Add Game Title
        Text title = new Text("Maze Morph");
        title.setFill(Color.web("#00B4D8")); // Bright Blue Color from your logo
        title.setFont(Font.font("Verdana", 48));

        // ✅ Create Stylish Buttons
        Button maze1Btn = createMenuButton("Maze Type 1 (Borders)");
        Button maze2Btn = createMenuButton("Maze Type 2 (Vertical Barriers)");
        Button maze3Btn = createMenuButton("Maze Type 3 (Random Obstacles)");

        maze1Btn.setOnAction(e -> startGame(1));
        maze2Btn.setOnAction(e -> startGame(2));
        maze3Btn.setOnAction(e -> startGame(3));

        root.getChildren().addAll(logoView, title, maze1Btn, maze2Btn, maze3Btn);

        Scene scene = new Scene(root, Constants.WIDTH, Constants.HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Maze Morph - Main Menu");
        primaryStage.show();
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: #0077B6; " + // Deep blue button
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 18px; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-padding: 10px 20px;"
        );
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #00B4D8; " + // Lighter blue on hover
                        "-fx-text-fill: black; " +
                        "-fx-font-size: 18px; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-padding: 10px 20px;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #0077B6; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 18px; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-padding: 10px 20px;"
        ));
        return button;
    }

    private void startGame(int mazeType) {
        GameWindow gameWindow = new GameWindow(mazeType);
        gameWindow.start(primaryStage);
    }
}
