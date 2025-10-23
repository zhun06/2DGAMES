package com.zhun.managers;

import com.zhun.snake.SnakeGame;
import com.zhun.util.GameChoice;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;

import java.io.IOException;


// Add or remove event handlers based on game state
public class GameKeyHandlerManager {
    // FXML
    private final Parent gameRoot; // Game root
    private final Canvas gameCanvas; // Game canvas

    // Event handlers
    private EventHandler<KeyEvent> snakeHandler, tetrisHandler, pacmanHandler; // Specific handlers
    private EventHandler<KeyEvent> onPauseHandler, onGameOverHandler; // Generic handlers

    // Games
    private SnakeGame snakeGame;
//    private TetrisGame tetrisGame;
//    private PacmanGame pacmanGame;

    // Current game
    private GameChoice currentGameChoice;

    // Constructor
    public GameKeyHandlerManager(Canvas canvas) {
        gameRoot = ControllerManager.getGameRoot();
        gameCanvas = canvas;
        this.setOnPauseHandler();
        this.setOnGameOverHandler();
    }

    // Initialize
    public void initKeyHandler() {
        currentGameChoice = GameManager.getCurrentGameChoice();
        switch(currentGameChoice) {
            case SNAKE -> {
                if(snakeHandler == null) {
                    this.snakeGame = GameManager.getSnakeGame();
                    this.setSnakeHandler(snakeGame);
                }}
//            case TETRIS -> {if(tetrisHandler == null) this.setTetrisHandler(tetrisGame);}
//            case PACMAN -> {if(pacmanHandler == null) this.setPacmanHandler(pacmanGame);}
        }
        this.inGame();
    }

    // In Game
    public void inGame() {
        this.attachGameHandler();
    }

    // Pause Game
    public void onPause() {
        this.detachGameHandler();
        this.attachHandler(onPauseHandler);
    }

    // Resume Game
    public void onResume() {
        this.detachHandler(onPauseHandler);
        this.attachGameHandler();
    }

    // Restart Game
    public void onRestart() {
        this.detachGameHandler();
        this.detachHandler(onPauseHandler);
        this.detachHandler(onGameOverHandler);
    }

    // Game Over
    public void onGameOver() {
        this.detachGameHandler();
        this.attachHandler(onGameOverHandler);
    }

    // Change Theme
    public void onChangeTheme() {this.detachHandler(onGameOverHandler);}

    // Exit Game
    public void onExit() {this.detachHandler(onGameOverHandler);}


    public void attachHandler(EventHandler<KeyEvent> handler) {
        gameRoot.removeEventHandler(KeyEvent.KEY_PRESSED, handler); // Remove if it was already attached
        gameRoot.addEventHandler(KeyEvent.KEY_PRESSED, handler);
    }

    // Generic key handlers
    public void detachHandler(EventHandler<KeyEvent> handler) {
        gameRoot.removeEventHandler(KeyEvent.KEY_PRESSED, handler);
    }

    // In game key handlers
    public void attachGameHandler() {
        switch (currentGameChoice) {
            case SNAKE -> gameRoot.addEventHandler(KeyEvent.KEY_PRESSED, snakeHandler);
            case TETRIS ->  gameRoot.addEventHandler(KeyEvent.KEY_PRESSED, tetrisHandler);
            case PACMAN ->  gameRoot.addEventHandler(KeyEvent.KEY_PRESSED, pacmanHandler);
        }
    }

    public void detachGameHandler() {
        switch (currentGameChoice) {
            case SNAKE -> gameRoot.removeEventHandler(KeyEvent.KEY_PRESSED, snakeHandler);
            case TETRIS ->  gameRoot.removeEventHandler(KeyEvent.KEY_PRESSED, tetrisHandler);
            case PACMAN ->  gameRoot.removeEventHandler(KeyEvent.KEY_PRESSED, pacmanHandler);
        }
    }

    public void setSnakeHandler(SnakeGame snakeGame) {
        this.snakeGame = snakeGame;
        snakeHandler = event -> {
            switch (event.getCode()) {
                case UP, W -> snakeGame.setDirection(SnakeGame.Direction.UP);
                case DOWN, S -> snakeGame.setDirection(SnakeGame.Direction.DOWN);
                case LEFT, A -> snakeGame.setDirection(SnakeGame.Direction.LEFT);
                case RIGHT, D -> snakeGame.setDirection(SnakeGame.Direction.RIGHT);
                case SPACE, ENTER, P -> GameManager.pauseGame();
                case R -> GameManager.restartGame();
                case ESCAPE, E, Q -> {
                    try {
                        GameManager.exitGame();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }

    public void setOnPauseHandler() {
        this.onPauseHandler = keyEvent -> {
            switch (keyEvent.getCode()) {
                case SPACE, ENTER -> {
                    try {
                        GameManager.resumeGame();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case R -> GameManager.restartGame();
                case ESCAPE, E, Q -> {
                    try {
                        GameManager.exitGame();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }

    public void setOnGameOverHandler() {
        this.onGameOverHandler = keyEvent -> {
            switch (keyEvent.getCode()) {
                case SPACE, ENTER, P -> GameManager.restartGame();
                case C -> {
                    try {
                        GameManager.changeTheme();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case ESCAPE, E, Q -> {
                    try {
                        GameManager.exitGame();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }

    public void focusCanvas() {gameCanvas.requestFocus();}
}
