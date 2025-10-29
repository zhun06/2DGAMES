package com.zhun.tetris.pieces;

public class TPiece extends Piece {
    public TPiece() {
        coordinates = new int[4][2];
        coordinates[0] = new int[]{x, y-1};
        coordinates[1] = new int[]{x-1, y};
        coordinates[2] = new int[]{x, y};
        coordinates[3] = new int[]{x+1, y};

        shape = Shape.T;
    }

    @Override
    public Piece copy() {return new TPiece();}
}
