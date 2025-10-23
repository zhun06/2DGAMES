package com.zhun.snake;

import com.zhun.util.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// Renders design Neon Theme
class NeonRenderer implements ThemeRenderer {
    @Override
    public void render(SnakeGame game, GraphicsContext gc) {
        // background
        gc.clearRect(0, 0, SnakeGame.WIDTH, SnakeGame.HEIGHT);
        gc.setStroke(Color.BLACK); // Black outline for all outlines
        gc.setLineWidth(2); // Outline width

        // background Fill
        gc.setFill(Color.rgb(1, 59, 68));
        gc.fillRect(0, 0, SnakeGame.WIDTH, SnakeGame.HEIGHT);

        // background design
        for (int row = 0; row < SnakeGame.ROWS; row++) {
            for (int col = 0; col < SnakeGame.COLUMNS; col++) {
                if ((row + col) % 2 == 0) {
                    gc.setFill(Color.rgb(8, 200, 245));
                } else {
                    gc.setFill(Color.rgb(6, 26, 43));
                }
                gc.fillRect(col * SnakeGame.BOX_SIZE, row * SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE);
                gc.strokeRect(col * SnakeGame.BOX_SIZE, row * SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE);
            }
        }

        // draw food
        Point food = game.getFood();
        gc.setFill(Color.rgb(255, 77, 77));
        gc.fillOval(food.x, food.y, SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE);
        gc.strokeOval(food.x, food.y, SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE);

        // draw snake
        gc.setFill(Color.rgb(255, 255, 255));
        for (Point p : game.getSnake()) {
            gc.fillRoundRect(p.x, p.y, SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE, 20, 20);
            gc.strokeRoundRect(p.x, p.y, SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE, 20, 20);
        }
    }
}
