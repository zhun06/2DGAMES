package com.zhun.managers;

import com.zhun.engines.Engine;
import com.zhun.engines.SnakeEngine;
import com.zhun.engines.TetrisEngine;
import com.zhun.util.GameChoice;
import com.zhun.util.GameState;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;

// Start, pause, resume, stop
public class TimelineManager {
    private GameChoice gameChoice;
    private GameState gameState;
    // Timeline
    private Timeline snakeTimeline;
    private Timeline tetrisInputTimeline;
    private Timeline tetrisBoardTimeline;
    private Timeline pacmanInputTimeline;
    private Timeline pacmanBoardTimeline;
    // Animation timer
    private AnimationTimer snakeLoop;
    private AnimationTimer tetrisLoop;
    private AnimationTimer pacmanGameLoop;

    public void initialize(Engine engine) {
        gameChoice = GameManager.getCurrentGameChoice();

        // Get timeline & animation timer
        switch(gameChoice) {
            case SNAKE -> {
                snakeTimeline = ((SnakeEngine)engine).getTimeline();
                snakeLoop = ((SnakeEngine)engine).getGameLoop();
            }
            case TETRIS -> {
                tetrisBoardTimeline = ((TetrisEngine)engine).getBoardTimeLine();
                tetrisInputTimeline = ((TetrisEngine)engine).getInputTimeLine();
                tetrisLoop = ((TetrisEngine)engine).getGameLoop();

            }
//            case PACMAN -> {
//                pacmanBoardTimeline = ((PacmanEngine)engine).getBoardTimeLine();
//                pacmanInputTimeline = ((PacmanEngine)engine).getInputTimeLine();
//            }
        }
    }

    public void update() {
        gameState = GameManager.getCurrentGameState();

        switch (gameState) {
            case START, RESUME -> this.onStartOrResume();
            case PAUSE -> this.onPause();
            case GAMEOVER, EXIT, RESTART -> this.onGameOverOrExitOrRestart();
        }
    }

    private void onStartOrResume() {
        switch (gameChoice) {
            case SNAKE -> {
                snakeTimeline.play();
                snakeLoop.start();
            }
            case TETRIS -> {
                tetrisInputTimeline.play();
                tetrisBoardTimeline.play();
                tetrisLoop.start();
            }
            case PACMAN -> {
                pacmanInputTimeline.play();
                pacmanBoardTimeline.play();
            }
        }
    }

    private void onPause() {
        switch (gameChoice) {
            case SNAKE -> {
                snakeTimeline.pause();
                snakeLoop.stop();
            }
            case TETRIS -> {
                tetrisInputTimeline.pause();
                tetrisBoardTimeline.pause();
                tetrisLoop.stop();
            }
            case PACMAN -> {
                pacmanInputTimeline.pause();
                pacmanBoardTimeline.pause();
            }
        }
    }

    private void onGameOverOrExitOrRestart() {
        switch (gameChoice) {
            case SNAKE -> {
                snakeTimeline.stop();
                snakeLoop.stop();
            }
            case TETRIS -> {
                tetrisInputTimeline.stop();
                tetrisBoardTimeline.stop();
                tetrisLoop.stop();
            }
            case PACMAN -> {
                pacmanInputTimeline.stop();
                pacmanBoardTimeline.stop();
            }
        }
    }


}
