package com.zhun.tetris.gameRenderer;

import com.zhun.games.TetrisGame;
import javafx.scene.paint.Color;

public class CandyRenderer extends TetrisRenderer {
    public CandyRenderer(TetrisGame game) {super(game);}

    @Override
    public void render() {
        this.renderBoard(Color.LIGHTPINK, Color.LIGHTPINK, Color.WHITE);

        this.renderPreviewBG(Color.BLACK, Color.WHITE, Color.WHITE);

        this.renderPreviewPieces(Color.BLACK);

        this.renderExistingPieces(Color.BLACK);

        this.renderCurrentPiece(Color.BLACK);

        this.renderGhostPiece(Color.BLACK);

        this.renderCanvasBorder(Color.WHITE);

        this.renderCurrentScore();
    }

}

