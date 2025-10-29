package com.zhun.snake.gameRenderer;

import com.zhun.games.SnakeGame;
import javafx.scene.paint.*;

// Renders design Candy Theme
public class CandyRenderer extends SnakeRenderer {
    public CandyRenderer(SnakeGame game) {super(game);}

    @Override
    public void render() {
        this.renderBG(Color.rgb(115, 38, 29));

        this.renderBoard(Color.rgb(252, 214, 180), Color.rgb(118, 51, 33));

        this.renderFood(Color.rgb(255, 60, 250));

        this.renderSolidSnake(Color.RED, Color.WHITE);

        this.renderCurrentScore();
    }

}
