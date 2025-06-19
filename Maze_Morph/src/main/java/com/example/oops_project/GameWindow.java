package com.example.oops_project;

import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameWindow {

    private static int selectedMazeType;

    public GameWindow(int mazeType) {
        selectedMazeType = mazeType;
    }

    public static int getSelectedMazeType() {
        return selectedMazeType;
    }

    public void start(Stage stage) {
        TextInputDialog dialog = new TextInputDialog("Player");
        dialog.setTitle("Player Name");
        dialog.setHeaderText("Enter your name for the leaderboard:");
        dialog.setContentText("Name:");
        Optional<String> result = dialog.showAndWait();
        String playerName = result.orElse("Player");

        GamePanel gamePanel = new GamePanel(selectedMazeType, playerName);
        Scene gameScene = new Scene(new StackPane(gamePanel), 1100, 700);

        gamePanel.setGameScene(gameScene);
        stage.setScene(gameScene);
        stage.setTitle("Maze Morph");
        stage.show();

        gamePanel.startGame();
    }
}