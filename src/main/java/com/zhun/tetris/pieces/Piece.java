package com.zhun.tetris.pieces;

import com.zhun.games.TetrisGame;

public abstract class Piece {
    protected int x = TetrisGame.startX;
    protected int y = TetrisGame.startY;
    protected int[][] coordinates;
    protected Shape shape;

    public Shape getShape() {return shape;}

    public int[][] getCoordinates() {return coordinates;}

    public abstract Piece copy(); // Returns new piece: L, Rl, T etc.

    public void setCoordinates(int[][] coordinates) {this.coordinates = coordinates;}

}
