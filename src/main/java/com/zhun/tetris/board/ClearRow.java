package com.zhun.tetris.board;

import com.zhun.games.TetrisGame;

import java.util.Arrays;

public class ClearRow {
    private final int[][] board;
    private final int[][] tempBoard;
    private final int ROWS = TetrisGame.BOARD_ROWS;
    private final int COLUMNS = TetrisGame.BOARD_COLUMNS;
    private int rowsCleared;


    // Constructor
    public ClearRow(int[][] board) {
        this.board = board;
        this.tempBoard = new int[ROWS][COLUMNS];
    }

    public void update() {
        rowsCleared = 0;
        for (int row = 0; row < ROWS; row++) {
            int num = 0;
            for (int col = 0; col < COLUMNS; col++) {
                if (board[row][col] != 0) num++;
                if (num == COLUMNS) this.clearRow(row);
            }
        }

    }

    public void clearRow(int row) {
        rowsCleared++;
        System.out.println("CLEARING ROW");
        int index = 1;
        for (int i = 0; i < row; i++) {
            tempBoard[index++] = Arrays.copyOf(board[i], COLUMNS);
        }
        index = row + 1;
        for (int j = row+1; j < ROWS; j++) { // Skip removed row
            tempBoard[index++] = Arrays.copyOf(board[j], COLUMNS);
        }

        // Copy tempBoard into board
        for (int k = 0; k < ROWS; k++) {
            board[k] = Arrays.copyOf(tempBoard[k], COLUMNS);
        }
    }



    public int updateScore() {
        switch (rowsCleared) {
            case 1 -> {return 50;}
            case 2 -> {return 150;}
            case 3 -> {return 300;}
            case 4 -> {return 500;}
        }
        return 0;
    }
}
