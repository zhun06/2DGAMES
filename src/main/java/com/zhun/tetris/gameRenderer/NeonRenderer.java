package com.zhun.tetris.gameRenderer;

import com.zhun.games.TetrisGame;
import javafx.scene.paint.Color;

public class NeonRenderer extends TetrisRenderer {
    public NeonRenderer(TetrisGame game) {super(game);}

    @Override
    public void render() {
        this.renderBoard(Color.BLACK, Color.BLACK, Color.rgb(50,50,50));

        this.renderPreviewBG(Color.rgb(15,15,62), Color.WHITE, Color.WHITE);

        this.renderPreviewPieces(Color.WHITE);

        this.renderExistingPieces(Color.WHITE);

        this.renderCurrentPiece(Color.WHITE);

        this.renderGhostPiece(Color.WHITE);

        this.renderCanvasBorder( Color.WHITE);

        this.renderCurrentScore();
    }

}