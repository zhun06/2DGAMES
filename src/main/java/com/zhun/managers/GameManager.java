package com.zhun.managers;

import com.zhun.engines.Engine;
import com.zhun.engines.SnakeEngine;
import com.zhun.engines.TetrisEngine;
import com.zhun.games.SnakeGame;
import com.zhun.games.TetrisGame;

import com.zhun.util.GameChoice;
import com.zhun.util.GameState;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import java.io.IOException;

// Manage ALL game loop
// Stores ALL games
// GAMES & MANAGERS created once and REUSED
public class GameManager {
    private static Canvas gameCanvas; // Reuse
    private static Canvas vfxCanvas; // Reuse
    private static GameChoice currentGameChoice; // Updates
    private static GameState currentGameState;
    // Games
    private static SnakeGame snakeGame; // Reuse
    private static TetrisGame tetrisGame; // Reuse
//    private static PacmanGame pacmanGame; // Reuse
    // Current game engine
    private static Engine engine; // Change every game
    // Game-state managers
    private static GameKeyHandlerManager gameKeyHandlerManager; // Reuse
    private static TimelineManager timelineManager; // Reuse
    private static OverlayManager overlayManager; // Reuse

    // Get resources (ONCE)
    public static void initialize() {
        if (gameCanvas == null) {
            Parent gameRoot = ControllerManager.getGameRoot();
            gameCanvas = (Canvas) gameRoot.lookup("#gameCanvas"); // Find by fx:id
            vfxCanvas = (Canvas) gameRoot.lookup("#vfxCanvas"); // Find by fx:id
            gameKeyHandlerManager = new GameKeyHandlerManager();
            timelineManager = new TimelineManager();
            overlayManager = new OverlayManager();

        }
    }

    public static void startSnake() {
        GameManager.initialize();
        if (snakeGame == null) snakeGame = new SnakeGame(); // Create and store
        engine = new SnakeEngine(snakeGame);
        engine.start();

        currentGameChoice = GameChoice.SNAKE;
        gameCanvas.setWidth(SnakeGame.WIDTH);
        gameCanvas.setHeight(SnakeGame.HEIGHT);

        gameKeyHandlerManager.initialize(snakeGame);
        timelineManager.initialize(engine);
        GameManager.startGame();
    }

    public static void startTetris() {
        GameManager.initialize();
        if (tetrisGame == null) tetrisGame = new TetrisGame(); // Create and store
        engine = new TetrisEngine(tetrisGame); // New engine
        engine.start();

        currentGameChoice = GameChoice.TETRIS;
        gameCanvas.setWidth(TetrisGame.CANVAS_WIDTH);
        gameCanvas.setHeight(TetrisGame.CANVAS_HEIGHT);

        gameKeyHandlerManager.initialize(tetrisGame);
        timelineManager.initialize(engine);
        GameManager.startGame();
    }

    public static void startPacman() {
        GameManager.initialize();
    }

    public static void startGame() {
        GameManager.focusCanvas();
        currentGameState = GameState.START;
        timelineManager.update();
        gameKeyHandlerManager.update();
    }

    public static void pauseGame() {
        currentGameState = GameState.PAUSE;
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.showPauseGame();
    }

    public static void resumeGame() throws IOException{
        currentGameState = GameState.RESUME;
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.hidePauseGame();
    }

    public static void restartGame() {
        currentGameState = GameState.RESTART;
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.hidePauseGame();
        overlayManager.hideGameOver();

        switch (currentGameChoice) {
            case SNAKE -> GameManager.startSnake();
            case TETRIS -> GameManager.startTetris();
//            case PACMAN -> GameManager.startPacman();
        }
    }

    public static void changeTheme() throws IOException {
        currentGameState = GameState.CHANGETHEME;
        gameKeyHandlerManager.update();
        overlayManager.hideGameOver();
        ControllerManager.callThemeController();
    }


    public static void exitGame() throws IOException {
        currentGameState = GameState.EXIT;
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.hidePauseGame();
        overlayManager.hideGameOver();
        ControllerManager.callHomeController();
    }

    private static void runOnGameOver() {
        currentGameState = GameState.GAMEOVER;
        timelineManager.update();
        gameKeyHandlerManager.update();
        overlayManager.showGameOver();
    }

    // Notify managers when game over
    public static Runnable setOnGameOver() {return GameManager::runOnGameOver;}

    // Focus canvas
    private static void focusCanvas() {gameCanvas.requestFocus();}


    // Getter
    public static GameChoice getCurrentGameChoice() {return currentGameChoice;}
    public static GameState getCurrentGameState() {return currentGameState;}
    public static Canvas getGameCanvas() {return gameCanvas;}
    public static Canvas getVfxCanvas() {return vfxCanvas;}

}
