package com.zhun.tetris.board;

import com.zhun.games.TetrisGame;
import com.zhun.tetris.pieces.Piece;

import java.util.Arrays;

public class UpdateGhost {
    private final int[][] board;
    private int[][] coordinates;

    public UpdateGhost(int[][] board) {
        this.board = board;
    }

    public void update (Piece currentPiece, Piece ghostPiece) {
        int arrSize = currentPiece.getCoordinates().length;
        coordinates = new int[arrSize][];
        for (int i = 0; i < arrSize; i++) { // Copy, dont mutate
            coordinates[i] = Arrays.copyOf(currentPiece.getCoordinates()[i], arrSize);
        }

        while (this.canMoveDown()) {
            for (int[] coordinate : coordinates) {coordinate[1]++;}
        }
        ghostPiece.setCoordinates(coordinates);
    }

    // Returns true if can move down
    private boolean canMoveDown() {
        for (int[] coordinate : coordinates) {
            if (coordinate[1] == TetrisGame.BOARD_ROWS - 1 || board[coordinate[1]+1][coordinate[0]] != 0 ) return false;
        }
        return true;
    }
}
