package com.example.oops_project;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PauseMenu {
    private Stage primaryStage;
    private GamePanel gamePanel;
    private SoundManager soundManager;

    public PauseMenu(Stage primaryStage, GamePanel gamePanel, SoundManager soundManager) {
        this.primaryStage = primaryStage;
        this.gamePanel = gamePanel;
        this.soundManager = soundManager;
    }

    public void showMenu() {
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: #0D1B2A; -fx-alignment: center;");

        Button resumeBtn = createMenuButton("Resume Game");
        Button toggleMusicBtn = createMenuButton("Toggle Music");
        Button quitBtn = createMenuButton("Quit Game");

        resumeBtn.setOnAction(e -> resumeGame());
        toggleMusicBtn.setOnAction(e -> toggleMusic());
        quitBtn.setOnAction(e -> primaryStage.close());

        root.getChildren().addAll(resumeBtn, toggleMusicBtn, quitBtn);

        Scene pauseScene = new Scene(root, Constants.WIDTH, Constants.HEIGHT);
        primaryStage.setScene(pauseScene);
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

    private void resumeGame() {
        gamePanel.resumeGame();
        Main.getPrimaryStage().setScene(gamePanel.getGameScene()); // ✅ Switch back to Game Scene
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
