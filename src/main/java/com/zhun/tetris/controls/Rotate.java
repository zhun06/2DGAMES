package com.zhun.tetris.controls;

import com.zhun.games.TetrisGame;
import com.zhun.tetris.pieces.Piece;
import com.zhun.tetris.pieces.Shape;

import java.util.Arrays;

public class Rotate {
    private final int COLUMNS = TetrisGame.BOARD_COLUMNS;
    private final int[][] board;
    private int[][] currentCoordinates;
    private int[][] temp1;
    private Shape shape;

    // Constructor
    public Rotate(int[][] board) {this.board = board;}

    public void rotate(Piece currentPiece) {
        temp1 = new int[4][2];
        shape = currentPiece.getShape();
        currentCoordinates = currentPiece.getCoordinates();

        // Copy
        for(int i = 0; i < 4; i++) {
            temp1[i] = Arrays.copyOf(currentCoordinates[i], 2);
        }

        // O don't rotate
        if (shape == Shape.O) return;

        tempRotate();
        this.tryPositions();
    }

    // Rotate temporary coordinates (pivot always 2)
    private void tempRotate() {
        int pivotX;
        int pivotY;

        // Define pivots
        if (shape == Shape.I) {
            pivotX = (temp1[1][0] + temp1[2][0]) / 2;
            pivotY = temp1[0][1];
        }
        else {
            pivotX = temp1[2][0];
            pivotY = temp1[2][1];
        }

        for (int[] coordinate : temp1) {
            int offsetX = coordinate[0] - pivotX;
            int offsetY = coordinate[1] - pivotY;
            coordinate[0] = pivotX + offsetY;
            coordinate[1] =  pivotY - offsetX;
        }
    }

    // Try different offsets
    private void tryPositions() {
        if (canRotate(0, 0)) return;    // Default
        if (canRotate(1, 0)) return;    // Right kick
        if (canRotate(-1, 0)) return;   // Left kick
        if (canRotate(2, 0)) return;    // Right * 2
        if (canRotate(-2, 0)) return;   // Left * 2
        if (canRotate(0, -1)) return;   // Up
        if (canRotate(0, 1)) return;    // Down
        if (canRotate(1, -1)) return;   // Up Right
        canRotate(-1, -1);  // Up Left
    }

    private boolean canRotate(int offsetX, int offsetY) {
        for (int[] coordinate : temp1) {
            if (coordinate[0] + offsetX < 0 || coordinate[0] + offsetX > COLUMNS - 1) return false; // Check horizontal
            if (board[coordinate[1] + offsetY][coordinate[0] + offsetX] != 0) return false; // Check existing pieces
        }

        for (int[] coordinate : temp1) {
            coordinate[0] += offsetX;
            coordinate[1] += offsetY;
        }

        this.copy(currentCoordinates, temp1);
        return true;
    }

    // Copy temp -> current
    private void copy(int[][] arr1, int[][]arr2) {
        for(int i = 0; i < 4; i++) {
            arr1[i] = Arrays.copyOf(arr2[i], 2);
        }
    }
}
