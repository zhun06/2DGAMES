package com.zhun.snake;

import javafx.scene.canvas.GraphicsContext;

@FunctionalInterface
public interface ThemeRenderer {
    void render(SnakeGame game, GraphicsContext gc);
}


