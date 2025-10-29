package com.zhun.tetris.controls;

import com.zhun.games.TetrisGame;
import com.zhun.tetris.pieces.Piece;

import java.util.Arrays;

public class SoftDrop {
    private final int ROWS = TetrisGame.BOARD_ROWS;
    private final int[][] board;
    private int[][] coordinates;

    // Constructor
    public SoftDrop(int[][] board) {this.board = board;}

    public void run(Piece currentPiece) {
        coordinates = currentPiece.getCoordinates();
        if (this.canMoveDown()) updatePiece();
    }

    // Return true if can mode down
    private boolean canMoveDown() {
        for (int[] coordinate : coordinates) {
            if (coordinate[1] == ROWS - 1 || board[coordinate[1] + 1][coordinate[0]] != 0) return false;
        }
        return true;
    }

    private void updatePiece() {
        for (int coordinate = 0; coordinate < 4; coordinate++) {
            coordinates[coordinate][1]++; // y+1
        }
        System.out.println("VALID MOVE SOFTDROP: " + Arrays.deepToString(coordinates));
    }
}
