package com.zhun.games;

import com.zhun.tetris.board.ClearRow;
import com.zhun.tetris.board.UpdateBoard;
import com.zhun.tetris.board.UpdateGhost;
import com.zhun.tetris.controls.*;
import com.zhun.tetris.pieces.Piece;
import com.zhun.tetris.pieces.PieceFactory;

import java.util.Arrays;

public class TetrisGame extends Game {
    // Grid & sizing
    public static final int BOX_SIZE = 25;
    public static final int CANVAS_WIDTH = 600;
    public static final int CANVAS_HEIGHT = 600;
    public static final int BOARD_WIDTH = 300;
    public static final int BOARD_HEIGHT = 600;
    public static final int PREVIEW_WIDTH = CANVAS_WIDTH - BOARD_WIDTH;
    public static final int PREVIEW_HEIGHT = BOARD_HEIGHT;
    public static final int BOARD_ROWS = BOARD_HEIGHT / BOX_SIZE;
    public static final int BOARD_COLUMNS = BOARD_WIDTH / BOX_SIZE;
    public static final int startX = BOARD_COLUMNS /2 - 1;
    public static final int startY = 3;

    // Game state
    private int score;
    private int highScore = 0;
    private final int[][] board = new int[BOARD_ROWS][BOARD_COLUMNS];
    private Piece currentPiece; // Store coordinates 4 x (x,y) and Shape
    private Piece nextPiece1;
    private Piece nextPiece2;
    private Piece nextPiece3;
    private Piece ghostPiece;
    private Key currentKey;
    private boolean gameOver;
    private boolean getNextPiece;
    public enum Key {NONE, UP, DOWN, LEFT, RIGHT, TAB}

    // Helpers
    private final UpdateGhost updateGhost = new UpdateGhost(board);
    private final UpdateBoard updateBoard = new UpdateBoard(board, this::setOnMerge, this::setOnGameOver);
    private final ClearRow clearRow = new ClearRow(board);
    private final Rotate rotator = new Rotate(board);
    private final SoftDrop softDrop = new SoftDrop(board);
    private final MoveLeft moveLeft = new MoveLeft(board);
    private final MoveRight moveRight = new MoveRight(board);
    private final HardDrop hardDrop = new HardDrop(board);
    private final PieceFactory pieceFactory = new PieceFactory(); // Creates new pieces -> update current

    // Initialize
    public void reset() {
        this.cleanBoard();
        currentKey = Key.NONE;
        score = 0;
        gameOver = false;
        getNextPiece = true;
        this.updatePiece();
    }

    public void updateBoard() {
        this.updatePiece(); // Get next piece
        updateBoard.update(currentPiece); // Move piece down || merge on collision
        clearRow.update(); // Clear row?
        score += clearRow.updateScore();
        if (score > highScore) highScore = score;
    }

    public void updateInput() {
        switch (currentKey) {
            case UP -> rotator.rotate(currentPiece);
            case DOWN -> softDrop.run(currentPiece);
            case LEFT -> moveLeft.run(currentPiece);
            case RIGHT -> moveRight.run(currentPiece);
            case TAB -> hardDrop.run(currentPiece);
        }
        currentKey = Key.NONE; // Reset
        updateGhost.update(currentPiece, ghostPiece); // Update ghost position
    }

    private void updatePiece() {
        if (getNextPiece) {
            if (currentPiece == null) {
                currentPiece = pieceFactory.createPiece();
                nextPiece1 = pieceFactory.createPiece();
                nextPiece2 = pieceFactory.createPiece();
                nextPiece3 = pieceFactory.createPiece();
            }
            else {
                currentPiece = nextPiece1;
                nextPiece1 = nextPiece2;
                nextPiece2 = nextPiece3;
                nextPiece3 = pieceFactory.createPiece();
            }
            ghostPiece = currentPiece.copy();
            getNextPiece = false;
        }
        System.out.println("Current Piece: " + Arrays.deepToString(currentPiece.getCoordinates()));
    }

    private void cleanBoard() {
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLUMNS; j++) {
                board[i][j] = 0;
            }
        }
    }

    // Setters
    public void setOnMerge() {
        System.out.println("In setOnMerge, Ready to get next piece.");
        getNextPiece = true;
    }
    public void setOnGameOver() {gameOver = true;}
    public void setKey(Key key) {this.currentKey = key;}

    // Getters
    public int[][] getBoard() { return board;}
    public Piece getCurrentPiece() { return currentPiece; }
    public Piece getGhostPiece() { return ghostPiece; }
    public Piece getNext1() { return nextPiece1; }
    public Piece getNext2() { return nextPiece2; }
    public Piece getNext3() { return nextPiece3; }
    public int getScore() { return score; }
    public int getHighScore() { return highScore; }
    public boolean isGameOver() { return gameOver; }
}
