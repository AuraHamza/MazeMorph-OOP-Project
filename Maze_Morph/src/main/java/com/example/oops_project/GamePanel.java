package com.example.oops_project;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;

import java.util.ArrayList;

public class GamePanel extends Pane {


    private ArrayList<Particle> particles = new ArrayList<>();
    private Canvas canvas;
    private GraphicsContext gc;
    private Snake snake;
    private Food food;
    private Maze maze;
    private Scoreboard scoreboard;
    private boolean gameOver = false;
    private AI_Snake aiSnake;
    private SoundManager soundManager;
    private long lastUpdate = 0;
    private boolean paused = false;
    private boolean speedBoostActive = false;
    private boolean shieldActive = false;
    private boolean doublePointsActive = false;
    private boolean slowTimeActive = false;
    private long powerUpEndTime = 0;
    private String activePowerUpMessage = "";
    private int foodEatenCount = 0;
    private Scene gameScene;
    private String playerName;

    public GamePanel(int selectedMazeType,String playerName) { //  Accept maze type from user
        canvas = new Canvas(Constants.WIDTH, Constants.HEIGHT);
        gc = canvas.getGraphicsContext2D();
        getChildren().add(canvas);

        snake = new Snake();
        this.playerName=playerName;
        maze = new Maze(selectedMazeType); //  Pass maze type to select maze
        food = new Food();
        food.respawn(maze,snake);
        scoreboard = new Scoreboard();
        aiSnake = new AI_Snake(); //  AI-controlled opponent snake
        soundManager = new SoundManager(); //  Add sound effects


        for (int i = 0; i < 50; i++) { // 50 small floating particles
            particles.add(new Particle());
        }

        setOnKeyPressed(event -> {
            if (!paused && !gameOver) {
                if (event.getCode() == KeyCode.UP) snake.setDirection("UP");
                if (event.getCode() == KeyCode.DOWN) snake.setDirection("DOWN");
                if (event.getCode() == KeyCode.LEFT) snake.setDirection("LEFT");
                if (event.getCode() == KeyCode.RIGHT) snake.setDirection("RIGHT");
            }

            if (event.getCode() == KeyCode.SPACE && gameOver) { //  Restart game on Spacebar
                restartGame();
            }

            if (event.getCode() == KeyCode.P && !gameOver) {
                if (!paused) {
                    paused = true;
                    soundManager.pauseBackgroundMusic();
                    timer.stop(); //  Stop the AnimationTimer
                    PauseMenu pauseMenu = new PauseMenu(Main.getPrimaryStage(), this, soundManager);
                    pauseMenu.showMenu();
                }
            }

        });

    }

    private AnimationTimer timer; // Move timer to class level

    public void startGame() {
        Leaderboard.loadScores();
        soundManager.playBackgroundMusic();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long currentTime = System.currentTimeMillis();

                long delay = 150;

                if (speedBoostActive) {
                    delay = 75;
                } else if (slowTimeActive) {
                    delay = 300;
                }

                if (!gameOver && now - lastUpdate > 150_000_000) {
                    if (!paused) {
                        update();
                    }
                    draw();
                    lastUpdate = now;
                }

                if (powerUpEndTime > 0 && currentTime > powerUpEndTime) {
                    speedBoostActive = false;
                    shieldActive = false;
                    doublePointsActive = false;
                    slowTimeActive = false;
                    powerUpEndTime = 0;
                    activePowerUpMessage = "";
                }
            }
        };
        timer.start();
        requestFocus();
    }



    private void update() {
        boolean hasEaten = snake.checkCollision(food);
        snake.move(hasEaten);

        //  Check collision between Green Snake and every part of AI Snake
        int[] playerHead = snake.getHead();
        for (int[] aiPart : aiSnake.getBody()) {
            if (playerHead[0] == aiPart[0] && playerHead[1] == aiPart[1]) {
                stopGame();
                break;
            }
        }

        if (hasEaten) {
            FoodType foodType = food.getType();
            food.respawn(maze, snake);

            switch (foodType) {
                case NORMAL:
                    scoreboard.increaseScore(doublePointsActive ? 20 : 10);
                    activePowerUpMessage = "";
                    break;
                case SPEED_BOOST:
                    speedBoostActive = true;
                    powerUpEndTime = System.currentTimeMillis() + 5000;
                    activePowerUpMessage = "Speed Boost Activated!";
                    break;
                case SHIELD:
                    shieldActive = true;
                    powerUpEndTime = System.currentTimeMillis() + 10000;
                    activePowerUpMessage = "Shield Activated!";
                    break;
                case DOUBLE_POINTS:
                    doublePointsActive = true;
                    powerUpEndTime = System.currentTimeMillis() + 10000;
                    activePowerUpMessage = "Double Points Activated!";
                    break;
                case SLOW_TIME:
                    slowTimeActive = true;
                    powerUpEndTime = System.currentTimeMillis() + 5000;
                    activePowerUpMessage = "Slow Time Activated!";
                    break;
            }

            soundManager.playEatSound();
            foodEatenCount++;

            if (foodEatenCount % 2 == 0) {
                aiSnake.grow();
            }
        }

        aiSnake.move();

        int[] head = snake.getHead();
        if (maze.isWall(head[0], head[1])) {
            stopGame();
        }

        if (head[0] <= 10 || head[0] >= 49 || head[1] <= 5 || head[1] >= 34) {
            stopGame();
        }

        if (snake.checkSelfCollision()) {
            if (shieldActive) {
                shieldActive = false;
            } else {
                stopGame();
            }
        }
    }

    private void drawBackground() {
        // Fill full background first (dark blue)
        gc.setFill(Color.web("#0D1B2A"));
        gc.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

        // Game Play Area box (centered)
        int gameX = 200; // left margin for high scores
        int gameY = 100; // top margin for title

        gc.setFill(Color.web("#0D1B2A")); // Same dark blue inside
        gc.fillRect(gameX, gameY, 800, 600);

        // Draw moving particles ONLY inside Game Area
        gc.setFill(Color.web("#00B4D8")); // Light blue dots
        for (Particle p : particles) {
            p.move();
            if (p.x >= gameX && p.x <= gameX + 800 && p.y >= gameY && p.y <= gameY + 600) {
                gc.fillOval(p.x, p.y, 2, 2); // Only draw particles inside game box
            }
        }

        // Draw subtle grid lines inside Game Area
        gc.setStroke(Color.web("#1B263B"));
        gc.setLineWidth(0.5);

        for (int x = gameX; x <= gameX + 800; x += Constants.BLOCK_SIZE) {
            gc.strokeLine(x, gameY, x, gameY + 600);
        }
        for (int y = gameY; y <= gameY + 600; y += Constants.BLOCK_SIZE) {
            gc.strokeLine(gameX, y, gameX + 800, y);
        }

        // Draw border around Game Area (optional for more beauty)
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRect(gameX, gameY, 800, 600);
    }


    private void draw() {
        drawBackground(); //  First draw the background

        // Title at the Top
        gc.setFill(Color.web("#00B4D8")); // Light blue (your theme)
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 48)); // Bold font with 48 size
        gc.setTextAlign(TextAlignment.CENTER);

        // Adding shadow for a cool effect
        DropShadow shadow = new DropShadow(10, Color.DARKRED); // Shadow in dark red
        DropShadow glow = new DropShadow(15, Color.YELLOW); // Glow with yellow color

        // Apply shadow and glow effects only to the title
        gc.setEffect(shadow);
        gc.fillText("Maze Morph", Constants.WIDTH / 2, 60);

        // Remove the effects after drawing the title so they don't affect other elements
        gc.setEffect(null);

        // Draw High Scores on the left
        gc.setFill(Color.GRAY);
        gc.setFont(new Font(20));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.fillText("High Scores:", 50, 180);
        ArrayList<Leaderboard.Entry> topScores = Leaderboard.getTopScores();
        for (int i = 0; i < topScores.size(); i++) {
            Leaderboard.Entry entry = topScores.get(i);
            gc.fillText((i + 1) + ". " + entry.getName() + " - " + entry.getScore(), 45, 250 + i * 40);
        }
        // Draw the main game objects inside play area
        maze.draw(gc);
        snake.draw(gc);
        aiSnake.draw(gc);
        food.draw(gc);

        // Draw PowerUp message if active
        if (!activePowerUpMessage.isEmpty()) {
            gc.setFill(Color.ORANGE);
            gc.setFont(new Font(24));
            // Adjust x and y for positioning
            gc.fillText(activePowerUpMessage, Constants.WIDTH / 2 + 80, 160); // Move right by 50 and lower by 40
        }

        // Draw Scoreboard on right
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial Black", 24));
        gc.setTextAlign(TextAlignment.RIGHT);
        gc.fillText("Score: " + scoreboard.getScore(), Constants.WIDTH - 50, 50);

        // If Game Over
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Arial Black", 40));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText("Game Over!", Constants.WIDTH / 2, Constants.HEIGHT / 2 - 100);

            gc.setFill(Color.WHITE);
            gc.setFont(new Font(24));
            gc.setTextAlign(TextAlignment.LEFT);  // Align text to the left
            gc.fillText("Leaderboard:", 50, Constants.HEIGHT / 2 - 10);

            for (int i = 0; i < topScores.size(); i++) {
                gc.fillText((i + 1) + ". " + topScores.get(i).getName() + " - " + topScores.get(i).getScore(),
                        50,
                        Constants.HEIGHT / 2 + (i * 30));
            }
        }

    }



    private void stopGame() {
        if (!gameOver) {
            gameOver = true;
            soundManager.playGameOverSound();
            System.out.println("Game Over! Snake hit a wall.");
            soundManager.stopBackgroundMusic();
            Leaderboard.addScore(playerName, scoreboard.getScore());
            GameOverMenu menu = new GameOverMenu(Main.getPrimaryStage(), soundManager, scoreboard.getScore()); // ✅ Pass score
            menu.showMenu();
        }
    }


    private void restartGame() {
        gameOver = false;
        snake = new Snake(); // Reset snake
        food.respawn(maze,snake); // Reset food
        scoreboard.resetScore(); // Reset score
        startGame(); // Restart game loop
    }

    public void resumeGame() {
        paused = false;
        soundManager.playBackgroundMusic();
        Main.getPrimaryStage().setScene(gameScene); //  Go back to Game Scene
        timer.start(); //  Restart AnimationTimer
        requestFocus(); //  Keys work again
    }

    public void setGameScene(Scene scene) {
        this.gameScene = scene;
    }

    public Scene getGameScene() {
        return gameScene;
    }

}