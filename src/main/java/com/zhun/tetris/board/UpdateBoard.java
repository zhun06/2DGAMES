package com.zhun.tetris.board;

import com.zhun.games.TetrisGame;
import com.zhun.tetris.pieces.Piece;
import com.zhun.tetris.pieces.Shape;

import java.util.Arrays;

// (Handle collisions & add piece to board) || move piece down
public class UpdateBoard {
    private final int[][] board;
    private int[][] coordinates;
    private Shape shape;
    private final int ROWS = TetrisGame.BOARD_ROWS;
    private final int COLUMNS = TetrisGame.BOARD_COLUMNS;
    private final Runnable onMerge;
    private final Runnable onGameOver;

    // Constructor
    public UpdateBoard(int[][] board, Runnable onMerge, Runnable onGameOver) {
        this.board = board;
        this.onMerge = onMerge;
        this.onGameOver = onGameOver;
    }

    public void update(Piece currentPiece) {
        this.coordinates = currentPiece.getCoordinates();
        this.shape = currentPiece.getShape();
        if (canMoveDown()) this.movePieceDown();
        else this.merge();
        if (this.isGameOver()) onGameOver.run();
    }

    // Check overlap
    private boolean isGameOver() {
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < COLUMNS; j++){
                if (board[i][j] != 0)return true;
            }
        }
        return false;
    }

    // Return true if can mode down
    private boolean canMoveDown() {
        for (int[] coordinate : coordinates) {
            if (coordinate[1] == ROWS - 1 || board[coordinate[1] + 1][coordinate[0]] != 0) return false;
        }
        return true;
    }

    public void movePieceDown() {
        for (int i = 0; i < 4; i++) {
            coordinates[i][1] ++;
        }
        System.out.println("Piece moved down:" + Arrays.deepToString(coordinates));
    }

    // Merge piece and board
    private void merge() {
        System.out.println("Merging board...");
        int marker = 0; // Mark board
        switch (shape) {
            case L -> marker = 1;
            case RL -> marker = 2;
            case Z -> marker = 3;
            case RZ -> marker = 4;
            case T -> marker = 5;
            case I ->  marker = 6;
            case O ->  marker = 7;
        }

        for (int piece = 0; piece < 4; piece++) {
            board[coordinates[piece][1]][coordinates[piece][0]] = marker;
        }

        onMerge.run(); // Notify game

        System.out.println("Merged, New Board: ");
        for (int[] coordinates : board) {
            System.out.println(Arrays.toString(coordinates));
        }
    }
}
