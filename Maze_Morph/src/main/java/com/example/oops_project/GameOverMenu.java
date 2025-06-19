package com.example.oops_project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameOverMenu {
    private Stage primaryStage;
    private SoundManager soundManager;
    private int finalScore;

    public GameOverMenu(Stage primaryStage, SoundManager soundManager, int finalScore) {
        this.primaryStage = primaryStage;
        this.soundManager = soundManager;
    }

    public void showMenu() {
        // Add score to leaderboard


        // Create the root Pane
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #0D1B2A;");

        // Set up a Text label to display the score
        Text scoreText = new Text("Score: " + finalScore);
        scoreText.setStyle("-fx-font-size: 24px; -fx-fill: white;");
        scoreText.setX(300); // X position on the screen
        scoreText.setY(100); // Y position on the screen

        // Create buttons
        Button playAgainBtn = createMenuButton("Play Again");
        Button selectMazeBtn = createMenuButton("Select Maze Type");
        Button toggleMusicBtn = createMenuButton("Toggle Music");
        Button quitBtn = createMenuButton("Quit");

        // Set button positions manually
        playAgainBtn.setLayoutX(300); // X position of Play Again button
        playAgainBtn.setLayoutY(150); // Y position of Play Again button
        selectMazeBtn.setLayoutX(300); // X position of Select Maze button
        selectMazeBtn.setLayoutY(200); // Y position of Select Maze button
        toggleMusicBtn.setLayoutX(300); // X position of Toggle Music button
        toggleMusicBtn.setLayoutY(250); // Y position of Toggle Music button
        quitBtn.setLayoutX(300); // X position of Quit button
        quitBtn.setLayoutY(300); // Y position of Quit button

        // Add buttons and text to the root Pane
        root.getChildren().addAll(scoreText, playAgainBtn, selectMazeBtn, toggleMusicBtn, quitBtn);

        // Set actions for buttons
        playAgainBtn.setOnAction(e -> restartSameMaze());
        selectMazeBtn.setOnAction(e -> selectNewMaze());
        toggleMusicBtn.setOnAction(e -> toggleMusic());
        quitBtn.setOnAction(e -> primaryStage.close());

        // Create scene and set it on the primary stage
        Scene scene = new Scene(root, Constants.WIDTH, Constants.HEIGHT);
        primaryStage.setScene(scene);
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: #0077B6; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 18px; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-padding: 10px 20px;"
        );

        // Button hover effects
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #00B4D8; " +
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

    private void restartSameMaze() {
        GameWindow gameWindow = new GameWindow(GameWindow.getSelectedMazeType());
        gameWindow.start(primaryStage);
    }

    private void selectNewMaze() {
        MenuScreen menu = new MenuScreen(primaryStage);
        menu.showMenu();
    }

    private void toggleMusic() {
        if (soundManager != null) {
            if (soundManager.isMusicPlaying()) {
                soundManager.pauseBackgroundMusic();
            } else {
                soundManager.playBackgroundMusic();
            }
        }
    }
}
