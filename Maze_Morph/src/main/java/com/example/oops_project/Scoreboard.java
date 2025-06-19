package com.example.oops_project;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Scoreboard {
    private int score;

    public Scoreboard() {
        score = 0;
    }

    public void increaseScore(int points) {
        score += points;
    }



    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial Black", 24));
        gc.setTextAlign(TextAlignment.RIGHT);
        gc.fillText("Score: " + score, Constants.WIDTH - 20, 30);
    }


    public void resetScore() {
        score = 0; // Reset score to 0
    }

    public int  getScore() {
        return score;
    }
}
