package com.example.oops_project;

public class Particle {
    double x, y;
    double speedX, speedY;

    public Particle() {
        reset();
    }

    public void reset() {
        x = Math.random() * Constants.WIDTH;
        y = Math.random() * Constants.HEIGHT;
        speedX = (Math.random() - 0.5) * 0.5; // Slow random speed
        speedY = (Math.random() - 0.5) * 0.5;
    }

    public void move() {
        x += speedX;
        y += speedY;

        // If particle goes off screen, reset it
        if (x < 0 || x > Constants.WIDTH || y < 0 || y > Constants.HEIGHT) {
            reset();
        }
    }
}

