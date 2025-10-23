package com.zhun.managers;

import com.zhun.snake.SnakeEngine;
import com.zhun.snake.SnakeGame;

import com.zhun.util.GameChoice;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import java.io.IOException;

// Manage ALL game loop
// Stores ALL games
// GAMES & ENGINES & MANAGERS created once and REUSED
public class GameManager {
    private static Canvas gameCanvas; // Reuse
    private static GameChoice currentGameChoice; // Updates
    // Games
    private static SnakeGame snakeGame; // Reuse
//    private static TetrisGame tetrisGame; // Reuse
//    private static PacmanGame pacmanGame; // Reuse
    // Game engines
    private static SnakeEngine snakeEngine; // Reuse
//    private static TetrisEngine tetrisEngine; // Reuse
//    private static PacmanEngine PacmanEngine; // Reuse
    // Game-state managers
    private static OverlayManager overlayManager; // Reuse
    private static GameKeyHandlerManager gameKeyHandler; // Reuse


    public static void startSnake() {
        GameManager.initGame();
        currentGameChoice = GameChoice.SNAKE;
        if (snakeGame == null) {
            snakeGame = new SnakeGame(); // Create and store
            snakeEngine = new SnakeEngine(snakeGame, gameCanvas); // Create and store
        }
        gameKeyHandler.focusCanvas();
        gameKeyHandler.initKeyHandler();
        snakeEngine.start();
    }

//    public static void startTetris() throws IOException {
//        GameManager.initGame();
//        currentGameChoice = GameChoice.TETRIS;
//        if (tetrisGame == null) {
//            tetrisGame = new tetrisGame(); // Create and store
//            tetrisEngine = new SnakeEngine(tetrisGame, gameCanvas); // Create and store
//        }
//        tetrisEngine.start();
//    }

//    public static void startPacman() throws IOException {
//        GameManager.initGame();
//        currentGameChoice = GameChoice.PACMAN;
//        if (pacManGame == null) {
//            pacmanGame = new pacmanGame(); // Create and store
//            pacmanEngine = new PacmanEngine(pacmanGame, gameCanvas); // Create and store
//        }
//        pacmanEngine.start();
//    }

    // Get resources (ONCE)
    public static void initGame() {
        if (gameCanvas == null) {
            Parent gameRoot = ControllerManager.getGameRoot();
            gameCanvas = (Canvas) gameRoot.lookup("#gameCanvas"); // Find by fx:id
            overlayManager = new OverlayManager();
            gameKeyHandler = new GameKeyHandlerManager(gameCanvas);
        }
    }

    public static void pauseGame() {
        snakeEngine.getTimeline().pause();
        gameKeyHandler.onPause();
        overlayManager.showPauseGame();
    }

    public static void resumeGame() throws IOException{
        gameKeyHandler.onResume();
        overlayManager.hidePauseGame();
        snakeEngine.getTimeline().play();
    }

    public static void restartGame() {
        snakeEngine.getTimeline().stop();
        gameKeyHandler.onRestart();
        overlayManager.hidePauseGame();
        overlayManager.hideGameOver();
        GameManager.startSnake();
    }

    public static void changeTheme() throws IOException {
        gameKeyHandler.onChangeTheme();
        overlayManager.hideGameOver();
        ControllerManager.callThemeController();
    }


    public static void exitGame() throws IOException {
        snakeEngine.getTimeline().stop();
        gameKeyHandler.onExit();
        overlayManager.hidePauseGame();
        overlayManager.hideGameOver();
        ControllerManager.callHomeController();
    }

    // Notify managers when game over
    public static Runnable setOnGameOver() {
        return GameManager::runOnGameOver;
    }

    public static void runOnGameOver() {
        overlayManager.showGameOver();
        gameKeyHandler.onGameOver();
    }

    // Getter
    public static GameChoice getCurrentGameChoice() {return currentGameChoice;}
    public static SnakeGame getSnakeGame() {return snakeGame;}
//    public static tetrisGame getTetrisGame() {return tetrisGame;}
//    public static pacmanGame getPacmanGame() {return pacmanGame;}

}
