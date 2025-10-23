package com.zhun.snake;

import com.zhun.controllers.GameController;
import com.zhun.managers.GameManager;
import com.zhun.managers.SceneManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

// Connects SnakeGame to Canvas: handle graphics
public class SnakeEngine {
    private ThemeRenderer themeRenderer; // Interface, implements render differently for each theme
    private Timeline timeline;
    private int tickMs = 200; // milliseconds per update
    private Runnable onGameOver;

    private final SnakeGame game;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private String currentTheme;


    public SnakeEngine(SnakeGame game, Canvas canvas) {
        this.game = game;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    // Decides which ThemeRenderer to use for each theme
    private void setRenderer() {
        switch (currentTheme) {
            case "retro" -> themeRenderer = new RetroRenderer();
            case "neon" -> themeRenderer = new NeonRenderer();
            case "candy" -> themeRenderer = new CandyRenderer();
        }
    }

    public void start() {
        this.initializeEngine();
        game.reset();
        this.setupTimeline();
        timeline.play();
        canvas.requestFocus();
    }

    private void initializeEngine() {
        this.currentTheme = SceneManager.getTheme();
        this.setRenderer();
        onGameOver = GameManager.setOnGameOver();
    }

    private void setupTimeline() {
        if (timeline != null) timeline.stop();
        timeline = new Timeline(new KeyFrame(Duration.millis(tickMs), e -> onTick()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void onTick() {
        game.update();
        themeRenderer.render(game, gc);
        GameController.setCurrentScore(game.getScore());
        this.updateSpeed();
        checkGameOver();
    }

    private void checkGameOver() {
        if (game.isGameOver()) {
            timeline.stop();
            GameController.setHighScore(game.getHighScore());
            onGameOver.run();
        }
    }

    private void updateSpeed() {
        double multiplier = 1.0 + ((double) game.getScore() / 5) * 0.2; // every 5 food → +20%
        double capped = Math.min(multiplier, 2.0); // prevent insane speeds
        timeline.setRate(capped);
    }

    // Getter
    public Timeline getTimeline() {return timeline;}
}