package com.example.oops_project;

import javafx.scene.canvas.GraphicsContext;     //to draw snake on canvas
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Snake {
    private ArrayList<int[]> body;
    private String direction;

    public Snake() {
        body = new ArrayList<>();
        body.add(new int[]{30, 20});         // Start away from walls
        direction = "RIGHT";
    }

    public void setDirection(String newDirection) {       //prevents snake from reversing into itself
        if (!newDirection.equals(oppositeDirection(direction))) {
            direction = newDirection;
        }
    }

    public void move(boolean hasEaten) {
        int[] head = body.get(0);
        int x = head[0], y = head[1];

        switch (direction) {
            case "UP": y--; break;
            case "DOWN": y++; break;
            case "LEFT": x--; break;
            case "RIGHT": x++; break;
        }

        // Insert new head at index 0 , the existing one becomes index1
        body.add(0, new int[]{x, y});

        if (!hasEaten) {
            // Remove tail(last index) if food wasn't eaten
            body.remove(body.size() - 1);
        }
    }

    public boolean checkCollision(Food food) {
        int[] head = body.get(0);
        return head[0] == food.getX() && head[1] == food.getY();    //snake head is on the same grid as food
    }

    public void draw(GraphicsContext gc) {    //colors the snake as green
        gc.setFill(Color.GREEN);
        for (int[] segment : body) {
            gc.fillRect(segment[0] * Constants.BLOCK_SIZE, segment[1] * Constants.BLOCK_SIZE, Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
        }
    }

    public int[] getHead() {
        return body.get(0);          // Returns the head of the snake
    }

    public ArrayList<int[]> getBody() {
        return body;                       //returns the snake body
    }

    public boolean checkSelfCollision() {          //compares the head position with every other body co ordinates
        int[] head = body.get(0);
        for (int i = 1; i < body.size(); i++) {
            int[] part = body.get(i);
            if (head[0] == part[0] && head[1] == part[1]) {
                return true;
            }
        }
        return false;
    }

    private String oppositeDirection(String dir) {
        switch (dir) {
            case "UP": return "DOWN";
            case "DOWN": return "UP";
            case "LEFT": return "RIGHT";
            case "RIGHT": return "LEFT";
            default: return "";
        }
    }
}
