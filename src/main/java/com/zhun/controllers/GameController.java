package com.zhun.controllers;

import com.zhun.managers.ControllerManager;
import com.zhun.managers.GameManager;
import com.zhun.managers.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

// Controls game page
public class GameController {
    private final static IntegerProperty highScore = new SimpleIntegerProperty(0);
    private final static IntegerProperty currentScore = new SimpleIntegerProperty(0);

    @FXML private StackPane stack;
    @FXML private VBox gameOverPane;

    @FXML
    private Label highScoreLabel, currentScoreLabel;

    @FXML // In Game
    private Button pauseBtn, restartBtn1, exitBtn1;

    @FXML // Pause Game
    private Button resumeBtn, restartBtn2, exitBtn2;

    @FXML // End Game
    private Button playAgainBtn, themeBtn, exitBtn3;

    @FXML
    private void initialize () throws IOException {
        // Block focus
        pauseBtn.setFocusTraversable(false);
        restartBtn1.setFocusTraversable(false);
        exitBtn1.setFocusTraversable(false);
        // set Label
        highScoreLabel.textProperty().bind(highScore.asString());
        currentScoreLabel.textProperty().bind(currentScore.asString());
        // Bind to root (auto resize)
        gameOverPane.prefWidthProperty().bind(stack.widthProperty());
        gameOverPane.prefHeightProperty().bind(stack.heightProperty());
    }

    @FXML // In Game
    private void onButtonClick (ActionEvent event) throws IOException {
        if (event.getSource() == pauseBtn) {
            GameManager.pauseGame();
        }
        if (event.getSource() == restartBtn1) {
            GameManager.restartGame();
        }
        if (event.getSource() == exitBtn1) {
            GameManager.exitGame();
        }
    }

    @FXML // Pause Game
    private void onPauseButtonClick (ActionEvent event) throws IOException {
        if (event.getSource() == resumeBtn) {
            GameManager.resumeGame();
        }
        if (event.getSource() == restartBtn2) {
            GameManager.restartGame();
        }
        if (event.getSource() == exitBtn2) {
            GameManager.exitGame();
        }
    }

    @FXML // Game Over
    private void onGameOverButtonClick (ActionEvent event) throws IOException {
        if (event.getSource() == playAgainBtn) {
            GameManager.restartGame();
        }
        if (event.getSource() == themeBtn) {
            GameManager.changeTheme();
        }
        if (event.getSource() == exitBtn3) {
            GameManager.exitGame();
        }
    }

    public void addKeyHandler() {
        Parent root = ControllerManager.getGameRoot();
        Stage stage = SceneManager.getStage();
        root.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case F -> {
                    stage.setFullScreen(!stage.isFullScreen());
                    System.out.println((stage.isFullScreen() + "FULLSCREEN TRIGGERED"));
                }
            }
        });
    }

    // Setters
    public static void setHighScore(int score) {highScore.set(score);}
    public static void setCurrentScore(int score) {currentScore.set(score);}
}
