package com.example.oops_project;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

public class Food {
    private int x, y;
    private Random random = new Random();
    private FoodType type;

    public Food() {

    }

    public void respawn(Maze maze, Snake snake) {
        while (true) {
            // Randomly generate x and y within the bounds of the maze grid (excluding the walls)
            // Ensure food spawns between the walls (x = 10 to 49, y = 5 to 34)
            x = random.nextInt(40) + 10;  // Random x within grid columns (10-49)
            y = random.nextInt(30) + 5;   // Random y within grid rows (5-34)


            if (maze.isWall(x, y)) {
                continue;            // Retry if it spawns on a wall,
            }

            // Ensure food does not spawn inside the snake's body
            boolean collisionWithSnake = false;
            for (int[] part : snake.getBody()) {
                if (part[0] == x && part[1] == y) {
                    collisionWithSnake = true;   // Retry if food collides with the snake body
                    break;
                }
            }
            if (collisionWithSnake) {
                continue;     // Retry if food collides with the snake
            }

            // Randomly assign a food type with defined probabilities
            int randomNum = random.nextInt(100);  // 0 - 99
            if (randomNum < 70) {
                type = FoodType.NORMAL;   // 70% normal food
            } else if (randomNum < 80) {
                type = FoodType.SPEED_BOOST;   // 10% speed boost food
            } else if (randomNum < 90) {
                type = FoodType.SHIELD;   // 10% shield food
            } else if (randomNum < 95) {
                type = FoodType.DOUBLE_POINTS;   // 5% double points food
            } else {
                type = FoodType.SLOW_TIME;   // 5% slow time food
            }
            break;
        }
    }



    public int getX() { return x; }
    public int getY() { return y; }
    public FoodType getType() {
        return type;
    }

    public void draw(GraphicsContext gc) {
        switch (type) {
            case NORMAL:
                gc.setFill(Color.RED); // Normal food color
                break;
            case SPEED_BOOST:
                gc.setFill(Color.YELLOW); // Speed boost food color
                break;
            case SHIELD:
                gc.setFill(Color.BLUE); // Shield food color
                break;
            case DOUBLE_POINTS:
                gc.setFill(Color.ORANGE); // Double points food color
                break;
            case SLOW_TIME:
                gc.setFill(Color.PURPLE); // Slow time food color
                break;
        }

        // Draw food as a circle inside the grid area
        gc.fillOval(x * Constants.BLOCK_SIZE, y * Constants.BLOCK_SIZE,
                Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
    }
}
