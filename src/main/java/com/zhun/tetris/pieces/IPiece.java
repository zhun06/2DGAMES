package com.zhun.tetris.pieces;

public class IPiece extends Piece {
    public IPiece() {
        coordinates = new int[4][2];
        coordinates[0] = new int[]{x-1, y};
        coordinates[1] = new int[]{x, y};
        coordinates[2] = new int[]{x+1, y};
        coordinates[3] = new int[]{x+2, y};

        shape = Shape.I;
    }

    @Override
    public Piece copy() {return new IPiece();}
}
