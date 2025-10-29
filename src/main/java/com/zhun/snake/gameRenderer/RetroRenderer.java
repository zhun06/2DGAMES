package com.zhun.snake.gameRenderer;

import com.zhun.games.SnakeGame;
import javafx.scene.paint.Color;

// Renders design Retro Theme
public class RetroRenderer extends SnakeRenderer {
    public RetroRenderer(SnakeGame game) {super(game);}

    @Override
    public void render() {
        this.renderBG(Color.rgb(44, 22, 43));

        this.renderBoard(Color.rgb(254, 229, 187), Color.rgb(35, 160, 111));

        this.renderFood(Color.rgb(255, 85, 85));


        this.renderSolidSnake(Color.BLUE, Color.DARKBLUE);

        this.renderCurrentScore();
    }
}
