package com.zhun.games;

import com.zhun.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Only game logic, no rendering
public class SnakeGame extends Game {
    // Grid & sizing
    public static final int BOX_SIZE = 40;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    public static final int COLUMNS = WIDTH / BOX_SIZE;
    public static final int ROWS = HEIGHT / BOX_SIZE;

    // Game state
    private final List<Point> snake = new ArrayList<>(); // head is index 0
    private Point food;
    private Direction currentDirection;
    private boolean growNextUpdate;
    private final Random rand = new Random();
    private int score;
    private int highScore = 0;
    private boolean gameOver;

    public enum Direction { UP, DOWN, LEFT, RIGHT }

    public void reset() {
        snake.clear();
        // Start in (Left, Center)
        int startX = 0;
        int startY = (ROWS / 2) * BOX_SIZE;
        snake.add(new Point(startX, startY)); // Head
        currentDirection = Direction.RIGHT;
        spawnFood();
        score = 0;
        gameOver = false;
        growNextUpdate = false;
    }

    public void setDirection(Direction dir) {
        // Prevent reversing directly
        if (dir == Direction.LEFT && currentDirection == Direction.RIGHT) return;
        if (dir == Direction.RIGHT && currentDirection == Direction.LEFT) return;
        if (dir == Direction.UP && currentDirection == Direction.DOWN) return;
        if (dir == Direction.DOWN && currentDirection == Direction.UP) return;
        this.currentDirection = dir;
    }

    public void update() {
        // compute new head position
        Point head = snake.getFirst();
        int newX = head.x;
        int newY = head.y;
        switch (currentDirection) {
            case UP:    newY -= BOX_SIZE; break;
            case DOWN:  newY += BOX_SIZE; break;
            case LEFT:  newX -= BOX_SIZE; break;
            case RIGHT: newX += BOX_SIZE; break;
        }

        // Check wall-collision
        if (newX < 0 || newX >= WIDTH || newY < 0 || newY >= HEIGHT) {
            gameOver = true;
            return;
        }

        Point newHead = new Point(newX, newY);

        // Check self-collision
        for (Point p : snake) {
            if (p.equals(newHead)) {
                gameOver = true;
                return;
            }
        }

        // Insert new head
        snake.addFirst(newHead);

        // Eat food?
        if (newHead.equals(food)) {
            score++;
            if (score > highScore) highScore = score;
            spawnFood();
            // keep tail (grow)
            growNextUpdate = false; // already grew
        } else {
            if (!growNextUpdate) {
                // remove tail
                snake.removeLast();
            } else {
                // if flagged to grow, do not remove tail once
                growNextUpdate = false;
            }
        }
    }

    private void spawnFood() {
        while (true) {
            int fx = rand.nextInt(COLUMNS) * BOX_SIZE;
            int fy = rand.nextInt(ROWS) * BOX_SIZE;
            Point candidate = new Point(fx, fy);
            boolean onSnake = false;
            for (Point p : snake) if (p.equals(candidate)) { onSnake = true; break; }
            if (!onSnake) { food = candidate; break; }
        }
    }

    // Getters for rendering / UI
    public List<Point> getSnake() { return snake; }  // read-only by convention; don't mutate
    public Point getFood() { return food; }
    public int getScore() { return score; }
    public int getHighScore() { return highScore; }
    public boolean isGameOver() { return gameOver; }
}
