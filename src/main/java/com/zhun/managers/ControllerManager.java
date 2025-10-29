package com.zhun.managers;

import com.zhun.controllers.*;

import com.zhun.util.GameChoice;
import javafx.scene.Parent;
import java.io.IOException;

// ALL scene switch goes through
// Manage and store ALL Controllers
// Each fxml file loaded exactly once
// If controller don't exist: create and store controller and it's root
// Else: reuse controller and it's root

public class ControllerManager {
    // Controllers
    private static HomeController homeController;
    private static ThemeController themeController;
    private static GameController gameController;
    // Roots
    private static Parent homeRoot, themeRoot, gameRoot;

    public static void callHomeController() throws IOException {
        SceneManager.initScene(); // Ensure scene exist
        if (homeRoot == null) {
            SceneManager.readFXML("Home");
            homeController = SceneManager.getLoader().getController(); // Get loader -> get controller
            homeRoot = SceneManager.getRoot(); // Get root
            homeController.addKeyHandler(); // Add key handler
        }
        SceneManager.setRoot(homeRoot); // Reuse root
        homeRoot.requestFocus();
    }

    public static void callThemeController() throws IOException {
        SceneManager.initScene(); // Ensure scene exist
        if (themeRoot == null) {
            SceneManager.readFXML("Theme");
            themeController = SceneManager.getLoader().getController(); // Get loader -> get controller
            themeRoot = SceneManager.getRoot(); // Get root
            themeController.addKeyHandler(); // Add key handler
        }
        SceneManager.setRoot(themeRoot); // Reuse root
        themeRoot.requestFocus();
    }

    public static void callGameController(GameChoice choice) throws IOException {
        SceneManager.initScene(); // Ensure scene exist
        if (gameController == null) {
            SceneManager.readFXML("Game");
            gameController = SceneManager.getLoader().getController(); // Get loader -> get controller
            gameRoot = SceneManager.getRoot(); // Get root
            gameController.addKeyHandler();
        }
        SceneManager.setRoot(gameRoot); // Reuse root
        gameRoot.requestFocus();

        // Start Game
        switch (choice) {
            case SNAKE -> GameManager.startSnake();
            case TETRIS -> GameManager.startTetris();
//            case PACMAN ->  GameManager.startPacman();
        }
    }

    // Getters
    public static Parent getHomeRoot() {return homeRoot;}
    public static Parent getThemeRoot() {return themeRoot;}
    public static Parent getGameRoot() {return gameRoot;}

}
