package com.zhun;

import com.zhun.managers.ControllerManager;
import com.zhun.managers.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.setStage(stage);
        ControllerManager.callHomeController();
        stage.show(); // Show stage
    }
}
