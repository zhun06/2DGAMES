package com.zhun.snake.gameRenderer;

import com.zhun.games.SnakeGame;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;

// Renders design Neon Theme
public class NeonRenderer extends SnakeRenderer {
    public NeonRenderer(SnakeGame game) {super(game);}

    private final LinearGradient lg1 = new LinearGradient(
            0, 0, 1, 1,true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.rgb(255, 10, 255)),
            new Stop(0.5, Color.rgb(255,10, 150)),
            new Stop(1.0, Color.rgb(255,10, 10))
    );
    private final LinearGradient lg2 = new LinearGradient(
            0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.rgb(42,181, 10)),
            new Stop(0.5, Color.rgb(10, 255, 30)),
            new Stop(1.0, Color.rgb(255,255, 10))
    );
    private final LinearGradient lg3 = new LinearGradient(
            0, 0, 1, 1,true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.rgb(10, 10, 255)),
            new Stop(0.5, Color.rgb(150, 10, 255)),
            new Stop(1.0, Color.rgb(255, 10, 200))
    );
    private final LinearGradient lg4 = new LinearGradient(
            0, 0, 1, 1,true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.rgb(0, 255, 255)),
            new Stop(0.5, Color.rgb(10, 200, 150)),
            new Stop(1.0, Color.rgb(255, 255, 50))
    );
    private final LinearGradient lg5 = new LinearGradient(
            0, 0, 1, 1,true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.rgb(255, 0, 200)),
            new Stop(0.5, Color.rgb(255, 100, 50)),
            new Stop(1.0, Color.rgb(255, 0, 0))

    );

    @Override
    public void render() {
        this.renderBG(Color.rgb(1, 59, 68));

        this.renderBoard(Color.rgb(8, 200, 245), Color.rgb(6, 26, 43));

        this.renderFood(Color.rgb(255, 77, 77));

        this.renderGradientSnake(lg1, lg2, lg3, lg4, lg5);

        this.renderCurrentScore();
    }

}
