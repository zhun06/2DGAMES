package com.zhun.tetris.controls;

import com.zhun.games.TetrisGame;
import com.zhun.tetris.pieces.Piece;

public class HardDrop {
    private final int[][] board;
    private int[][] coordinates;

    // Constructor
    public HardDrop(int[][] board) {this.board = board;}

    public void run(Piece currentPiece) {
        System.out.println("IN HARD DROP");
        coordinates = currentPiece.getCoordinates();

        while (this.canMoveDown()) {
            for (int[] coordinate : coordinates) {
                coordinate[1]++;
            }
        }
    }

    // Return true if can mode down
    private boolean canMoveDown() {
        for (int[] coordinate : this.coordinates) {
            if (coordinate[1] == TetrisGame.BOARD_ROWS - 1 || board[coordinate[1] + 1][coordinate[0]] != 0) return false;
        }
        return true;
    }

}
