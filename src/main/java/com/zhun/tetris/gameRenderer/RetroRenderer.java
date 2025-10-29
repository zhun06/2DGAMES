package com.zhun.tetris.gameRenderer;

import com.zhun.games.TetrisGame;
import javafx.scene.paint.Color;

public class RetroRenderer extends TetrisRenderer {
    public RetroRenderer(TetrisGame game) {super(game);}

    @Override
    public void render() {
        this.renderBoard(Color.rgb(0,0,0),Color.rgb(0,0,0),Color.rgb(100,100,100));

        this.renderPreviewBG(Color.rgb(0,0,0), Color.WHITE, Color.WHITE);

        this.renderPreviewPieces(Color.rgb(0,0,0));

        this.renderExistingPieces(Color.rgb(0,0,0));

        this.renderCurrentPiece(Color.rgb(0,0,0));

        this.renderGhostPiece(Color.rgb(0,0,0));

        this.renderCanvasBorder(Color.WHITE);

        this.renderCurrentScore();
    }

}
