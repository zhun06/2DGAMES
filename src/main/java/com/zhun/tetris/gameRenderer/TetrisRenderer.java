package com.zhun.tetris.gameRenderer;

import com.zhun.controllers.GameController;
import com.zhun.games.TetrisGame;
import com.zhun.managers.GameManager;
import com.zhun.tetris.pieces.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

// Grid & sizing
import static com.zhun.games.TetrisGame.*;


public abstract class TetrisRenderer {
    private final TetrisGame game;
    private final GraphicsContext gc;
    private int[][] currentCoordinates;
    private Shape currentShape;

    public abstract void render();

    public TetrisRenderer(TetrisGame game) {
        this.game = game;
        this.gc = GameManager.getGameCanvas().getGraphicsContext2D();
    }

    protected void renderBoard(Color topBgColor, Color bottBgColor, Color gridColor) {
        gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        // Board
        gc.setFill(topBgColor);
        for (int row = 0; row < startY + 1; row++) {
            for (int col = 0; col < BOARD_COLUMNS; col++) {
                gc.fillRect(col * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE);
            }
        }
        gc.setFill(bottBgColor);
        gc.setStroke(gridColor);
        gc.setLineWidth(1);
        for (int row = startY + 1; row < BOARD_ROWS; row++) {
            for (int col = 0; col < BOARD_COLUMNS; col++) {
                gc.fillRect(col * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE);
                gc.strokeRect(col * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE);
            }
        }
    }

    protected void renderPreviewBG(Color bgColor, Color textColor, Color outlineColor) {
        // Piece preview background
        gc.setFill(bgColor);
        gc.fillRect(BOARD_WIDTH, 0, PREVIEW_WIDTH, PREVIEW_HEIGHT);

        // Text
        String text = "NEXT:";
        Font font = Font.font("Outline Bold", 40);
        gc.setFill(textColor);
        gc.setStroke(outlineColor);
        gc.setLineWidth(1);
        gc.setFont(font);


        // Measure the text using a Text node
        Text temp = new Text(text);
        temp.setFont(font);
        double textWidth = temp.getLayoutBounds().getWidth();
        double textHeight = temp.getLayoutBounds().getHeight();

        // Center position
        double x = BOARD_WIDTH + (PREVIEW_WIDTH - textWidth) / 2;
        double y = textHeight + 2 * BOX_SIZE; // adjust for baseline

        gc.fillText(text, x, y);
        gc.strokeText(text, x, y);
    }

    protected void renderPreviewPieces(Color outlineColor) {
        // Preview pieces
        gc.setLineWidth(3);
        gc.setStroke(outlineColor);
        int offsetX = BOARD_WIDTH ;
        int offsetY1 = 4 * BOX_SIZE;
        int offsetY2 = 10 * BOX_SIZE;
        int offsetY3 = 16 * BOX_SIZE;

        // 1st next
        currentCoordinates = game.getNext1().getCoordinates();
        currentShape = game.getNext1().getShape();
        this.printPiece(offsetX, offsetY1);

        // 2nd next
        currentCoordinates = game.getNext2().getCoordinates();
        currentShape = game.getNext2().getShape();
        this.printPiece(offsetX, offsetY2);

        // 3rd next
        currentCoordinates = game.getNext3().getCoordinates();
        currentShape = game.getNext3().getShape();
        this.printPiece(offsetX, offsetY3);
    }

    protected void renderExistingPieces(Color outlineColor) {
        // Existing pieces
        gc.setStroke(outlineColor);
        gc.setLineWidth(3);
        gc.setStroke(Color.BLACK);
        int[][] board = game.getBoard();
        for (int row = 0; row < BOARD_ROWS; row++) {
            for (int col = 0; col < BOARD_COLUMNS; col++) {
                switch (board[row][col]) {
                    case 1 -> gc.setFill(Color.DARKGREEN);
                    case 2 -> gc.setFill(Color.DARKMAGENTA);
                    case 3 -> gc.setFill(Color.DARKTURQUOISE);
                    case 4 -> gc.setFill(Color.DARKRED);
                    case 5 -> gc.setFill(Color.DARKORANGE);
                    case 6 -> gc.setFill(Color.DARKBLUE);
                    case 7 -> gc.setFill(Color.DARKVIOLET);
                }
                if (board[row][col] != 0) {
                    gc.strokeRect(col * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE);
                    gc.fillRect(col * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE);
                }
            }
        }
    }

    protected void renderCurrentPiece(Color outlineColor) {
        // Current piece
        gc.setStroke(outlineColor);
        currentCoordinates = game.getCurrentPiece().getCoordinates();
        currentShape = game.getCurrentPiece().getShape();
        this.printPiece(0,0);
    }

    protected void renderGhostPiece(Color outlineColor) {
        // Ghost piece
        gc.setStroke(outlineColor);
        currentCoordinates = game.getGhostPiece().getCoordinates();
        currentShape = game.getCurrentPiece().getShape();
        this.printGhost();
    }

    protected void renderCanvasBorder(Color borderColor) {
        gc.setLineWidth(5);
        gc.setStroke(borderColor);
        gc.strokeRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        gc.strokeRect(BOARD_WIDTH, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    private void printPiece(int offsetX, int offsetY) {
        for (int[] coordinate : currentCoordinates) {
            switch (currentShape) {
                case L -> gc.setFill(Color.DARKGREEN);
                case RL -> gc.setFill(Color.DARKMAGENTA);
                case Z -> gc.setFill(Color.DARKTURQUOISE);
                case RZ -> gc.setFill(Color.DARKRED);
                case T -> gc.setFill(Color.DARKORANGE);
                case I -> gc.setFill(Color.DARKBLUE);
                case O -> gc.setFill(Color.DARKVIOLET);
            }
            gc.strokeRect(offsetX + coordinate[0] * BOX_SIZE, offsetY + coordinate[1] * BOX_SIZE, BOX_SIZE, BOX_SIZE);
            gc.fillRect(offsetX + coordinate[0] * BOX_SIZE, offsetY + coordinate[1] * BOX_SIZE, BOX_SIZE, BOX_SIZE);
        }
    }

    private void printGhost() {
        for (int[] coordinate : currentCoordinates) {
            switch (currentShape) {
                case L -> gc.setFill(Color.web("#90EE90", 0.4));
                case RL -> gc.setFill(Color.web("#DA70D6", 0.4));
                case Z -> gc.setFill(Color.web("#AFEEEE", 0.4));
                case RZ -> gc.setFill(Color.web("#FF7F7F", 0.4));
                case T -> gc.setFill(Color.web("#FFD580", 0.4));
                case I -> gc.setFill(Color.web("#87CEFA", 0.4));
                case O -> gc.setFill(Color.web("#D8BFD8", 0.4));
            }
            gc.strokeRect(coordinate[0] * BOX_SIZE, coordinate[1] * BOX_SIZE, BOX_SIZE, BOX_SIZE);
            gc.fillRect(coordinate[0] * BOX_SIZE, coordinate[1] * BOX_SIZE, BOX_SIZE, BOX_SIZE);
        }
    }

    protected void renderCurrentScore() {
        GameController.setCurrentScore(game.getScore());
        GameController.setHighScore(game.getHighScore());
    }

}
