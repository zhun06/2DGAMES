package com.zhun.engines;

import com.zhun.games.TetrisGame;
import com.zhun.managers.GameManager;
import com.zhun.managers.SceneManager;
import com.zhun.tetris.gameRenderer.RetroRenderer;
import com.zhun.tetris.gameRenderer.*;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class TetrisEngine implements Engine {
    private TetrisRenderer renderer; // Interface, render based on theme
    private Timeline boardTimeLine;
    private Timeline inputTimeLine;
    private AnimationTimer gameLoop;
    private int boardTick = 400; // milliseconds per update
    private final int inputTick = 10;
    private Runnable onGameOver;

    private final TetrisGame game;

    private String currentTheme;

    // Constructor
    public TetrisEngine(TetrisGame game) {
        this.game = game;
    }

    @Override
    public void start() {
        game.reset();
        this.initialize();
        this.setupBoardTimeline();
        this.setupKeyInputTimeline();
        this.setupGameLoop();
    }

    private void initialize() {
        currentTheme = SceneManager.getTheme();
        this.setRenderer();
        onGameOver = GameManager.setOnGameOver();
    }

    // Decides which ThemeRenderer to use for each theme
    private void setRenderer() {
        switch (currentTheme) {
            case "retro" -> renderer = new RetroRenderer(game);
            case "neon" -> renderer = new NeonRenderer(game);
            case "candy" -> renderer = new CandyRenderer(game);
        }
    }

    private void setupBoardTimeline() {
        boardTimeLine = new Timeline(new KeyFrame(Duration.millis(boardTick), e -> {
            game.updateBoard();
            this.updateSpeed();
            checkGameOver();
        }));
        boardTimeLine.setCycleCount(Timeline.INDEFINITE);
    }

    private void setupKeyInputTimeline() {
        inputTimeLine = new Timeline(new KeyFrame(Duration.millis(inputTick), event -> {
            game.updateInput();
        }));
        inputTimeLine.setCycleCount(Timeline.INDEFINITE);
    }

    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                renderer.render();
            }
        };
    }

    private void checkGameOver() {
        if (game.isGameOver()) {
            onGameOver.run(); // Notify GameManager
        }
    }

    private void updateSpeed() {
        double multiplier = 1.0 + ((double) game.getScore() / 1000) * 0.2;
        double capped = Math.min(multiplier, 2.0); // prevent insane speeds
        boardTimeLine.setRate(capped);
    }

    // Getter
    public Timeline getBoardTimeLine() {return boardTimeLine;}
    public Timeline getInputTimeLine() {return inputTimeLine;}
    public AnimationTimer getGameLoop() {return gameLoop;}
}
