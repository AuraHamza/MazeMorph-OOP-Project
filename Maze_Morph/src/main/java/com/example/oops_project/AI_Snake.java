package com.example.oops_project;

import javafx.scene.canvas.GraphicsContext;    //used to draw on FX canvas
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;


public class AI_Snake {
    private ArrayList<int[]> body = new ArrayList<>();  //stores as 2D coordinates
    private Random random = new Random();
    private String direction;               // AI Direction (UP, DOWN, LEFT, RIGHT)

    public AI_Snake() {
        int startX = random.nextInt(40) + 10;    // Random X within grid columns (10-49)
        int startY = random.nextInt(30) + 5;     // Random Y within grid rows (5-34)
        body.add(new int[]{startX, startY});
        direction = "RIGHT"; // Start moving right
    }

    public void move() {
        int[] head = body.get(0); // Current head
        int x = head[0];
        int y = head[1];

        if (random.nextInt(10) < 7) {       // 70% chance to change direction
            int moveDirection = random.nextInt(4);
            switch (moveDirection) {
                case 0: direction = "UP"; break;
                case 1: direction = "DOWN"; break;
                case 2: direction = "LEFT"; break;
                case 3: direction = "RIGHT"; break;
            }
        }

        // Move in current direction
        switch (direction) {
            case "UP": y--; break;
            case "DOWN": y++; break;
            case "LEFT": x--; break;
            case "RIGHT": x++; break;
        }

        // Stay inside screen (adjust within the grid boundaries of the game area)
        if (x < 10) x = 10;  // Left boundary
        if (x >= 50) x = 49; // Right boundary
        if (y < 5) y = 5;    // Top boundary
        if (y >= 35) y = 34; // Bottom boundary

        // Move body: add new head, remove tail
        body.add(0, new int[]{x, y});
        body.remove(body.size() - 1);
    }

    public void grow() {
        // Add extra body part at tail position
        int[] tail = body.get(body.size() - 1);
        body.add(new int[]{tail[0], tail[1]});
    }

    public void draw(GraphicsContext gc) {   //to fill color in AI snake
        gc.setFill(Color.BLUE);
        for (int[] part : body) {
            gc.fillRect(part[0] * Constants.BLOCK_SIZE, part[1] * Constants.BLOCK_SIZE,
                    Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
        }
    }

    public ArrayList<int[]> getBody() {
        return body;
    }
}
