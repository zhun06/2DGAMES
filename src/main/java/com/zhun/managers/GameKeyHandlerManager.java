package com.zhun.managers;

import com.zhun.games.Game;
import com.zhun.games.SnakeGame;
import com.zhun.games.TetrisGame;
import com.zhun.util.GameChoice;

import com.zhun.util.GameState;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;

import java.io.IOException;


// Add or remove event handlers based on game state
public class GameKeyHandlerManager {
    private final Parent gameRoot; // Game root

    // Event handlers
    private EventHandler<KeyEvent> snakeHandler, tetrisHandler, pacmanHandler; // Specific handlers
    private EventHandler<KeyEvent> onPauseHandler, onGameOverHandler; // Generic handlers

    // Games
    private SnakeGame snakeGame;
    private TetrisGame tetrisGame;
//    private PacmanGame pacmanGame;

    // Current game
    private GameChoice currentGameChoice;
    private GameState currentGameState;

    // Constructor
    public GameKeyHandlerManager() {
        gameRoot = ControllerManager.getGameRoot();
        this.setOnPauseHandler();
        this.setOnGameOverHandler();
    }

    public void initialize(Game currentGame) {
        currentGameChoice = GameManager.getCurrentGameChoice();

        switch(currentGameChoice) {
            case SNAKE -> {
                if(snakeHandler == null) {
                    this.snakeGame = (SnakeGame)currentGame;
                    this.setSnakeHandler(snakeGame);
                }}
            case TETRIS -> {
                if(tetrisHandler == null) {
                    this.tetrisGame = (TetrisGame)currentGame;
                    this.setTetrisHandler(tetrisGame);
                }}
//            case PACMAN -> {if(pacmanHandler == null) this.setPacmanHandler(pacmanGame);}
        }
    }

    public void update() {
        currentGameState = GameManager.getCurrentGameState();

        switch (currentGameState) {
            case START -> this.onStart();
            case PAUSE -> this.onPause();
            case RESUME -> this.onResume();
            case RESTART, EXIT -> this.onRestartOrExit();
            case GAMEOVER -> this.onGameOver();
            case CHANGETHEME ->  this.onChangeTheme();
        }
    }

    // In Game
    public void onStart() {
        System.out.println("INSIDE UPDATE GAME HANDLER ON START");
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

    // Restart Game || Exit || Game Over
    public void onRestartOrExit() {
        this.detachGameHandler();
        this.detachHandler(onPauseHandler);
        this.detachHandler(onGameOverHandler);
    }

    public void onGameOver() {
        this.detachGameHandler();
        this.attachHandler(onGameOverHandler);
    }

    // Change Theme
    public void onChangeTheme() {this.detachHandler(onGameOverHandler);}

    // Generic key handlers
    public void attachHandler(EventHandler<KeyEvent> handler) {
        gameRoot.addEventHandler(KeyEvent.KEY_PRESSED, handler);
    }

    public void detachHandler(EventHandler<KeyEvent> handler) {
        gameRoot.removeEventHandler(KeyEvent.KEY_PRESSED, handler);
    }

    // In game key handlers
    public void attachGameHandler() {
        switch (currentGameChoice) {
            case SNAKE -> gameRoot.addEventHandler(KeyEvent.KEY_PRESSED, snakeHandler);
            case TETRIS -> gameRoot.addEventHandler(KeyEvent.KEY_PRESSED, tetrisHandler);
            case PACMAN -> gameRoot.addEventHandler(KeyEvent.KEY_PRESSED, pacmanHandler);
        }
    }

    public void detachGameHandler() {
        switch (currentGameChoice) {
            case SNAKE -> gameRoot.removeEventHandler(KeyEvent.KEY_PRESSED, snakeHandler);
            case TETRIS -> gameRoot.removeEventHandler(KeyEvent.KEY_PRESSED, tetrisHandler);
            case PACMAN -> gameRoot.removeEventHandler(KeyEvent.KEY_PRESSED, pacmanHandler);
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
                case SPACE, ENTER -> GameManager.pauseGame();
                case R, P-> GameManager.restartGame();
                case E, Q -> {
                    try {
                        GameManager.exitGame();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }

    public void setTetrisHandler(TetrisGame tetrisGame) {
        this.tetrisGame = tetrisGame;
        tetrisHandler = event -> {
            switch (event.getCode()) {
                case UP, W -> tetrisGame.setKey(TetrisGame.Key.UP);
                case DOWN, S -> tetrisGame.setKey(TetrisGame.Key.DOWN);
                case LEFT, A -> tetrisGame.setKey(TetrisGame.Key.LEFT);
                case RIGHT, D -> tetrisGame.setKey(TetrisGame.Key.RIGHT);
                case TAB, ENTER -> tetrisGame.setKey(TetrisGame.Key.TAB);
                case SPACE ->  GameManager.pauseGame();
                case R, P-> GameManager.restartGame();
                case E, Q -> {
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
                case P, R -> GameManager.restartGame();
                case E, Q -> {
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
                case SPACE, ENTER, P, R -> GameManager.restartGame();
                case C, T -> {
                    try {
                        GameManager.changeTheme();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case E, Q -> {
                    try {
                        GameManager.exitGame();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }

}
