package com.zhun.tetris.controls;

import com.zhun.games.TetrisGame;
import com.zhun.tetris.pieces.Piece;

import java.util.Arrays;

public class MoveRight {
    private final int COLUMNS = TetrisGame.BOARD_COLUMNS;
    private final int[][] board;
    private int[][] coordinates;

    // Constructor
    public MoveRight(int[][] board) {this.board = board;}

    public void run(Piece currentPiece) {
        this.coordinates = currentPiece.getCoordinates();
        if (this.canMoveRight()) updatePiece();
        else System.out.println("INVALID MOVE RIGHT");
    }

    private boolean canMoveRight() {
        for (int[] coordinate : coordinates) {
            if (coordinate[0] == COLUMNS - 1 || board[coordinate[1]][coordinate[0] + 1] != 0) return false;
        }
        return true;
    }


    private void updatePiece() {
        for (int[] coordinate : coordinates) {
            coordinate[0]++; // x+1
        }
        System.out.println("MOVED RIGHT: " + Arrays.deepToString(coordinates));
    }

}
