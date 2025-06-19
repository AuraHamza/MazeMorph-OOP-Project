package com.example.oops_project;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze {
    private List<int[]> walls; // List of wall coordinates
    private int mazeType; // To store selected maze type

    public Maze(int type) {          // Allow choosing different maze types
        walls = new ArrayList<>();
        this.mazeType = type;
        generateMaze();
    }

    private void generateMaze() {
        walls.clear();
        generateBoundaryWalls();

        if (mazeType == 1) {
                 // Simple Maze (only boundaries, no obstacles)
        } else if (mazeType == 2) {
            generateVerticalObstacles();
        } else if (mazeType == 3) {
            generateRandomObstacles();
        }
    }

    private void generateBoundaryWalls() {
        // Horizontal Walls (Top and Bottom)
        for (int x = 10; x < 50; x++) {
            walls.add(new int[]{x, 5});   // Top Wall
            walls.add(new int[]{x, 34});  // Bottom Wall
        }
        // Vertical Walls (Left and Right)
        for (int y = 5; y <= 34; y++) {
            walls.add(new int[]{10, y});  // Left Wall
            walls.add(new int[]{49, y});  // Right Wall
        }
    }

    private void generateVerticalObstacles() {
        Random rand = new Random();
        for (int i = 0; i < 5; i++) { // Add 5 vertical walls
            int randomX = rand.nextInt(38) + 11; // between 11 and 48
            for (int y = 6; y <= 33; y++) {
                if (y % 2 == 0) {        // Create broken vertical lines
                    walls.add(new int[]{randomX, y});
                }
            }
        }
    }

    private void generateRandomObstacles() {
        Random rand = new Random();
        int attempts = 0;
        int maxObstacles = 20;
        while (attempts < maxObstacles) {
            int randomX = rand.nextInt(38) + 11; // between 11 and 48
            int randomY = rand.nextInt(28) + 6;  // between 6 and 33
            if (!isWall(randomX, randomY)) {
                walls.add(new int[]{randomX, randomY});
                attempts++;
            }
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GRAY);
        for (int[] wall : walls) {
            gc.fillRect(wall[0] * Constants.BLOCK_SIZE, wall[1] * Constants.BLOCK_SIZE,
                    Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
        }
    }

    public boolean isWall(int x, int y) {
        for (int[] wall : walls) {
            if (wall[0] == x && wall[1] == y) {
                return true;
            }
        }
        return false;
    }

    public void setMazeType(int type) {
        this.mazeType = type;
        generateMaze();
    }
}
