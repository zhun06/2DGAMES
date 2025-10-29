package com.zhun.engines;

import com.zhun.games.SnakeGame;
import com.zhun.managers.GameManager;
import com.zhun.managers.SceneManager;
import com.zhun.snake.gameRenderer.*;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

// Connects SnakeGame to Canvas
public class SnakeEngine implements Engine{
    private final SnakeGame game;
    private SnakeRenderer gameRenderer; // Interface, render based on theme
    private Timeline timeline;
    private AnimationTimer gameLoop;
    private int tickMs = 200; // milliseconds per update
    private Runnable onGameOver;

    private String currentTheme;

    // Constructor
    public SnakeEngine(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void start() {
        game.reset();
        this.initialize();
        this.setupTimeline();
        this.setupGameLoop();
    }

    private void initialize() {
        currentTheme = SceneManager.getTheme();
        onGameOver = GameManager.setOnGameOver();
        this.setRenderer();
    }

    // Decides which ThemeRenderer to use for each theme
    private void setRenderer() {
        switch (currentTheme) {
            case "retro" -> gameRenderer = new RetroRenderer(game);
            case "neon" -> gameRenderer = new NeonRenderer(game);
            case "candy" -> gameRenderer = new CandyRenderer(game);
        }
    }

    private void setupTimeline() {
        if (timeline != null) {timeline.stop();}
        timeline = new Timeline(new KeyFrame(Duration.millis(tickMs), e -> onTick()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameRenderer.render();
            }
        };
    }

    private void onTick() {
        game.update();
        this.updateSpeed();
        checkGameOver();
    }

    private void checkGameOver() {
        if (game.isGameOver()) {
            onGameOver.run(); // Notify GameManager
        }
    }

    private void updateSpeed() {
        double multiplier = 1.0 + ((double) game.getScore() / 5) * 0.2; // every 5 food → +20%
        double capped = Math.min(multiplier, 2.0); // prevent insane speeds
        timeline.setRate(capped);
    }

    // Getter
    public Timeline getTimeline() {return timeline;}
    public AnimationTimer getGameLoop() {return gameLoop;}
}