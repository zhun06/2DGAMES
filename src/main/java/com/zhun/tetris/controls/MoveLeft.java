package com.zhun.tetris.controls;

import com.zhun.tetris.pieces.Piece;

import java.util.Arrays;

public class MoveLeft {
    private final int[][] board;
    private int[][] coordinates;

    // Constructor
    public MoveLeft(int[][] board) {this.board = board;}

    public void run(Piece currentPiece) {
        this.coordinates = currentPiece.getCoordinates();
        if (this.canMoveLeft()) updatePiece();
        else System.out.println("INVALID MOVE LEFT");
    }


    private boolean canMoveLeft() {
        for (int[] coordinate : coordinates) {
            if (coordinate[0] == 0 || board[coordinate[1]][coordinate[0] - 1] != 0) return false;
        }
        return true;
    }


    private void updatePiece() {
        for (int[] coordinate : coordinates) {
            coordinate[0]--; // x-1
        }
        System.out.println("MOVED LEFT: " + Arrays.deepToString(coordinates));
    }

}
