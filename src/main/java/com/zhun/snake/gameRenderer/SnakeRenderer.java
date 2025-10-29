package com.zhun.snake.gameRenderer;

import com.zhun.controllers.GameController;
import com.zhun.managers.GameManager;
import com.zhun.games.SnakeGame;
import com.zhun.util.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;

public abstract class SnakeRenderer {
    private final SnakeGame game;
    private final GraphicsContext gc;
    private Color[] colors;
    private LinearGradient[] lgs;
    private int colorSize;

    public abstract void render();

    public SnakeRenderer(SnakeGame game) {
        this.game = game;
        this.gc = GameManager.getGameCanvas().getGraphicsContext2D();
    }

    protected void renderBG(Color color) {
        // Canvas
        gc.setStroke(Color.BLACK); // Black outline for all outlines
        gc.setLineWidth(2); // Outline width
        gc.clearRect(0, 0, SnakeGame.WIDTH, SnakeGame.HEIGHT);

        // Background Fill
        gc.setFill(color);
        gc.fillRect(0, 0, SnakeGame.WIDTH, SnakeGame.HEIGHT);
    }

    protected void renderBoard(Color color1, Color color2) {
        // Board design
        for (int row = 0; row < SnakeGame.ROWS; row++) {
            for (int col = 0; col < SnakeGame.COLUMNS; col++) {
                if ((row + col) % 2 == 0) {
                    gc.setFill(color1);
                } else {
                    gc.setFill(color2);
                }
                gc.fillRect(col * SnakeGame.BOX_SIZE, row * SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE);
                gc.strokeRect(col * SnakeGame.BOX_SIZE, row * SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE);
            }
        }
    }

    protected void renderFood(Color color) {
        // Draw food
        Point food = game.getFood();
        gc.setFill(color);
        gc.fillOval(food.x, food.y, SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE);
        gc.strokeOval(food.x, food.y, SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE);
    }

    // Default green snake
    protected void renderDefaultSnake() {
        for (Point p : game.getSnake()) {
            gc.setFill(Color.GREEN);
            gc.fillRoundRect(p.x, p.y, SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE, 20, 20);
            gc.strokeRoundRect(p.x, p.y, SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE, 20, 20);
        }
    }

    // Gradient snake
    protected void renderGradientSnake(LinearGradient... lgs) {
        if (lgs == null || lgs.length == 0) {
            this.renderDefaultSnake();
            return;
        }
        this.lgs = lgs;
        this.colorSize = lgs.length;

        for (int i = 0; i < game.getSnake().size(); i++) {
            gc.setFill(this.lgs[i % colorSize]);
            gc.fillRoundRect(game.getSnake().get(i).x, game.getSnake().get(i).y, SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE, 20, 20);
            gc.strokeRoundRect(game.getSnake().get(i).x, game.getSnake().get(i).y, SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE, 20, 20);
        }

    }

    // Solid color Snake
    protected void renderSolidSnake(Color... colors) {
        if (colors == null || colors.length == 0) {
            this.renderDefaultSnake();
            return;
        }
        this.colors = colors;
        this.colorSize = colors.length;

        for (int i = 0; i < game.getSnake().size(); i++) {
            gc.setFill(this.colors[i % colorSize]);
            gc.fillRoundRect(game.getSnake().get(i).x, game.getSnake().get(i).y, SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE, 20, 20);
            gc.strokeRoundRect(game.getSnake().get(i).x, game.getSnake().get(i).y, SnakeGame.BOX_SIZE, SnakeGame.BOX_SIZE, 20, 20);
        }
    }

    // Render current score
    protected void renderCurrentScore() {
        GameController.setCurrentScore(game.getScore());
        GameController.setHighScore(game.getHighScore());
    }

}
